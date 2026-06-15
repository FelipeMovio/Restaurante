package dev.felipe.restaurante.controller;

import dev.felipe.restaurante.dto.PedidoRequest;
import dev.felipe.restaurante.dto.PedidoResponse;
import dev.felipe.restaurante.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse abrirPedido(@RequestBody PedidoRequest pedidoRequest){
        return pedidoService.abrirPedido(pedidoRequest);
    }

    @GetMapping
    public Page<PedidoResponse> listar(Pageable pageable){
        return pedidoService.listar(pageable);
    }

    @GetMapping("/{id}")
    public PedidoResponse buscarPorId(@PathVariable Long id){
        return pedidoService.buscarPorId(id);
    }

}
