package com.ecommerce.producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.producto.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {}
