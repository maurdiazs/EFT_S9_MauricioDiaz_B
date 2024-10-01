package com.example.ocproductosmascotas.model;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "ordenCompra")
public class OrdenCompra extends RepresentationModel<OrdenCompra> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "No puede ingresar un número de cliente vacío")
    @Column(name = "clienteID")
    private Long clienteId;

    @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Producto> productos; // Lista de productos en la orden

    @NotNull(message = "La fecha de fin no puede ser nula")
    @Column(name = "fecha_fin")
    private LocalDate fechaOrden;

    @NotNull(message = "No puede ingresar un estado vacío")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EstadoOrden estadoOrden;  // Estado de la orden

    // Constructor
    public OrdenCompra(Long id, Long clienteId, List<Producto> productos, LocalDate fechaOrden, EstadoOrden estadoOrden) {
        this.id = id;
        this.clienteId = clienteId;
        this.productos = productos;
        this.fechaOrden = fechaOrden;
        this.estadoOrden = estadoOrden;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getClienteId() {
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
    public void setId(Long id) {
        this.id = id;
    }

    public void setClienteId(Long clienteId) {
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
