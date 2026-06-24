package dev.felipe.restaurante.dto;

import dev.felipe.restaurante.domain.entity.FechamentoConta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FechamentoContaResponse(
        Long id,
        Long pedidoId,
        Integer numeroMesa,
        BigDecimal subtotal,
        BigDecimal taxaServico,
        BigDecimal desconto,
        BigDecimal total,
        LocalDateTime dataFechamento

) {

    public static FechamentoContaResponse fromEntity(FechamentoConta fechamentoConta){
        return new FechamentoContaResponse(
                fechamentoConta.getId(),
                fechamentoConta.getPedido().getId(),
                fechamentoConta.getPedido().getMesa().getNumero(),
                fechamentoConta.getSubtotal(),
                fechamentoConta.getTaxaServico(),
                fechamentoConta.getDesconto(),
                fechamentoConta.getTotal(),
                fechamentoConta.getDataFechamento()

        );
    }
}
