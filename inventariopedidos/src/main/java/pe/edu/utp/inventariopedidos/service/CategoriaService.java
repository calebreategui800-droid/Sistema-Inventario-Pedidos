package pe.edu.utp.inventariopedidos.service;

import org.springframework.stereotype.Service;
import pe.edu.utp.inventariopedidos.model.Categoria;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final List<Categoria> categorias = new ArrayList<>();
    private Long contadorId = 1L;

    // Datos ficticios precargados
    public CategoriaService() {
        categorias.add(new Categoria(contadorId++, "Electrodomesticos", "Equipos para el hogar", "ACTIVO"));
        categorias.add(new Categoria(contadorId++, "Computo", "Laptops, PCs y accesorios", "ACTIVO"));
        categorias.add(new Categoria(contadorId++, "Muebles", "Mobiliario de oficina y hogar", "ACTIVO"));
    }

    public List<Categoria> listarTodas() {
        return categorias;
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return categorias.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    public Categoria guardar(Categoria categoria) {
        categoria.setId(contadorId++);
        categorias.add(categoria);
        return categoria;
    }

    public Optional<Categoria> actualizar(Long id, Categoria datosNuevos) {
        Optional<Categoria> existente = buscarPorId(id);
        if (existente.isPresent()) {
            Categoria categoria = existente.get();
            categoria.setNombre(datosNuevos.getNombre());
            categoria.setDescripcion(datosNuevos.getDescripcion());
            categoria.setEstado(datosNuevos.getEstado());
            return Optional.of(categoria);
        }
        return Optional.empty();
    }

    public boolean eliminar(Long id) {
        return categorias.removeIf(c -> c.getId().equals(id));
    }
}