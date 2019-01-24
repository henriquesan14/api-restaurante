package br.com.henrique.DTO;

import br.com.henrique.domain.Pedido;
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
    private String funcionario;
    private BigDecimal valorTotal;

    public PedidoDTO() {
    }

    public PedidoDTO(Pedido obj){
        this.id = obj.getId();
        this.data = obj.getData();
        this.status  = obj.getStatus().getDescricao();
        this.mesa = obj.getMesa().getNome();
        this.cliente = obj.getCliente().getNome();
        this.funcionario = obj.getFuncionario().getNome();
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

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
