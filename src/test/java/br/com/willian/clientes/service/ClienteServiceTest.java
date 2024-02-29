package br.com.willian.clientes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.willian.clientes.exception.error.BusinessException;
import br.com.willian.clientes.model.Cliente;
import br.com.willian.clientes.model.Contato;
import br.com.willian.clientes.model.dto.ClienteListagem;
import br.com.willian.clientes.model.dto.FiltroCliente;
import br.com.willian.clientes.repository.ClienteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ClienteService.class})
@ExtendWith(SpringExtension.class)
class ClienteServiceTest {
    @MockBean
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @MockBean
    private ContatoService contatoService;

    /**
     * MTestando o metodo: {@link ClienteService#salvar(Cliente)}
     */
    @Test
    void testSalvar() {

        Cliente cliente = new Cliente();
        cliente.setBairro("Bairro");
        cliente.setContatos(new ArrayList<>());
        cliente.setEndereco("Endereco");
        cliente.setId(1L);
        cliente.setNome("Nome");

        assertThrows(BusinessException.class, () -> clienteService.salvar(cliente));
    }

    /**
     * MTestando o metodo: {@link ClienteService#salvar(Cliente)}
     */
    @Test
    void testSalvar2() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setBairro("Bairro");
        cliente.setContatos(new ArrayList<>());
        cliente.setEndereco("Endereco");
        cliente.setId(1L);
        cliente.setNome("Nome");
        when(clienteRepository.buscarPorIdAndNome(Mockito.<Long>any(), Mockito.<String>any())).thenReturn(cliente);
        Cliente cliente2 = mock(Cliente.class);
        when(cliente2.getId()).thenReturn(1L);
        when(cliente2.getNome()).thenReturn("limite.caracteres.nome.cliente");
        doNothing().when(cliente2).setBairro(Mockito.<String>any());
        doNothing().when(cliente2).setContatos(Mockito.<List<Contato>>any());
        doNothing().when(cliente2).setEndereco(Mockito.<String>any());
        doNothing().when(cliente2).setId(Mockito.<Long>any());
        doNothing().when(cliente2).setNome(Mockito.<String>any());
        cliente2.setBairro("Bairro");
        cliente2.setContatos(new ArrayList<>());
        cliente2.setEndereco("Endereco");
        cliente2.setId(1L);
        cliente2.setNome("Nome");

