package br.com.henrique.domain.enums;

public enum Perfil {

    ADMIN(1,"ROLE_ADMIN"),
    GARCOM(2,"ROLE_GARCOM"),
    COZINHEIRO(3,"ROLE_COZINHEIRO"),
    CLIENTE(4,"ROLE_CLIENTE");

    private int cod;
    private String descricao;

    Perfil(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Perfil toEnum(Integer cod){
        if(cod == null){
            return null;
        }

        for(Perfil t: Perfil.values()){
            if(cod.equals(t.getCod())){
                return t;
            }
        }

        throw new IllegalArgumentException("Id Invalido: " + cod);
    }
}
