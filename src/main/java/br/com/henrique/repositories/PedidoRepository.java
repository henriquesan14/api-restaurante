package br.com.henrique.repositories;

import br.com.henrique.domain.Pedido;
import br.com.henrique.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT p FROM Pedido p WHERE p.cliente.id=?1")
    Page<Pedido> findByCliente(Long id, Pageable pageRequest);
}
