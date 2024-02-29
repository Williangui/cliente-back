package br.com.willian.clientes.exception.error;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static lombok.AccessLevel.PRIVATE;


/**
 * Representa uma resposta de erro de API, com um status HTTP e uma lista de erros específicos.
 *
 * @author Willian.Soares
 * @version 1.0
 */
@JsonAutoDetect(fieldVisibility = ANY)
@RequiredArgsConstructor(access = PRIVATE)
public class ErrorResponse {

    /**
     * Código de status HTTP da resposta de erro.
     */
    private final int statusCode;

    /**
     * Lista de erros específicos que ocorreram.
     */
    private final List<ApiError> erros;

    /**
     * Cria um ErrorResponse a partir de um status HTTP e uma lista de erros.
     *
     * @param status Status HTTP da resposta.
     * @param errors Lista de erros específicos.
     * @return Objeto ErrorResponse configurado.
     */
    public static ErrorResponse of(HttpStatus status, List<ApiError> errors) {
        return new ErrorResponse(status.value(), errors);
    }

    /**
     * Cria um ErrorResponse a partir de um status HTTP e um único erro.
     *
     * @param status Status HTTP da resposta.
     * @param error Erro específico.
     * @return Objeto ErrorResponse configurado.
     */
    public static ErrorResponse of(HttpStatus status, ApiError error) {
        return of(status, Collections.singletonList(error));
    }
}
