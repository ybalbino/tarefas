package com.catalisa.tarefas.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_tarefas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tarefas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String titulo;

    @Column(length = 100, nullable = false)
    private String descricao;

    @Column(length = 10, nullable = false)
    private Date dataVencimento;

    @Column
    private boolean status;
}
