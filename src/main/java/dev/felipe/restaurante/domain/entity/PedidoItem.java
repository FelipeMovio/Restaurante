package dev.felipe.restaurante.domain.entity;

import dev.felipe.restaurante.domain.enums.StatusItemPedido;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pedido_itens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    @Column(name = "preco_unitario")
    private BigDecimal precoUnitario;

    private String observacao;

    @Enumerated(EnumType.STRING)
    private StatusItemPedido status = StatusItemPedido.PENDENTE;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

}
