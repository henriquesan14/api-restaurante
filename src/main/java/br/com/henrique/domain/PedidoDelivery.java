package br.com.henrique.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@JsonTypeName("pedidoDelivery")
public class PedidoDelivery extends Pedido {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name="endereco_id")
    @NotNull(message = "Preenchimento obrigatorio")
    private Endereco enderecoEntrega;

    public PedidoDelivery() {
    }

    public PedidoDelivery(Long id, Date data, Usuario cliente, @NotNull(message = "Preenchimento obrigatorio") Endereco enderecoEntrega) {
        super(id, data, cliente);
        this.enderecoEntrega = enderecoEntrega;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }
}
