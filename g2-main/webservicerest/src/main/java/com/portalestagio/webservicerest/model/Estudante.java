package com.portalestagio.webservicerest.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;
import java.lang.Long;


@Entity
@Table(name = "TB_ESTUDANTE")
public class Estudante{
   

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true) // email deve ser unico entre os estudantes
    private String email;

    @Column(nullable = false)
    private int anoIngresso; 

    @Column(nullable = false)
    private String curso; 

    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(nullable = false)
    private String periodoAtual;


    //RELAÇAO COM INSCRIÇAO
    @OneToMany(mappedBy = "estudante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //evita com que a resposta json do estudante tenha uma lista completa de inscriçoe, e tbm evita loop
    private List<Inscricao> inscricoes;


    //RELAÇAO COM AREAS
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_estudante_area", joinColumns = @JoinColumn(name = "estudante_id"), inverseJoinColumns = @JoinColumn(name = "area_id"))
    private List<Area> areas;



    public Estudante() {
    }

    public Estudante(Long id, String nome, String email, int anoIngresso, String curso, String matricula, String periodoAtual) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.anoIngresso = anoIngresso;
        this.curso = curso;
        this.matricula = matricula;
        this.periodoAtual = periodoAtual;
    }

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAnoIngresso() { return anoIngresso; }
    public void setAnoIngresso(int anoIngresso) { this.anoIngresso = anoIngresso; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getPeriodoAtual() { return periodoAtual; }
    public void setPeriodoAtual(String periodoAtual) { this.periodoAtual = periodoAtual; }
    
    public List<Inscricao> getInscricoes() { return inscricoes; }   
    public void setInscricoes(List<Inscricao> inscricoes) { this.inscricoes = inscricoes; }

    public List<Area> getAreas() { return areas; }  
    public void setAreas(List<Area> areas) { this.areas = areas; }
    


    
}
