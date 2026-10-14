package pe.edu.utp.inventariopedidos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; // 👈 Corregido el import
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser; // 👈 Añadido soporte de seguridad
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "nicolas", roles = {"USER"}) // 👈 Añadido usuario ficticio
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listarCategoriasDebeRetornar200() throws Exception {
        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk());
    }

    @Test
    void listarCategoriasDebeRetornarJson() throws Exception {
        mockMvc.perform(get("/api/categorias"))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void listarCategoriasDebeContenerNombreEsperado() throws Exception {
        mockMvc.perform(get("/api/categorias"))
                .andExpect(jsonPath("$[0].nombre").value("Electrodomesticos"));
    }
}
