package br.com.henrique.resources;

import br.com.henrique.DTO.PedidoDTO;
import br.com.henrique.domain.ItemPedido;
import br.com.henrique.domain.Pagamento;
import br.com.henrique.domain.Pedido;
import br.com.henrique.domain.statistics.PedidoStatistics;
import br.com.henrique.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

    @PreAuthorize("hasAnyRole('ADMIN','COZINHEIRO','GARCOM')")
    @RequestMapping(value="/status", method=RequestMethod.GET)
    public ResponseEntity<Page<PedidoDTO>> findByStatus(@RequestParam Integer status,
                                                     @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                     @RequestParam(value = "linesPorPage", defaultValue = "24") Integer linesPorPage,
                                                     @RequestParam(value = "orderBy", defaultValue = "data") String orderBy,
                                                     @RequestParam(value = "direction", defaultValue = "DESC") String direction){
        Page<Pedido> list = pedidoService.findByStatus(status, page, linesPorPage, orderBy, direction);
        Page<PedidoDTO> listDto = list.map(obj -> new PedidoDTO(obj));
        return ResponseEntity.ok().body(listDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN','COZINHEIRO','GARCOM')")
    @RequestMapping(value="/now/count", method = RequestMethod.GET)
    public long countPedidosDiario(){
        return pedidoService.countPedidosDiario();
    }

    @PreAuthorize("hasAnyRole('ADMIN','COZINHEIRO','GARCOM')")
    @RequestMapping(value="/now", method = RequestMethod.GET)
    public ResponseEntity<Page<PedidoDTO>> pedidosDiario(
                                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                         @RequestParam(value = "linesPorPage", defaultValue = "24") Integer linesPorPage,
                                                         @RequestParam(value = "orderBy", defaultValue = "data") String orderBy,
                                                         @RequestParam(value = "direction", defaultValue = "DESC") String direction){
        Page<Pedido> list = pedidoService.pedidosDiario(page, linesPorPage, orderBy, direction);
        Page<PedidoDTO> listDto = list.map(obj -> new PedidoDTO(obj));
        return ResponseEntity.ok().body(listDto);
    }


    @PreAuthorize("hasAnyRole('ADMIN','COZINHEIRO','GARCOM')")
    @RequestMapping(value="/itens/count", method = RequestMethod.GET)
    public long countItensDiario(){
        return pedidoService.countItensDiario();
    }

    @PreAuthorize("hasAnyRole('ADMIN','COZINHEIRO','GARCOM')")
    @RequestMapping(value="/itens/now", method = RequestMethod.GET)
    public ResponseEntity<List<ItemPedido>> itensDiario(){
        List<ItemPedido> list = pedidoService.itensDiario();
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN','COZINHEIRO','GARCOM')")
    @RequestMapping(value="/total", method = RequestMethod.GET)
    public BigDecimal totalDiario(){
        return pedidoService.totalDiario();
    }

    @PreAuthorize("hasAnyRole('ADMIN','COZINHEIRO','GARCOM')")
    @RequestMapping(value="/itens", method = RequestMethod.GET)
    public List<ItemPedido> demandas(@RequestParam Integer status){
        return pedidoService.itensByStatus(status);
    }

    @PreAuthorize("hasAnyRole('ADMIN','COZINHEIRO','GARCOM')")
    @RequestMapping(value="/itens/demandas", method = RequestMethod.GET)
    public long countItensByStatusItem(@RequestParam Integer status){
        return pedidoService.countItensByStatusItem(status);
    }

    @RequestMapping(value="/itens", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateStatusItem(@RequestBody Integer status, @RequestParam Long idPedido, @RequestParam Long idProduto){
        pedidoService.updateStatusItem(status, idPedido, idProduto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value="/statistics", method=RequestMethod.GET)
    public ResponseEntity<List<PedidoStatistics>> pedidoStatistic(){
        List<PedidoStatistics> list =pedidoService.pedidoStatistics();
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN','COZINHEIRO','GARCOM')")
    @RequestMapping(value="/{id}/pagamentos", method = RequestMethod.POST)
    public ResponseEntity<Void> addPagamento(@PathVariable Long id, @Valid @RequestBody Pagamento obj){
        Pagamento pag = pedidoService.addPagamento(id, obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(pag.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
