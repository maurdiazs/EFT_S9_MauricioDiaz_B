package com.example.ocproductosmascotas.controller;

//import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ocproductosmascotas.model.EstadoOrden;
import com.example.ocproductosmascotas.model.OrdenCompra;
import com.example.ocproductosmascotas.model.Producto;
import com.example.ocproductosmascotas.service.OrdenCompraService;

//import ch.qos.logback.classic.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/ordenesCompra")
public class OrdenCompraController {

    private static final Logger log = LoggerFactory.getLogger(OrdenCompraController.class);
    @Autowired
    private OrdenCompraService ordenCompraService;

    @GetMapping
    public CollectionModel<EntityModel<OrdenCompra>> getAllOrdenesCompra() {
        List<OrdenCompra> ordenesCompra = ordenCompraService.getAllOrdenesCompra();
        log.info("GET /ordenesCompra");
        log.info("Retornando todas las ordenes de compra");
        List<EntityModel<OrdenCompra>> ordenesCompraResources = ordenesCompra.stream()
            .map( ordenCompra -> EntityModel.of(ordenCompra,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getOrdenCompraById(ordenCompra.getId())).withSelfRel()
            ))
            .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllOrdenesCompra());
        CollectionModel<EntityModel<OrdenCompra>> resources = CollectionModel.of(ordenesCompraResources, linkTo.withRel("ordenesCompra"));

        return resources;
    }    
    //Mensaje en el log para cuando se solicitan las órdenes de compra
    public List<OrdenCompra> getAllOrdenesCompras(){
        log.info("GET /ordenesCompra");
        log.info("Retornando todas las ordenes de compra");
        return ordenCompraService.getAllOrdenesCompra();
    }
        
    @GetMapping("/{id}")
    public EntityModel<OrdenCompra> getOrdenCompraById(@PathVariable Long id) {
        Optional<OrdenCompra> ordenCompra = ordenCompraService.getOrdenCompraById(id);

        if (ordenCompra.isPresent()) {
            return EntityModel.of(ordenCompra.get(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getOrdenCompraById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllOrdenesCompra()).withRel("all-ordenesCompra"));
        } else {
            throw new OrdenCompraNotFoundException("Orden not found with id: " + id);
        }
    }
    //Mensaje en el log al no encontrar las órdenes de compra
    public ResponseEntity<Object> getOrdenCompraById(@PathVariable Long id) {
        Optional<OrdenCompra> ordenCompra = ordenCompraService.getOrdenCompraById(id);
        if (ordenCompra.isEmpty()) {
            log.error("No se encontró la orden de compra con ID {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontró la orden de compra con ID" + id));
        }
        return ResponseEntity.ok(ordenCompra);
    }

    @PostMapping
    public EntityModel<OrdenCompra> createOrdenCompra(@Validated @RequestBody OrdenCompra ordenCompra) {
        OrdenCompra createdOrdenCompra = ordenCompraService.createOrdenCompra(ordenCompra);
            return EntityModel.of(createdOrdenCompra,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getOrdenCompraById(createdOrdenCompra.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllOrdenesCompra()).withRel("all-ordenesCompra"));

    }
    //Mensaje en el log al no poder crear la orden de compra
    public ResponseEntity<Object> createOrdenCompra(@Validated @RequestBody OrdenCompra ordenCompra) {
        OrdenCompra createdOrdenCompra = ordenCompraService.createOrdenCompra(ordenCompra);
        if (createdOrdenCompra == null) {
            log.error("Error al crear la orden de compra {}", ordenCompra);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error al crear la orden de compra"));
        }
        return ResponseEntity.ok(createdOrdenCompra);
    }
    
    @PutMapping("/{id}")
    public EntityModel<OrdenCompra> updateOrdenCompra(@PathVariable Long id, @RequestBody OrdenCompra ordenCompra) {
        OrdenCompra updatedOrdenCompra = ordenCompraService.updateOrdenCompra(id, ordenCompra);
        return EntityModel.of(updatedOrdenCompra,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getOrdenCompraById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllOrdenesCompra()).withRel("all-ordenesCompra"));

    }
    public OrdenCompra updateStudent(@PathVariable Long id, @RequestBody OrdenCompra ordenCompra) {
        return ordenCompraService.updateOrdenCompra(id, ordenCompra);
    }

    @DeleteMapping("/{id}")
    public void deleteOrdenCompra(@PathVariable Long id){
        ordenCompraService.deleteOrdenCompra(id);
    }


    static class ErrorResponse {
        private final String message;
    
        public ErrorResponse(String message) {
            this.message = message;
        }
    
        public String getMessage() {
            return message;
        }
    }
}
