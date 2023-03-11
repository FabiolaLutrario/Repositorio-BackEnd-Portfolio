package com.project.portfolio.security.service;

import com.project.portfolio.security.entity.Rol;
import com.project.portfolio.security.enums.RolNombre;
import com.project.portfolio.security.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository iRolRepository;

    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return  iRolRepository.findByRolNombre(rolNombre);
    }

    public void save (Rol rol){
        iRolRepository.save(rol);
    }
}
