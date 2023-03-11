package com.project.portfolio.repository;

import com.project.portfolio.entity.PersonaDominaIdioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaDominaIdiomaRepository extends JpaRepository <PersonaDominaIdioma, Integer> {

    boolean existsByIdioma(String idioma);
    boolean existsByIdiomaId(Integer idiomaId);
    Optional<PersonaDominaIdioma> findByIdiomaId (Integer idiomaId);
}
