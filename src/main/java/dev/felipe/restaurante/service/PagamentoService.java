package dev.felipe.restaurante.service;

import dev.felipe.restaurante.controller.PagamentoController;
import dev.felipe.restaurante.domain.entity.FechamentoConta;
import dev.felipe.restaurante.domain.entity.Mesa;
import dev.felipe.restaurante.domain.entity.Pagamento;
import dev.felipe.restaurante.domain.entity.Pedido;
import dev.felipe.restaurante.domain.enums.FormaPagamento;
import dev.felipe.restaurante.domain.enums.StatusMesa;
import dev.felipe.restaurante.domain.enums.StatusPagamento;
import dev.felipe.restaurante.domain.enums.StatusPedido;
import dev.felipe.restaurante.dto.PagamentoRequest;
import dev.felipe.restaurante.dto.PagamentoResponse;
import dev.felipe.restaurante.exception.RegraNegocioException;
import dev.felipe.restaurante.repository.FechamentoContaRepository;
import dev.felipe.restaurante.repository.MesaRepository;
import dev.felipe.restaurante.repository.PagamentoRepository;
import dev.felipe.restaurante.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoController pagamentoController;
    private final FechamentoContaRepository fechamentoContaRepository;
    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final PagamentoRepository pagamentoRepository;


    @Transactional
    public void pagar(Long pedidoId, String formaPagamento){

        FechamentoConta fechamento = fechamentoContaRepository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new RegraNegocioException("fechamento indisponivel"));

        PagamentoResponse response = pagamentoController.processar(
                new PagamentoRequest(
                       fechamento.getTotal(),
                        formaPagamento
                )
        );

        if ("APROVADO".equals(response.status())){
            Pedido pedido = fechamento.getPedido();
            pedido.setStatus(StatusPedido.FECHADO);

            Mesa mesa = pedido.getMesa();
            mesa.setStatus(StatusMesa.LIVRE);

            Pagamento pagamento = new Pagamento();
            pagamento.setPedido(pedido);
            pagamento.setFormaPagamento(FormaPagamento.valueOf(formaPagamento));
            pagamento.setStatus(StatusPagamento.APROVADO);
            pagamento.setValor(fechamento.getTotal());
            pagamento.setDataPagamento(fechamento.getDataFechamento());

            pedidoRepository.save(pedido);
            mesaRepository.save(mesa);
            pagamentoRepository.save(pagamento);
        }
    }
}
