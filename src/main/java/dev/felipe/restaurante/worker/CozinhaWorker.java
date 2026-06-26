package dev.felipe.restaurante.worker;

import dev.felipe.restaurante.domain.entity.PedidoItem;
import dev.felipe.restaurante.domain.enums.StatusItemPedido;
import dev.felipe.restaurante.repository.PedidoItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class CozinhaWorker {

    private final PedidoItemRepository pedidoItemRepository;

    private final ExecutorService executorService =
            Executors.newVirtualThreadPerTaskExecutor();

    @Scheduled(fixedRate = 60000)
    public void verificarItensAtrasados(){
        List<PedidoItem> itensEmPreparo =
                pedidoItemRepository.buscarItensComProdutoEPedido(StatusItemPedido.EM_PREPARO);

        for (PedidoItem pedidoItem : itensEmPreparo){
            executorService.submit(() -> verificarItem(pedidoItem));
        }
    }

    private void verificarItem(PedidoItem item){

        if (item.getDataIncioPreparo() == null){
            return;
        }

        Integer tempoPreparo = item.getProduto().getTempoPreparoMinutos();

        if (tempoPreparo == null || tempoPreparo <= 0){
            return;
        }

        long minutosEmPreparo = Duration.between(item.getDataIncioPreparo(), LocalDateTime.now()).toMinutes();

        if (minutosEmPreparo > tempoPreparo){
            System.out.println(
                    """
                            [ALETA COZINHA]
                            Item atrasado:
                            Pedido: %d
                            Mesa: %d
                            Produto: %d
                            Tempo esperado: %d minutosd
                            Tempo em preparo : %d minutos
                            """.formatted(
                                    item.getPedido().getId(),
                            item.getPedido().getMesa().getNumero(),
                            item.getProduto().getNome(),
                            tempoPreparo,
                            minutosEmPreparo
                    )
            );
        }

    }
}
