package dev.felipe.restaurante.client;

import dev.felipe.restaurante.dto.PagamentoRequest;
import dev.felipe.restaurante.dto.PagamentoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "pagamento-client", url = "${pagamento.api.url}")
public interface PagamentoClient {

    @PostMapping("/pagamentos/processar")
    PagamentoResponse processar(@RequestBody PagamentoRequest request);
}
