package com.ecommerce.pedido;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoRepository repo;

    public PedidoController(PedidoRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Pedido> listar() { return repo.findAll(); }

    @PostMapping
    public Pedido crear(@RequestBody Pedido p) { return repo.save(p); }
}