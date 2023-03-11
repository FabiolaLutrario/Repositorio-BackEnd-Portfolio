package com.project.portfolio.repository;

import com.project.portfolio.entity.Porcentaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PorcentajeRepository extends JpaRepository <Porcentaje, Integer> {
}
