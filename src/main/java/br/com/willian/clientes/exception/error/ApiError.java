package br.com.willian.clientes.exception.error;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;


/**
 * Representa um erro de API.
 *
 * @author Willian.Soares
 * @version 1.0
 */
@Data
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = ANY)
@RequiredArgsConstructor
public class ApiError {

    /**
     * Código do erro.
     */
    private String error;

    /**
     * Mensagem amigável do erro para o usuário.
     */
    private String message;

    /**
     * Mensagem detalhada do erro para desenvolvedores.
     */
    private String developerMessage;
}