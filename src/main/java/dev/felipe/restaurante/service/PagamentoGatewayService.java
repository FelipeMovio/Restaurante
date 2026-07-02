package dev.felipe.restaurante.service;

import dev.felipe.restaurante.dto.PagamentoRequest;
import dev.felipe.restaurante.dto.PagamentoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class PagamentoGatewayService {

    private final Random random = new Random();

    public PagamentoResponse processar(PagamentoRequest request) {

        if (request.valor().compareTo(new BigDecimal("1000")) > 0) {
            return new PagamentoResponse(
                    "RECUSADO",
                    "Limite insuficiente"
            );
        }

        boolean aprovado = random.nextInt(10) < 8;

        if (aprovado) {
            return new PagamentoResponse(
                    "APROVADO",
                    "Pagamento aprovado"
            );
        }

        return new PagamentoResponse(
                "RECUSADO",
                "Transação recusada pela operadora"
        );
    }
}