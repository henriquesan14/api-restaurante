package br.com.henrique.domain.statistics;

import java.math.BigDecimal;

public interface PedidoStatistics {
    Integer getDia();
    Integer getMes();
    BigDecimal getTotal();
}
