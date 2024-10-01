package com.example.ocproductosmascotas.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.ocproductosmascotas.model.EstadoOrden;
import com.example.ocproductosmascotas.model.OrdenCompra;
import com.example.ocproductosmascotas.repository.OrdenCompraRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OrdenCompraRepositoryTest {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Test
    public void testSaveOrdenCompra() {
        // Creamos una instancia de OrdenCompra
        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setClienteId(123L);
        ordenCompra.setFechaOrden(LocalDate.now());
        ordenCompra.setEstadoOrden(EstadoOrden.ACTIVO);

        // Guardamos la orden de compra
        OrdenCompra savedOrdenCompra = ordenCompraRepository.save(ordenCompra);

        // Verificamos que la orden de compra se haya guardado correctamente
        assertEquals(123L, savedOrdenCompra.getClienteId());
        assertEquals(EstadoOrden.ACTIVO, savedOrdenCompra.getEstadoOrden());
        assertEquals(ordenCompra.getFechaOrden(), savedOrdenCompra.getFechaOrden());
    }

    @Test
    public void testFindById() {
        // Creamos y guardamos una orden de compra
        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setClienteId(456L);
        ordenCompra.setFechaOrden(LocalDate.now());
        ordenCompra.setEstadoOrden(EstadoOrden.ACTIVO);

        OrdenCompra savedOrdenCompra = ordenCompraRepository.save(ordenCompra);

        // Buscamos la orden de compra por su ID
        Optional<OrdenCompra> foundOrdenCompra = ordenCompraRepository.findById(savedOrdenCompra.getId());

        // Verificamos que se haya encontrado la orden de compra
        assertTrue(foundOrdenCompra.isPresent());
        assertEquals(456L, foundOrdenCompra.get().getClienteId());
    }

    @Test
    public void testDeleteOrdenCompra() {
        // Creamos y guardamos una orden de compra
        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setClienteId(789L);
        ordenCompra.setFechaOrden(LocalDate.now());
        ordenCompra.setEstadoOrden(EstadoOrden.ACTIVO);

        OrdenCompra savedOrdenCompra = ordenCompraRepository.save(ordenCompra);

        // Eliminamos la orden de compra
        ordenCompraRepository.deleteById(savedOrdenCompra.getId());

        // Verificamos que la orden de compra haya sido eliminada
        Optional<OrdenCompra> deletedOrdenCompra = ordenCompraRepository.findById(savedOrdenCompra.getId());
        assertTrue(deletedOrdenCompra.isEmpty());
    }
}