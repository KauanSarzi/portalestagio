package com.portalestagio.webservicerest.controller;

import com.portalestagio.webservicerest.model.Area;
import com.portalestagio.webservicerest.model.Estudante;
import com.portalestagio.webservicerest.repositories.AreaRepository;
import com.portalestagio.webservicerest.repositories.EstudanteRepository;

import jakarta.annotation.PostConstruct;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estudantes")
public class EstudanteController {

    @Autowired
    private EstudanteRepository estudanteRepo;

    @Autowired
    private AreaRepository areaRepo;



    @PostConstruct
    public void criarEstudantePadrao() {
        if (estudanteRepo.existsById(1L)) {
            return;
        }

        
        Area area1;
        if (areaRepo.existsById(1L)) {
            area1 = areaRepo.findById(1L).get();
        } else {
           
            area1 = new Area();
            area1.setNome("Engenharia");
            areaRepo.save(area1);
        }

       
        Estudante est = new Estudante();
        est.setNome("Estudante Padrão");
        est.setEmail("estudante1@exemplo.com");
        est.setAnoIngresso(2023);
        est.setCurso("Engenharia de Software");
        est.setMatricula("20230001");
        est.setPeriodoAtual("1º semestre");

        // Associa a lista de áreas (aqui, apenas a área1)
        est.setAreas(List.of(area1));

        estudanteRepo.save(est);
        // Em um banco vazio, este save irá gerar ID = 1 para o Estudante
    }











    @GetMapping
    public List<Estudante> listarTodos() {
        return estudanteRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudante> buscarPorId(@PathVariable Long id) {
        Optional<Estudante> opt = estudanteRepo.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(opt.get());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Estudante novo) {
        if (novo.getNome() == null || novo.getNome().isBlank()) {
            return ResponseEntity.badRequest().body("O campo 'nome' é obrigatório.");
        }
        if (novo.getEmail() == null || novo.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("O campo 'email' é obrigatório.");
        }
        if (novo.getAreas() != null && !novo.getAreas().isEmpty()) {
            List<Long> idsAreas = novo.getAreas().stream().map(Area::getId).toList();
            List<Area> listaAreas = areaRepo.findAllById(idsAreas);
            novo.setAreas(listaAreas);
        }
        Estudante salvo = estudanteRepo.save(novo);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Estudante dados) {
        Optional<Estudante> opt = estudanteRepo.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Estudante existente = opt.get();

        if (dados.getNome() != null) {
            existente.setNome(dados.getNome());
        }
        if (dados.getEmail() != null) {
            existente.setEmail(dados.getEmail());
        }
        if (dados.getAnoIngresso() != 0) {
            existente.setAnoIngresso(dados.getAnoIngresso());
        }
        if (dados.getCurso() != null) {
            existente.setCurso(dados.getCurso());
        }
        if (dados.getMatricula() != null) {
            existente.setMatricula(dados.getMatricula());
        }
        if (dados.getPeriodoAtual() != null) {
            existente.setPeriodoAtual(dados.getPeriodoAtual());
        }
        if (dados.getAreas() != null && !dados.getAreas().isEmpty()) {
            List<Long> idsAreas = dados.getAreas().stream().map(Area::getId).toList();
            List<Area> listaAreas = areaRepo.findAllById(idsAreas);
            existente.setAreas(listaAreas);
        }

        Estudante atualizado = estudanteRepo.save(existente);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        if (!estudanteRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        estudanteRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
