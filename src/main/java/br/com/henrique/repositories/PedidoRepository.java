package br.com.henrique.repositories;

import br.com.henrique.domain.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT p FROM Pedido p WHERE p.cliente.id=?1")
    Page<Pedido> findByCliente(Long id, Pageable pageRequest);

    @Query(value = "SELECT COUNT(*) FROM PEDIDO p WHERE CAST(p.data as date) =?1", nativeQuery = true)
    public long pedidosDiario(LocalDate data);


    @Query(value = "SELECT SUM(p.valor_total) FROM Pedido p WHERE CAST(p.data as date) =?1", nativeQuery = true)
    public BigDecimal totalDiario(LocalDate data);
}
