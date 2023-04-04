package com.sevenine.futebola.evento.adapter.repositories.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "fornecedores")
@Entity(name = "Fornecedor")
public class FornecedorData {

    @Id
    private Long id;
    private String codigo;
    private String nome;
    private boolean situacao;

}
