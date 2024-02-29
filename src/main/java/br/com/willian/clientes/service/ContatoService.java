package br.com.willian.clientes.service;

import br.com.willian.clientes.exception.error.BusinessException;
import br.com.willian.clientes.exception.mensagem.MensagemNegocio;
import br.com.willian.clientes.model.Contato;
import br.com.willian.clientes.repository.ContatoRepository;
import br.com.willian.clientes.util.TelefoneUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Serviço para realizar operações com a entidade Contato.
 *
 * @author Willian.Soares
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ContatoService {

    /**
     * Repositório para acesso e gerenciamento de entidades Contato no banco de dados.
     */
    private final ContatoRepository repository;


    /**
     * Valida uma lista de contatos.
     *
     * @param contatos A lista de contatos a ser validada.
     * @throws BusinessException Se algum contato possuir um número de telefone inválido ou um número de telefone duplicado dentro de um cliente.
     */
    public void validarContatos(List<Contato> contatos) {
        contatos.forEach(contato -> {
            String telefone = TelefoneUtil.extrairNumeros(contato.getTelefone());
            String telefoneValidado = TelefoneUtil.extrairNumero(telefone);
            if (telefoneValidado == null) {
                throw new BusinessException(MensagemNegocio.TELEFONE_INVALIDO, contato.getTelefone());
            }
            contato.setTelefone(telefoneValidado);
            Contato contatoExistente = repository.buscarPorIdAndTelefone(contato.getCliente() != null ? contato.getCliente().getId() : null,
                    contato.getTelefone());
            if (contatoExistente != null) {
                throw new BusinessException(MensagemNegocio.JA_EXISTE_TELEFONE, contatoExistente.getTelefone(),
                        contatoExistente.getCliente().getNome());
            }
        });
    }

    /**
     * Salva um contato no banco de dados.
     *
     * @param contato O contato a ser salvo.
     * @return O contato salvo com o ID gerado.
     * @throws BusinessException Se o contato possuir um número de telefone inválido ou um número de telefone duplicado dentro de um cliente.
     */
    public Contato salvar(Contato contato) {
        validarContatos(Collections.singletonList(contato));
        return repository.save(contato);
    }

    /**
     * Exclui um contato pelo seu ID.
     *
     * @param id O ID do contato a ser excluído.
     */
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    /**
     * Lista contatos associados a um cliente, com paginação.
     *
     * @param idCliente O ID do cliente para listar contatos associados.
     * @param pageable Parâmetros de paginação.
     * @return Uma página de contatos associados ao cliente.
     */
    public Page<Contato> listarPorCliente(Long idCliente, Pageable pageable) {
        return repository.listarPorCliente(idCliente, pageable);
    }
}
