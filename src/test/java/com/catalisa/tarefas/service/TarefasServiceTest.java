package com.catalisa.tarefas.service;

import com.catalisa.tarefas.exception.TarefaNaoEncontradaExcecao;
import com.catalisa.tarefas.exception.TarefaNaoEncontradaPorTituloException;
import com.catalisa.tarefas.model.Tarefas;
import com.catalisa.tarefas.repository.TarefasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TarefasServiceTest {

    @Mock
    private TarefasRepository tarefasRepository;

    @InjectMocks
    private TarefasService tarefasService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarTodasTarefas() {
        when(tarefasRepository.findAll()).thenReturn(Arrays.asList(new Tarefas(), new Tarefas()));
        List<Tarefas> result = tarefasService.buscarTodasTarefas();
        assertEquals(2, result.size());
    }

    @Test
    void testBuscarPorId_Success() {
        when(tarefasRepository.findById(1L)).thenReturn(Optional.of(new Tarefas()));
        Tarefas result = tarefasService.buscarPorId(1L);
        assertNotNull(result);
    }

    @Test
    void testBuscarPorId_Failure() {
        when(tarefasRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(TarefaNaoEncontradaExcecao.class, () -> tarefasService.buscarPorId(1L));
    }

    @Test
    void testBuscarPorTitulo_Success() {
        when(tarefasRepository.findByTitulo("Teste")).thenReturn(Optional.of(new Tarefas()));
        Optional<Tarefas> result = tarefasService.buscarPorTitulo("Teste");
        assertTrue(result.isPresent());
    }

    @Test
    void testBuscarPorTitulo_Failure() {
        // Configuração do comportamento do repositório para retornar Optional vazio
        when(tarefasRepository.findByTitulo("Teste")).thenReturn(Optional.empty());

        // Execução do método e captura da exceção
        TarefaNaoEncontradaPorTituloException exception = assertThrows(
                TarefaNaoEncontradaPorTituloException.class,
                () -> tarefasService.buscarPorTitulo("Teste")
        );

        // Verificação da mensagem da exceção
        assertEquals("Tarefa não encontrada com o título: Teste", exception.getMessage());
    }

    @Test
    void testCadastrarTarefa() {
        Tarefas tarefa = new Tarefas();
        when(tarefasRepository.save(any(Tarefas.class))).thenReturn(tarefa);
        Tarefas result = tarefasService.cadastrarTarefa(tarefa);
        assertNotNull(result);
    }

    @Test
    void testAlterarPorId_Success() {
        // Configuração do Mockito
        MockitoAnnotations.initMocks(this);

        // Dados de exemplo
        Long id = 1L;
        Tarefas tarefaExistente = new Tarefas();
        tarefaExistente.setId(id);
        tarefaExistente.setTitulo("Tarefa Antiga");

        Tarefas tarefaAtualizada = new Tarefas();
        tarefaAtualizada.setId(id);
        tarefaAtualizada.setTitulo("Tarefa Atualizada");

        // Simula que a tarefa existente foi encontrada no repositório
        when(tarefasRepository.findById(id)).thenReturn(Optional.of(tarefaExistente));

        // Simula que o método save do repositório retorna a tarefa atualizada
        when(tarefasRepository.save(any(Tarefas.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Executa o método a ser testado
        Tarefas resultado = tarefasService.alterarPorId(id, tarefaAtualizada);

        verify(tarefasRepository).findById(id);
        verify(tarefasRepository).save(any(Tarefas.class));

        // Verifica se os campos foram atualizados corretamente
        assertEquals(tarefaAtualizada.getId(), resultado.getId());
        assertEquals(tarefaAtualizada.getTitulo(), resultado.getTitulo());

    }

    @Test
    void testAlterarPorId_Failure() {
        when(tarefasRepository.findById(1L)).thenReturn(Optional.empty());
        Tarefas tarefaAtualizada = new Tarefas();
        assertThrows(TarefaNaoEncontradaExcecao.class, () -> tarefasService.alterarPorId(1L, tarefaAtualizada));
    }

    @Test
    void testExcluirTarefa_Success() {
        when(tarefasRepository.existsById(1L)).thenReturn(true);
        assertDoesNotThrow(() -> tarefasService.excluirTarefa(1L));
    }

    @Test
    void testExcluirTarefa_Failure() {
        when(tarefasRepository.existsById(1L)).thenReturn(false);
        assertThrows(TarefaNaoEncontradaExcecao.class, () -> tarefasService.excluirTarefa(1L));
    }
}
