package com.example.ocproductosmascotas.model;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;


@Entity
@Table(name = "estadoOrden")
public class EstadoOrden extends RepresentationModel<EstadoOrden>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "No puede ingresar una descripción vacía")
    @Column(name= "descripcion")
    private String descripcion;

    // Constructor
    public EstadoOrden(Long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
