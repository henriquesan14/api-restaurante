package br.com.henrique.resources;

import br.com.henrique.DTO.PedidoDTO;
import br.com.henrique.domain.Pedido;
import br.com.henrique.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService pedidoService;

//    @RequestMapping(method = RequestMethod.GET)
//    public ResponseEntity<List<PedidoDTO>> findAll(){
//        List<Pedido> list = pedidoService.findAll();
//        List<PedidoDTO > listDto = list.stream().map(obj -> new PedidoDTO(obj)).collect(Collectors.toList());
//        return ResponseEntity.ok().body(listDto);
//    }

    @RequestMapping(method = RequestMethod.GET, value="/{id}")
    public ResponseEntity<Pedido> find(@PathVariable Long id){
        Pedido obj = pedidoService.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody Pedido obj){
        obj = pedidoService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<Page<PedidoDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPorPage", defaultValue = "24") Integer linesPorPage,
            @RequestParam(value = "orderBy", defaultValue = "data") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {

        Page<Pedido> list = pedidoService.findPage(page, linesPorPage, orderBy, direction);
        Page<PedidoDTO> listDto = list.map(obj -> new PedidoDTO(obj));
        return ResponseEntity.ok().body(listDto);
    }

    @RequestMapping(value="/count", method = RequestMethod.GET)
    public long pedidosDiario(){
        return pedidoService.pedidosDiario();
    }

    @RequestMapping(value="/total", method = RequestMethod.GET)
    public BigDecimal totalDiario(){
        return pedidoService.totalDiario();
    }

}
