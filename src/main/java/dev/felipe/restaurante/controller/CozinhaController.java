package dev.felipe.restaurante.controller;

import dev.felipe.restaurante.dto.CozinhaItemResponse;
import dev.felipe.restaurante.service.CozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cozinha")
@RequiredArgsConstructor
public class CozinhaController {

    private final CozinhaService cozinhaService;

    @GetMapping("/itens-pendentes")
    public List<CozinhaItemResponse> listarItensPendentes(){
        return cozinhaService.listarItensPendentes();
    }

    @GetMapping("/itens-em-preparo")
    public List<CozinhaItemResponse> listarItensEmPreparo(){
        return cozinhaService.listarItensEmPreparo();
    }

    @PatchMapping("/itens/{itemId}/iniciar-preparo")
    public CozinhaItemResponse iniciarPreparo(@PathVariable Long itemId){
        return cozinhaService.iniciarPreparo(itemId);
    }


    @PatchMapping("/itens/{itemId}/marcar-pronto")
    public CozinhaItemResponse marcarComoPronto(@PathVariable Long itemId){
        return cozinhaService.marcarComoPronto(itemId);
    }

    @PatchMapping("/itens/{itemId}/entregar")
    public CozinhaItemResponse entregarItem(@PathVariable Long itemId){
        return cozinhaService.entregarItem(itemId);
    }
}
