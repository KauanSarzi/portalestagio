package com.portalestagio.webservicerest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.portalestagio.webservicerest.model.Area;

public interface AreaRepository extends JpaRepository<Area, Long> {
    
}
