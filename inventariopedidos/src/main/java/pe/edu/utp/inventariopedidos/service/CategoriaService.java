package pe.edu.utp.inventariopedidos.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.inventariopedidos.model.Categoria;
import pe.edu.utp.inventariopedidos.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll(Sort.by("id"));
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public Categoria guardar(Categoria categoria) {
        categoria.setId(null); // el id lo genera la base de datos
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Optional<Categoria> actualizar(Long id, Categoria datosNuevos) {
        return categoriaRepository.findById(id).map(categoria -> {
            categoria.setNombre(datosNuevos.getNombre());
            categoria.setDescripcion(datosNuevos.getDescripcion());
            categoria.setEstado(datosNuevos.getEstado());
            return categoriaRepository.save(categoria);
        });
    }

    public boolean eliminar(Long id) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
