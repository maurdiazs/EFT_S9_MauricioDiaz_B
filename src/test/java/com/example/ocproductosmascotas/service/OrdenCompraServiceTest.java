package com.example.ocproductosmascotas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ocproductosmascotas.model.EstadoOrden;
import com.example.ocproductosmascotas.model.OrdenCompra;
import com.example.ocproductosmascotas.model.Producto;
import com.example.ocproductosmascotas.repository.OrdenCompraRepository;

@ExtendWith(MockitoExtension.class)
public class OrdenCompraServiceTest {
    @InjectMocks
    private OrdenCompraServiceImpl ordenCompraServicio;

    @Mock
    private OrdenCompraRepository ordenCompraRepositoryMock;

    //Prueba para guardar orden de compra
    @Test
    public void guardarOrdenCompraTest() {
        // Dado
        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setClienteId(123L);
        ordenCompra.setFechaOrden(LocalDate.of(2024, 10, 1));
        ordenCompra.setEstadoOrden(EstadoOrden.CONFIRMADA); // EstadoOrden es un enum que tienes definido

        List<Producto> productos = new ArrayList<>();
        Producto producto = new Producto();
        producto.setNombre("Arnés de perro");
        producto.setDescripcion("Arnés para perro de tamaño mediano");
        producto.setPrecio(10000);
        productos.add(producto);

        ordenCompra.setProductos(productos);

        // Configuración del mock
        when(ordenCompraRepositoryMock.save(any(OrdenCompra.class))).thenReturn(ordenCompra);

        // Cuando
        OrdenCompra resultado = ordenCompraService.createOrdenCompra(ordenCompra);

        // Entonces
        assertEquals(123L, resultado.getClienteId());
        assertEquals(LocalDate.of(2024, 10, 1), resultado.getFechaOrden());
        assertEquals(EstadoOrden.CONFIRMADA, resultado.getEstadoOrden());
        assertEquals(1, resultado.getProductos().size());
        assertEquals("Producto 1", resultado.getProductos().get(0).getNombre());
    }

    //Prueba para obtener orden de compra por ID de compra
    @Test
    public void obtenerOrdenCompraPorIdTest() {
        // Dado
        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setId(1L);
        ordenCompra.setClienteId(123L);
        ordenCompra.setFechaOrden(LocalDate.of(2024, 10, 1));
        ordenCompra.setEstadoOrden(EstadoOrden.CONFIRMADA);

        when(ordenCompraRepositoryMock.findById(1L)).thenReturn(Optional.of(ordenCompra));

        // Cuando
        Optional<OrdenCompra> resultado = ordenCompraService.getOrdenCompraById(1L);

        // Entonces
        assertEquals(true, resultado.isPresent());
        assertEquals(123L, resultado.get().getClienteId());
    }

    //Prueba para actualizar una orden de compra existente
    @Test
    public void actualizarOrdenCompraTest() {
        // Dado
        OrdenCompra ordenExistente = new OrdenCompra();
        ordenExistente.setId(1L);
        ordenExistente.setClienteId(123L);
        ordenExistente.setFechaOrden(LocalDate.of(2024, 9, 1));
        ordenExistente.setEstadoOrden(EstadoOrden.PENDIENTE);

        when(ordenCompraRepositoryMock.findById(1L)).thenReturn(Optional.of(ordenExistente));
        when(ordenCompraRepositoryMock.save(any(OrdenCompra.class))).thenReturn(ordenExistente);

        OrdenCompra actualizada = new OrdenCompra();
        actualizada.setClienteId(456L);
        actualizada.setFechaOrden(LocalDate.of(2024, 12, 1));
        actualizada.setEstadoOrden(EstadoOrden.EN_PROCESO);

        // Cuando
        OrdenCompra resultado = ordenCompraService.updateOrdenCompra(1L, actualizada);

        // Entonces
        assertEquals(456L, resultado.getClienteId());
        assertEquals(LocalDate.of(2024, 12, 1), resultado.getFechaOrden());
        assertEquals(EstadoOrden.EN_PROCESO, resultado.getEstadoOrden());
    }

    //Prueba para cuando una orden no existe o no la encuentra
    @Test
    public void testOrdenCompraNoEncontrada() {
        // Configuración del mock para devolver vacío
        when(ordenCompraRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // Verificar que la orden de compra sea nula cuando no se encuentra
        Optional<OrdenCompra> resultado = ordenCompraService.getOrdenCompraById(1L);
        assertFalse(resultado.isPresent(), "La orden de compra no debería existir");
    }
}