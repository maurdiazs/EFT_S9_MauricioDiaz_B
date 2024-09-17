package com.example.ocproductosmascotas.model;

public class EstadoOrden {
    private int id;
    private String descripcion;

    // Constructor
    public EstadoOrden(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
