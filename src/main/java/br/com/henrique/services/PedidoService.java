package br.com.henrique.services;

import br.com.henrique.domain.ItemPedido;
import br.com.henrique.domain.Pedido;
import br.com.henrique.domain.Usuario;
import br.com.henrique.domain.enums.Perfil;
import br.com.henrique.domain.enums.StatusPedido;
import br.com.henrique.repositories.PedidoRepository;
import br.com.henrique.security.UserSS;
import br.com.henrique.services.exceptions.AuthorizationException;
import br.com.henrique.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProdutoService produtoService;


    public List<Pedido> findAll(){
        return pedidoRepository.findAll();
    }

    public Pedido insert(Pedido obj){
        Usuario us = usuarioService.find(UserService.authenticated().getId());
        obj.setId(null);
        obj.setData(new Date());
        obj.setFuncionario(us);
        obj.setStatus(StatusPedido.PENDENTE);
        obj.getItens().forEach(x -> {
            x.setPedido(obj);
            x.setStatusItem(1);
            x.setProduto(produtoService.find(x.getProduto().getId()));
        });
        obj.calculaTotal();
        return pedidoRepository.save(obj);
    }

    public Pedido find(Long id){
        UserSS user= UserService.authenticated();
        Pedido obj = pedidoRepository.findById(id).orElseThrow( () -> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+", Tipo: "+Pedido.class.getName()));
        if(user.isCliente() && !obj.getCliente().getId().equals(user.getId()) ) {
            throw new AuthorizationException("Acesso negado");
        }
        return obj;
    }



    public Page<Pedido> findPage(Integer page, Integer linesPorPage, String orderBy, String direction){
        UserSS user = UserService.authenticated();
        if(user == null){
            throw new AuthorizationException("Acesso negado");
        }else if(user.hasRole(Perfil.ADMIN) || user.hasRole(Perfil.GARCOM) || user.hasRole(Perfil.COZINHEIRO)){
            PageRequest pageRequest = PageRequest.of(page, linesPorPage, Sort.Direction.valueOf(direction), orderBy);
            return pedidoRepository.findAll(pageRequest);
        }
        PageRequest pageRequest = PageRequest.of(page, linesPorPage, Sort.Direction.valueOf(direction), orderBy);
        return pedidoRepository.findByCliente(user.getId(),pageRequest);
    }

    public long countPedidosDiario() {
        LocalDate data = LocalDate.now();
        return pedidoRepository.countPedidosDiario(data);
    }

    public long countItensDiario() {
        LocalDate data = LocalDate.now();
        return pedidoRepository.countItensDiario(data);
    }

    public List<ItemPedido> itensDiario(){
        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            System.out.println(sdf.format(sdf.parse(sdf.format(data))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            return pedidoRepository.itensDiario(sdf.parse(sdf.format(data)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Pedido> pedidosDiario(){
        LocalDate data = LocalDate.now();
        return pedidoRepository.pedidosDiario(data);
    }


    public BigDecimal totalDiario(){
        LocalDate data = LocalDate.now();
        return pedidoRepository.totalDiario(data);
    }

    public List<ItemPedido> itensByStatus(Integer status){
        return pedidoRepository.itensByStatusItem(status);
    }

    public long countItensByStatusItem(Integer status){
        return pedidoRepository.countItensByStatusItem(status);
    }

    public void updateStatusItem(Integer status, Long idPedido, Long idProduto){
        pedidoRepository.updateStatusItem(status, idPedido, idProduto);
    }

}
