
package com.example.demo_integracao_mongodb.controllers;

import com.example.demo_integracao_mongodb.models.Curso;
import com.example.demo_integracao_mongodb.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public void gravar(@RequestBody Curso curso) {
        this.cursoRepository.save(curso);
    }

    @GetMapping
    public List<Curso> buscarTodos() {
        return this.cursoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarPorId(
            @PathVariable String id) {

        return cursoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> atualizar(
            @PathVariable String id,
            @RequestBody Curso dados) {

        return cursoRepository.findById(id)
                .map(curso -> {
                    curso.setDescricao(dados.getDescricao());

                    cursoRepository.save(curso);

                    return ResponseEntity.ok(curso);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable String id) {

        if (!cursoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        cursoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
