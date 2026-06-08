package dev.felipe.restaurante.domain.entity;

import dev.felipe.restaurante.domain.enums.StatusMesa;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mesas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mesa {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;
    private String descricao;
    private Integer capacidade;


    @Enumerated(EnumType.STRING)
    private StatusMesa status = StatusMesa.LIVRE;
}
