package com.portalestagio.webservicerest.controller;

import com.portalestagio.webservicerest.model.Area;
import com.portalestagio.webservicerest.repositories.AreaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/areas")
public class AreaController {

    @Autowired
    private AreaRepository areaRepo;

    @GetMapping
    public List<Area> listarTodas() {
        return areaRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Area> buscarPorId(@PathVariable Long id) {
        Optional<Area> opt = areaRepo.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(opt.get());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Area nova) {
        if (nova.getNome() == null || nova.getNome().isBlank()) {
            return ResponseEntity.badRequest().body("O campo 'nome' é obrigatório.");
        }
        Area salva = areaRepo.save(nova);
        return ResponseEntity.status(201).body(salva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Area dados) {
        Optional<Area> opt = areaRepo.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Area existente = opt.get();
        if (dados.getNome() != null) {
            existente.setNome(dados.getNome());
        }
        Area atualizada = areaRepo.save(existente);
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Optional<Area> opt = areaRepo.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Area area = opt.get();
        if (area.getEstudantes() != null && !area.getEstudantes().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Não é possível excluir esta área: existem estudantes vinculados.");
        }
        if (area.getVagas() != null && !area.getVagas().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Não é possível excluir esta área: existem vagas vinculadas.");
        }
        areaRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
