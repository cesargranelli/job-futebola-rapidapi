package com.sevenine.futebola.rapidapi.adapter.repositories.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "jogadores",
        uniqueConstraints = @UniqueConstraint(columnNames = {
                "externoId", "fornecedor_id"
        }))
@Entity(name = "Jogador")
public class JogadorData {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    private String nomeCurto;
    private String posicao;
    private int numeroCamisa;
    private Long externoId;

    @ManyToOne
    private FornecedorData fornecedor;

}
