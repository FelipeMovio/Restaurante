package dev.felipe.restaurante.service;

import dev.felipe.restaurante.domain.entity.Mesa;
import dev.felipe.restaurante.domain.entity.Pedido;
import dev.felipe.restaurante.domain.enums.StatusMesa;
import dev.felipe.restaurante.domain.enums.StatusPedido;
import dev.felipe.restaurante.dto.PedidoRequest;
import dev.felipe.restaurante.dto.PedidoResponse;
import dev.felipe.restaurante.repository.MesaRepository;
import dev.felipe.restaurante.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;

    public PedidoResponse abrirPedido(PedidoRequest pedidoRequest){
        Mesa mesa = mesaRepository.findById(pedidoRequest.mesaId())
                .orElseThrow(() -> new RuntimeException("mesa nao encontrada"));

        if (mesa.getStatus() != StatusMesa.LIVRE){
            throw new RuntimeException("Mesa off");
        }

        Pedido pedido = new Pedido();
        pedido.setMesa(mesa);
        pedido.setStatus(StatusPedido.ABERTO);
        pedido.setObservacao(pedidoRequest.observacao());

        mesa.setStatus(StatusMesa.OCUPADA);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        mesaRepository.save(mesa);

        return PedidoResponse.fromEntity(pedidoSalvo);
    }

    public Page<PedidoResponse> listar(Pageable pageable){
        return pedidoRepository.findAll(pageable)
                .map(PedidoResponse :: fromEntity);
    }

    public PedidoResponse buscarPorId(Long id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("pedido nao encontrado"));

        return PedidoResponse.fromEntity(pedido);
    }
}
