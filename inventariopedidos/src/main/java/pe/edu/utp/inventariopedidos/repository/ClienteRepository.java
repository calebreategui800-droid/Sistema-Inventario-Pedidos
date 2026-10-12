package pe.edu.utp.inventariopedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.inventariopedidos.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
