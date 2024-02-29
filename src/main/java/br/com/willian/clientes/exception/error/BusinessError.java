package br.com.willian.clientes.exception.error;

import lombok.Getter;


/**
 * Representa um erro de negócio.
 *
 * @author Willian.Soares
 * @version 1.0
 */
@Getter
public class BusinessError {

    /**
     * Código do erro, utilizado para identificar o erro de negócio.
     */
    private final String codeMessage;

    /**
     * Mensagem detalhada do erro, destinada à equipe de desenvolvimento para compreensão da causa.
     */
    private final String developerMessage;

    /**
     * Parâmetros a serem utilizados na formatação da mensagem de erro, permitindo a criação de mensagens mais dinâmicas e informativas.
     */
    private String[] messageParams;

    /**
     * Constrói um erro de negócio com o código, a mensagem para desenvolvedores e parâmetros opcionais.
     *
     * @param codeMessage Código identificador do erro.
     * @param developerMessage Mensagem detalhada para desenvolvedores.
     * @param messageParams Parâmetros para a mensagem de erro (opcionais).
     */
    public BusinessError(String codeMessage, String developerMessage, String... messageParams) {
        this.codeMessage = codeMessage;
        this.developerMessage = developerMessage;
        this.messageParams = messageParams;
    }
}
