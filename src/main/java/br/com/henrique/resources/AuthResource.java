package br.com.henrique.resources;


import br.com.henrique.DTO.ClienteDTO;
import br.com.henrique.domain.Usuario;
import br.com.henrique.security.JWTUtil;
import br.com.henrique.security.UserSS;
import br.com.henrique.services.UserService;
import br.com.henrique.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers","Authorization");
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Void> register(@Valid @RequestBody ClienteDTO objDto, HttpServletResponse response){
        Usuario usuario =  usuarioService.fromDto(objDto);
        usuarioService.insert(usuario);
        String token = jwtUtil.generateToken(usuario.getEmail());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers","Authorization");
        return ResponseEntity.ok().build();
    }


}
