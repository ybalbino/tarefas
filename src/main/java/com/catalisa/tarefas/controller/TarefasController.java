package com.catalisa.tarefas.controller;

import com.catalisa.tarefas.model.Tarefas;
import com.catalisa.tarefas.service.TarefasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TarefasController {

    @Autowired
    TarefasService tarefasService;

    @GetMapping(path = "/tarefas")
    public List<Tarefas> buscaTodasTarefas(){
        return tarefasService.buscarTodasTarefas();
    }

    @GetMapping(path = "/tarefas/{id}")
    public Optional<Tarefas> buscarTarefaPorId(@PathVariable Long id){
        return tarefasService.buscarTarefaPorId(id);
    }

    @PostMapping(path = "/tarefas")
    @ResponseStatus(HttpStatus.CREATED)
    public Tarefas cadastrarTarefa( @RequestBody Tarefas tarefas){
        return tarefasService.cadastrarTarefa(tarefas);
    }

    @PutMapping(path = "/tarefas/{id}")
    public Tarefas alterarTarefaId(@PathVariable Long id, @RequestBody Tarefas tarefas){
        return tarefasService.alterarPorId(id, tarefas);
    }

    @PutMapping(path = "/tarefas/{titulo}")
    public Optional<Tarefas> alterarTarefaTitulo(@PathVariable String titulo, @RequestBody Tarefas tarefas){
        return tarefasService.buscarTarefaPorTitulo(titulo);
    }

    @DeleteMapping(path = "/tarefas/{id}")
    public void excluirTareta(@PathVariable Long id){
        tarefasService.excluirTarefa(id);
    }

}
