
package com.example.demo_integracao_mongodb.controllers;

import com.example.demo_integracao_mongodb.models.Estudante;
import com.example.demo_integracao_mongodb.repositories.EstudanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/estudantes")
public class EstudanteController {

    @Autowired
    private EstudanteRepository estudanteRepository;

    @PostMapping
    public void gravar(@RequestBody Estudante estudante) {
        this.estudanteRepository.save(estudante);
    }

    @GetMapping
    public List<Estudante> buscarTodos() {
        return this.estudanteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudante> buscarPorId(
            @PathVariable String id) {

        return estudanteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudante> atualizar(
            @PathVariable String id,
            @RequestBody Estudante dados) {

        return estudanteRepository.findById(id)
                .map(estudante -> {
                    estudante.setNome(dados.getNome());
                    estudante.setEmail(dados.getEmail());
                    estudante.setIdade(dados.getIdade());

                    estudanteRepository.save(estudante);

                    return ResponseEntity.ok(estudante);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable String id) {

        if (!estudanteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        estudanteRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
