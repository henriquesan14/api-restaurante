package br.com.henrique.DTO;

import br.com.henrique.domain.Usuario;

import java.io.Serializable;

public class UsuarioDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String nome;
    private String sobrenome;
    private String cpf;
    private String email;
    private String telefone;

    public UsuarioDTO() {
    }
    public UsuarioDTO(Usuario obj){
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.sobrenome = obj.getSobrenome();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.telefone = obj.getTelefone();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
