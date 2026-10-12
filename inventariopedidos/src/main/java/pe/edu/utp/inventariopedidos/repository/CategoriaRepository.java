package pe.edu.utp.inventariopedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.inventariopedidos.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
