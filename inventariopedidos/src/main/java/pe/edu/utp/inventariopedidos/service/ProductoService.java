package pe.edu.utp.inventariopedidos.service;

import org.springframework.stereotype.Service;
import pe.edu.utp.inventariopedidos.model.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final List<Producto> productos = new ArrayList<>();
    private final CategoriaService categoriaService;
    private Long contadorId = 1L;

    // Datos ficticios precargados (categoriaId hace referencia a las categorias
    // ya precargadas en CategoriaService: 1=Electrodomesticos, 2=Computo, 3=Muebles)
    public ProductoService(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
        productos.add(new Producto(contadorId++, "Laptop Lenovo V15", "Laptop 15.6\" i5 8GB RAM", 2500.00, 10, 2L));
        productos.add(new Producto(contadorId++, "Mouse Logitech M170", "Mouse inalambrico", 35.90, 50, 2L));
        productos.add(new Producto(contadorId++, "Refrigeradora LG 300L", "Refrigeradora No Frost", 1800.00, 5, 1L));
        productos.add(new Producto(contadorId++, "Escritorio de Melamine", "Escritorio 120x60 cm", 320.00, 8, 3L));
    }

    public List<Producto> listarTodos() {
        return productos;
    }

    public Optional<Producto> buscarPorId(Long id) {
        return productos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public boolean categoriaExiste(Long categoriaId) {
        return categoriaId != null && categoriaService.buscarPorId(categoriaId).isPresent();
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
            producto.setDescripcion(datosNuevos.getDescripcion());
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