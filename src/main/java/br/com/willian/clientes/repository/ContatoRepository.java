package br.com.willian.clientes.repository;

import br.com.willian.clientes.model.Contato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repositório para realizar operações com a entidade Contato no banco de dados.
 *
 * @author Willian.Soares
 * @version 1.0
 */
public interface ContatoRepository extends JpaRepository<Contato, Long> {

    /**
     * Busca um contato por telefone, ignorando um cliente específico caso seja informado.
     *
     * @param idCliente ID do cliente a ser ignorado na busca (opcional).
     * @param telefone Telefone do contato a ser buscado.
     * @return Contato encontrado, ou null se não houver contato com o telefone informado.
     */
    @Query("SELECT ctt FROM Contato ctt                         " +
            "INNER JOIN Cliente cli ON cli.id = ctt.cliente.id  " +
            "WHERE (:idCliente IS NULL OR cli.id <> :idCliente) " +
            "AND ctt.telefone = :telefone                       ")
    Contato buscarPorIdAndTelefone(Long idCliente, String telefone);

    /**
     * Lista os contatos associados a um cliente, com paginação.
     *
     * @param idCliente ID do cliente a ser pesquisado.
     * @param pageable Parâmetros de paginação.
     * @return Página com os contatos encontrados.
     */
    @Query("SELECT ctt FROM Contato ctt " +
            "INNER JOIN Cliente cli ON cli.id = ctt.cliente.id    " +
            "WHERE cli.id = :idCliente  ")
    Page<Contato> listarPorCliente(Long idCliente, Pageable pageable);
}
