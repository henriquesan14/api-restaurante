package br.com.henrique.services;

import br.com.henrique.DTO.UsuarioNewDTO;
import br.com.henrique.domain.Endereco;
import br.com.henrique.domain.Usuario;
import br.com.henrique.domain.enums.Perfil;
import br.com.henrique.repositories.UsuarioRepository;
import br.com.henrique.security.UserSS;
import br.com.henrique.services.exceptions.AuthorizationException;
import br.com.henrique.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Usuario find(Long id){
        UserSS user= UserService.authenticated();

        if(user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())){
            throw new AuthorizationException("Acesso negado");
        }

        Optional<Usuario> obj = usuarioRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: "+id+", Tipo: "+Usuario.class.getName()));
    }

    public Usuario insert(Usuario obj){
        obj.setId(null);
        return usuarioRepository.save(obj);
    }

    public Usuario fromDto(UsuarioNewDTO objDto){
        Usuario us = new Usuario(null, objDto.getNome(),objDto.getSobrenome(),objDto.getCpf(),objDto.getEmail(),objDto.getTelefone(), pe.encode(objDto.getSenha()));
        Endereco end = new Endereco(null, objDto.getLogradouro(),objDto.getNumero(),objDto.getComplemento(),objDto.getBairro(),objDto.getCep(),us);
        us.getEnderecos().add(end);
        us.addPerfil(Perfil.toEnum(objDto.getPerfil()));
        return us;
    }
}
