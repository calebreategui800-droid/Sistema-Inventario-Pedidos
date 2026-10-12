package pe.edu.utp.inventariopedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.inventariopedidos.model.Pedido;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Spring Data genera la consulta a partir del nombre del metodo:
    // SELECT * FROM pedidos WHERE cliente_id = ? ORDER BY id ASC
    List<Pedido> findByClienteIdOrderByIdAsc(Long clienteId);
}
