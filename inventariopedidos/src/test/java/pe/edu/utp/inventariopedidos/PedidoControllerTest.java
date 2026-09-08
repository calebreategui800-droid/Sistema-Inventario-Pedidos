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
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // =====================================
    // GET /api/pedidos
    // =====================================

    @Test
    void listarPedidosDebeRetornar200() throws Exception {
        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk());
    }

    @Test
    void listarPedidosDebeRetornarJson() throws Exception {
        mockMvc.perform(get("/api/pedidos"))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void listarPedidosDebeContenerEstadoEsperado() throws Exception {
        mockMvc.perform(get("/api/pedidos"))
                .andExpect(jsonPath("$[0].estado").value("ENTREGADO"));
    }
}