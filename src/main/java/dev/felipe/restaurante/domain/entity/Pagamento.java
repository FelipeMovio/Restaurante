package dev.felipe.restaurante.domain.entity;

import dev.felipe.restaurante.domain.enums.FormaPagamento;
import dev.felipe.restaurante.domain.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status = StatusPagamento.PENDENTE;

    @Column(name = "codigo_transacao_externa")
    private String codigoTransacaoExterna;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;


    @PrePersist
    public void prePersist(){
        dataPagamento = LocalDateTime.now();
    }
}
