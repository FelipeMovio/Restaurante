package dev.felipe.restaurante.service;

import dev.felipe.restaurante.domain.entity.Mesa;
import dev.felipe.restaurante.domain.entity.Pedido;
import dev.felipe.restaurante.domain.entity.PedidoItem;
import dev.felipe.restaurante.domain.entity.Produto;
import dev.felipe.restaurante.domain.enums.StatusItemPedido;
import dev.felipe.restaurante.domain.enums.StatusMesa;
import dev.felipe.restaurante.domain.enums.StatusPedido;
import dev.felipe.restaurante.dto.PedidoItemRequest;
import dev.felipe.restaurante.dto.PedidoItemResponse;
import dev.felipe.restaurante.dto.PedidoRequest;
import dev.felipe.restaurante.dto.PedidoResponse;
import dev.felipe.restaurante.exception.RegraNegocioException;
import dev.felipe.restaurante.repository.MesaRepository;
import dev.felipe.restaurante.repository.PedidoItemRepository;
import dev.felipe.restaurante.repository.PedidoRepository;
import dev.felipe.restaurante.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoItemRepository pedidoItemRepository;

    public PedidoResponse abrirPedido(PedidoRequest pedidoRequest){
        Mesa mesa = mesaRepository.findById(pedidoRequest.mesaId())
                .orElseThrow(() -> new RegraNegocioException("mesa nao encontrada"));

        if (mesa.getStatus() != StatusMesa.LIVRE){
            throw new RegraNegocioException("Mesa off");
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
        Pedido pedido = buscarPedidoPorId(id);

        return PedidoResponse.fromEntity(pedido);
    }

    public PedidoItemResponse addItem(Long pedidoId, PedidoItemRequest request){
        Pedido pedido = buscarPedidoPorId(pedidoId);
        if (pedido.getStatus() != StatusPedido.ABERTO){
            throw new RegraNegocioException("So e possivel adicionar item em pedidos abertos ");
        }

        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new RegraNegocioException("produto nao encontrado"));

        if (!produto.getDisponivel()){
            throw new RegraNegocioException("Produto indisponivel");
        }

        if (request.quantidade() == null || request.quantidade() <= 0){
            throw new RegraNegocioException("quantidade deve ser maior que zero ");
        }

        PedidoItem pedidoItem = new PedidoItem();
        pedidoItem.setPedido(pedido);
        pedidoItem.setProduto(produto);
        pedidoItem.setQuantidade(request.quantidade());
        pedidoItem.setPrecoUnitario(produto.getPreco());
        pedidoItem.setObservacao(request.observacao());
        pedidoItem.setStatus(StatusItemPedido.PENDENTE);
        PedidoItem itemSalvo = pedidoItemRepository.save(pedidoItem);

        return PedidoItemResponse.fromEntity(itemSalvo);
    }

    public List<PedidoItemResponse> listarItens(Long pedidoId){
        buscarPedidoPorId(pedidoId);

        return pedidoItemRepository.findByPedidoId(pedidoId)
                .stream()
                .map(PedidoItemResponse ::fromEntity)
                .collect(Collectors.toList());


    }

    private Pedido buscarPedidoPorId(Long pedidoId){
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RegraNegocioException("Pedido nao encontrado "));
    }
}

