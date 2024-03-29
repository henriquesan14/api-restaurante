package br.com.henrique.domain;

import br.com.henrique.domain.enums.StatusMesa;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Mesa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Preenchimento obrigatorio")
    private String nome;

    private Integer status;

    @JsonIgnore
    @OneToMany(mappedBy = "mesa")
    private List<PedidoComum> pedidos = new ArrayList<>();

    public Mesa() {
    }

    public Mesa(Long id, String nome) {
        this.id = id;
        this.nome = nome;
        this.status = StatusMesa.DISPONIVEL.getCod();
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

    public StatusMesa getStatus() {
        return StatusMesa.toEnum(status);
    }

    public void setStatus(StatusMesa status) {

        this.status = status.getCod();
    }

    public List<PedidoComum> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<PedidoComum> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Mesa other = (Mesa) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
