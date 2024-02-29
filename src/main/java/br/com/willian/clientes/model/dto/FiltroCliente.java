package br.com.willian.clientes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltroCliente {

    private Long id;
    private String nome;
    private String endereco;
    private String bairro;
    private String telefone;
}
