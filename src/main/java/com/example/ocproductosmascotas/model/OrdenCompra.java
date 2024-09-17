package com.example.ocproductosmascotas.model;

import java.time.LocalDate;
import java.util.List;

public class OrdenCompra {
    private int id;
    private int clienteId;
    private List<Producto> productos; // Lista de productos en la orden
    private LocalDate fechaOrden;
    private EstadoOrden estadoOrden;  // Estado de la orden

    // Constructor
    public OrdenCompra(int id, int clienteId, List<Producto> productos, LocalDate fechaOrden, EstadoOrden estadoOrden) {
        this.id = id;
        this.clienteId = clienteId;
        this.productos = productos;
        this.fechaOrden = fechaOrden;
        this.estadoOrden = estadoOrden;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public LocalDate getFechaOrden() {
        return fechaOrden;
    }

    public EstadoOrden getEstadoOrden() {
        return estadoOrden;
    }


    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public void setFechaOrden(LocalDate fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public void setEstadoOrden(EstadoOrden estadoOrden) {
        this.estadoOrden = estadoOrden;
    }
}
