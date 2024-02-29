package br.com.willian.clientes.exception.config;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Configura o MessageSource para mensagens de erro da API.
 *
 * @author Willian.Soares
 * @version 1.0
 */
@Configuration
public class ApiErrorConfiguration {

    /**
     * Cria um MessageSource para mensagens de erro da API.
     *
     * @return MessageSource para as mensagens de erro.
     */
    @Bean
    public MessageSource apiErrorMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/api_error");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}