package br.com.willian.clientes.exception.error;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma exceção de negócio.
 *
 * @author Willian.Soares
 * @version 1.0
 */
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    /**
     * Status HTTP da exceção.
     */
    private HttpStatus status;

    /**
     * Lista de erros de negócio associados à exceção.
     */
    private List<BusinessError> errors;

    /**
     * Constrói uma exceção de negócio com o código do erro.
     *
     * @param codeMessage Código do erro.
     */
    public BusinessException(String codeMessage) {
        this(codeMessage, null, "");
    }

    /**
     * Constrói uma exceção de negócio com o código do erro e o status HTTP.
     *
     * @param codeMessage Código do erro.
     * @param status Status HTTP da exceção.
     */
    public BusinessException(String codeMessage, HttpStatus status) {
        this(codeMessage, status, "");
    }

    /**
     * Constrói uma exceção de negócio com o código do erro, status HTTP e mensagem detalhada.
     *
     * @param codeMessage Código do erro.
     * @param status Status HTTP da exceção.
     * @param developerMessage Mensagem detalhada para desenvolvedores.
     */
    public BusinessException(String codeMessage, HttpStatus status, String developerMessage) {
        this.status = status;
        addError(codeMessage, developerMessage);
    }

    /**
     * Constrói uma exceção de negócio com o código do erro e parâmetros para a mensagem.
     *
     * @param codeMessage Código do erro.
     * @param messageParams Parâmetros para a mensagem de erro.
     */
    public BusinessException(String codeMessage, String... messageParams) {
        this.addError(codeMessage, "", messageParams);
    }

    /**
     * Constrói uma exceção de negócio com o código do erro, status HTTP e parâmetros para a mensagem.
     *
     * @param codeMessage Código do erro.
     * @param status Status HTTP da exceção.
     * @param messageParams Parâmetros para a mensagem de erro.
     */
    public BusinessException(String codeMessage, HttpStatus status, String... messageParams) {
        this.addError(codeMessage, "", messageParams);
    }

    /**
     * Adiciona um erro de negócio à exceção.
     *
     * @param codeMessage Código do erro.
     * @param developerMessage Mensagem detalhada para desenvolvedores (opcional).
     */
    public final void addError(String codeMessage, String developerMessage) {
        getErrors().add(new BusinessError(codeMessage, developerMessage));
    }

    /**
     * Adiciona um erro de negócio à exceção com parâmetros para a mensagem.
     *
     * @param codeMessage Código do erro.
     * @param developerMessage Mensagem detalhada para desenvolvedores (opcional).
     * @param messageParams Parâmetros para a mensagem de erro.
     */
    public final void addError(String codeMessage, String developerMessage, String... messageParams) {
        getErrors().add(new BusinessError(codeMessage, developerMessage, messageParams));
    }

    /**
     * Retorna o status HTTP da exceção.
     *
     * @return Status HTTP da exceção.
     */
    public HttpStatus getStatus() {
        if (status == null) {
            this.status = HttpStatus.BAD_REQUEST;
        }
        return status;
    }

    /**
     * Retorna a lista de erros de negócio associados à exceção.
     *
     * @return Lista de erros de negócio.
     */
    public List<BusinessError> getErrors() {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        return errors;
    }
}
