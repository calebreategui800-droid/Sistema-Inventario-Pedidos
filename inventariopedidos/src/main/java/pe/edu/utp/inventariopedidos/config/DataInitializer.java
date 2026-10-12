package pe.edu.utp.inventariopedidos.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pe.edu.utp.inventariopedidos.model.Categoria;
import pe.edu.utp.inventariopedidos.model.Cliente;
import pe.edu.utp.inventariopedidos.model.Pedido;
import pe.edu.utp.inventariopedidos.model.Producto;
import pe.edu.utp.inventariopedidos.repository.CategoriaRepository;
import pe.edu.utp.inventariopedidos.repository.ClienteRepository;
import pe.edu.utp.inventariopedidos.repository.PedidoRepository;
import pe.edu.utp.inventariopedidos.repository.ProductoRepository;

import java.time.LocalDate;

/**
 * Carga datos de ejemplo en la base de datos al arrancar la aplicacion.
 * Solo inserta si las tablas estan vacias, asi que no duplica datos en cada reinicio.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;

    public DataInitializer(CategoriaRepository categoriaRepository,
                           ProductoRepository productoRepository,
                           ClienteRepository clienteRepository,
                           PedidoRepository pedidoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void run(String... args) {
        if (categoriaRepository.count() == 0) {
            cargarCategoriasYProductos();
        }
        if (clienteRepository.count() == 0) {
            cargarClientesYPedidos();
        }
    }

    private void cargarCategoriasYProductos() {
        Categoria electrodomesticos = categoriaRepository.save(
                new Categoria(null, "Electrodomesticos", "Equipos para el hogar", "ACTIVO"));
        Categoria computo = categoriaRepository.save(
                new Categoria(null, "Computo", "Laptops, PCs y accesorios", "ACTIVO"));
        Categoria muebles = categoriaRepository.save(
                new Categoria(null, "Muebles", "Mobiliario de oficina y hogar", "ACTIVO"));

        productoRepository.save(new Producto(null, "Laptop Lenovo V15",
                "Laptop 15.6\" i5 8GB RAM", 2500.00, 10, computo.getId()));
        productoRepository.save(new Producto(null, "Mouse Logitech M170",
                "Mouse inalambrico", 35.90, 50, computo.getId()));
        productoRepository.save(new Producto(null, "Refrigeradora LG 300L",
                "Refrigeradora No Frost", 1800.00, 5, electrodomesticos.getId()));
        productoRepository.save(new Producto(null, "Escritorio de Melamine",
                "Escritorio 120x60 cm", 320.00, 8, muebles.getId()));
    }

    private void cargarClientesYPedidos() {
        Cliente carlos = clienteRepository.save(
                new Cliente(null, "Carlos", "Ramirez", "carlos@gmail.com", "987654321", "ACTIVO"));
        Cliente maria = clienteRepository.save(
                new Cliente(null, "Maria", "Lopez", "maria@gmail.com", "956123789", "ACTIVO"));
        clienteRepository.save(
                new Cliente(null, "Luis", "Torres", "luis@gmail.com", "945678123", "ACTIVO"));

        pedidoRepository.save(new Pedido(null, carlos.getId(), LocalDate.of(2026, 9, 1), "ENTREGADO", 3245.0));
        pedidoRepository.save(new Pedido(null, carlos.getId(), LocalDate.of(2026, 9, 5), "PENDIENTE", 1800.0));
        pedidoRepository.save(new Pedido(null, maria.getId(), LocalDate.of(2026, 9, 6), "ENVIADO", 45.0));
    }
}
