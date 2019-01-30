package br.com.henrique.services;

import br.com.henrique.DTO.NewSenhaDTO;
import br.com.henrique.DTO.UsuarioNewDTO;
import br.com.henrique.domain.Endereco;
import br.com.henrique.domain.Usuario;
import br.com.henrique.domain.enums.Perfil;
import br.com.henrique.repositories.EnderecoRepository;
import br.com.henrique.repositories.UsuarioRepository;
import br.com.henrique.security.UserSS;
import br.com.henrique.services.exceptions.AuthorizationException;
import br.com.henrique.services.exceptions.ObjectNotFoundException;
import br.com.henrique.services.exceptions.PasswordInvalidException;
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

    @Autowired
    private EnderecoRepository enderecoRepository;

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

    public Usuario findByEmail(String email) {
        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
            throw new AuthorizationException("Acesso negado");
        }
        Usuario obj = usuarioRepository.findByEmail(email);
        if (obj == null) {
            throw new ObjectNotFoundException(
                    "Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Usuario.class.getName());
        }
        return obj;
    }

    public Usuario insert(Usuario obj){
        obj.setId(null);
        return usuarioRepository.save(obj);
    }

    public Usuario updatePassword(NewSenhaDTO objDto){
        UserSS user = UserService.authenticated();
        Usuario usuario = usuarioRepository.findByEmail(user.getUsername());
        if(user==null){
            throw new AuthorizationException("Acesso negado");
        }
        if(!objDto.getNovaSenha().equals(objDto.getConfirmSenha())){
            throw new PasswordInvalidException("Senha e confirmação de senha não conferem");
        }
        if(!pe.matches(objDto.getSenhaAtual(), user.getPassword())) {
            throw new PasswordInvalidException("Senha atual inválida");
        }
        if(pe.matches(objDto.getNovaSenha(), user.getPassword())){
            throw new PasswordInvalidException("Nova senha não pode ser igual a atual");
        }
        usuario.setSenha(pe.encode(objDto.getNovaSenha()));
        return usuarioRepository.save(usuario);
    }
    public Endereco findEndereco(Long idUsuario, Long idEndereco){
        UserSS user= UserService.authenticated();

        if(user==null || !user.hasRole(Perfil.ADMIN) && !idUsuario.equals(user.getId())){
            throw new AuthorizationException("Acesso negado");
        }
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new ObjectNotFoundException("Objeto não encontrado! Id: " + idUsuario + ", Tipo: " + Usuario.class.getName()));
        Endereco end = enderecoRepository.findByIdAndUsuario(idEndereco,idUsuario).orElseThrow(
                () -> new ObjectNotFoundException("Objeto não encontrado! Id: " + idEndereco + ", Tipo: " + Endereco.class.getName()));
        return end;
    }

    public void deleteEndereco(Long idUsuario, Long idEndereco){
        UserSS user= UserService.authenticated();

        if(user==null || !user.hasRole(Perfil.ADMIN) && !idUsuario.equals(user.getId())){
            throw new AuthorizationException("Acesso negado");
        }
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new ObjectNotFoundException("Objeto não encontrado! Id: " + idUsuario + ", Tipo: " + Usuario.class.getName()));
        Endereco end = enderecoRepository.findByIdAndUsuario(idEndereco,idUsuario).orElseThrow(
                () -> new ObjectNotFoundException("Objeto não encontrado! Id: " + idEndereco + ", Tipo: " + Endereco.class.getName()));
        enderecoRepository.deleteById(end.getId());
    }

    public Endereco insertEndereco(Long idUsuario, Endereco obj){
        UserSS user= UserService.authenticated();

        if(user==null || !user.hasRole(Perfil.ADMIN) && !idUsuario.equals(user.getId())){
            throw new AuthorizationException("Acesso negado");
        }
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new ObjectNotFoundException("Objeto não encontrado! Id: " + idUsuario + ", Tipo: " + Usuario.class.getName()));
        obj.setId(null);
        obj.setUsuario(usuario);
        return enderecoRepository.save(obj);
    }

    public Endereco updateEndereco(Long idUsuario, Long idEndereco, Endereco obj){
        UserSS user= UserService.authenticated();

        if(user==null || !user.hasRole(Perfil.ADMIN) && !idUsuario.equals(user.getId())){
            throw new AuthorizationException("Acesso negado");
        }
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new ObjectNotFoundException("Objeto não encontrado! Id: " + idUsuario + ", Tipo: " + Usuario.class.getName()));
        Endereco end = enderecoRepository.findById(idEndereco).orElseThrow(
                () -> new ObjectNotFoundException("Objeto não encontrado! Id: " + obj.getId() + ", Tipo: " + Endereco.class.getName()));
        obj.setId(idEndereco);
        obj.setUsuario(usuario);
        return enderecoRepository.save(obj);
    }

    public Usuario fromDto(UsuarioNewDTO objDto){
        Usuario us = new Usuario(null, objDto.getNome(),objDto.getSobrenome(),objDto.getCpf(),objDto.getEmail(),objDto.getTelefone(), pe.encode(objDto.getSenha()));
        Endereco end = new Endereco(null,objDto.getLogradouro(),objDto.getNumero(),objDto.getBairro(),objDto.getComplemento(),objDto.getCep(),us);
        us.getEnderecos().add(end);
        us.addPerfil(Perfil.toEnum(objDto.getPerfil()));
        return us;
    }



}
