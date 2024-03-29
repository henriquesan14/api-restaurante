package br.com.henrique.domain.enums;

public enum StatusPedido {

    PENDENTE(1, "Pendente"),
    PAGO(2, "Pago");

    private int cod;
    private String descricao;

    StatusPedido(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusPedido toEnum(Integer cod){
        if(cod == null){
            return null;
        }

        for(StatusPedido s: StatusPedido.values()){
            if(cod.equals(s.getCod())){
                return s;
            }
        }
        throw new IllegalArgumentException("Id invalido: "+cod);
    }
}
