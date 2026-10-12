package pe.edu.utp.inventariopedidos.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.inventariopedidos.model.Pedido;
import pe.edu.utp.inventariopedidos.repository.PedidoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll(Sort.by("id"));
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public List<Pedido> buscarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteIdOrderByIdAsc(clienteId);
    }

    public Pedido guardar(Pedido pedido) {
        pedido.setId(null); // el id lo genera la base de datos
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Optional<Pedido> actualizar(Long id, Pedido datosNuevos) {
        return pedidoRepository.findById(id).map(pedido -> {
            pedido.setClienteId(datosNuevos.getClienteId());
            pedido.setFechaPedido(datosNuevos.getFechaPedido());
            pedido.setEstado(datosNuevos.getEstado());
            pedido.setTotal(datosNuevos.getTotal());
            return pedidoRepository.save(pedido);
        });
    }

    public boolean eliminar(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
