package dev.felipe.restaurante.dto;

public record PagamentoResponse(
        String status,
        String codigoTransacao
) {
}
