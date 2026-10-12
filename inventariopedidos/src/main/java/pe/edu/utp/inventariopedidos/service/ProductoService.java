package pe.edu.utp.inventariopedidos.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.inventariopedidos.model.Producto;
import pe.edu.utp.inventariopedidos.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaService categoriaService;

    public ProductoService(ProductoRepository productoRepository, CategoriaService categoriaService) {
        this.productoRepository = productoRepository;
        this.categoriaService = categoriaService;
    }

    public List<Producto> listarTodos() {
        return productoRepository.findAll(Sort.by("id"));
    }

    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    // Valida la relacion Producto -> Categoria (categoriaId debe existir en la BD)
    public boolean categoriaExiste(Long categoriaId) {
        return categoriaId != null && categoriaService.buscarPorId(categoriaId).isPresent();
    }

    public Producto guardar(Producto producto) {
        producto.setId(null); // el id lo genera la base de datos
        return productoRepository.save(producto);
    }

    @Transactional
    public Optional<Producto> actualizar(Long id, Producto datosNuevos) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(datosNuevos.getNombre());
            producto.setDescripcion(datosNuevos.getDescripcion());
            producto.setPrecio(datosNuevos.getPrecio());
            producto.setStock(datosNuevos.getStock());
            producto.setCategoriaId(datosNuevos.getCategoriaId());
            return productoRepository.save(producto);
        });
    }

    public boolean eliminar(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
