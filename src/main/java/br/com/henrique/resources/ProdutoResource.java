package br.com.henrique.resources;

import br.com.henrique.DTO.ProdutoDTO;
import br.com.henrique.domain.Produto;
import br.com.henrique.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;

//    @RequestMapping(method = RequestMethod.GET)
//    public ResponseEntity<List<ProdutoDTO>> findAll(){
//        List<Produto> list = produtoService.findAll();
//        List<ProdutoDTO> listDto = list.stream().map(obj -> new ProdutoDTO(obj)).collect(Collectors.toList());
//        return ResponseEntity.ok().body(listDto);
//    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<Produto> find(@PathVariable Long id){
        Produto obj = produtoService.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody Produto obj){
        obj = produtoService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody Produto obj, @PathVariable Long id){
        obj.setId(id);
        produtoService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id){
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<Page<Produto>> findPage(
            @RequestParam(value = "categoria") Long idCategoria,
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPorPage", defaultValue = "24") Integer linesPorPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Produto> list = produtoService.findByCategoria(idCategoria, nome, page, linesPorPage, orderBy, direction);
//        Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
        return ResponseEntity.ok().body(list);
    }

}
