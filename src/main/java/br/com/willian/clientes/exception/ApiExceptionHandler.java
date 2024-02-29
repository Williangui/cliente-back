package br.com.willian.clientes.exception;

import br.com.willian.clientes.exception.error.ApiError;
import br.com.willian.clientes.exception.error.BusinessError;
import br.com.willian.clientes.exception.error.BusinessException;
import br.com.willian.clientes.exception.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


/**
 * Manipulador de exceções para a API.
 *
 * @author Willian.Soares
 * @version 1.0
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    /**
     * Mensagem padrão para erros inesperados.
     */
    private static final String DEFAULT_MESSAGE_UNEXPECTED_ERROR = "Erro inesperado";

    /**
     * Mensagem exibida quando a mensagem de erro não é encontrada para o código informado.
     */
    private static final String NO_MESSSAGE_AVAILABLE = " Não foi encontrado nenhuma mensagem para o código: ";

    /**
     * Logger para registrar eventos relacionados ao tratamento de exceções.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);

    /**
     * Fonte de mensagens de erro da API.
     */
    private final MessageSource apiErrorMessageSource;

    /**
     * Trata exceções do tipo BusinessException, mapeando-as para respostas de erro da API no formato ErrorResponse.
     *
     * @param exception Exceção de negócio a ser tratada.
     * @param locale Localização do usuário para customizar a mensagem de erro.
     * @return Objeto ResponseEntity contendo o código de status HTTP e o corpo da resposta de erro.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception, Locale locale) {
        // Converte os erros de negócio em uma lista de erros de API com mensagens traduzidas e formatadas.
        List<ApiError> apiErrors = exception.getErrors().stream()
                .map(businessError -> toApiError(businessError.getCodeMessage(), locale,
                        businessError.getDeveloperMessage(), businessError.getMessageParams()))
                .collect(toList());

        // Cria e configura a resposta de erro.
        final ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, apiErrors);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Trata exceções de maneira genérica, retornando um erro 501 (Não Implementado).
     *
     * @param exception Exceção capturada.
     * @param locale Localização do usuário para customizar a mensagem de erro.
     * @return Objeto ResponseEntity contendo o status 501 e uma resposta de erro genérica.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerInternalServerError(Exception exception, Locale locale) {
        LOG.error("Erro inesperado", exception);
        final HttpStatus status = HttpStatus.NOT_IMPLEMENTED;
        final ErrorResponse errorResponse = ErrorResponse.of(status, toApiError(DEFAULT_MESSAGE_UNEXPECTED_ERROR,
                locale, exception.getMessage()));
        return ResponseEntity.status(status).body(errorResponse);
    }

    /**
     * Converte um código de erro de negócio, localidade e mensagem do desenvolvedor em um objeto ApiError.
     *
     * @param code Código do erro de negócio.
     * @param locale Localização do usuário para customizar a mensagem de erro.
     * @param developerMessage Mensagem detalhada do erro para desenvolvedores.
     * @param args Parâmetros para formatação da mensagem de erro.
     * @return Objeto ApiError contendo o código, mensagem e mensagem para desenvolvedores.
     */
    public ApiError toApiError(String code, Locale locale, String developerMessage, String... args) {
        String message;
        try {
            // Tenta recuperar a mensagem de erro traduzida da fonte de mensagens.
            message = apiErrorMessageSource.getMessage(code, args, locale);
        } catch (NoSuchMessageException e) {
            // Caso a mensagem não seja encontrada, utiliza a mensagem padrão e adiciona a informação do código ausente.
            message = DEFAULT_MESSAGE_UNEXPECTED_ERROR;
            developerMessage += NO_MESSSAGE_AVAILABLE + code;
        }

        // Cria e retorna o objeto ApiError.
        return getApiError(code, message, developerMessage);
    }

    /**
     * Cria um objeto ApiError com os parâmetros fornecidos.
     *
     * @param error Código do erro.
     * @param message Mensagem de erro para o usuário.
     * @param developerMessage Mensagem detalhada do erro para desenvolvedores.
     * @return Objeto ApiError configurado.
     */
    private ApiError getApiError(String error, String message, String developerMessage) {
        return new ApiError(error, message, developerMessage);
    }
}
