package com.project.portfolio.service;

import com.project.portfolio.dto.PersonaDominaIdiomaDTO;
import com.project.portfolio.entity.PersonaDominaIdioma;
import com.project.portfolio.repository.PersonaDominaIdiomaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonaDominaIdiomaService {

    @Autowired
    PersonaDominaIdiomaRepository personaDominaIdiomaRepository;

    public PersonaDominaIdiomaDTO getOne (Integer id){
        PersonaDominaIdioma personaDominaIdioma = personaDominaIdiomaRepository.findById(id).orElse(null);
        return mapToDTO(personaDominaIdioma);
    }

    public boolean existsById(Integer id){
        return personaDominaIdiomaRepository.existsById(id);
    }

    public boolean existsByIdiomaId(Integer id){
        return personaDominaIdiomaRepository.existsByIdiomaId(id);
    }

    public boolean exists(String idioma){
        return personaDominaIdiomaRepository.existsByIdioma(idioma);
    }

    public void save (PersonaDominaIdioma personaDominaIdioma){
        personaDominaIdiomaRepository.save(personaDominaIdioma);
    }

    public void delete(Integer id){
        personaDominaIdiomaRepository.deleteById(id);
    }

    public List<PersonaDominaIdiomaDTO> mapToDTOS(List<PersonaDominaIdioma> personaIdiomas) {

        return personaIdiomas.stream()
                .map(personaIdioma -> mapToDTO(personaIdioma))
                .collect(Collectors.toList());
    }

    private PersonaDominaIdiomaDTO mapToDTO (PersonaDominaIdioma personaDominaIdioma){
        PersonaDominaIdiomaDTO personaDominaIdiomaDTO = new PersonaDominaIdiomaDTO(personaDominaIdioma.getIdioma().getId(),
                personaDominaIdioma.getIdioma().getIdioma(),personaDominaIdioma.getPorcentaje().getId());
        personaDominaIdiomaDTO.setId(personaDominaIdioma.getId());

        return personaDominaIdiomaDTO;
    }
}
