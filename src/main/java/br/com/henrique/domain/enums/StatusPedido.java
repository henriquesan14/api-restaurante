package br.com.henrique.domain.enums;

public enum StatusPedido {

    PENDENTE(1, "Pendente"),
    PRONTO(2, "Pronto"),
    ENTREGUE(3, "Entregue"),
    PAGO(4, "Pago");

    private int cod;
    private String nome;

    StatusPedido(int cod, String nome) {
        this.cod = cod;
        this.nome = nome;
    }

    public int getCod() {
        return cod;
    }

    public String getNome() {
        return nome;
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
