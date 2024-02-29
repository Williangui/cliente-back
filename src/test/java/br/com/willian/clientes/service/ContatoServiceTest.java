package br.com.willian.clientes.service;

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
import br.com.willian.clientes.repository.ContatoRepository;

import java.util.ArrayList;
import java.util.List;

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

@ContextConfiguration(classes = {ContatoService.class})
@ExtendWith(SpringExtension.class)
class ContatoServiceTest {
    @MockBean
    private ContatoRepository contatoRepository;

    @Autowired
    private ContatoService contatoService;

    /**
     * Testa o metodo: {@link ContatoService#validarContatos(List)}
     */
    @Test
    void testValidarContatos() {
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
        ArrayList<Contato> contatos = new ArrayList<>();
        contatos.add(contato);
        assertThrows(BusinessException.class, () -> contatoService.validarContatos(contatos));
    }

    /**
     * Testa o metodo: {@link ContatoService#validarContatos(List)}
     */
    @Test
    void testValidarContatos2() {
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
        Cliente cliente2 = new Cliente();
        cliente2.setBairro("Bairro2");
        cliente2.setContatos(new ArrayList<>());
        cliente2.setEndereco("Endereco2");
        cliente2.setId(2L);
        cliente2.setNome("Nome2");
        Contato contato2 = new Contato();
        contato2.setCliente(cliente2);
        contato2.setId(2L);
        contato2.setTelefone("Telefone2");
        ArrayList<Contato> contatos = new ArrayList<>();
        contatos.add(contato2);
        contatos.add(contato);
        assertThrows(BusinessException.class, () -> contatoService.validarContatos(contatos));
    }

    /**
     * Testa o metodo: {@link ContatoService#validarContatos(List)}
     */
    @Test
    void testValidarContatos3() {
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
        when(contatoRepository.buscarPorIdAndTelefone(Mockito.<Long>any(), Mockito.<String>any())).thenReturn(contato);
        Cliente cliente2 = new Cliente();
        cliente2.setBairro("Bairro");
        cliente2.setContatos(new ArrayList<>());
        cliente2.setEndereco("Endereco");
        cliente2.setId(1L);
        cliente2.setNome("Nome");
        Contato contato2 = new Contato();
        contato2.setCliente(cliente2);
        contato2.setId(1L);
        contato2.setTelefone("(99)9999-9999");
        ArrayList<Contato> contatos = new ArrayList<>();
        contatos.add(contato2);
        assertThrows(BusinessException.class, () -> contatoService.validarContatos(contatos));
        verify(contatoRepository).buscarPorIdAndTelefone(Mockito.<Long>any(), Mockito.<String>any());
    }

    /**
     * Testa o metodo: {@link ContatoService#validarContatos(List)}
     */
    @Test
    void testValidarContatos4() {
        Cliente cliente = new Cliente();
        cliente.setBairro("Bairro");
        cliente.setContatos(new ArrayList<>());
        cliente.setEndereco("Endereco");
        cliente.setId(1L);
        cliente.setNome("Nome");
        Contato contato = mock(Contato.class);
        when(contato.getTelefone()).thenReturn("Telefone");
        doNothing().when(contato).setCliente(Mockito.<Cliente>any());
        doNothing().when(contato).setId(Mockito.<Long>any());
        doNothing().when(contato).setTelefone(Mockito.<String>any());
        contato.setCliente(cliente);
        contato.setId(1L);
        contato.setTelefone("Telefone");
        Cliente cliente2 = new Cliente();
        cliente2.setBairro("Bairro");
        cliente2.setContatos(new ArrayList<>());
        cliente2.setEndereco("Endereco");
        cliente2.setId(1L);
        cliente2.setNome("Nome");
        Contato contato2 = mock(Contato.class);
        when(contato2.getTelefone()).thenReturn("");
        doNothing().when(contato2).setCliente(Mockito.<Cliente>any());
        doNothing().when(contato2).setId(Mockito.<Long>any());
        doNothing().when(contato2).setTelefone(Mockito.<String>any());
        contato2.setCliente(cliente2);
        contato2.setId(1L);
        contato2.setTelefone("(99)9999-9999");
        ArrayList<Contato> contatos = new ArrayList<>();
        contatos.add(contato2);
        assertThrows(BusinessException.class, () -> contatoService.validarContatos(contatos));
        verify(contato2, atLeast(1)).getTelefone();
        verify(contato).setCliente(Mockito.<Cliente>any());
        verify(contato2).setCliente(Mockito.<Cliente>any());
        verify(contato).setId(Mockito.<Long>any());
        verify(contato2).setId(Mockito.<Long>any());
        verify(contato).setTelefone(Mockito.<String>any());
        verify(contato2).setTelefone(Mockito.<String>any());
    }

