package br.com.willian.clientes.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.willian.clientes.model.Cliente;
import br.com.willian.clientes.model.Contato;
import br.com.willian.clientes.model.dto.ContatoDTO;
import br.com.willian.clientes.repository.ContatoRepository;
import br.com.willian.clientes.service.ContatoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ContatoController.class})
@ExtendWith(SpringExtension.class)
class ContatoControllerTest {
    @Autowired
    private ContatoController contatoController;

    @MockBean
    private ContatoService contatoService;

    /**
     * Testando o metodo: {@link ContatoController#salvar(ContatoDTO)}
     */
    @Test
    void testSalvar() throws Exception {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setBairro("Bairro");
        cliente.setContatos(new ArrayList<>());
        cliente.setEndereco("Endereco");
        cliente.setId(1L);
        cliente.setNome("Nome");

        Contato contato = new Contato();
        contato.setCliente(cliente);
        contato.setId(1L);
        contato.setTelefone("Telefone");
        when(contatoService.salvar(Mockito.<Contato>any())).thenReturn(contato);

        Cliente cliente2 = new Cliente();
        cliente2.setBairro("Bairro");
        cliente2.setContatos(new ArrayList<>());
        cliente2.setEndereco("Endereco");
        cliente2.setId(1L);
        cliente2.setNome("Nome");

        Contato contato2 = new Contato();
        contato2.setCliente(cliente2);
        contato2.setId(1L);
        contato2.setTelefone("Telefone");
        String content = (new ObjectMapper()).writeValueAsString(contato2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/contato/salvar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(contatoController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"telefone\":\"Telefone\"}"));
    }

    /**
     * Testando o metodo: {@link ContatoController#excluir(Long)}
     */
    @Test
    void testExcluir() throws Exception {

        doNothing().when(contatoService).excluir(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
                "/api/v1/contato/excluir/{id}", 1L);

        MockMvcBuilders.standaloneSetup(contatoController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Testando o metodo: {@link ContatoController#excluir(Long)}
     */
    @Test
    void testExcluir2() throws Exception {

        doNothing().when(contatoService).excluir(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
                "/api/v1/contato/excluir/{id}", 1L);
        requestBuilder.contentType("https://example.org/example");

        MockMvcBuilders.standaloneSetup(contatoController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Testando o metodo: {@link ContatoController#listarPorCliente(Long, Pageable)}
     */
    @Test
    void testListarPorCliente() {

        ContatoRepository repository = mock(ContatoRepository.class);
        PageImpl<Contato> pageImpl = new PageImpl<>(new ArrayList<>());
        when(repository.listarPorCliente(Mockito.<Long>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);
        Page<Contato> actualListarPorClienteResult = (new ContatoController(new ContatoService(repository)))
                .listarPorCliente(1L, null);
        verify(repository).listarPorCliente(Mockito.<Long>any(), Mockito.<Pageable>any());
        assertTrue(actualListarPorClienteResult.toList().isEmpty());
        assertSame(pageImpl, actualListarPorClienteResult);
    }

    /**
     * Testando o metodo: {@link ContatoController#listarPorCliente(Long, Pageable)}
     */
    @Test
    void testListarPorCliente2() {

        ContatoService service = mock(ContatoService.class);
        PageImpl<Contato> pageImpl = new PageImpl<>(new ArrayList<>());
        when(service.listarPorCliente(Mockito.<Long>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);
        Page<Contato> actualListarPorClienteResult = (new ContatoController(service))
                .listarPorCliente(1L, null);
        verify(service).listarPorCliente(Mockito.<Long>any(), Mockito.<Pageable>any());
        assertTrue(actualListarPorClienteResult.toList().isEmpty());
        assertSame(pageImpl, actualListarPorClienteResult);
    }
}
