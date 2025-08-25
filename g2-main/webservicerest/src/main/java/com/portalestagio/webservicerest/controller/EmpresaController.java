package com.portalestagio.webservicerest.controller;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portalestagio.webservicerest.model.Empresa;
import com.portalestagio.webservicerest.repositories.EmpresaRepository;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepo;

    @PostConstruct
    public void criarEmpresaPadrao() {
        // Verifica se já existe empresa com ID = 1
        boolean jaExiste = empresaRepo.existsById(1L);
        if (!jaExiste) {
            // Se não existe, criamos um novo objeto e deixamos o JPA gerar o ID.
            Empresa emp = new Empresa();
            emp.setCnpj("12.345.678/0001-99");
            emp.setNomeFantasia("Empresa Padrão");
            emp.setEmailContato("contato@empresa1.com");
            emp.setEndereco("Rua Exemplo, 123");
            emp.setDescricao("Empresa padrão inserida automaticamente");
            emp.setTelefoneContato("(11) 99999-0001");
            emp.setRamoAtuacao("Tecnologia");
            empresaRepo.save(emp);
         
        }
    }


    @GetMapping
    public List<Empresa> listarTodas() {
        return empresaRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> buscarPorId(@PathVariable Long id) {
        Optional<Empresa> opt = empresaRepo.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(opt.get());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Empresa nova) {
        if (nova.getCnpj() == null || nova.getCnpj().isBlank()) {
            return ResponseEntity.badRequest().body("O campo 'cnpj' é obrigatório.");
        }
        if (nova.getNomeFantasia() == null || nova.getNomeFantasia().isBlank()) {
            return ResponseEntity.badRequest().body("O campo 'nomeFantasia' é obrigatório.");
        }
        if (nova.getEmailContato() == null || nova.getEmailContato().isBlank()) {
            return ResponseEntity.badRequest().body("O campo 'emailContato' é obrigatório.");
        }
        Empresa salva = empresaRepo.save(nova);
        return ResponseEntity.status(201).body(salva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Empresa dados) {
        Optional<Empresa> opt = empresaRepo.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Empresa existente = opt.get();

        if (dados.getCnpj() != null) {
            existente.setCnpj(dados.getCnpj());
        }
        if (dados.getNomeFantasia() != null) {
            existente.setNomeFantasia(dados.getNomeFantasia());
        }
        if (dados.getEmailContato() != null) {
            existente.setEmailContato(dados.getEmailContato());
        }
        if (dados.getEndereco() != null) {
            existente.setEndereco(dados.getEndereco());
        }
        if (dados.getDescricao() != null) {
            existente.setDescricao(dados.getDescricao());
        }
        if (dados.getTelefoneContato() != null) {
            existente.setTelefoneContato(dados.getTelefoneContato());
        }
        if (dados.getRamoAtuacao() != null) {
            existente.setRamoAtuacao(dados.getRamoAtuacao());
        }

        Empresa atualizada = empresaRepo.save(existente);
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Optional<Empresa> opt = empresaRepo.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Empresa empresa = opt.get();

        if (empresa.getVagasPublicadas() != null && !empresa.getVagasPublicadas().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Não é possível excluir esta empresa: existem vagas vinculadas.");
        }
        empresaRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
