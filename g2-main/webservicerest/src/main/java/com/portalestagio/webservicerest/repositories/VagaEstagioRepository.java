package com.portalestagio.webservicerest.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.portalestagio.webservicerest.model.VagaEstagio;

public interface VagaEstagioRepository extends JpaRepository<VagaEstagio, Long> {

    VagaEstagio findVagaEstagioByTitulo(String titulo); //query(interna) para buscar na base de dados uma determinada vaga de estagio e seu titulo 

    VagaEstagio findVagaEstagioById(Long id);

    Page<VagaEstagio> findByAtivoTrue(Pageable pg);

    Page<VagaEstagio> findByAtivoTrueAndAreas_IdIn(List<Long> areaIds, Pageable pg);

    Page<VagaEstagio> findByEmpresa_IdAndAtivoTrue(Long empresaId, Pageable pg);


    //@Query PARA CONSULTAS MAIS COMPLEXAS


}
