package com.project.portfolio.service;

import com.project.portfolio.dto.PersonaTieneHabilidadDTO;
import com.project.portfolio.entity.PersonaTieneHabilidad;
import com.project.portfolio.repository.PersonaTieneHabilidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonaTieneHabilidadService {

    @Autowired
    PersonaTieneHabilidadRepository personaTieneHabilidadRepository;

    public PersonaTieneHabilidadDTO getOne (Integer id){
        PersonaTieneHabilidad personaTieneHabilidad = personaTieneHabilidadRepository.findById(id).orElse(null);
        return mapToDTO(personaTieneHabilidad);
    }

    public boolean existsById(Integer id){
        return personaTieneHabilidadRepository.existsById(id);
    }

    public boolean exists(String habilidad){
        return personaTieneHabilidadRepository.existsByHabilidad(habilidad);
    }

    public void save (PersonaTieneHabilidad personaTieneHabilidad){
        personaTieneHabilidadRepository.save(personaTieneHabilidad);
    }

    public void delete(Integer id){
        personaTieneHabilidadRepository.deleteById(id);
    }

    public List<PersonaTieneHabilidadDTO> mapToDTOS(List<PersonaTieneHabilidad> personaHabilidades) {

        return personaHabilidades.stream()
                .map(personaHabilidad -> mapToDTO(personaHabilidad))
                .collect(Collectors.toList());
    }

    private PersonaTieneHabilidadDTO mapToDTO (PersonaTieneHabilidad personaTieneHabilidad){
        PersonaTieneHabilidadDTO personaTieneHabilidadDTO = new PersonaTieneHabilidadDTO(personaTieneHabilidad.getHabilidad(),
                personaTieneHabilidad.getPorcentaje().getId());
        personaTieneHabilidadDTO.setId(personaTieneHabilidad.getId());

        return personaTieneHabilidadDTO;
    }
}
