package pe.edu.utp.inventariopedidos.service;

import org.springframework.stereotype.Service;
import pe.edu.utp.inventariopedidos.model.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final List<Producto> productos = new ArrayList<>();
    private Long contadorId = 1L;

    // Datos ficticios precargados (categoriaId hace referencia a las categorias ya creadas: 1, 2, 3)
    public ProductoService() {
        productos.add(new Producto(contadorId++, "Laptop Lenovo", 3200.0, 15, 2L));
        productos.add(new Producto(contadorId++, "Mouse inalambrico", 45.0, 80, 2L));
        productos.add(new Producto(contadorId++, "Refrigeradora", 1800.0, 5, 1L));
    }

    public List<Producto> listarTodos() {
        return productos;
    }

    public Optional<Producto> buscarPorId(Long id) {
        return productos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public List<Producto> buscarPorCategoria(Long categoriaId) {
        return productos.stream()
                .filter(p -> p.getCategoriaId().equals(categoriaId))
                .toList();
    }

    public Producto guardar(Producto producto) {
        producto.setId(contadorId++);
        productos.add(producto);
        return producto;
    }

    public Optional<Producto> actualizar(Long id, Producto datosNuevos) {
        Optional<Producto> existente = buscarPorId(id);
        if (existente.isPresent()) {
            Producto producto = existente.get();
            producto.setNombre(datosNuevos.getNombre());
            producto.setPrecio(datosNuevos.getPrecio());
            producto.setStock(datosNuevos.getStock());
            producto.setCategoriaId(datosNuevos.getCategoriaId());
            return Optional.of(producto);
        }
        return Optional.empty();
    }

    public boolean eliminar(Long id) {
        return productos.removeIf(p -> p.getId().equals(id));
    }
}