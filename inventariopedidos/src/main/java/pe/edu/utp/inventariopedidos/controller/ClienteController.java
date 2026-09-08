package pe.edu.utp.inventariopedidos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.edu.utp.inventariopedidos.model.Cliente;
import pe.edu.utp.inventariopedidos.service.ClienteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // GET - Listar todos los clientes
    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteService.listarTodos();
    }

    // GET - Buscar un cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {

        Optional<Cliente> cliente = clienteService.buscarPorId(id);

        return cliente
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST - Crear un nuevo cliente
    @PostMapping
    public ResponseEntity<Cliente> crear(@RequestBody Cliente cliente) {

        Cliente nuevo = clienteService.guardar(cliente);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevo);
    }

    // PUT - Actualizar un cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(
            @PathVariable Long id,
            @RequestBody Cliente cliente) {

        return clienteService
                .actualizar(id, cliente)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE - Eliminar un cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        boolean eliminado = clienteService.eliminar(id);

        if (eliminado) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}