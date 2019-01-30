package br.com.henrique.DTO;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class NewSenhaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String senhaAtual;

    @NotEmpty(message = "Preenchimento obrigatorio")
    @Length(min=6, message = "A senha requer no minimo 6 caracteres")
    private String novaSenha;

    @NotEmpty(message = "Preenchimento obrigatorio")
    @Length(min=6, message = "A senha requer no minimo 6 caracteres")
    private String confirmSenha;

    public NewSenhaDTO() {
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getConfirmSenha() {
        return confirmSenha;
    }

    public void setConfirmSenha(String confirmSenha) {
        this.confirmSenha = confirmSenha;
    }
}
