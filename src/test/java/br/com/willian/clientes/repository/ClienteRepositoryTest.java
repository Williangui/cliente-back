package br.com.willian.clientes.repository;

import br.com.willian.clientes.model.Cliente;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {ClienteRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"br.com.willian.clientes.model"})
@DataJpaTest
class ClienteRepositoryTest {
    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Testando o metodo: {@link ClienteRepository#buscarPorIdAndNome(Long, String)}
     */
    @Test
    void testBuscarPorIdAndNome() {

        Cliente cliente = new Cliente();
        cliente.setBairro("Bairro");
        cliente.setContatos(new ArrayList<>());
        cliente.setEndereco("Endereco");
        cliente.setNome("Nome");

        Cliente cliente2 = new Cliente();
        cliente2.setBairro("Bairro2");
        cliente2.setContatos(new ArrayList<>());
        cliente2.setEndereco("Endereco2");
        cliente2.setNome("Nome2");
        clienteRepository.save(cliente);
        clienteRepository.save(cliente2);

        clienteRepository.buscarPorIdAndNome(1L, "Nome");
    }

    /**
     * Testando o metodo:
     * {@link ClienteRepository#listar(Long, String, String, String, String, Pageable)}
     */
    @Test
    void testListar() {
        Cliente cliente = new Cliente();
        cliente.setBairro("Bairro");
        cliente.setContatos(new ArrayList<>());
        cliente.setEndereco("Endereco");
        cliente.setNome("Nome");

        Cliente cliente2 = new Cliente();
        cliente2.setBairro("Bairro2");
        cliente2.setContatos(new ArrayList<>());
        cliente2.setEndereco("Endereco2");
        cliente2.setNome("Nome2");
        clienteRepository.save(cliente);
        clienteRepository.save(cliente2);

        clienteRepository.listar(1L, "Nome", "Endereco", "Bairro", "Telefone", Pageable.unpaged());
    }
}
