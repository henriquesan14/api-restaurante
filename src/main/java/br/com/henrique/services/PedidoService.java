package br.com.henrique.services;

import br.com.henrique.domain.Pedido;
import br.com.henrique.repositories.PedidoRepository;
import br.com.henrique.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

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
        Optional<Pedido> obj = pedidoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: "+id+", Tipo: "+Pedido.class.getName()));
    }
}
