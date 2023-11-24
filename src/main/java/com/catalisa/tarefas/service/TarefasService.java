package com.catalisa.tarefas.service;

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

    public List<Tarefas> buscarTodasTarefas() {
        return tarefasRepository.findAll();
    }

    public Optional<Tarefas> buscarTarefaPorId(Long id) {
        return tarefasRepository.findById(id);
    }

    public Optional<Tarefas> buscarTarefaPorTitulo(String titulo) {
        return tarefasRepository.findByTitulo(titulo);
    }


    public Tarefas cadastrarTarefa(Tarefas tarefas) {
//        tarefas.getId();
//        tarefas.getTitulo();
//        tarefas.getDescricao();
//        tarefas.getDataVencimento();
        //pq não puxou o boolean?
        return tarefasRepository.save(tarefas);
    }

    public Tarefas alterarPorId(Long id, Tarefas tarefas) {

        Tarefas tarefas1 = buscarTarefaPorId(id).get();

        if (tarefas.getTitulo() != null) {
            tarefas1.setTitulo(tarefas.getTitulo());
        }
        if (tarefas.getDescricao() != null) {
            tarefas1.setDescricao(tarefas.getDescricao());
        }
        if (tarefas.getDataVencimento() != null) {
            tarefas1.setDataVencimento(tarefas.getDataVencimento());
        }

        return tarefasRepository.save(tarefas1);
    }

    public void excluirTarefa(Long id){
        tarefasRepository.deleteById(id);
    }

}
