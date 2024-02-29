package br.com.willian.clientes.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.willian.clientes.model.Cliente;
import br.com.willian.clientes.model.dto.ClienteListagem;
import br.com.willian.clientes.model.dto.FiltroCliente;
import br.com.willian.clientes.repository.ClienteRepository;
import br.com.willian.clientes.repository.ContatoRepository;
import br.com.willian.clientes.service.ClienteService;
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

@ContextConfiguration(classes = {ClienteController.class})
@ExtendWith(SpringExtension.class)
class ClienteControllerTest {
    @Autowired
    private ClienteController clienteController;

    @MockBean
    private ClienteService clienteService;

    /**
     * Testando o metodo: {@link ClienteController#salvar(Cliente)}
     */
    @Test
    void testSalvar() throws Exception {

        Cliente cliente = new Cliente();
        cliente.setBairro("Bairro");
        cliente.setContatos(new ArrayList<>());
        cliente.setEndereco("Endereco");
        cliente.setId(1L);
        cliente.setNome("Nome");
        when(clienteService.salvar(Mockito.<Cliente>any())).thenReturn(cliente);

        Cliente cliente2 = new Cliente();
        cliente2.setBairro("Bairro");
        cliente2.setContatos(new ArrayList<>());
        cliente2.setEndereco("Endereco");
        cliente2.setId(1L);
        cliente2.setNome("Nome");
        String content = (new ObjectMapper()).writeValueAsString(cliente2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/cliente/salvar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(clienteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"nome\":\"Nome\",\"endereco\":\"Endereco\",\"bairro\":" +
                                "\"Bairro\",\"contatos\":[]}"));
    }

    /**
     * Testando o metodo: {@link ClienteController#buscarPorId(Long)}
     */
    @Test
    void testBuscarPorId() throws Exception {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setBairro("Bairro");
        cliente.setContatos(new ArrayList<>());
        cliente.setEndereco("Endereco");
        cliente.setId(1L);
        cliente.setNome("Nome");
        when(clienteService.buscarPorId(Mockito.<Long>any())).thenReturn(cliente);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/v1/cliente/buscarPorId/{id}", 1L);

        MockMvcBuilders.standaloneSetup(clienteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"nome\":\"Nome\",\"endereco\":\"Endereco\",\"bairro\":" +
                                "\"Bairro\",\"contatos\":[]}"));
    }

    /**
     * Testando o metodo: {@link ClienteController#excluir(Long)}
     */
    @Test
    void testExcluir() throws Exception {

        doNothing().when(clienteService).excluir(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
                "/api/v1/cliente/excluir/{id}", 1L);

        MockMvcBuilders.standaloneSetup(clienteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Testando o metodo: {@link ClienteController#excluir(Long)}
     */
    @Test
    void testExcluir2() throws Exception {

        doNothing().when(clienteService).excluir(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
                "/api/v1/cliente/excluir/{id}", 1L);
        requestBuilder.contentType("https://example.org/example");

        MockMvcBuilders.standaloneSetup(clienteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Testando o metodo: {@link ClienteController#listar(FiltroCliente, Pageable)}
     */
    @Test
    void testListar() {

        ClienteRepository repository = mock(ClienteRepository.class);
        PageImpl<ClienteListagem> pageImpl = new PageImpl<>(new ArrayList<>());
        when(repository.listar(Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);
        ClienteController clienteController = new ClienteController(
                new ClienteService(repository, new ContatoService(mock(ContatoRepository.class))));

        Page<ClienteListagem> actualListarResult = clienteController.listar(new FiltroCliente(), null);

        verify(repository).listar(Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<Pageable>any());
        assertTrue(actualListarResult.toList().isEmpty());
        assertSame(pageImpl, actualListarResult);
    }

    /**
     * Testando o metodo: {@link ClienteController#listar(FiltroCliente, Pageable)}
     */
    @Test
    void testListar2() {

        ClienteService service = mock(ClienteService.class);
        PageImpl<ClienteListagem> pageImpl = new PageImpl<>(new ArrayList<>());
        when(service.listar(Mockito.<FiltroCliente>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);
        ClienteController clienteController = new ClienteController(service);

        Page<ClienteListagem> actualListarResult = clienteController.listar(new FiltroCliente(), null);

        verify(service).listar(Mockito.<FiltroCliente>any(), Mockito.<Pageable>any());
        assertTrue(actualListarResult.toList().isEmpty());
        assertSame(pageImpl, actualListarResult);
    }

    /**
     * Testando o metodo: {@link ClienteController#listar(FiltroCliente, Pageable)}
     */
    @Test
    void testListar3() {

        ClienteRepository repository = mock(ClienteRepository.class);
        PageImpl<ClienteListagem> pageImpl = new PageImpl<>(new ArrayList<>());
        when(repository.listar(Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);
        ClienteController clienteController = new ClienteController(
                new ClienteService(repository, new ContatoService(mock(ContatoRepository.class))));

        FiltroCliente filtro = new FiltroCliente();
        filtro.setEndereco("Endereco");
        filtro.setBairro(null);
        filtro.setNome(null);

        Page<ClienteListagem> actualListarResult = clienteController.listar(filtro, null);

        verify(repository).listar(Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<Pageable>any());
        assertEquals("%Endereco%", filtro.getEndereco());
        assertTrue(actualListarResult.toList().isEmpty());
        assertSame(pageImpl, actualListarResult);
    }

    /**
     * Testando o metodo: {@link ClienteController#listar(FiltroCliente, Pageable)}
     */
    @Test
    void testListar4() {

        ClienteRepository repository = mock(ClienteRepository.class);
        PageImpl<ClienteListagem> pageImpl = new PageImpl<>(new ArrayList<>());
        when(repository.listar(Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);
        ClienteController clienteController = new ClienteController(
                new ClienteService(repository, new ContatoService(mock(ContatoRepository.class))));

        FiltroCliente filtro = new FiltroCliente();
        filtro.setEndereco(null);
        filtro.setBairro("Bairro");
        filtro.setNome(null);

        Page<ClienteListagem> actualListarResult = clienteController.listar(filtro, null);

        verify(repository).listar(Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<Pageable>any());
        assertEquals("%Bairro%", filtro.getBairro());
        assertTrue(actualListarResult.toList().isEmpty());
        assertSame(pageImpl, actualListarResult);
    }

    /**
     * Testando o metodo: {@link ClienteController#listar(FiltroCliente, Pageable)}
     */
    @Test
    void testListar5() {

        ClienteRepository repository = mock(ClienteRepository.class);
        PageImpl<ClienteListagem> pageImpl = new PageImpl<>(new ArrayList<>());
        when(repository.listar(Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);
        ClienteController clienteController = new ClienteController(
                new ClienteService(repository, new ContatoService(mock(ContatoRepository.class))));

        FiltroCliente filtro = new FiltroCliente();
        filtro.setEndereco(null);
        filtro.setBairro(null);
        filtro.setNome("Nome");

        Page<ClienteListagem> actualListarResult = clienteController.listar(filtro, null);

        verify(repository).listar(Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<Pageable>any());
        assertEquals("%Nome%", filtro.getNome());
        assertTrue(actualListarResult.toList().isEmpty());
        assertSame(pageImpl, actualListarResult);
    }
}
