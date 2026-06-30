package dev.felipe.restaurante.controller;

import dev.felipe.restaurante.dto.*;
import dev.felipe.restaurante.service.PagamentoService;
import dev.felipe.restaurante.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PagamentoService pagamentoService;

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

    @PostMapping("/{pedidoId}/itens")
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoItemResponse adicionarItem( @PathVariable Long pedidoId, @RequestBody PedidoItemRequest request){
        return pedidoService.addItem(pedidoId,request);
    }

    @GetMapping("/{pedidoId}/itens")
    public List<PedidoItemResponse> listarItens(@PathVariable Long pedidoID){
        return pedidoService.listarItens(pedidoID);
    }

    @PostMapping("/pedidos/{pedidoId}/pagar")
    public void pagar(@PathVariable Long pedidoId, @RequestParam String formaPagamento){
        pagamentoService.pagar(pedidoId,formaPagamento);
    }

}
