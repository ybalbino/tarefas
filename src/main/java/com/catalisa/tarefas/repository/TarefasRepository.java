package com.catalisa.tarefas.repository;

import com.catalisa.tarefas.model.Tarefas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TarefasRepository extends JpaRepository<Tarefas, Long> {
    Optional<Tarefas> findByTitulo(String titulo);
}

