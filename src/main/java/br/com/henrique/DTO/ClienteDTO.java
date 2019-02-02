package br.com.henrique.DTO;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class ClienteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotEmpty(message = "Preenchimento obrigatorio")
    private String nome;

    @NotEmpty(message = "Preenchimento obrigatorio")
    private String sobrenome;

    @CPF(message = "CPF inválido")
    @NotEmpty(message = "Preenchimento obrigatorio")
    private String cpf;

    @Column(unique = true)
    @NotEmpty(message = "Preenchimento obrigatorio")
    @Email(message= "Email inválido")
    private String email;

    @NotEmpty(message = "Preenchimento obrigatorio")
    @Length(min= 11, max=14, message = "Tamanho deve ser no minimo 11 caracteres")
    private String telefone;


    @NotEmpty(message = "Preenchimento obrigatorio")
    @Length(min= 6, message = "Tamanho deve ser no minimo 6 caracteres")
    private String senha;

    @NotEmpty(message = "Preenchimento obrigatorio")
    @Length(min= 6, message = "Tamanho deve ser no minimo 6 caracteres")
    private String confirmSenha;


    @NotEmpty(message = "Preenchimento obrigatorio")
    private String logradouro;
    @NotEmpty(message = "Preenchimento obrigatorio")
    private String numero;
    @NotEmpty(message = "Preenchimento obrigatorio")
    private String bairro;

    private String complemento;

    @NotEmpty(message = "Preenchimento obrigatorio")
    @Length(min= 9, max=9, message = "Tamanho deve ser no minimo 9 caracteres")
    private String cep;

    public ClienteDTO() {
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


    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmSenha() {
        return confirmSenha;
    }

    public void setConfirmSenha(String confirmSenha) {
        this.confirmSenha = confirmSenha;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}

