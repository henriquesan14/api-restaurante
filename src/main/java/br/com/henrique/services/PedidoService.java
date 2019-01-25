package br.com.henrique.services;

import br.com.henrique.domain.Pedido;
import br.com.henrique.domain.Usuario;
import br.com.henrique.domain.enums.Perfil;
import br.com.henrique.repositories.PedidoRepository;
import br.com.henrique.security.UserSS;
import br.com.henrique.services.exceptions.AuthorizationException;
import br.com.henrique.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<Pedido> findAll(){
        return pedidoRepository.findAll();
    }

    public Pedido insert(Pedido obj){
        obj.setId(null);
        obj.setData(new Date());
        obj.getItens().parallelStream().forEach(i -> i.setPedido(obj));
        return pedidoRepository.save(obj);
    }

    public Pedido find(Long id){
        UserSS user= UserService.authenticated();
        if(user.isCliente() && !id.equals(user.getId()) ){
            throw new AuthorizationException("Acesso negado");
        }

        Optional<Pedido> obj = pedidoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: "+id+", Tipo: "+Pedido.class.getName()));
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
}
