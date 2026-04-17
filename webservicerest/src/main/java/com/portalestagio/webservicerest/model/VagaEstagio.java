package com.portalestagio.webservicerest.model;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_VAGAESTAGIO")
public class VagaEstagio {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false) //definir se limita tamanho ou nao
    private String descricao;

    @Column(nullable = false)
    private Date dataPublicacao;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(nullable = false)
    private Double salario;

    @Column(nullable = false)
    private int cargaHoraria;

    @Column(nullable = false)
    private String modalidade; //hibrido, presencial ou online



    
 
    //RELAÇAO COM EMPRESA
    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
    //RELAÇAO COM INSCRIÇAO
    @OneToMany(mappedBy = "vaga", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) 
    private List<Inscricao> inscricaoVaga;

    //RELAÇAO COM AREA
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_vaga_area", joinColumns = @JoinColumn(name = "vaga_id"), inverseJoinColumns = @JoinColumn(name = "area_id"))
    private List<Area> areas;


    //CONSTRUTORES
    public VagaEstagio() {}

    public VagaEstagio(Long id, String titulo, String descricao, Boolean ativo, Double salario, int cargaHoraria, String modalidade) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.ativo = ativo;
        this.salario = salario;
        this.cargaHoraria = cargaHoraria;
        this.modalidade = modalidade;
    }


    //GETTERS E SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Date getDataPublicacao() { return dataPublicacao; }
    public void setDataPublicacao(Date dataPublicacao) { this.dataPublicacao = dataPublicacao; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public Double getSalario() { return salario; }
    public void setSalario(Double salario) { this.salario = salario; }

    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    public String getModalidade() { return modalidade; }
    public void setModalidade(String modalidade) { this.modalidade = modalidade; }
    
    public Empresa getEmpresa(){ return empresa; }
    public void setEmpresa(Empresa empresa){ this.empresa = empresa; }

    public List<Inscricao> getInscricaoVaga(){ return inscricaoVaga; }
    public void setInscricaoVaga(List<Inscricao> inscricaoVaga) { this.inscricaoVaga = inscricaoVaga; }

    public List<Area> getAreas() { return areas; }  
    public void setAreas(List<Area> areas) { this.areas = areas; }
    

    
}