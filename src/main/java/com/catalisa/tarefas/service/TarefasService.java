package com.catalisa.tarefas.service;

import com.catalisa.tarefas.exception.TarefaNaoEncontradaExcecao;
import com.catalisa.tarefas.exception.TarefaNaoEncontradaPorTituloException;
import com.catalisa.tarefas.model.Tarefas;
import com.catalisa.tarefas.repository.TarefasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class TarefasService {

    @Autowired
    TarefasRepository tarefasRepository;

    @Autowired
    public TarefasService(TarefasRepository tarefasRepository) {
        this.tarefasRepository = tarefasRepository;
    }

    public List<Tarefas> buscarTodasTarefas() {
        return tarefasRepository.findAll();
    }

    public Tarefas buscarPorId(Long id) {
        Optional<Tarefas> optionalTarefasModel = tarefasRepository.findById(id);
        return optionalTarefasModel.orElseThrow(() -> new TarefaNaoEncontradaExcecao("Tarefa não encontrada com o ID: " + id));
    }

    public Optional<Tarefas> buscarPorTitulo(String titulo) {
        return Optional.ofNullable(tarefasRepository.findByTitulo(titulo)
                .orElseThrow(() -> new TarefaNaoEncontradaPorTituloException(titulo)));
    }

    public Tarefas cadastrarTarefa(Tarefas tarefas) {
        return tarefasRepository.save(tarefas);
    }

    public Tarefas alterarPorId(Long id, Tarefas tarefas) {
        Optional<Tarefas> optionalTarefas = tarefasRepository.findById(id);

        if (optionalTarefas.isPresent()) {
            Tarefas tarefaExistente = optionalTarefas.get();
            if (tarefas.getTitulo() != null) {
                tarefaExistente.setTitulo(tarefas.getTitulo());
            }
            if (tarefas.getDescricao() != null) {
                tarefaExistente.setDescricao(tarefas.getDescricao());
            }
            if (tarefas.getDataVencimento() != null) {
                tarefaExistente.setDataVencimento(tarefas.getDataVencimento());
            }
            return tarefasRepository.save(tarefaExistente);
        } else {
            throw new TarefaNaoEncontradaExcecao("Tarefa não encontrada com ID: " + id);
        }
    }


    public void excluirTarefa(Long id) {
        if (tarefasRepository.existsById(id)) {
            tarefasRepository.deleteById(id);
        } else {
            throw new TarefaNaoEncontradaExcecao("Tarefa não encontrada com ID: " + id);
        }

    }

}
