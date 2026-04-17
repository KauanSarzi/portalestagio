package com.portalestagio.webservicerest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.portalestagio.webservicerest.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    
}
