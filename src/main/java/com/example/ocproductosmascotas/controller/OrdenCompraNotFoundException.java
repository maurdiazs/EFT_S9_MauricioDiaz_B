package com.example.ocproductosmascotas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//import com.example.ocproductosmascotas.model.OrdenCompra;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrdenCompraNotFoundException extends RuntimeException {
    
    public OrdenCompraNotFoundException(String message) {
        super(message);
    }
}