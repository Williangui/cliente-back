package br.com.willian.clientes.model;

import br.com.willian.clientes.model.dto.ContatoDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Representa um contato associado a um cliente.
 *
 * @author Willian.Soares
 * @version 1.0
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Contato {

    /**
     * Identificador único do contato.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Telefone do contato (obrigatório e único).
     */
    @Column(nullable = false, unique = true)
    private String telefone;

    /**
     * Cliente ao qual o contato está associado (relacionamento ManyToOne).
     *
     * <p>
     * A anotação @JsonIgnore indica que este campo não deve ser serializado ou desserializado durante a conversão para JSON.
     * <p>
     * Carregamento lazy (FetchType.LAZY) significa que o cliente associado só será carregado do banco de dados quando for necessário, evitando consultas desnecessárias.
     * </p>
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

    public Contato(ContatoDTO contato) {
        this.id = contato.getId();
        this.telefone = contato.getTelefone();
        this.cliente = new Cliente();
        this.cliente.setId(contato.getIdCliente());
    }
}

