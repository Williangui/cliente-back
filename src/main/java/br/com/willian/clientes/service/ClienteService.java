package br.com.willian.clientes.service;

import br.com.willian.clientes.exception.error.BusinessException;
import br.com.willian.clientes.exception.mensagem.MensagemNegocio;
import br.com.willian.clientes.model.Cliente;
import br.com.willian.clientes.model.dto.ClienteListagem;
import br.com.willian.clientes.model.dto.FiltroCliente;
import br.com.willian.clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Serviço para realizar operações com a entidade Cliente.
 *
 * @author Willian.Soares
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    private final ContatoService contatoService;

    /**
     * Salva um cliente no banco de dados.
     *
     * @param cliente Cliente a ser salvo.
     * @return Cliente salvo com o ID gerado.
     * @throws BusinessException Se o nome do cliente for maior que 10 caracteres ou já existir no sistema.
     */
    public Cliente salvar(Cliente cliente) {
        validar(cliente);
        Cliente clienteSalvo = repository.save(cliente);
        if (cliente.getContatos() != null) {
            cliente.getContatos().forEach(contato -> {
                contato.setCliente(clienteSalvo);
                contatoService.salvar(contato);
            });
        }
        return cliente;
    }

    /**
     * Busca um cliente pelo seu ID.
     *
     * @param id ID do cliente a ser buscado.
     * @return Cliente encontrado, ou uma exceção `BusinessException` se o cliente não for encontrado.
     * @throws BusinessException Se o cliente não for encontrado.
     */
    public Cliente buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(MensagemNegocio.CLIENTE_NAO_ENCONTRADO));
    }

    /**
     * Exclui um cliente pelo seu ID.
     *
     * @param id ID do cliente a ser excluído.
     */
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    /**
     * Lista clientes com critérios de pesquisa flexíveis, incluindo paginação.
     *
     * @param filtro Filtro com os parâmetros de pesquisa.
     * @param pageable Parâmetros de paginação.
     * @return Página com informações resumidas dos clientes encontrados.
     */
    public Page<ClienteListagem> listar(FiltroCliente filtro, Pageable pageable) {
        ajustarFiltros(filtro);
        return repository.listar(filtro.getId(), filtro.getNome(), filtro.getEndereco(), filtro.getBairro(),
                filtro.getTelefone(), pageable);
    }


    private void ajustarFiltros(FiltroCliente filtro) {
        if (filtro.getNome() != null) {
            filtro.setNome("%".concat(filtro.getNome()).concat("%"));
        }
        if (filtro.getEndereco() != null) {
            filtro.setEndereco("%".concat(filtro.getEndereco()).concat("%"));
        }
        if (filtro.getBairro() != null) {
            filtro.setBairro("%".concat(filtro.getBairro()).concat("%"));
        }
    }


    private void validar(Cliente cliente) {
        if (cliente.getNome().length() < 10) {
            throw new BusinessException(MensagemNegocio.LIMITE_CARACTERES_NOME_CLIENTE);
        }
        Cliente clienteExistente = repository.buscarPorIdAndNome(cliente.getId(), cliente.getNome());
        if (clienteExistente != null) {
            throw new BusinessException(MensagemNegocio.NOME_CLIENTE_JA_EXISTE, cliente.getNome());
        }
    }
}
