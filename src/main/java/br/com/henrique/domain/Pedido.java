package br.com.henrique.domain;

import br.com.henrique.domain.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Inheritance(strategy =InheritanceType.SINGLE_TABLE)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public abstract class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date data;

    private Integer status;


    @JsonIgnoreProperties("pedidos")
    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Usuario cliente;


    @NotEmpty
    @NotNull(message = "Preenchimento obrigatorio")
    @OneToMany(mappedBy = "id.pedido", cascade = CascadeType.ALL)
    private Set<ItemPedido> itens = new HashSet<>();

    private BigDecimal valorTotal;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<Pagamento> pagamentos = new ArrayList<>();


    public Pedido() {
    }

    public Pedido(Long id, Date data, Usuario cliente) {
        this.id = id;
        this.data = data;
        this.status = 1;
        this.cliente = cliente;
    }

    public void calculaTotal(){
        BigDecimal soma = BigDecimal.ZERO;
        for(ItemPedido ip: itens){
            soma = soma.add(ip.getSubTotal());
        }
        this.valorTotal = soma;
    }

    public boolean calculaPagamento(){
        BigDecimal valorRecebido = BigDecimal.ZERO;
        for(Pagamento p: pagamentos){
            valorRecebido = valorRecebido.add(p.getValorRecebido());
        }
        if(valorRecebido.compareTo(valorTotal) >= 0){
            status = 2;
            return true;
        }
        return false;
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

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }


    public Set<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(Set<ItemPedido> itens) {
        this.itens = itens;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
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
