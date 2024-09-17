package com.example.ocproductosmascotas.service;

import com.example.ocproductosmascotas.model.OrdenCompra;
import java.util.List;
import java.util.Optional;
    
public interface OrdenCompraService {
    List<OrdenCompra> getAllOrdenesCompra();
    Optional<OrdenCompra> getOrdenComprayId(Long id);
    OrdenCompra createOrdenCompra(OrdenCompra ordenCompra);
    OrdenCompra updateOrdenCompra(Long id,OrdenCompra ordenCompra);
    void deleteOrdenCompra(Long id);
    
} 