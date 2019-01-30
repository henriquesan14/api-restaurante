package br.com.henrique.repositories;

import br.com.henrique.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Transactional(readOnly=true)
    @Query("SELECT DISTINCT p FROM Produto p WHERE p.categoria.id=?1 AND UPPER(p.nome) LIKE UPPER(?2)")
    Page<Produto> findByNomeAndCategoria(Long idCategoria, String nome, Pageable pageRequest);

    @Transactional(readOnly=true)
    @Query("SELECT DISTINCT p FROM Produto p WHERE UPPER(p.nome) LIKE UPPER(?1)")
    Page<Produto> findByNome(String nome, Pageable pageRequest);

}
