package pe.edu.utp.inventariopedidos;

import tools.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pe.edu.utp.inventariopedidos.model.Producto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper jsonMapper;

    // ---------------------------------------------------------
    // GET /api/productos  (listar todos)
    // ---------------------------------------------------------

    @Test
    void listarTodos_deberiaRetornarStatus200() throws Exception {
        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk());
    }

    @Test
    void listarTodos_deberiaRetornarListaConLosDatosPrecargados() throws Exception {
        mockMvc.perform(get("/api/productos"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(org.hamcrest.Matchers.greaterThanOrEqualTo(4)));
    }

    @Test
    void listarTodos_deberiaContenerUnProductoPrecargadoConocido() throws Exception {
        mockMvc.perform(get("/api/productos"))
                .andExpect(jsonPath("$[?(@.nombre=='Laptop Lenovo V15')]").exists());
    }

    // ---------------------------------------------------------
    // GET /api/productos/{id}  (buscar por id)
    // ---------------------------------------------------------

    @Test
    void buscarPorId_existente_deberiaRetornar200ConElProducto() throws Exception {
        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Laptop Lenovo V15"));
    }

    @Test
    void buscarPorId_inexistente_deberiaRetornar404() throws Exception {
        mockMvc.perform(get("/api/productos/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarPorId_deberiaRetornarCategoriaIdCorrecta() throws Exception {
        mockMvc.perform(get("/api/productos/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoriaId").value(2));
    }

    // ---------------------------------------------------------
    // POST /api/productos  (crear)
    // ---------------------------------------------------------

    @Test
    void crear_conDatosValidos_deberiaRetornar201ConIdAsignado() throws Exception {
        Producto nuevo = new Producto(null, "Teclado Mecanico", "Teclado RGB switches rojos", 150.0, 20, 2L);

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").value("Teclado Mecanico"));
    }

    @Test
    void crear_conCategoriaInexistente_deberiaRetornar400() throws Exception {
        Producto nuevo = new Producto(null, "Producto Invalido", "Categoria que no existe", 99.0, 1, 999L);

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(nuevo)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crear_productoCreado_deberiaPoderConsultarsePorSuNuevoId() throws Exception {
        Producto nuevo = new Producto(null, "Monitor 24 pulgadas", "Monitor Full HD", 600.0, 7, 2L);

        String respuesta = mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Producto creado = jsonMapper.readValue(respuesta, Producto.class);

        mockMvc.perform(get("/api/productos/" + creado.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Monitor 24 pulgadas"));
    }

    // ---------------------------------------------------------
    // PUT /api/productos/{id}  (actualizar)
    // ---------------------------------------------------------

    @Test
    void actualizar_productoExistente_deberiaRetornar200ConDatosActualizados() throws Exception {
        Producto original = new Producto(null, "Impresora Laser", "Impresora blanco y negro", 400.0, 3, 2L);
        String respuestaCreacion = mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(original)))
                .andReturn().getResponse().getContentAsString();
        Producto creado = jsonMapper.readValue(respuestaCreacion, Producto.class);

        Producto actualizado = new Producto(null, "Impresora Laser Pro", "Impresora con wifi", 550.0, 5, 2L);

        mockMvc.perform(put("/api/productos/" + creado.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Impresora Laser Pro"))
                .andExpect(jsonPath("$.precio").value(550.0));
    }

    @Test
    void actualizar_productoInexistente_deberiaRetornar404() throws Exception {
        Producto actualizado = new Producto(null, "No existe", "No existe", 10.0, 1, 2L);

        mockMvc.perform(put("/api/productos/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(actualizado)))
                .andExpect(status().isNotFound());
    }

    @Test
    void actualizar_conCategoriaInexistente_deberiaRetornar400() throws Exception {
        Producto original = new Producto(null, "Parlante Bluetooth", "Parlante portatil", 120.0, 12, 2L);
        String respuestaCreacion = mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(original)))
                .andReturn().getResponse().getContentAsString();
        Producto creado = jsonMapper.readValue(respuestaCreacion, Producto.class);

        Producto actualizado = new Producto(null, "Parlante Bluetooth", "Parlante portatil", 120.0, 12, 999L);

        mockMvc.perform(put("/api/productos/" + creado.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(actualizado)))
                .andExpect(status().isBadRequest());
    }

    // ---------------------------------------------------------
    // DELETE /api/productos/{id}  (eliminar)
    // ---------------------------------------------------------

    @Test
    void eliminar_productoExistente_deberiaRetornar204() throws Exception {
        Producto original = new Producto(null, "Webcam HD", "Webcam 1080p", 90.0, 25, 2L);
        String respuestaCreacion = mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(original)))
                .andReturn().getResponse().getContentAsString();
        Producto creado = jsonMapper.readValue(respuestaCreacion, Producto.class);

        mockMvc.perform(delete("/api/productos/" + creado.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_productoInexistente_deberiaRetornar404() throws Exception {
        mockMvc.perform(delete("/api/productos/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminar_productoEliminado_noDeberiaEncontrarsePorId() throws Exception {
        Producto original = new Producto(null, "Silla Gamer", "Silla ergonomica gamer", 700.0, 4, 3L);
        String respuestaCreacion = mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(original)))
                .andReturn().getResponse().getContentAsString();
        Producto creado = jsonMapper.readValue(respuestaCreacion, Producto.class);

        mockMvc.perform(delete("/api/productos/" + creado.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/productos/" + creado.getId()))
                .andExpect(status().isNotFound());
    }
}