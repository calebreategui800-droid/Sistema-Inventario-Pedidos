package pe.edu.utp.inventariopedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.inventariopedidos.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
