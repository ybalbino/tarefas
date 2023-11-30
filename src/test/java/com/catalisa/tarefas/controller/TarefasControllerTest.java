package com.catalisa.tarefas.controller;

import com.catalisa.tarefas.exception.TarefaNaoEncontradaExcecao;
import com.catalisa.tarefas.model.Tarefas;
import com.catalisa.tarefas.service.TarefasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TarefasControllerTest {

    @Mock
    private TarefasService tarefasService;

    @InjectMocks
    private TarefasController tarefasController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscaTodasTarefasTest() {
        List<Tarefas> tarefasList = new ArrayList<>();
        when(tarefasService.buscarTodasTarefas()).thenReturn(tarefasList);

        List<Tarefas> result = tarefasController.buscaTodasTarefas();

        assertEquals(tarefasList, result);
    }

    @Test
    void buscarPorIdTest() {
        Long id = 1L;
        Tarefas tarefas = new Tarefas();
        when(tarefasService.buscarPorId(id)).thenReturn(tarefas);

        ResponseEntity<Tarefas> result = tarefasController.buscarPorId(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(tarefas, result.getBody());
    }

    @Test
    void buscarPorIdNotFoundTest() {
        Long id = 1L;
        when(tarefasService.buscarPorId(id)).thenThrow(new TarefaNaoEncontradaExcecao("Tarefa não encontrada"));

        ResponseEntity<Tarefas> result = tarefasController.buscarPorId(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void buscarTarefaPorTituloTest() {
        String titulo = "Teste";
        Tarefas tarefas = new Tarefas();
        when(tarefasService.buscarPorTitulo(titulo)).thenReturn(Optional.of(tarefas));

        ResponseEntity<?> result = tarefasController.buscarTarefaPorTitulo(titulo);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(tarefas, ((ResponseEntity<Tarefas>) result).getBody());
    }

    @Test
    void buscarTarefaPorTituloNotFoundTest() {
        String titulo = "Teste";
        when(tarefasService.buscarPorTitulo(titulo)).thenReturn(Optional.empty());

        ResponseEntity<?> result = tarefasController.buscarTarefaPorTitulo(titulo);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void buscarTarefaPorTituloBadRequestTest() {
        String titulo = "Teste123";
        ResponseEntity<?> result = tarefasController.buscarTarefaPorTitulo(titulo);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void criarTarefaTest() {
        Tarefas tarefas = new Tarefas();
        when(tarefasService.cadastrarTarefa(any(Tarefas.class))).thenReturn(tarefas);

        ResponseEntity<Tarefas> result = tarefasController.criarTarefa(tarefas);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(tarefas, result.getBody());
    }

    @Test
    void criarTarefaInternalServerErrorTest() {
        Tarefas tarefas = new Tarefas();
        when(tarefasService.cadastrarTarefa(any(Tarefas.class))).thenThrow(new RuntimeException("Error while creating task"));

        ResponseEntity<Tarefas> result = tarefasController.criarTarefa(tarefas);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void atualizarTarefaTest() {
        Long id = 1L;
        Tarefas tarefas = new Tarefas();
        when(tarefasService.alterarPorId(eq(id), any(Tarefas.class))).thenReturn(tarefas);

        ResponseEntity<Tarefas> result = tarefasController.atualizarTarefa(id, tarefas);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(tarefas, result.getBody());
    }

    @Test
    void atualizarTarefaNotFoundTest() {
        Long id = 1L;
        when(tarefasService.alterarPorId(eq(id), any(Tarefas.class))).thenThrow(new TarefaNaoEncontradaExcecao());

        ResponseEntity<Tarefas> result = tarefasController.atualizarTarefa(id, new Tarefas());

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void deleteTarefaTest() {
        Long id = 1L;
        ResponseEntity<Void> result = tarefasController.deleteTarefa(id);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(tarefasService, times(1)).excluirTarefa(id);
    }

    @Test
    void deleteTarefaNotFoundTest() {
        Long id = 1L;
        doThrow(new TarefaNaoEncontradaExcecao("Tarefa não encontrada")).when(tarefasService).excluirTarefa(id);

        ResponseEntity<Void> result = tarefasController.deleteTarefa(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }


    @Test
    void deleteTarefaInternalServerErrorTest() {
        Long id = 1L;
        doThrow(new RuntimeException("Error while deleting task")).when(tarefasService).excluirTarefa(id);

        ResponseEntity<Void> result = tarefasController.deleteTarefa(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }
}