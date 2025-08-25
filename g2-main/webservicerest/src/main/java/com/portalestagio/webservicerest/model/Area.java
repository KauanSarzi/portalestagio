package com.portalestagio.webservicerest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "TB_AREA")
public class Area {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String nome;

    //RELAÇAO COM VAGA ESTAGIO
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //PARA EVITAR ERRO DE SERIALIZAÇAO E LOOPS
    @ManyToMany(mappedBy = "areas", fetch = FetchType.LAZY) //GARANTE QUE vagas NAO SERA CARREGADO AUTOMATICAMENTE QUANDO A ENTIDADE AREAMODEL FOR BUSCADA, vagas SO SERA CARREGADA QUANDO SOLICITADA
    private List<VagaEstagio> vagas;


    //RELAÇAO COM ESTUDANTE
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //PARA EVITAR ERRO DE SERIALIZAÇAO E LOOPS
    @ManyToMany(mappedBy = "areas", fetch = FetchType.LAZY) //MESMA COISA QUE O FETCH ACIMA
    private List<Estudante> estudantes;


    public Area(){}

    public Area(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    //getter e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }


    public List<VagaEstagio> getVagas() { return vagas; }
    public void setVagas(List<VagaEstagio> vagas) { this.vagas = vagas; }
    
    public List<Estudante> getEstudantes() { return estudantes; }
    public void setEstudantes(List<Estudante> estudantes) { this.estudantes = estudantes; }
    
    
}
