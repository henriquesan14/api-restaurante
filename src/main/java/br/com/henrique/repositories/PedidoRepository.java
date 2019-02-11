package br.com.henrique.repositories;

import br.com.henrique.domain.ItemPedido;
import br.com.henrique.domain.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT p FROM Pedido p WHERE p.cliente.id=?1")
    Page<Pedido> findByCliente(Long id, Pageable pageRequest);

    @Query(value = "SELECT COUNT(*) FROM PEDIDO p WHERE CAST(p.data as date) =?1", nativeQuery = true)
    long pedidosDiario(LocalDate data);

    @Query(value = "SELECT COUNT(*) FROM ITEM_PEDIDO I INNER JOIN PEDIDO P ON I.PEDIDO_ID= P.ID WHERE CAST(P.DATA AS DATE) = ?1", nativeQuery = true)
    long itensDiario(LocalDate data);

    @Query(value = "SELECT SUM(p.valor_total) FROM Pedido p WHERE CAST(p.data as date) =?1", nativeQuery = true)
    BigDecimal totalDiario(LocalDate data);

    @Query("SELECT i FROM ItemPedido i WHERE i.statusItem=?1 ORDER BY i.id.pedido.data ASC")
    List<ItemPedido> itensByStatusItem(Integer status);

    @Query("SELECT COUNT(i) FROM ItemPedido i WHERE i.statusItem=?1")
    long countItensByStatusItem(Integer status);


}
