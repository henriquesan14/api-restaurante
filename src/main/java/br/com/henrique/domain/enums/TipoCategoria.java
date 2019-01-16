package br.com.henrique.domain.enums;

public enum TipoCategoria {

    REFEICAO(1,"Refeição"),
    BEBIDA(2,"Bebida"),
    SOBREMESA(3,"Sobremesa");

    private Integer cod;
    private String descricao;

    TipoCategoria(Integer cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public Integer getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoCategoria toEnum(Integer cod){
        if(cod == null){
            return null;
        }

        for(TipoCategoria t: TipoCategoria.values()){
            if(cod.equals(t.getCod())){
                return t;
            }
        }
        throw new IllegalArgumentException("Id invalido: "+cod);
    }
}
