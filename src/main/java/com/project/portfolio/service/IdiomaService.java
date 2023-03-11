package com.project.portfolio.service;

import com.project.portfolio.entity.Idioma;
import com.project.portfolio.repository.IdiomaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class IdiomaService {

    @Autowired
    IdiomaRepository idiomaRepository;

    //Retorna la lista de idiomas de la base de datos.
    public List<Idioma> list(){
        return idiomaRepository.findAll();
    }

}
