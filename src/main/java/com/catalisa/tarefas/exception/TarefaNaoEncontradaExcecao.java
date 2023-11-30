package com.catalisa.tarefas.exception;

public class TarefaNaoEncontradaExcecao extends RuntimeException{

    public TarefaNaoEncontradaExcecao(String mensagem){
        super(mensagem);
    }

    public TarefaNaoEncontradaExcecao() {

    }
}
