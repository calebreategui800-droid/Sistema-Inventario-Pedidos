package pe.edu.utp.inventariopedidos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class InventariopedidosApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	public void testBuscarCategoriaPorId() throws Exception {
		System.out.println("Ejecutando Test Buscar Categoria Por Id");

		URI uri = new URI("/api/categorias/1");
		MockHttpServletRequestBuilder req =
				MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(req).andReturn();

		System.out.println("Respuesta recibida: " + result.getResponse().getContentAsString());

		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		assertTrue(result.getResponse().getContentAsString().contains("Electrodomesticos"));

		System.out.println("Test Buscar Categoria Por Id finalizado correctamente");
	}

}