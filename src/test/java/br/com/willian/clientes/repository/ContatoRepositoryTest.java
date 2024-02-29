package br.com.willian.clientes.repository;

import br.com.willian.clientes.model.Cliente;
import br.com.willian.clientes.model.Contato;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {ContatoRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"br.com.willian.clientes.model"})
@DataJpaTest
class ContatoRepositoryTest {

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Testa o método {@link ContatoRepository#buscarPorIdAndTelefone(Long, String)}
     */
    @Test
    void testBuscarPorIdAndTelefone() {

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setBairro("Bairro");
        cliente.setContatos(new ArrayList<>());
        cliente.setEndereco("Endereco");
        cliente.setNome("Nome");
        Contato contato = new Contato();
        contato.setCliente(cliente);
        contato.setTelefone("123456789");
        clienteRepository.save(cliente);
        contatoRepository.save(contato);

        Contato contatoEncontrado = contatoRepository.buscarPorIdAndTelefone(null, "123456789");

        assertNotNull(contatoEncontrado);
        assertEquals(contato.getId(), contatoEncontrado.getId());
        assertEquals(contato.getCliente().getId(), contatoEncontrado.getCliente().getId());
        assertEquals(contato.getTelefone(), contatoEncontrado.getTelefone());

        contatoEncontrado = contatoRepository.buscarPorIdAndTelefone(1L, "987654321");

        assertNull(contatoEncontrado);
    }

    /**
     * Testa o método {@link ContatoRepository#listarPorCliente(Long, Pageable)}
     */
    @Test
    void testListarPorCliente() {

        Cliente cliente = new Cliente();
        cliente.setBairro("Bairro");
        cliente.setContatos(new ArrayList<>());
        cliente.setEndereco("Telefone");
        cliente.setNome("Nome");

        Contato contato1 = new Contato();
        contato1.setCliente(cliente);
        contato1.setTelefone("123456789");

        Contato contato2 = new Contato();
        contato2.setCliente(cliente);
        contato2.setTelefone("Telefone2");

        clienteRepository.save(cliente);
        contatoRepository.save(contato1);
        contatoRepository.save(contato2);

        Page<Contato> page = contatoRepository.listarPorCliente(cliente.getId(), Pageable.unpaged());

        assertEquals(2, page.getTotalElements());
        assertTrue(page.getContent().contains(contato1));
        assertTrue(page.getContent().contains(contato2));
    }
}

