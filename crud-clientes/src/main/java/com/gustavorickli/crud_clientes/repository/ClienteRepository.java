package com.gustavorickli.crud_clientes.repository;

import com.gustavorickli.crud_clientes.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
