package br.com.willian.clientes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContatoDTO {

    private Long id;
    private String telefone;
    private Long idCliente;
}
