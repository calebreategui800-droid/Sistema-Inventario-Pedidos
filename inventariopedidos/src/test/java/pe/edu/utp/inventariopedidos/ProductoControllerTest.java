package pe.edu.utp.inventariopedidos;

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
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // =====================================
    // GET /api/productos
    // =====================================

    @Test
    void listarProductosDebeRetornar200() throws Exception {
        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk());
    }

    @Test
    void listarProductosDebeRetornarJson() throws Exception {
        mockMvc.perform(get("/api/productos"))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void listarProductosDebeContenerNombreEsperado() throws Exception {
        mockMvc.perform(get("/api/productos"))
                .andExpect(jsonPath("$[0].nombre").value("Laptop Lenovo"));
    }
}
