package com.project.portfolio.controller;

import com.project.portfolio.dto.EducacionDTO;
import com.project.portfolio.entity.Educacion;
import com.project.portfolio.repository.EducacionRepository;
import com.project.portfolio.security.controller.Mensaje;
import com.project.portfolio.security.entity.Usuario;
import com.project.portfolio.security.repository.UsuarioRepository;
import com.project.portfolio.service.EducacionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/educacion")
@CrossOrigin(origins = "http://localhost:4200")
public class EducacionController {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private LocalDate fechaFinParse = null;

    @Autowired
    EducacionService educacionService;

    @Autowired
    EducacionRepository educacionRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/detail/{id}")
    public ResponseEntity<EducacionDTO> getById(@PathVariable("id") Integer id) {
        if (!educacionService.existsById(id))
            return new ResponseEntity(new Mensaje("No existe."), HttpStatus.NOT_FOUND);
        EducacionDTO educacionDTO = educacionService.getOne(id);
        return new ResponseEntity(educacionDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody EducacionDTO educacionDTO) {
        if (StringUtils.isBlank(educacionDTO.getTitulo()))
            return new ResponseEntity(new Mensaje("El título es obligatorio."), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(educacionDTO.getCentroEducativo()))
            return new ResponseEntity(new Mensaje("El nombre del centro donde cursó es obligatorio."), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(educacionDTO.getFechaInicio()))
            return new ResponseEntity(new Mensaje("La fecha de inicio es obligatoria."), HttpStatus.BAD_REQUEST);

        if (educacionService.exists(educacionDTO.getTitulo(), educacionDTO.getCentroEducativo(),
                LocalDate.parse(educacionDTO.getFechaInicio(), DATE_TIME_FORMATTER)))
            return new ResponseEntity(new Mensaje("Esa educación ya fue agregada a su perfil."), HttpStatus.BAD_REQUEST);

        if (educacionDTO.getFechaFin() != null) {
            fechaFinParse = LocalDate.parse(educacionDTO.getFechaFin(), DATE_TIME_FORMATTER);
        }

        Usuario usuario = usuarioRepository.findById(educacionDTO.getUsuarioId()).orElse(null);
        Educacion educacion = new Educacion(educacionDTO.getLogo(),
                educacionDTO.getTitulo(), educacionDTO.getCentroEducativo(),
                LocalDate.parse(educacionDTO.getFechaInicio(), DATE_TIME_FORMATTER),
                fechaFinParse,
                educacionDTO.getSeEncuentraCursando(), educacionDTO.getDescripcion(), usuario);
        educacionService.save(educacion);

        return new ResponseEntity(new Mensaje("Educación agregada."), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody EducacionDTO educacionDTO) {
        if (!educacionService.existsById(id))
            return new ResponseEntity(new Mensaje("El ID no existe."), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(educacionDTO.getTitulo()))
            return new ResponseEntity(new Mensaje("El título es obligatorio."), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(educacionDTO.getCentroEducativo()))
            return new ResponseEntity(new Mensaje("El nombre del centro donde cursó es obligatorio."), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(educacionDTO.getFechaInicio()))
            return new ResponseEntity(new Mensaje("La fecha de inicio es obligatoria."), HttpStatus.BAD_REQUEST);

        if (educacionDTO.getFechaFin() != null) {
            fechaFinParse = LocalDate.parse(educacionDTO.getFechaFin(), DATE_TIME_FORMATTER);
        }

        Educacion educacion = educacionRepository.findById(id).orElse(null);

        /*Con las instrucciones del bloque de abajo, lo que hacemos es buscar si ya está asociada la educación
        al usuario en caso de que el usuario quiera modificar los campos "titulo", "centroEducativo" y
         "fechaInicio" (Evita que se guarde la misma educacion 2 veces.)*/
        Optional<Educacion> educacionComparator = educacionRepository.findByTitulo(educacionDTO.getTitulo());
        if (educacionService.exists(educacionDTO.getTitulo(), educacionDTO.getCentroEducativo(),
                LocalDate.parse(educacionDTO.getFechaInicio(), DATE_TIME_FORMATTER))
                && educacion.getId() != educacionComparator.get().getId())
            return new ResponseEntity(new Mensaje("Esa educación ya existe en su perfil."), HttpStatus.BAD_REQUEST);

        educacion.setLogo(educacionDTO.getLogo());
        educacion.setTitulo(educacionDTO.getTitulo());
        educacion.setCentroEducativo(educacionDTO.getCentroEducativo());
        educacion.setFechaInicio(LocalDate.parse(educacionDTO.getFechaInicio(), DATE_TIME_FORMATTER));
        educacion.setFechaFin(fechaFinParse);
        educacion.setSeEncuentraCursando(educacionDTO.getSeEncuentraCursando());
        educacion.setDescripcion(educacionDTO.getDescripcion());

        educacionService.save(educacion);
        return new ResponseEntity(new Mensaje("Educacion actualizada."), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        if (!educacionService.existsById(id))
            return new ResponseEntity(new Mensaje("El ID no existe."), HttpStatus.BAD_REQUEST);

        educacionService.delete(id);

        return new ResponseEntity(new Mensaje("Educacion eliminada."), HttpStatus.OK);
    }
}
