package dev.felipe.restaurante.service;

import dev.felipe.restaurante.domain.entity.PedidoItem;
import dev.felipe.restaurante.domain.enums.StatusItemPedido;
import dev.felipe.restaurante.dto.CozinhaItemResponse;
import dev.felipe.restaurante.exception.RegraNegocioException;
import dev.felipe.restaurante.repository.PedidoItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CozinhaService {

    private final PedidoItemRepository pedidoItemRepository;


    public List<CozinhaItemResponse> listarItensPendentes(){
        return pedidoItemRepository.findByStatusOrderByIdAsc(StatusItemPedido.PENDENTE)
                .stream()
                .map(CozinhaItemResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<CozinhaItemResponse> listarItensEmPreparo(){
        return pedidoItemRepository.findByStatusOrderByIdAsc(StatusItemPedido.EM_PREPARO)
                .stream()
                .map(CozinhaItemResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public CozinhaItemResponse iniciarPreparo(Long itemId){
        PedidoItem item = pedidoItemRepository.findById(itemId)
                .orElseThrow(() -> new RegraNegocioException("nao encontrado"));
        if (item.getStatus() != StatusItemPedido.PENDENTE){
            throw new RegraNegocioException("somentes itens pendentes podem inicar preparo");
        }
        item.setStatus(StatusItemPedido.EM_PREPARO);

        PedidoItem salvo = pedidoItemRepository.save(item);
        return CozinhaItemResponse.fromEntity(salvo);
    }

    public CozinhaItemResponse marcarComoPronto(Long itemId){
        PedidoItem item = pedidoItemRepository.findById(itemId)
                .orElseThrow(() -> new RegraNegocioException("nao encontrado"));
        if (item.getStatus() != StatusItemPedido.EM_PREPARO){
            throw new RegraNegocioException("somentes itens empreparo podem ficar pronto");
        }
        item.setStatus(StatusItemPedido.PRONTO);

        PedidoItem salvo = pedidoItemRepository.save(item);
        return CozinhaItemResponse.fromEntity(salvo);
    }
}
