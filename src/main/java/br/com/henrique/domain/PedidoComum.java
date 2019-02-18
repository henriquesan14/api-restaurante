package br.com.henrique.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@JsonTypeName("pedidoComum")
public class PedidoComum extends Pedido {
    private static final long serialVersionUID = 1L;

    @JsonIgnoreProperties("pedidos")
    @ManyToOne
    @JoinColumn(name="mesa_id")
    @NotNull(message = "Preenchimento obrigatorio")
    private Mesa mesa;

    @JsonIgnoreProperties("pedidos")
    @ManyToOne
    @JoinColumn(name="funcionario_id")
    private Usuario funcionario;

    public PedidoComum() {
    }

    public PedidoComum(Long id, Date data, Usuario cliente, @NotNull(message = "Preenchimento obrigatorio") Mesa mesa, Usuario funcionario) {
        super(id, data, cliente);
        this.mesa = mesa;
        this.funcionario = funcionario;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Usuario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Usuario funcionario) {
        this.funcionario = funcionario;
    }
}
