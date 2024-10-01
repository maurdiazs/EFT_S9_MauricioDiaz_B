package com.example.ocproductosmascotas.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrdenCompraTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testOrdenCompraValida() {
        // Creamos una instancia válida de OrdenCompra
        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setClienteId(123L);

        List<Producto> productos = new ArrayList<>();
        Producto producto = new Producto();
        producto.setNombre("Producto 1");
        producto.setPrecio(100.0);
        producto.setOrdenCompra(ordenCompra); // Vinculamos el producto a la orden de compra
        productos.add(producto);
        
        ordenCompra.setProductos(productos);
        ordenCompra.setFechaOrden(LocalDate.of(2024, 10, 1));
        ordenCompra.setEstadoOrden(EstadoOrden.CONFIRMADA);

        // Validamos la instancia
        Set<ConstraintViolation<OrdenCompra>> violations = validator.validate(ordenCompra);

        // No debe haber violaciones si la instancia es válida
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testClienteIdNoDebeSerNulo() {
        // Creamos una instancia de OrdenCompra con clienteId nulo
        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setClienteId(null);
        ordenCompra.setFechaOrden(LocalDate.of(2024, 10, 1));
        ordenCompra.setEstadoOrden(EstadoOrden.CONFIRMADA);

        // Validamos la instancia
        Set<ConstraintViolation<OrdenCompra>> violations = validator.validate(ordenCompra);

        // Debe haber una violación para el campo clienteId
        assertEquals(1, violations.size());
        assertEquals("No puede ingresar un número de cliente vacío", violations.iterator().next().getMessage());
    }

    @Test
    public void testFechaOrdenNoDebeSerNula() {
        // Creamos una instancia de OrdenCompra con fechaOrden nula
        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setClienteId(123L);
        ordenCompra.setFechaOrden(null);
        ordenCompra.setEstadoOrden(EstadoOrden.CONFIRMADA);

        // Validamos la instancia
        Set<ConstraintViolation<OrdenCompra>> violations = validator.validate(ordenCompra);

        // Debe haber una violación para el campo fechaOrden
        assertEquals(1, violations.size());
        assertEquals("La fecha de fin no puede ser nula", violations.iterator().next().getMessage());
    }

    @Test
    public void testEstadoOrdenNoDebeSerNulo() {
        // Creamos una instancia de OrdenCompra con estadoOrden nulo
        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setClienteId(123L);
        ordenCompra.setFechaOrden(LocalDate.of(2024, 10, 1));
        ordenCompra.setEstadoOrden(null);

        // Validamos la instancia
        Set<ConstraintViolation<OrdenCompra>> violations = validator.validate(ordenCompra);

        // Debe haber una violación para el campo estadoOrden
        assertEquals(1, violations.size());
        assertEquals("No puede ingresar un estado vacío", violations.iterator().next().getMessage());
    }

    @Test
    public void testProductosRelacionados() {
        // Verificamos que los productos estén correctamente relacionados con la orden
        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setClienteId(123L);
        ordenCompra.setFechaOrden(LocalDate.of(2024, 10, 1));
        ordenCompra.setEstadoOrden(EstadoOrden.CONFIRMADA);

        List<Producto> productos = new ArrayList<>();
        Producto producto = new Producto();
        producto.setNombre("Producto 1");
        producto.setPrecio(100.0);
        producto.setOrdenCompra(ordenCompra); // Establecemos la relación bidireccional
        productos.add(producto);

        ordenCompra.setProductos(productos);

        // Validamos la instancia
        Set<ConstraintViolation<OrdenCompra>> violations = validator.validate(ordenCompra);

        // No debe haber violaciones si la relación es correcta
        assertTrue(violations.isEmpty());
        assertEquals(1, ordenCompra.getProductos().size());
        assertEquals(ordenCompra, producto.getOrdenCompra());
    }
}