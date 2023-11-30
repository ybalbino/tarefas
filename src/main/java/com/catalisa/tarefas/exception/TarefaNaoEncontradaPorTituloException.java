package com.catalisa.tarefas.exception;

public class TarefaNaoEncontradaPorTituloException extends RuntimeException{

    public TarefaNaoEncontradaPorTituloException(String titulo) {
        super("Tarefa não encontrada com o título: " + titulo);
    }
}
