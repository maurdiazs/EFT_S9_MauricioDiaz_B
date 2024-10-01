package com.example.ocproductosmascotas.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.ocproductosmascotas.model.OrdenCompra;
import com.example.ocproductosmascotas.service.OrdenCompraService;

// Test para OrdenCompraController
@WebMvcTest(OrdenCompraController.class)
public class OrdenCompraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdenCompraService ordenCompraServiceMock;

    @Test
    public void obtenerTodasLasOrdenesCompraTest() throws Exception {
        // Creación de datos de prueba
        OrdenCompra orden1 = new OrdenCompra();
        orden1.setId(1L);
        orden1.setNombre("Orden de Compra 1");

        OrdenCompra orden2 = new OrdenCompra();
        orden2.setId(2L);
        orden2.setNombre("Orden de Compra 2");

        List<OrdenCompra> ordenesCompra = List.of(orden1, orden2);

        // Simulación del comportamiento del servicio
        when(ordenCompraServiceMock.getAllOrdenesCompra()).thenReturn(ordenesCompra);

        // Realizar la solicitud HTTP GET y validar la respuesta
        mockMvc.perform(get("/ordenesCompra"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.ordenesCompra.length()").value(2))
                .andExpect(jsonPath("$._embedded.ordenesCompra[0].nombre").value("Orden de Compra 1"))
                .andExpect(jsonPath("$._embedded.ordenesCompra[1].nombre").value("Orden de Compra 2"))
                .andExpect(jsonPath("$._embedded.ordenesCompra[0]._links.self.href").value("http://localhost:8080/ordenesCompra/1"))
                .andExpect(jsonPath("$._embedded.ordenesCompra[1]._links.self.href").value("http://localhost:8080/ordenesCompra/2"));
    }

    @Test
    public void obtenerOrdenCompraPorIdTest() throws Exception {
        // Creación de datos de prueba
        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setId(1L);
        ordenCompra.setNombre("Orden de Compra 1");

        // Simulación del comportamiento del servicio
        when(ordenCompraServiceMock.getOrdenCompraById(1L)).thenReturn(Optional.of(ordenCompra));

        // Realizar la solicitud HTTP GET y validar la respuesta
        mockMvc.perform(get("/ordenesCompra/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Orden de Compra 1"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost:8080/ordenesCompra/1"));
    }

    @Test
    public void crearOrdenCompraTest() throws Exception {
        // Creación de datos de prueba
        OrdenCompra nuevaOrdenCompra = new OrdenCompra();
        nuevaOrdenCompra.setNombre("Nueva Orden de Compra");

        OrdenCompra ordenCompraCreada = new OrdenCompra();
        ordenCompraCreada.setId(1L);
        ordenCompraCreada.setNombre("Nueva Orden de Compra");

        // Simulación del comportamiento del servicio
        when(ordenCompraServiceMock.createOrdenCompra(any(OrdenCompra.class))).thenReturn(ordenCompraCreada);

        // Realizar la solicitud HTTP POST y validar la respuesta
        mockMvc.perform(post("/ordenesCompra")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\": \"Nueva Orden de Compra\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Nueva Orden de Compra"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost:8080/ordenesCompra/1"));
    }

    @Test
    public void actualizarOrdenCompraTest() throws Exception {
        // Creación de datos de prueba
        OrdenCompra ordenCompraActualizada = new OrdenCompra();
        ordenCompraActualizada.setId(1L);
        ordenCompraActualizada.setNombre("Orden de Compra Actualizada");

        // Simulación del comportamiento del servicio
        when(ordenCompraServiceMock.updateOrdenCompra(eq(1L), any(OrdenCompra.class))).thenReturn(ordenCompraActualizada);

        // Realizar la solicitud HTTP PUT y validar la respuesta
        mockMvc.perform(put("/ordenesCompra/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\": \"Orden de Compra Actualizada\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Orden de Compra Actualizada"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost:8080/ordenesCompra/1"));
    }

    @Test
    public void eliminarOrdenCompraTest() throws Exception {
        // Realizar la solicitud HTTP DELETE y validar la respuesta
        mockMvc.perform(delete("/ordenesCompra/1"))
                .andExpect(status().isOk());

        // Verificar que el método de eliminación fue llamado en el servicio
        verify(ordenCompraServiceMock, times(1)).deleteOrdenCompra(1L);
    }
}