        assertThrows(BusinessException.class, () -> clienteService.salvar(cliente2));
        verify(cliente2).getId();
        verify(cliente2, atLeast(1)).getNome();
        verify(cliente2).setBairro(Mockito.<String>any());
        verify(cliente2).setContatos(Mockito.<List<Contato>>any());
        verify(cliente2).setEndereco(Mockito.<String>any());
        verify(cliente2).setId(Mockito.<Long>any());
        verify(cliente2).setNome(Mockito.<String>any());
        verify(clienteRepository).buscarPorIdAndNome(Mockito.<Long>any(), Mockito.<String>any());
    }

    /**
     * MTestando o metodo: {@link ClienteService#salvar(Cliente)}
     */
    @Test
    void testSalvar3() {

        Cliente cliente = mock(Cliente.class);
        when(cliente.getId()).thenThrow(new BusinessException("An error occurred"));
        when(cliente.getNome()).thenReturn("limite.caracteres.nome.cliente");
        doNothing().when(cliente).setBairro(Mockito.<String>any());
        doNothing().when(cliente).setContatos(Mockito.<List<Contato>>any());
        doNothing().when(cliente).setEndereco(Mockito.<String>any());
        doNothing().when(cliente).setId(Mockito.<Long>any());
        doNothing().when(cliente).setNome(Mockito.<String>any());
        cliente.setBairro("Bairro");
        cliente.setContatos(new ArrayList<>());
        cliente.setEndereco("Endereco");
        cliente.setId(1L);
        cliente.setNome("Nome");

        assertThrows(BusinessException.class, () -> clienteService.salvar(cliente));
        verify(cliente).getId();
        verify(cliente).getNome();
        verify(cliente).setBairro(Mockito.<String>any());
        verify(cliente).setContatos(Mockito.<List<Contato>>any());
        verify(cliente).setEndereco(Mockito.<String>any());
        verify(cliente).setId(Mockito.<Long>any());
        verify(cliente).setNome(Mockito.<String>any());
    }

    /**
     * MTestando o metodo: {@link ClienteService#buscarPorId(Long)}
     */
    @Test
    void testBuscarPorId() {

        Cliente cliente = new Cliente();
        cliente.setBairro("Bairro");
        cliente.setContatos(new ArrayList<>());
        cliente.setEndereco("Endereco");
        cliente.setId(1L);
        cliente.setNome("Nome");
        Optional<Cliente> ofResult = Optional.of(cliente);
        when(clienteRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Cliente actualBuscarPorIdResult = clienteService.buscarPorId(1L);

        verify(clienteRepository).findById(Mockito.<Long>any());
        assertSame(cliente, actualBuscarPorIdResult);
    }

    /**
     * MTestando o metodo: {@link ClienteService#buscarPorId(Long)}
     */
    @Test
    void testBuscarPorId2() {

        Optional<Cliente> emptyResult = Optional.empty();
        when(clienteRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        assertThrows(BusinessException.class, () -> clienteService.buscarPorId(1L));
        verify(clienteRepository).findById(Mockito.<Long>any());
    }

    /**
     * MTestando o metodo: {@link ClienteService#buscarPorId(Long)}
     */
    @Test
    void testBuscarPorId3() {

        when(clienteRepository.findById(Mockito.<Long>any())).thenThrow(new BusinessException("An error occurred"));

        assertThrows(BusinessException.class, () -> clienteService.buscarPorId(1L));
        verify(clienteRepository).findById(Mockito.<Long>any());
    }

    /**
     * MTestando o metodo: {@link ClienteService#excluir(Long)}
     */
    @Test
    void testExcluir() {

        doNothing().when(clienteRepository).deleteById(Mockito.<Long>any());

        clienteService.excluir(1L);

        verify(clienteRepository).deleteById(Mockito.<Long>any());
    }

    /**
     * MTestando o metodo: {@link ClienteService#excluir(Long)}
     */
    @Test
    void testExcluir2() {

        doThrow(new BusinessException("An error occurred")).when(clienteRepository).deleteById(Mockito.<Long>any());

        assertThrows(BusinessException.class, () -> clienteService.excluir(1L));
        verify(clienteRepository).deleteById(Mockito.<Long>any());
    }

    /**
     * MTestando o metodo: {@link ClienteService#listar(FiltroCliente, Pageable)}
     */
    @Test
    void testListar() {

        PageImpl<ClienteListagem> pageImpl = new PageImpl<>(new ArrayList<>());
        when(clienteRepository.listar(Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);

        Page<ClienteListagem> actualListarResult = clienteService.listar(new FiltroCliente(), null);

        verify(clienteRepository).listar(Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Pageable>any());
        assertTrue(actualListarResult.toList().isEmpty());
        assertSame(pageImpl, actualListarResult);
    }

    /**
     * MTestando o metodo: {@link ClienteService#listar(FiltroCliente, Pageable)}
     */
    @Test
    void testListar2() {

        PageImpl<ClienteListagem> pageImpl = new PageImpl<>(new ArrayList<>());
        when(clienteRepository.listar(Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);
        FiltroCliente filtro = new FiltroCliente(1L, "Nome", "Endereco", "Bairro", "Telefone");

        Page<ClienteListagem> actualListarResult = clienteService.listar(filtro, null);

        verify(clienteRepository).listar(Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Pageable>any());
        assertEquals("%Bairro%", filtro.getBairro());
        assertEquals("%Endereco%", filtro.getEndereco());
        assertEquals("%Nome%", filtro.getNome());
        assertTrue(actualListarResult.toList().isEmpty());
        assertSame(pageImpl, actualListarResult);
    }

    /**
     * MTestando o metodo: {@link ClienteService#listar(FiltroCliente, Pageable)}
     */
    @Test
    void testListar3() {

        when(clienteRepository.listar(Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Pageable>any()))
                .thenThrow(new BusinessException("An error occurred"));

        assertThrows(BusinessException.class, () -> clienteService.listar(new FiltroCliente(), null));
        verify(clienteRepository).listar(Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Pageable>any());
    }
}
