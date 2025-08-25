package com.portalestagio.webservicerest.controller;

import com.portalestagio.webservicerest.model.Inscricao;

import com.portalestagio.webservicerest.repositories.InscricaoRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {

    @Autowired
    private InscricaoRepository inscricaoRepo;

    @GetMapping
    public Page<Inscricao> listar(Pageable pageable) {
        return inscricaoRepo.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<Inscricao> criar(@RequestBody Inscricao ins) {
        Inscricao salva = inscricaoRepo.save(ins);
        return ResponseEntity.status(201).body(salva);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Inscricao> atualizarParcial(
            @PathVariable Long id,
            @RequestBody Inscricao dados
    ) {
        return inscricaoRepo.findById(id)
          .map(existente -> {
            if (dados.getStatus() != null) {
                existente.setStatus(dados.getStatus());
            }
            if (dados.getMensagemApresentacao() != null) {
                existente.setMensagemApresentacao(dados.getMensagemApresentacao());
            }
            Inscricao atualizada = inscricaoRepo.save(existente);
            return ResponseEntity.ok(atualizada);
          })
          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!inscricaoRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        inscricaoRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

