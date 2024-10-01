package com.example.ocproductosmascotas.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testProductoValido() {
        // Creamos una instancia válida de Producto
        Producto producto = new Producto();
        producto.setNombre("Laptop");
        producto.setDescripcion("Laptop de alto rendimiento");
        producto.setPrecio(1500.00);

        // Validamos la instancia
        Set<ConstraintViolation<Producto>> violations = validator.validate(producto);

        // No debe haber violaciones si la instancia es válida
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNombreNoDebeSerVacio() {
        // Creamos una instancia de Producto con nombre vacío
        Producto producto = new Producto();
        producto.setNombre("");
        producto.setDescripcion("Descripción válida");
        producto.setPrecio(1500.00);

        // Validamos la instancia
        Set<ConstraintViolation<Producto>> violations = validator.validate(producto);

        // Debe haber una violación para el campo nombre
        assertEquals(1, violations.size());
        assertEquals("No puede ingresar un nombre vacío", violations.iterator().next().getMessage());
    }

    @Test
    public void testDescripcionNoDebeSerVacia() {
        // Creamos una instancia de Producto con descripción vacía
        Producto producto = new Producto();
        producto.setNombre("Producto válido");
        producto.setDescripcion("");
        producto.setPrecio(1500.00);

        // Validamos la instancia
        Set<ConstraintViolation<Producto>> violations = validator.validate(producto);

        // Debe haber una violación para el campo descripción
        assertEquals(1, violations.size());
        assertEquals("No puede ingresar una descripción vacía", violations.iterator().next().getMessage());
    }

    @Test
    public void testPrecioNoDebeSerNulo() {
        // Creamos una instancia de Producto con precio nulo
        Producto producto = new Producto();
        producto.setNombre("Producto válido");
​⬤