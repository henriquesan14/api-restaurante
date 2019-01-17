package br.com.henrique.domain.enums;

public enum StatusMesa {

    DISPONIVEL(1,"Disponivel"),
    OCUPADA(2,"Ocupada");

    private int cod;
    private String descricao;

    StatusMesa(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusMesa toEnum(Integer cod){
        if(cod==null){
            return null;
        }

        for(StatusMesa s: StatusMesa.values()){
            if(cod.equals(s.getCod())){
                return s;
            }
        }

        throw new IllegalArgumentException("Id invalido: "+cod);
    }
}
