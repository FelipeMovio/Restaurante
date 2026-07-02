package dev.felipe.restaurante.controller;

import dev.felipe.restaurante.dto.PagamentoRequest;
import dev.felipe.restaurante.dto.PagamentoResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Random;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final Random random = new Random();

    @PostMapping("/processar")
    public PagamentoResponse processar(@RequestBody PagamentoRequest request) {

        // Valores muito altos são recusados
        if (request.valor().compareTo(new BigDecimal("1000")) > 0) {
            return new PagamentoResponse(
                    "RECUSADO",
                    "Limite insuficiente"
            );
        }

        // Aprovação de aproximadamente 80%
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
