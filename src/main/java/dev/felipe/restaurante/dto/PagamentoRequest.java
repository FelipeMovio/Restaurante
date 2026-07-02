package dev.felipe.restaurante.dto;

import dev.felipe.restaurante.domain.enums.FormaPagamento;

import java.math.BigDecimal;

public record PagamentoRequest(
        BigDecimal valor,
        FormaPagamento formaPagamento
) {
}
