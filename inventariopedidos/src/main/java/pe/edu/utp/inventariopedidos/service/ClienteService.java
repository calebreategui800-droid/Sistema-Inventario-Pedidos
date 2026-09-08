package pe.edu.utp.inventariopedidos.service;

import org.springframework.stereotype.Service;
import pe.edu.utp.inventariopedidos.model.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final List<Cliente> clientes = new ArrayList<>();
    private Long contadorId = 1L;

    public ClienteService() {

        clientes.add(new Cliente(
                contadorId++,
                "Carlos",
                "Ramirez",
                "carlos@gmail.com",
                "987654321",
                "ACTIVO"
        ));

        clientes.add(new Cliente(
                contadorId++,
                "Maria",
                "Lopez",
                "maria@gmail.com",
                "956123789",
                "ACTIVO"
        ));

        clientes.add(new Cliente(
                contadorId++,
                "Luis",
                "Torres",
                "luis@gmail.com",
                "945678123",
                "ACTIVO"
        ));
    }

    public List<Cliente> listarTodos() {
        return clientes;
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clientes.stream()
                .filter(cliente -> cliente.getId().equals(id))
                .findFirst();
    }

    public Cliente guardar(Cliente cliente) {
        cliente.setId(contadorId++);
        clientes.add(cliente);
        return cliente;
    }

    public Optional<Cliente> actualizar(Long id, Cliente datosNuevos) {

        Optional<Cliente> existente = buscarPorId(id);

        if (existente.isPresent()) {

            Cliente cliente = existente.get();

            cliente.setNombre(datosNuevos.getNombre());
            cliente.setApellido(datosNuevos.getApellido());
            cliente.setEmail(datosNuevos.getEmail());
            cliente.setTelefono(datosNuevos.getTelefono());
            cliente.setEstado(datosNuevos.getEstado());

            return Optional.of(cliente);
        }

        return Optional.empty();
    }

    public boolean eliminar(Long id) {
        return clientes.removeIf(
                cliente -> cliente.getId().equals(id)
        );
    }
}