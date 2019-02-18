package br.com.henrique.DTO;

import br.com.henrique.domain.Pedido;
import br.com.henrique.domain.PedidoComum;
import br.com.henrique.domain.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PedidoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date data;
    private String status;
    private String mesa;
    private String cliente;
    private BigDecimal valorTotal;
    private String tipo;

    public PedidoDTO() {
    }

    public PedidoDTO(Pedido obj){
        this.id = obj.getId();
        this.data = obj.getData();
        this.status  = obj.getStatus().getDescricao();
        this.cliente = (obj.getCliente()==null) ? null : obj.getCliente().getNome();
        if(obj instanceof PedidoComum){
            PedidoComum ped = (PedidoComum) obj;
            this.mesa = ped.getMesa().getNome();
            this.tipo = "Comum";
        }else{
            this.tipo = "Delivery";
        }
        this.valorTotal = obj.getValorTotal();
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

    public String getStatus() {
        return status;
    }


    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
