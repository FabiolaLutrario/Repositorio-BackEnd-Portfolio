package com.project.portfolio.service;

import com.project.portfolio.entity.Porcentaje;
import com.project.portfolio.repository.PorcentajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PorcentajeService {

    @Autowired
    PorcentajeRepository porcentajeRepository;

    public List<Porcentaje> list(){
        return porcentajeRepository.findAll();
    }
}
