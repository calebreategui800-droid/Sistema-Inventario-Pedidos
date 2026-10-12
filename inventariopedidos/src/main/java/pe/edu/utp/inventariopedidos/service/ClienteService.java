package pe.edu.utp.inventariopedidos.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.inventariopedidos.model.Cliente;
import pe.edu.utp.inventariopedidos.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll(Sort.by("id"));
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente guardar(Cliente cliente) {
        cliente.setId(null); // el id lo genera la base de datos
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Optional<Cliente> actualizar(Long id, Cliente datosNuevos) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNombre(datosNuevos.getNombre());
            cliente.setApellido(datosNuevos.getApellido());
            cliente.setEmail(datosNuevos.getEmail());
            cliente.setTelefono(datosNuevos.getTelefono());
            cliente.setEstado(datosNuevos.getEstado());
            return clienteRepository.save(cliente);
        });
    }

    public boolean eliminar(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
