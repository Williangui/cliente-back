package br.com.willian.clientes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * Representa um cliente do sistema.
 *
 * @author Willian.Soares
 * @version 1.0
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    /**
     * Identificador único do cliente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do cliente (obrigatório e único, minimo de 10 caracteres).
     */
    @Column(nullable = false, unique = true)
    private String nome;

    /**
     * Endereço do cliente.
     */
    private String endereco;

    /**
     * Bairro do cliente.
     */
    private String bairro;

    /**
     * Lista de contatos associados ao cliente.
     *
     * <p>
     * Relacionamento OneToMany com a classe Contato.
     * <p>
     * Cascata de remoção: quando um cliente é deletado, seus contatos associados também serão excluídos.
     * <p>
     * Remoção órfã: se um contato for removido da lista de contatos do cliente, mas persistir no banco de dados, ele será removido do banco.
     * </p>
     */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Contato> contatos;
}

