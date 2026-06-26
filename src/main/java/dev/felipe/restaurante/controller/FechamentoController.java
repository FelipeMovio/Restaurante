package dev.felipe.restaurante.controller;

import dev.felipe.restaurante.dto.FechamentoContaRequest;
import dev.felipe.restaurante.dto.FechamentoContaResponse;
import dev.felipe.restaurante.service.FechamentoContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{pedidoId}/fechamento")
@RequiredArgsConstructor
public class FechamentoController {

    private final FechamentoContaService fechamentoContaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FechamentoContaResponse fecharConta(@PathVariable Long pedidoId, @RequestBody FechamentoContaRequest request){

        return fechamentoContaService.fecharConta(pedidoId, request);

    }

    @GetMapping
    public FechamentoContaResponse ver(@PathVariable Long pedidoId) {
        return fechamentoContaService.buscarPorPedido(pedidoId);
    }
}
