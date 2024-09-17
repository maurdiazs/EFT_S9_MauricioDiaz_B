package com.example.ocproductosmascotas.controller;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ocproductosmascotas.model.EstadoOrden;
import com.example.ocproductosmascotas.model.OrdenCompra;
import com.example.ocproductosmascotas.model.Producto;

import ch.qos.logback.classic.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ordenesCompra")
public class OrdenCompraController {

    private static final Logger log = LoggerFactory.getLogger(OrdenCompraController.class);

    @Autowired
    private OrdenCompra ordenCompra;

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
/* 
    public List<Student> getAllStudents(){
        log.info("GET /students");
        log.info("Retornando todos los estudiantes");
        return studentService.getAllStudents();
    }
    */    
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
    /*public ResponseEntity<Object> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isEmpty()) {
            log.error("No se encontró el estudiante con ID {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontró el estudiante con ID " + id));
        }
        return ResponseEntity.ok(student);
    }
*/
    @PostMapping
    public EntityModel<OrdenCompra> createOrdenCompra(@Validated @RequestBody OrdenCompra ordenCompra) {
        OrdenCompra createdOrdenCompra= ordenCompraService.createOrdenCompra(ordenCompra);
            return EntityModel.of(createdOrdenCompra,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getOrdenCompraById(createdOrdenCompra.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllOrdenesCompra()).withRel("all-ordenesCompra"));

    }
    /*public ResponseEntity<Object> createStudent(@Validated @RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        if (createdStudent == null) {
            log.error("Error al crear el estudiante {}", student);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error al crear el estudiante"));
        }
        return ResponseEntity.ok(createdStudent);
    }
    */
    @PutMapping("/{id}")
    public EntityModel<OrdenCompra> updateOrdenCompra(@PathVariable Long id, @RequestBody OrdenCompra ordenCompra) {
        OrdenCompra updatedOrdenCompra = ordenCompraService.updateOrdenCompra(id, ordenCompra);
        return EntityModel.of(updatedOrdenCompra,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getOrdenCompraById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllOrdenesCompra()).withRel("all-ordenesCompra"));

    }
    /*public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }
*/
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