    /**
     * Testa o metodo: {@link ContatoService#salvar(Contato)}
     */
    @Test
    void testSalvar() {
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
        assertThrows(BusinessException.class, () -> contatoService.salvar(contato));
    }

    /**
     * Testa o metodo: {@link ContatoService#salvar(Contato)}
     */
    @Test
    void testSalvar2() {
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
        when(contatoRepository.buscarPorIdAndTelefone(Mockito.<Long>any(), Mockito.<String>any())).thenReturn(contato);
        Cliente cliente2 = new Cliente();
        cliente2.setBairro("Bairro");
        cliente2.setContatos(new ArrayList<>());
        cliente2.setEndereco("Endereco");
        cliente2.setId(1L);
        cliente2.setNome("Nome");
        Cliente cliente3 = new Cliente();
        cliente3.setBairro("Bairro");
        cliente3.setContatos(new ArrayList<>());
        cliente3.setEndereco("Endereco");
        cliente3.setId(1L);
        cliente3.setNome("Nome");
        Contato contato2 = mock(Contato.class);
        when(contato2.getCliente()).thenReturn(cliente3);
        when(contato2.getTelefone()).thenReturn("(99)9999-9999");
        doNothing().when(contato2).setCliente(Mockito.<Cliente>any());
        doNothing().when(contato2).setId(Mockito.<Long>any());
        doNothing().when(contato2).setTelefone(Mockito.<String>any());
        contato2.setCliente(cliente2);
        contato2.setId(1L);
        contato2.setTelefone("Telefone");
        assertThrows(BusinessException.class, () -> contatoService.salvar(contato2));
        verify(contato2, atLeast(1)).getCliente();
        verify(contato2, atLeast(1)).getTelefone();
        verify(contato2).setCliente(Mockito.<Cliente>any());
        verify(contato2).setId(Mockito.<Long>any());
        verify(contato2, atLeast(1)).setTelefone(Mockito.<String>any());
        verify(contatoRepository).buscarPorIdAndTelefone(Mockito.<Long>any(), Mockito.<String>any());
    }

    /**
     * Testa o metodo: {@link ContatoService#salvar(Contato)}
     */
    @Test
    void testSalvar3() {
        Cliente cliente = new Cliente();
        cliente.setBairro("Bairro");
        cliente.setContatos(new ArrayList<>());
        cliente.setEndereco("Endereco");
        cliente.setId(1L);
        cliente.setNome("Nome");
        Contato contato = mock(Contato.class);
        when(contato.getCliente()).thenThrow(new BusinessException("Ocorreu um erro ao tentar salvar cliente"));
        when(contato.getTelefone()).thenReturn("(99)9999-9999");
        doNothing().when(contato).setCliente(Mockito.<Cliente>any());
        doNothing().when(contato).setId(Mockito.<Long>any());
        doNothing().when(contato).setTelefone(Mockito.<String>any());
        contato.setCliente(cliente);
        contato.setId(1L);
        contato.setTelefone("Telefone");
        assertThrows(BusinessException.class, () -> contatoService.salvar(contato));
        verify(contato).getCliente();
        verify(contato).getTelefone();
        verify(contato).setCliente(Mockito.<Cliente>any());
        verify(contato).setId(Mockito.<Long>any());
        verify(contato, atLeast(1)).setTelefone(Mockito.<String>any());
    }

