package com.portalestagio.webservicerest.model;
import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_INSCRICAO")
public class Inscricao {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false) 
    private Date dataInscricao;

    @Column(nullable = false)
    //@Enumerated(EnumType.STRING) avaliar para ver se o jpa suporta 
    private String status;

    //@Column(length= 500) define o tamanho maximo da mensagem
    private String mensagemApresentacao;

    //RELAÇAO COM VAGA
    @ManyToOne
    @JoinColumn(name = "vaga_id") //CHAVE ESTRANGEIRA PARA FAZER O RELACIONAMENTO
    private VagaEstagio vaga; // OBJETO CRIADO COM O MESMOMNOME DO MAPPED BY

    
    //RELAÇAO COM ESTUDANTE
    @ManyToOne
    @JoinColumn(name = "estudante_id") //CHAVE ESTRANGEIRA PARA FAZER O RELACIONAMENTO
    private Estudante estudante; // OBJETO CRIADO COM O MESMOMNOME DO MAPPED BY


    //CONSTRUTORES
    public Inscricao() {}

    public Inscricao(Long id, Date dataInscricao, String status, String mensagemApresentacao) {
        this.id = id;
        this.dataInscricao = dataInscricao;
        this.status = status;
        this.mensagemApresentacao = mensagemApresentacao;
        
    }



    //GETTER E SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getDataInscricao() { return dataInscricao; }
    public void setDataInscricao(Date dataInscricao) { this.dataInscricao = dataInscricao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMensagemApresentacao() { return mensagemApresentacao; }
    public void setMensagemApresentacao(String mensagemApresentacao) { this.mensagemApresentacao = mensagemApresentacao; }

    public VagaEstagio getVaga(){ return vaga; }
    public void setVaga(VagaEstagio vaga){ this.vaga = vaga; }

    public Estudante getEstudante() { return estudante; }
    public void setEstudante(Estudante estudante) { this.estudante = estudante; }
    

    
}
