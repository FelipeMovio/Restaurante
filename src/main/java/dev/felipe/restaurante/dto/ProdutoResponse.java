package dev.felipe.restaurante.dto;

import dev.felipe.restaurante.domain.entity.Produto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoResponse(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Boolean disponivel,
        Integer tempoPreparoMinutos,
        Long categoriaId,
        String categoriaNome,
        LocalDateTime criadoEm
) {
    public ProdutoResponse (Produto produto){
        this(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(),produto.getDisponivel(), produto.getTempoPreparoMinutos(),
                produto.getCategoria().getId(),produto.getCategoria().getNome(),produto.getCriadoEm());
    }
}
