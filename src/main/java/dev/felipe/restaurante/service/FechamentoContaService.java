package dev.felipe.restaurante.service;

import dev.felipe.restaurante.domain.entity.Pedido;
import dev.felipe.restaurante.domain.entity.PedidoItem;
import dev.felipe.restaurante.domain.enums.StatusItemPedido;
import dev.felipe.restaurante.domain.enums.StatusPedido;
import dev.felipe.restaurante.dto.FechamentoContaRequest;
import dev.felipe.restaurante.dto.FechamentoContaResponse;
import dev.felipe.restaurante.exception.RegraNegocioException;
import dev.felipe.restaurante.repository.FechamentoContaRepository;
import dev.felipe.restaurante.repository.PedidoItemRepository;
import dev.felipe.restaurante.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FechamentoContaService {

    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final FechamentoContaRepository fechamentoContaRepository;

    public FechamentoContaResponse fecharConta(Long pedidoId, FechamentoContaRequest request){
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RegraNegocioException("nnao encontrado "));

        if (pedido.getStatus() == StatusPedido.FECHADO){
            throw new RegraNegocioException("pedido ja esta fechado");
        }

        if (pedido.getStatus() == StatusPedido.CANCELADO){
            throw new RegraNegocioException("pedido cancelado nao pode ser fechado ");
        }

        if (fechamentoContaRepository.existsByPedidoId(pedidoId)){
            throw new RegraNegocioException(" ja existye fechamneto para este pedido ");
        }

        List<PedidoItem> itens = pedidoItemRepository.findByPedidoId(pedidoId);
        if (itens.isEmpty()){
            throw new RegraNegocioException("nao es possivel fechar uma conta sem itens");
        }

        List<PedidoItem> itensNaoEntregues = pedidoItemRepository
                .findByPedidoIdAndStatusNot(pedidoId, StatusItemPedido.ENTREGUE);

        if (!itensNaoEntregues.isEmpty()){
            throw new RegraNegocioException("todos os itens precisam estra entrgues para fehcar conta ");
        }
    }
}
