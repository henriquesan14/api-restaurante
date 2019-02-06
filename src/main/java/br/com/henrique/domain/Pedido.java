package br.com.henrique.domain;

import br.com.henrique.domain.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date data;

    private Integer status;

    @JsonIgnoreProperties("pedidos")
    @ManyToOne
    @JoinColumn(name="mesa_id")
    @NotNull(message = "Preenchimento obrigatorio")
    private Mesa mesa;

    @JsonIgnoreProperties("pedidos")
    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Usuario cliente;

    @JsonIgnoreProperties("pedidos")
    @ManyToOne
    @JoinColumn(name="funcionario_id")
    private Usuario funcionario;

    @NotNull(message = "Preenchimento obrigatorio")
    @OneToMany(mappedBy = "id.pedido", cascade = CascadeType.ALL)
    private Set<ItemPedido> itens = new HashSet<>();

    public Pedido() {
    }

    public Pedido(Long id, Date data, Mesa mesa, Usuario cliente, Usuario funcionario) {
        this.id = id;
        this.data = data;
        this.status = 1;
        this.mesa = mesa;
        this.cliente = cliente;
        this.funcionario = funcionario;
    }

    public BigDecimal getValorTotal(){
        BigDecimal soma = BigDecimal.ZERO;
        for(ItemPedido ip: itens){
            soma = soma.add(ip.getSubTotal());
        }
        return soma;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public StatusPedido getStatus() {
        return StatusPedido.toEnum(status);
    }

    public void setStatus(StatusPedido status) {
        this.status = status.getCod();
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Usuario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Usuario funcionario) {
        this.funcionario = funcionario;
    }

    public Set<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(Set<ItemPedido> itens) {
        this.itens = itens;
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
        Pedido other = (Pedido) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
