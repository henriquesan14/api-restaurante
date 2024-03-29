package br.com.henrique.domain;

import br.com.henrique.domain.enums.TipoCategoria;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Preenchimento obrigatorio")
    private String nome;

    @NotNull(message = "Preenchimento obrigatorio")
    private Integer tipoCategoria;

    @JsonIgnore
    @OneToMany(mappedBy = "categoria")
    private List<Produto> produtos =  new ArrayList<>();

    public Categoria() {
    }

    public Categoria(Long id, String nome, TipoCategoria tipoCategoria) {
        this.id = id;
        this.nome = nome;
        this.tipoCategoria = tipoCategoria.getCod();
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
        return TipoCategoria.toEnum(tipoCategoria);
    }

    public void setTipoCategoria(TipoCategoria tipoCategoria) {
        this.tipoCategoria = tipoCategoria.getCod();
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
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
        Categoria other = (Categoria) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
