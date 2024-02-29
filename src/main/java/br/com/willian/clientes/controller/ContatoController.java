package br.com.willian.clientes.controller;

import br.com.willian.clientes.model.Contato;
import br.com.willian.clientes.model.dto.ContatoDTO;
import br.com.willian.clientes.service.ContatoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsável pelo gerenciamento de contatos.
 *
 * @author Willian.Soares
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contato")
public class ContatoController {

    /**
     * Serviço de contato para operações de negócio.
     */
    private final ContatoService service;

    /**
     * Salva um novo contato.
     *
     * @param contato Objeto contato a ser salvo.
     * @return Contato salvo.
     */
    @PostMapping("/salvar")
    public Contato salvar(@RequestBody ContatoDTO contato) {
        return service.salvar(new Contato(contato));
    }

    /**
     * Exclui um contato por seu ID.
     *
     * @param id ID do contato a ser excluído.
     */
    @DeleteMapping("/excluir/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }

    /**
     * Lista contatos associados a um cliente específico.
     *
     * @param idCliente ID do cliente para o qual deseja listar os contatos.
     * @param pageable Objeto de paginação.
     * @return Página com os contatos encontrados.
     */
    @PostMapping("/listarPorCliente")
    public Page<Contato> listarPorCliente(@RequestBody Long idCliente, Pageable pageable) {
        return service.listarPorCliente(idCliente, pageable);
    }
}
