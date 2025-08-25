package com.portalestagio.webservicerest.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.portalestagio.webservicerest.model.Inscricao;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    Optional<Inscricao> findByEstudante_IdAndVaga_Id(Long id, Long id2);

    List<Inscricao> findByVaga_Id(Long vagaId);

    List<Inscricao> findByEstudante_Id(Long estudanteId);
    
}
