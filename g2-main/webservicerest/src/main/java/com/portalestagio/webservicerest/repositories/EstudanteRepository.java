package com.portalestagio.webservicerest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.portalestagio.webservicerest.model.Estudante;

public interface EstudanteRepository extends JpaRepository<Estudante, Long> {
    
}
