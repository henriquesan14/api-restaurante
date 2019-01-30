package br.com.henrique.DTO;

import br.com.henrique.domain.Categoria;
import br.com.henrique.domain.enums.TipoCategoria;

import java.io.Serializable;

public class CategoriaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private TipoCategoria tipoCategoria;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Categoria obj) {
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.tipoCategoria = obj.getTipoCategoria();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoCategoria getTipoCategoria() {
        return tipoCategoria;
    }

    public void setTipoCategoria(TipoCategoria tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }
}
