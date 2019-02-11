package br.com.henrique.domain.enums;

public enum StatusItem {

    PENDENTE(1, "Pendente"),
    PRONTO(2, "Pronto"),
    ENTREGUE(3, "Entregue");


    private int cod;
    private String descricao;

    StatusItem(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusItem toEnum(Integer cod){
        if(cod == null){
            return null;
        }

        for(StatusItem s: StatusItem.values()){
            if(cod.equals(s.getCod())){
                return s;
            }
        }
        throw new IllegalArgumentException("Id invalido: "+cod);
    }
}
