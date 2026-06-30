package dev.felipe.restaurante.dto;

import java.math.BigDecimal;

public record PagamentoRequest(
        BigDecimal valor,
        String formaPagamento
) {
}
