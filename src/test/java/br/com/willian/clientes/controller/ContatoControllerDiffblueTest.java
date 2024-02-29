package br.com.willian.clientes.controller;

import static org.mockito.Mockito.doNothing;

import br.com.willian.clientes.service.ContatoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ContatoController.class})
@ExtendWith(SpringExtension.class)
class ContatoControllerDiffblueTest {
    @Autowired
    private ContatoController contatoController;

    @MockBean
    private ContatoService contatoService;


}
