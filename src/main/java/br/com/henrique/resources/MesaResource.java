package br.com.henrique.resources;

import br.com.henrique.DTO.MesaDTO;
import br.com.henrique.domain.Mesa;
import br.com.henrique.services.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/mesas")
public class MesaResource {

    @Autowired
    public MesaService mesaService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<MesaDTO>> findAll(){
        List<Mesa> list = mesaService.findAll();
        List<MesaDTO> listDto = list.stream().map(obj -> new MesaDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public ResponseEntity<Mesa> find(@PathVariable Long id){
        Mesa obj = mesaService.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody Mesa obj){
        obj =  mesaService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Mesa obj){
        obj.setId(id);
        mesaService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id){
        mesaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Void> updateStatus(@RequestParam(value="value") Integer status, @PathVariable Long id){
        mesaService.updateStatus(status, id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/status", method = RequestMethod.GET)
    public ResponseEntity<List<Mesa>> findByStatus(@RequestParam Integer status){
        List<Mesa> list = mesaService.findStatus(status);
        return ResponseEntity.ok().body(list);
    }
}
