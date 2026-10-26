
package com.example.demo_integracao_mongodb.controllers;

import com.example.demo_integracao_mongodb.models.Professor;
import com.example.demo_integracao_mongodb.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @PostMapping
    public void gravar(@RequestBody Professor professor) {
        this.professorRepository.save(professor);
    }

    @GetMapping
    public List<Professor> buscarTodos() {
        return this.professorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> buscarPorId(
            @PathVariable String id) {

        return professorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> atualizar(
            @PathVariable String id,
            @RequestBody Professor dados) {

        return professorRepository.findById(id)
                .map(professor -> {
                    professor.setNome(dados.getNome());
                    professor.setEmail(dados.getEmail());
                    professor.setIdade(dados.getIdade());

                    professorRepository.save(professor);

                    return ResponseEntity.ok(professor);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable String id) {

        if (!professorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        professorRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
