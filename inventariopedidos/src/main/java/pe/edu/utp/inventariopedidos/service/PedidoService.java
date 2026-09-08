package pe.edu.utp.inventariopedidos.service;

import org.springframework.stereotype.Service;
import pe.edu.utp.inventariopedidos.model.Pedido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final List<Pedido> pedidos = new ArrayList<>();
    private Long contadorId = 1L;

    // Datos ficticios precargados (clienteId hace referencia a los clientes ya creados)
    public PedidoService() {
        pedidos.add(new Pedido(contadorId++, 1L, LocalDate.of(2026, 9, 1), "ENTREGADO", 3245.0));
        pedidos.add(new Pedido(contadorId++, 1L, LocalDate.of(2026, 9, 5), "PENDIENTE", 1800.0));
        pedidos.add(new Pedido(contadorId++, 2L, LocalDate.of(2026, 9, 6), "ENVIADO", 45.0));
    }

    public List<Pedido> listarTodos() {
        return pedidos;
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return pedidos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public List<Pedido> buscarPorCliente(Long clienteId) {
        return pedidos.stream()
                .filter(p -> p.getClienteId().equals(clienteId))
                .toList();
    }

    public Pedido guardar(Pedido pedido) {
        pedido.setId(contadorId++);
        pedidos.add(pedido);
        return pedido;
    }

    public Optional<Pedido> actualizar(Long id, Pedido datosNuevos) {
        Optional<Pedido> existente = buscarPorId(id);
        if (existente.isPresent()) {
            Pedido pedido = existente.get();
            pedido.setClienteId(datosNuevos.getClienteId());
            pedido.setFechaPedido(datosNuevos.getFechaPedido());
            pedido.setEstado(datosNuevos.getEstado());
            pedido.setTotal(datosNuevos.getTotal());
            return Optional.of(pedido);
        }
        return Optional.empty();
    }

    public boolean eliminar(Long id) {
        return pedidos.removeIf(p -> p.getId().equals(id));
    }
}