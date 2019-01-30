package br.com.henrique.DTO;

import br.com.henrique.domain.Mesa;
import br.com.henrique.domain.enums.StatusMesa;

import java.io.Serializable;

public class MesaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private StatusMesa status;

    public MesaDTO() {
    }

    public MesaDTO(Mesa obj){
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.status = obj.getStatus();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public StatusMesa getStatus() {
        return status;
    }
}
