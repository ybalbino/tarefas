package com.catalisa.tarefas.controller;

import com.catalisa.tarefas.exception.TarefaNaoEncontradaExcecao;
import com.catalisa.tarefas.model.Tarefas;
import com.catalisa.tarefas.service.TarefasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TarefasController {

    @Autowired
    TarefasService tarefasService;

    @GetMapping(path = "/tarefas")
    public List<Tarefas> buscaTodasTarefas() {
        return tarefasService.buscarTodasTarefas();
    }
    @GetMapping(path = "/tarefa/{id}")
    public ResponseEntity<Tarefas> buscarPorId(@PathVariable Long id) {
        try {
            Tarefas tarefas = tarefasService.buscarPorId(id);
            return new ResponseEntity<>(tarefas, HttpStatus.OK);
        } catch (TarefaNaoEncontradaExcecao e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/tarefa")
    public ResponseEntity<?> buscarTarefaPorTitulo(@RequestParam String titulo) {
        if (titulo.matches(".*\\d+.*")) {
            return ResponseEntity.badRequest().body("O título da tarefa não pode conter números.");
        }

        Optional<Tarefas> tarefas = tarefasService.buscarPorTitulo(titulo);
        return tarefas.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/tarefa")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Tarefas> criarTarefa(@RequestBody Tarefas tarefas) {
        try {
            Tarefas criarTarefa = tarefasService.cadastrarTarefa(tarefas);
            return new ResponseEntity<>(criarTarefa, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/tarefa/{id}")
    public ResponseEntity<Tarefas> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefas tarefas) {
        try {
            Tarefas  atualizarTarefa = tarefasService.alterarPorId(id, tarefas);
            return new ResponseEntity<>(atualizarTarefa, HttpStatus.OK);
        } catch (TarefaNaoEncontradaExcecao e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping(path = "/tarefa/{id}")
    public ResponseEntity<Void> deleteTarefa(@PathVariable Long id) {
        try {
            tarefasService.excluirTarefa(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (TarefaNaoEncontradaExcecao e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


