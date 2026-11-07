package com.ecommerce.producto.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.producto.model.Producto;
import com.ecommerce.producto.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor 
public class ProductoController {
    private final ProductoRepository repo;


    @GetMapping
    public List<Producto> listar(){
     return repo.findAll(); }
    
     @PostMapping
    public Producto crear(@RequestBody Producto p) { return repo.save(p); }

    // Sirve en el paso 5 para demostrar qué réplica atendió la petición
    @GetMapping("/instancia")
    public Map<String, String> instancia() throws UnknownHostException {
        return Map.of("instancia", InetAddress.getLocalHost().getHostName());
    }
}
