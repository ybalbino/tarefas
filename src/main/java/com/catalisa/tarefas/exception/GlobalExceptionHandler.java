package com.catalisa.tarefas.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TarefaNaoEncontradaExcecao.class)
    public ResponseEntity<Object> handlerTarefaNotFound(TarefaNaoEncontradaExcecao ex, WebRequest request){
        String mensagem = ex.getMessage();
        return handleExceptionInternal(ex, mensagem, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
