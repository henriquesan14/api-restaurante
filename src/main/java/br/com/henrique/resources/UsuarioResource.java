package br.com.henrique.resources;

import br.com.henrique.DTO.NewSenhaDTO;
import br.com.henrique.DTO.UsuarioDTO;
import br.com.henrique.DTO.UsuarioNewDTO;
import br.com.henrique.domain.Endereco;
import br.com.henrique.domain.Usuario;
import br.com.henrique.domain.enums.Perfil;
import br.com.henrique.security.JWTUtil;
import br.com.henrique.security.UserSS;
import br.com.henrique.services.UserService;
import br.com.henrique.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UsuarioDTO>> findAll(){
        List<Usuario> list = usuarioService.findAll();
        List<UsuarioDTO> listDto = list.stream().map(obj -> new UsuarioDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Usuario> find(@PathVariable Long id){
        Usuario obj = usuarioService.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value="/email", method=RequestMethod.GET)
    public ResponseEntity<Usuario> find(@RequestParam(value="value") String email) {
        Usuario obj = usuarioService.findByEmail(email);
        return ResponseEntity.ok().body(obj);
    }


    @RequestMapping(value="/cliente", method =RequestMethod.GET)
    public ResponseEntity<List<Usuario>> findLikeEmail(@RequestParam(value="email") String email){
        List<Usuario> list = usuarioService.findLikeEmail(email);
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value="/{idUsuario}/enderecos/{idEndereco}", method = RequestMethod.GET)
    public ResponseEntity<Endereco> findEndereco(@PathVariable Long idUsuario, @PathVariable Long idEndereco){
        Endereco obj = usuarioService.findEndereco(idUsuario, idEndereco);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value="/{id}", method= RequestMethod.PUT)
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody UsuarioDTO objDto, HttpServletResponse response){
        Usuario obj = usuarioService.fromDto(objDto);
        usuarioService.update(id, obj);
        UserSS user = UserService.authenticated();
        if(user.getId().equals(id) && !user.getUsername().equals(objDto.getEmail())) {
            String token = jwtUtil.generateToken(objDto.getEmail());
            response.addHeader("Authorization", "Bearer " + token);
            response.addHeader("access-control-expose-headers", "Authorization");
        }
        return ResponseEntity.noContent().build();
    }


    @RequestMapping(value="/password", method = RequestMethod.PUT)
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody NewSenhaDTO objDto){
        usuarioService.updatePassword(objDto);
        return ResponseEntity.noContent().build();
    }
    

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioNewDTO objDto){
        Usuario obj = usuarioService.fromDto(objDto);
        obj = usuarioService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value="/enderecos", method = RequestMethod.GET)
    public ResponseEntity<List<Endereco>> findByUsuario(@RequestParam(required = false) Long idUsuario){
        List<Endereco> list = usuarioService.findByUsuario(idUsuario);
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value="/{id}/enderecos", method = RequestMethod.POST)
    public ResponseEntity<Void> insertEndereco(@PathVariable Long id,@Valid @RequestBody Endereco obj){
        Endereco end = usuarioService.insertEndereco(id, obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(end.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value="/{idUsuario}/enderecos/{idEndereco}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateEndereco(@PathVariable Long idUsuario, @PathVariable Long idEndereco,@Valid @RequestBody Endereco obj){
        usuarioService.updateEndereco(idUsuario, idEndereco, obj);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{idUsuario}/enderecos/{idEndereco}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteEndereco(@PathVariable Long idUsuario, @PathVariable Long idEndereco){
        usuarioService.deleteEndereco(idUsuario, idEndereco);
        return ResponseEntity.noContent().build();
    }
}
