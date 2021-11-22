package com.example.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.entity.Cliente;

public interface ClienteDao extends CrudRepository<Cliente, Long>{

}