    /**
     * Testa o metodo: {@link ContatoService#salvar(Contato)}
     */
    @Test
    void testSalvar4() {
        Cliente cliente = new Cliente();
        cliente.setBairro("Bairro");
        cliente.setContatos(new ArrayList<>());
        cliente.setEndereco("Endereco");
        cliente.setId(1L);
        cliente.setNome("Nome");
        Contato contato = mock(Contato.class);
        when(contato.getTelefone()).thenReturn("Telefone");
        doNothing().when(contato).setCliente(Mockito.<Cliente>any());
        doNothing().when(contato).setId(Mockito.<Long>any());
        doNothing().when(contato).setTelefone(Mockito.<String>any());
        contato.setCliente(cliente);
        contato.setId(1L);
        contato.setTelefone("Telefone");
        Cliente cliente2 = new Cliente();
        cliente2.setBairro("Bairro");
        cliente2.setContatos(new ArrayList<>());
        cliente2.setEndereco("Endereco");
        cliente2.setId(1L);
        cliente2.setNome("Nome");
        Contato contato2 = mock(Contato.class);
        when(contato2.getTelefone()).thenReturn("");
        doNothing().when(contato2).setCliente(Mockito.<Cliente>any());
        doNothing().when(contato2).setId(Mockito.<Long>any());
        doNothing().when(contato2).setTelefone(Mockito.<String>any());
        contato2.setCliente(cliente2);
        contato2.setId(1L);
        contato2.setTelefone("Telefone");
        assertThrows(BusinessException.class, () -> contatoService.salvar(contato2));
        verify(contato2, atLeast(1)).getTelefone();
        verify(contato).setCliente(Mockito.<Cliente>any());
        verify(contato2).setCliente(Mockito.<Cliente>any());
        verify(contato).setId(Mockito.<Long>any());
        verify(contato2).setId(Mockito.<Long>any());
        verify(contato).setTelefone(Mockito.<String>any());
        verify(contato2).setTelefone(Mockito.<String>any());
    }

    /**
     * Testa o metodo: {@link ContatoService#excluir(Long)}
     */
    @Test
    void testExcluir() {
        doNothing().when(contatoRepository).deleteById(Mockito.<Long>any());
        contatoService.excluir(1L);
        verify(contatoRepository).deleteById(Mockito.<Long>any());
    }

    /**
     * Testa o metodo: {@link ContatoService#excluir(Long)}
     */
    @Test
    void testExcluir2() {
        doThrow(new BusinessException("Ocorreu um erro ao tentar excluir")).when(contatoRepository)
                .deleteById(Mockito.<Long>any());
        assertThrows(BusinessException.class, () -> contatoService.excluir(1L));
        verify(contatoRepository).deleteById(Mockito.<Long>any());
    }

    /**
     * Testa o metodo: {@link ContatoService#listarPorCliente(Long, Pageable)}
     */
    @Test
    void testListarPorCliente() {
        PageImpl<Contato> pageImpl = new PageImpl<>(new ArrayList<>());
        when(contatoRepository.listarPorCliente(Mockito.<Long>any(), Mockito.<Pageable>any())).thenReturn(pageImpl);
        Page<Contato> actualListarPorClienteResult = contatoService.listarPorCliente(1L, null);
        verify(contatoRepository).listarPorCliente(Mockito.<Long>any(), Mockito.<Pageable>any());
        assertTrue(actualListarPorClienteResult.toList().isEmpty());
        assertSame(pageImpl, actualListarPorClienteResult);
    }

    /**
     * Testa o metodo: {@link ContatoService#listarPorCliente(Long, Pageable)}
     */
    @Test
    void testListarPorCliente2() {
        when(contatoRepository.listarPorCliente(Mockito.<Long>any(), Mockito.<Pageable>any()))
                .thenThrow(new BusinessException("Ocorreu um erro ao tentar listar por cliente"));
        assertThrows(BusinessException.class, () -> contatoService.listarPorCliente(1L, null));
        verify(contatoRepository).listarPorCliente(Mockito.<Long>any(), Mockito.<Pageable>any());
    }
}
