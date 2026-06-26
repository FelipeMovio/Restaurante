package dev.felipe.restaurante.service;

import dev.felipe.restaurante.domain.entity.FechamentoConta;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FechamentoContaService {

    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final FechamentoContaRepository fechamentoContaRepository;


    @Transactional
    public FechamentoContaResponse fecharConta(Long pedidoId, FechamentoContaRequest request){
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RegraNegocioException("nao encontrado "));

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

        BigDecimal subtotal = itens.stream()
                .map(item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        BigDecimal taxaServico = request.taxaServico() != null ? request.taxaServico() : BigDecimal.ZERO;

        BigDecimal desconto = request.desconto() != null ? request.desconto() : BigDecimal.ZERO;

        if (taxaServico.compareTo(BigDecimal.ZERO) < 0){
            throw new RegraNegocioException("taxa de servico nao pode ser negativa ");
        }

        if (desconto.compareTo(BigDecimal.ZERO) < 0){
            throw new RegraNegocioException("desconto nao pode ser negativo");
        }

        BigDecimal total = subtotal.add(taxaServico.subtract(desconto));

        if (total.compareTo(BigDecimal.ZERO) < 0){
            throw new RegraNegocioException("total nao pode ser negativo");
        }

        FechamentoConta fechamentoConta = new FechamentoConta();

        fechamentoConta.setPedido(pedido);
        fechamentoConta.setSubtotal(subtotal);
        fechamentoConta.setTaxaServico(taxaServico);
        fechamentoConta.setDesconto(desconto);
        fechamentoConta.setTotal(total);
        pedido.setStatus(StatusPedido.FECHADO);
        fechamentoConta.setDataFechamento(LocalDateTime.now());

        FechamentoConta fechamentoSalvo = fechamentoContaRepository.save(fechamentoConta);
        pedidoRepository.save(pedido);

        return FechamentoContaResponse.fromEntity(fechamentoSalvo);
    }

    public FechamentoContaResponse buscarPorPedido(Long pedidoId){
        FechamentoConta fechamentoConta = fechamentoContaRepository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new RegraNegocioException("fechamento nao encontrado "));

        return FechamentoContaResponse.fromEntity(fechamentoConta);
    }
}
