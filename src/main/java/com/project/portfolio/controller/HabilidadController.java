package com.project.portfolio.controller;

import com.project.portfolio.dto.PersonaTieneHabilidadDTO;
import com.project.portfolio.entity.PersonaTieneHabilidad;
import com.project.portfolio.entity.Porcentaje;
import com.project.portfolio.repository.PersonaTieneHabilidadRepository;
import com.project.portfolio.repository.PorcentajeRepository;
import com.project.portfolio.security.controller.Mensaje;
import com.project.portfolio.security.entity.Usuario;
import com.project.portfolio.security.repository.UsuarioRepository;
import com.project.portfolio.service.PersonaTieneHabilidadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/habilidad")
@CrossOrigin(origins = {"https://frontendportfolio-bff8c.web.app", "http://localhost:4200"})
public class HabilidadController {

    @Autowired
    PersonaTieneHabilidadService personaTieneHabilidadService;

    @Autowired
    PersonaTieneHabilidadRepository personaTieneHabilidadRepository;

    @Autowired
    PorcentajeRepository porcentajeRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/detail/{id}")
    public ResponseEntity<PersonaTieneHabilidadDTO> getById(@PathVariable("id") Integer id) {
        if (!personaTieneHabilidadService.existsById(id))
            return new ResponseEntity(new Mensaje("La habilidad no existe."), HttpStatus.NOT_FOUND);
        PersonaTieneHabilidadDTO personaTieneHabilidadDTO = personaTieneHabilidadService.getOne(id);
        return new ResponseEntity(personaTieneHabilidadDTO, HttpStatus.OK);
    }

    //Guarda una habilidad en un usuario
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PersonaTieneHabilidadDTO personaTieneHabilidadDTO) {
        if (StringUtils.isBlank(personaTieneHabilidadDTO.getHabilidad()))
            return new ResponseEntity(new Mensaje("La habilidad es obligatoria."), HttpStatus.BAD_REQUEST);
        String porcentajeId = personaTieneHabilidadDTO.getPorcentajeId().toString();
        if (StringUtils.isBlank(porcentajeId))
            return new ResponseEntity(new Mensaje("El porcentaje del dominio de la habilidad es obligatorio."), HttpStatus.BAD_REQUEST);

        if (personaTieneHabilidadService.exists(personaTieneHabilidadDTO.getHabilidad()))
            return new ResponseEntity(new Mensaje("Esa habilidad ya existe."), HttpStatus.BAD_REQUEST);

        Usuario usuario = usuarioRepository.findById(personaTieneHabilidadDTO.getUsuarioId()).orElse(null);
        Porcentaje porcentaje = porcentajeRepository.findById(personaTieneHabilidadDTO.getPorcentajeId()).orElse(null);

        PersonaTieneHabilidad personaTieneHabilidad = new PersonaTieneHabilidad(personaTieneHabilidadDTO.getHabilidad(), porcentaje, usuario);
        personaTieneHabilidadService.save(personaTieneHabilidad);

        return new ResponseEntity(new Mensaje("Habilidad agregada."), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody PersonaTieneHabilidadDTO personaTieneHabilidadDTO) {
        if (StringUtils.isBlank(personaTieneHabilidadDTO.getHabilidad()))
            return new ResponseEntity(new Mensaje("La habilidad es obligatoria."), HttpStatus.BAD_REQUEST);
        String porcentajeId = personaTieneHabilidadDTO.getPorcentajeId().toString();
        if (StringUtils.isBlank(porcentajeId))
            return new ResponseEntity(new Mensaje("El porcentaje del dominio de la habilidad es obligatorio."), HttpStatus.BAD_REQUEST);

        PersonaTieneHabilidad personaTieneHabilidad = personaTieneHabilidadRepository.findById(id).orElse(null);
        Porcentaje porcentaje = porcentajeRepository.findById(personaTieneHabilidadDTO.getPorcentajeId()).orElse(null);

        /*Con las instrucciones del bloque de abajo, lo que hacemos es buscar si ya está asociada la habilidad
        al usuario en caso de que el usuario quiera modificar el campo "habilidad" (Evita que se guarde la misma
        habilidad 2 veces.)*/
        Optional<PersonaTieneHabilidad> personaHabilidadComparator = personaTieneHabilidadRepository.findByHabilidad(personaTieneHabilidadDTO.getHabilidad());
        if (personaTieneHabilidadService.exists(personaTieneHabilidadDTO.getHabilidad())
        && personaTieneHabilidad.getId() != personaHabilidadComparator.get().getId())
            return new ResponseEntity(new Mensaje("Esa habilidad ya existe."), HttpStatus.BAD_REQUEST);

        personaTieneHabilidad.setHabilidad(personaTieneHabilidadDTO.getHabilidad());
        personaTieneHabilidad.setPorcentaje(porcentaje);

        personaTieneHabilidadService.save(personaTieneHabilidad);
        return new ResponseEntity(new Mensaje("Habilidad actualizada."), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        if (!personaTieneHabilidadService.existsById(id))
            return new ResponseEntity(new Mensaje("El ID de la habilidad que intenta eliminar no existe."), HttpStatus.BAD_REQUEST);

        personaTieneHabilidadService.delete(id);

        return new ResponseEntity(new Mensaje("Habilidad eliminada."), HttpStatus.OK);
    }
}
