package com.portalestagio.webservicerest.controller;

import com.portalestagio.webservicerest.model.Area;
import com.portalestagio.webservicerest.model.Empresa;
import com.portalestagio.webservicerest.model.VagaEstagio;
import com.portalestagio.webservicerest.repositories.AreaRepository;
import com.portalestagio.webservicerest.repositories.EmpresaRepository;
import com.portalestagio.webservicerest.repositories.VagaEstagioRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/vagas")
public class VagaEstagioController {

    @Autowired
    private VagaEstagioRepository vagaRepo;

    @Autowired
    private EmpresaRepository empresaRepo;

    @Autowired
    private AreaRepository areaRepo;

    @GetMapping
    public Page<VagaEstagio> listarVagas(
            @RequestParam(value = "areaIds", required = false) List<Long> areaIds,
            @RequestParam(value = "empresaId", required = false) Long empresaId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pg = PageRequest.of(page, size);

        if (empresaId != null) {
            return vagaRepo.findByEmpresa_IdAndAtivoTrue(empresaId, pg);
        }
        if (areaIds != null && !areaIds.isEmpty()) {
            return vagaRepo.findByAtivoTrueAndAreas_IdIn(areaIds, pg);
        }
        return vagaRepo.findByAtivoTrue(pg);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VagaEstagio> buscarPorId(@PathVariable Long id) {
        Optional<VagaEstagio> opt = vagaRepo.findById(id);
        return opt.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody VagaEstagio novo) {
        if (novo.getTitulo() == null || novo.getTitulo().isBlank()) {
            return ResponseEntity.badRequest().body("O campo 'titulo' é obrigatório.");
        }
        if (novo.getDescricao() == null || novo.getDescricao().isBlank()) {
            return ResponseEntity.badRequest().body("O campo 'descricao' é obrigatório.");
        }
        if (novo.getEmpresa() == null || novo.getEmpresa().getId() == null) {
            return ResponseEntity.badRequest().body("É obrigatório informar 'empresa.id'.");
        }
        if (novo.getAreas() == null || novo.getAreas().isEmpty()) {
            return ResponseEntity.badRequest().body("É obrigatório informar ao menos uma 'areas.id'.");
        }
        if (novo.getCargaHoraria() <= 0) {
            return ResponseEntity.badRequest().body("O campo 'cargaHoraria' deve ser maior que zero.");
        }
        if (novo.getModalidade() == null || novo.getModalidade().isBlank()) {
            return ResponseEntity.badRequest().body("O campo 'modalidade' é obrigatório.");
        }

        Optional<Empresa> empOpt = empresaRepo.findById(novo.getEmpresa().getId());
        if (empOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Empresa não encontrada.");
        }
        novo.setEmpresa(empOpt.get());

        List<Long> idsAreas = novo.getAreas().stream().map(Area::getId).toList();
        List<Area> listaAreas = areaRepo.findAllById(idsAreas);
        if (listaAreas.size() != idsAreas.size()) {
            return ResponseEntity.badRequest().body("Uma ou mais áreas não foram encontradas.");
        }
        novo.setAreas(listaAreas);

        novo.setDataPublicacao(new Date());
        novo.setAtivo(true);

        VagaEstagio salva = vagaRepo.save(novo);
        return ResponseEntity.status(201).body(salva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody VagaEstagio dados) {
        Optional<VagaEstagio> opt = vagaRepo.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        VagaEstagio existente = opt.get();

        if (Boolean.FALSE.equals(existente.getAtivo())) {
            return ResponseEntity.badRequest().body("Não é possível editar vaga inativa.");
        }

        if (dados.getTitulo() != null) {
            existente.setTitulo(dados.getTitulo());
        }
        if (dados.getDescricao() != null) {
            existente.setDescricao(dados.getDescricao());
        }
        if (dados.getSalario() != null) {
            existente.setSalario(dados.getSalario());
        }
        if (dados.getCargaHoraria() > 0) {
            existente.setCargaHoraria(dados.getCargaHoraria());
        }
        if (dados.getModalidade() != null) {
            existente.setModalidade(dados.getModalidade());
        }

        if (dados.getEmpresa() != null && dados.getEmpresa().getId() != null) {
            Optional<Empresa> empOpt = empresaRepo.findById(dados.getEmpresa().getId());
            if (empOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Empresa não encontrada.");
            }
            existente.setEmpresa(empOpt.get());
        }
        if (dados.getAreas() != null && !dados.getAreas().isEmpty()) {
            List<Long> idsAreas = dados.getAreas().stream().map(Area::getId).toList();
            List<Area> listaAreas = areaRepo.findAllById(idsAreas);
            if (listaAreas.size() != idsAreas.size()) {
                return ResponseEntity.badRequest().body("Uma ou mais áreas não foram encontradas.");
            }
            existente.setAreas(listaAreas);
        }

        VagaEstagio atualizada = vagaRepo.save(existente);
        return ResponseEntity.ok(atualizada);
    }

    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<?> encerrar(@PathVariable Long id) {
        Optional<VagaEstagio> opt = vagaRepo.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        VagaEstagio vaga = opt.get();
        vaga.setAtivo(false);
        vagaRepo.save(vaga);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Optional<VagaEstagio> opt = vagaRepo.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        VagaEstagio vaga = opt.get();
        if (vaga.getInscricaoVaga() != null && !vaga.getInscricaoVaga().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Não é possível excluir esta vaga: existem inscrições vinculadas.");
        }
        vagaRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

