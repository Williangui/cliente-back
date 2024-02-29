package br.com.willian.clientes.repository;

import br.com.willian.clientes.model.Cliente;
import br.com.willian.clientes.model.dto.ClienteListagem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repositório para realizar operações com a entidade Cliente no banco de dados.
 *
 * @author Willian.Soares
 * @version 1.0
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Busca um cliente por nome, ignorando um ID específico caso seja informado.
     *
     * @param id ID a ser ignorado na busca (opcional).
     * @param nome Nome do cliente a ser buscado.
     * @return Cliente encontrado, ou null se não houver cliente com o nome informado.
     */
    @Query("SELECT cli FROM Cliente cli             " +
            "WHERE (:id IS NULL OR cli.id <> :id)   " +
            "AND cli.nome = :nome                   ")
    Cliente buscarPorIdAndNome(Long id, String nome);

    /**
     * Lista clientes com critérios de pesquisa flexíveis, incluindo paginação.
     *
     * @param id ID do cliente a ser buscado (opcional).
     * @param nome Nome do cliente (LIKE) (opcional).
     * @param endereco Endereço do cliente (LIKE) (opcional).
     * @param bairro Bairro do cliente (LIKE) (opcional).
     * @param telefone Telefone do cliente (exato) (opcional).
     * @param pageable Parâmetros de paginação.
     * @return Página com informações resumidas dos clientes encontrados.
     */
    @Query("SELECT DISTINCT new br.com.willian.clientes.model.dto.ClienteListagem(cli.id, cli.nome)  " +
            "FROM Cliente cli                                                               " +
            "LEFT JOIN Contato ctt ON ctt.cliente.id = cli.id                               " +
            "WHERE (:id IS NULL OR cli.id = :id)                                            " +
            "AND (:nome IS NULL OR cli.nome LIKE :nome)                                     " +
            "AND (:endereco IS NULL OR cli.endereco LIKE :endereco)                         " +
            "AND (:bairro IS NULL OR cli.bairro LIKE :bairro)                               " +
            "AND (:telefone IS NULL OR ctt.telefone = :telefone)                            ")
    Page<ClienteListagem> listar(Long id, String nome, String endereco, String bairro, String telefone,
                                 Pageable pageable);
}
