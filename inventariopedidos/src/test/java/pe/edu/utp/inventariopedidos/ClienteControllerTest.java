package pe.edu.utp.inventariopedidos;
import org.springframework.test.annotation.DirtiesContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // =====================================
    // GET /api/clientes
    // =====================================

    @Test
    void listarClientesDebeRetornar200() throws Exception {
        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk());
    }

    @Test
    void listarClientesDebeRetornarJson() throws Exception {
        mockMvc.perform(get("/api/clientes"))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void listarClientesDebeContenerDatos() throws Exception {
        mockMvc.perform(get("/api/clientes"))
                .andExpect(jsonPath("$[0].nombre").exists());
    }

    // =====================================
    // GET /api/clientes/{id}
    // =====================================

    @Test
    void buscarClienteExistenteDebeRetornar200() throws Exception {
        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarClienteDebeRetornarNombreCorrecto() throws Exception {
        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(jsonPath("$.nombre").value("Carlos"));
    }

    @Test
    void buscarClienteInexistenteDebeRetornar404() throws Exception {
        mockMvc.perform(get("/api/clientes/999"))
                .andExpect(status().isNotFound());
    }

    // =====================================
    // POST /api/clientes
    // =====================================

    @Test
    void crearClienteDebeRetornar201() throws Exception {

        String cliente = """
                {
                    "nombre": "Ana",
                    "apellido": "Perez",
                    "email": "ana@gmail.com",
                    "telefono": "999888777",
                    "estado": "ACTIVO"
                }
                """;

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cliente))
                .andExpect(status().isCreated());
    }

    @Test
    void crearClienteDebeRetornarNombre() throws Exception {

        String cliente = """
                {
                    "nombre": "Lucia",
                    "apellido": "Diaz",
                    "email": "lucia@gmail.com",
                    "telefono": "987111222",
                    "estado": "ACTIVO"
                }
                """;

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cliente))
                .andExpect(jsonPath("$.nombre").value("Lucia"));
    }

    @Test
    void crearClienteDebeGenerarId() throws Exception {

        String cliente = """
                {
                    "nombre": "Pedro",
                    "apellido": "Rojas",
                    "email": "pedro@gmail.com",
                    "telefono": "956111333",
                    "estado": "ACTIVO"
                }
                """;

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cliente))
                .andExpect(jsonPath("$.id").exists());
    }

    // =====================================
    // PUT /api/clientes/{id}
    // =====================================

    @Test
    void actualizarClienteExistenteDebeRetornar200() throws Exception {

        String cliente = """
                {
                    "nombre": "Carlos Actualizado",
                    "apellido": "Ramirez",
                    "email": "carlosnuevo@gmail.com",
                    "telefono": "900000000",
                    "estado": "ACTIVO"
                }
                """;

        mockMvc.perform(put("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cliente))
                .andExpect(status().isOk());
    }

    @Test
    void actualizarClienteDebeCambiarNombre() throws Exception {

        String cliente = """
                {
                    "nombre": "Carlos Modificado",
                    "apellido": "Ramirez",
                    "email": "carlos@gmail.com",
                    "telefono": "987654321",
                    "estado": "ACTIVO"
                }
                """;

        mockMvc.perform(put("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cliente))
                .andExpect(jsonPath("$.nombre")
                        .value("Carlos Modificado"));
    }

    @Test
    void actualizarClienteInexistenteDebeRetornar404() throws Exception {

        String cliente = """
                {
                    "nombre": "Prueba",
                    "apellido": "Prueba",
                    "email": "prueba@gmail.com",
                    "telefono": "999999999",
                    "estado": "ACTIVO"
                }
                """;

        mockMvc.perform(put("/api/clientes/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cliente))
                .andExpect(status().isNotFound());
    }

    // =====================================
    // DELETE /api/clientes/{id}
    // =====================================

    @Test
    void eliminarClienteExistenteDebeRetornar204() throws Exception {
        mockMvc.perform(delete("/api/clientes/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminarClienteInexistenteDebeRetornar404() throws Exception {
        mockMvc.perform(delete("/api/clientes/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void clienteEliminadoDebeDejarDeExistir() throws Exception {

        mockMvc.perform(delete("/api/clientes/3"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/clientes/3"))
                .andExpect(status().isNotFound());
    }
}