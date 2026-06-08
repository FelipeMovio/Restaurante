package dev.felipe.restaurante.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categorias_produtos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Boolean ativa = true;
}
