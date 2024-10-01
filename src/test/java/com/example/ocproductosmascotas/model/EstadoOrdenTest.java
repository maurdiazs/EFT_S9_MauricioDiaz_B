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

public class EstadoOrdenTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testEstadoOrdenValido() {
        // Creamos una instancia válida de EstadoOrden
        EstadoOrden estadoOrden = new EstadoOrden();
        estadoOrden.setDescripcion("Orden Confirmada");

        // Validamos la instancia
        Set<ConstraintViolation<EstadoOrden>> violations = validator.validate(estadoOrden);

        // No debe haber violaciones si la instancia es válida
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testDescripcionNoDebeSerNula() {
        // Creamos una instancia de EstadoOrden con descripción nula
        EstadoOrden estadoOrden = new EstadoOrden();
        estadoOrden.setDescripcion(null);

        // Validamos la instancia
        Set<ConstraintViolation<EstadoOrden>> violations = validator.validate(estadoOrden);

        // Debe haber una violación para el campo descripción
        assertEquals(1, violations.size());
        assertEquals("No puede ingresar una descripción vacía", violations.iterator().next().getMessage());
    }

    @Test
    public void testDescripcionNoDebeSerVacia() {
        // Creamos una instancia de EstadoOrden con descripción vacía
        EstadoOrden estadoOrden = new EstadoOrden();
        estadoOrden.setDescripcion("");

        // Validamos la instancia
        Set<ConstraintViolation<EstadoOrden>> violations = validator.validate(estadoOrden);

        // Debe haber una violación para el campo descripción
        assertEquals(1, violations.size());
        assertEquals("No puede ingresar una descripción vacía", violations.iterator().next().getMessage());
    }

    @Test
    public void testDescripcionSoloEspacios() {
        // Creamos una instancia de EstadoOrden con descripción que tiene solo espacios en blanco
        EstadoOrden estadoOrden = new EstadoOrden();
        estadoOrden.setDescripcion("   ");

        // Validamos la instancia
        Set<ConstraintViolation<EstadoOrden>> violations = validator.validate(estadoOrden);

        // Debe haber una violación para el campo descripción
        assertEquals(1, violations.size());
        assertEquals("No puede ingresar una descripción vacía", violations.iterator().next().getMessage());
    }
}