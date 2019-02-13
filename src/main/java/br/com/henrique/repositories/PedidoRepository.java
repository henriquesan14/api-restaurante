package br.com.henrique.repositories;

import br.com.henrique.domain.ItemPedido;
import br.com.henrique.domain.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT p FROM Pedido p WHERE p.cliente.id=?1")
    Page<Pedido> findByCliente(Long id, Pageable pageRequest);

    @Query(value = "SELECT COUNT(*) FROM PEDIDO p WHERE CAST(p.data as date) =?1", nativeQuery = true)
    long countPedidosDiario(LocalDate data);

    @Query(value = "SELECT * FROM PEDIDO p WHERE CAST(p.data as date) =?1", nativeQuery = true)
    List<Pedido> pedidosDiario(LocalDate data);

    @Query(value = "SELECT COUNT(*) FROM ITEM_PEDIDO I INNER JOIN PEDIDO P ON I.PEDIDO_ID= P.ID WHERE CAST(P.DATA AS DATE) = ?1", nativeQuery = true)
    long countItensDiario(LocalDate data);

    @Query(value = "SELECT i FROM ItemPedido i WHERE CAST(i.id.pedido.data as date)=?1")
    List<ItemPedido> itensDiario(Date data);

    @Query(value = "SELECT SUM(p.valor_total) FROM Pedido p WHERE CAST(p.data as date) =?1", nativeQuery = true)
    BigDecimal totalDiario(LocalDate data);

    @Query("SELECT i FROM ItemPedido i WHERE i.statusItem=?1 ORDER BY i.id.pedido.data ASC")
    List<ItemPedido> itensByStatusItem(Integer status);

    @Query("SELECT COUNT(i) FROM ItemPedido i WHERE i.statusItem=?1")
    long countItensByStatusItem(Integer status);

    @Transactional
    @Modifying
    @Query("UPDATE ItemPedido i SET i.statusItem=?1 WHERE i.id.pedido.id=?2 AND i.id.produto.id=?3")
    void updateStatusItem(Integer status, Long idPedido, Long idProduto);


}
