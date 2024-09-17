package com.example.ocproductosmascotas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ocproductosmascotas.model.OrdenCompra;
import com.example.ocproductosmascotas.repository.OrdenCompraRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenCompraServiceImpl implements OrdenCompraService{
    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Override
    public List<OrdenCompra> getAllOrdenesCompra() {
        return ordenCompraRepository.findAll();
    }

    @Override
    public Optional<OrdenCompra> getOrdenCompraById(Long id) {
        return ordenCompraRepository.findById(id);
    }
    
    @Override
    public OrdenCompra createOrdenCompra(OrdenCompra ordenCompra){
        return ordenCompraRepository.save(ordenCompra);
    }

    @Override
    public OrdenCompra updateOrdenCompra(Long id, OrdenCompra ordenCompra){
        if(ordenCompraRepository.existsById(id)){
            ordenCompra.setId(id);
            return ordenCompraRepository.save(ordenCompra);
        }   else {
                return null;
        }
    }

    @Override
    public void deleteOrdenCompra(Long id){
        ordenCompraRepository.deleteById(id);
    }
}
