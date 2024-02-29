package br.com.willian.clientes.controller;

import br.com.willian.clientes.model.Cliente;
import br.com.willian.clientes.model.dto.ClienteListagem;
import br.com.willian.clientes.model.dto.FiltroCliente;
import br.com.willian.clientes.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsável pelo gerenciamento de clientes.
 *
 * @author Willian.Soares
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cliente")
public class ClienteController {

    /**
     * Serviço de cliente para operações de negócio.
     */
    private final ClienteService service;

    /**
     * Salva um novo cliente.
     *
     * @param cliente Objeto cliente a ser salvo.
     * @return Cliente salvo.
     */
    @PostMapping("/salvar")
    public Cliente salvar(@RequestBody Cliente cliente) {
        return service.salvar(cliente);
    }

    /**
     * Busca um cliente por seu ID.
     *
     * @param id ID do cliente a ser buscado.
     * @return Cliente encontrado.
     */
    @GetMapping("/buscarPorId/{id}")
    public Cliente buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    /**
     * Exclui um cliente por seu ID.
     *
     * @param id ID do cliente a ser excluído.
     */
    @DeleteMapping("/excluir/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }

    /**
     * Lista clientes de acordo com os filtros informados.
     *
     * @param filtro Objeto com os filtros de busca.
     * @param pageable Objeto de paginação.
     * @return Página com os clientes encontrados.
     */
    @PostMapping("/listar")
    public Page<ClienteListagem> listar(@RequestBody FiltroCliente filtro, Pageable pageable) {
        return service.listar(filtro, pageable);
    }
}