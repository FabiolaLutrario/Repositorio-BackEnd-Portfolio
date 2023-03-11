package com.project.portfolio.repository;

import com.project.portfolio.entity.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdiomaRepository extends JpaRepository<Idioma, Integer> {

    Optional<Idioma> findByIdioma (String idioma);
}
