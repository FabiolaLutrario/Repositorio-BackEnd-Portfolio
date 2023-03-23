package com.project.portfolio.controller;

import com.project.portfolio.dto.ExperienciaDTO;
import com.project.portfolio.entity.Experiencia;
import com.project.portfolio.repository.ExperienciaRepository;
import com.project.portfolio.security.controller.Mensaje;
import com.project.portfolio.security.entity.Usuario;
import com.project.portfolio.security.repository.UsuarioRepository;
import com.project.portfolio.service.ExperienciaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/expLaboral")
@CrossOrigin(origins = {"https://frontendportfolio-bff8c.web.app", "http://localhost:4200"})
public class ExperienciaController {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private LocalDate fechaFinParse = null;

    @Autowired
    ExperienciaService experienciaService;

    @Autowired
    ExperienciaRepository experienciaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/detail/{id}")
    public ResponseEntity<ExperienciaDTO> getById(@PathVariable("id") Integer id) {
        if (!experienciaService.existsById(id))
            return new ResponseEntity(new Mensaje("La experiencia no existe."), HttpStatus.NOT_FOUND);
        ExperienciaDTO experienciaDTO = experienciaService.getOne(id);
        return new ResponseEntity(experienciaDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ExperienciaDTO experienciaDTO) {
        if (StringUtils.isBlank(experienciaDTO.getCargo()))
            return new ResponseEntity(new Mensaje("El cargo es obligatorio."), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(experienciaDTO.getEmpresa()))
            return new ResponseEntity(new Mensaje("El nombre de la empresa donde trabajó es obligatorio."), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(experienciaDTO.getFechaInicio()))
            return new ResponseEntity(new Mensaje("La fecha de inicio es obligatoria."), HttpStatus.BAD_REQUEST);

        if (experienciaService.exists(experienciaDTO.getCargo(), experienciaDTO.getEmpresa(),
                LocalDate.parse(experienciaDTO.getFechaInicio(), DATE_TIME_FORMATTER)))
            return new ResponseEntity(new Mensaje("Esa experiencia ya existe."), HttpStatus.BAD_REQUEST);

        if (experienciaDTO.getFechaFin() != null) {
            fechaFinParse = LocalDate.parse(experienciaDTO.getFechaFin(), DATE_TIME_FORMATTER);
        }

        Usuario usuario = usuarioRepository.findById(experienciaDTO.getUsuarioId()).orElse(null);
        Experiencia experiencia = new Experiencia(experienciaDTO.getLogo(),
                experienciaDTO.getCargo(), experienciaDTO.getEmpresa(),
                LocalDate.parse(experienciaDTO.getFechaInicio(), DATE_TIME_FORMATTER),
                fechaFinParse,
                experienciaDTO.getEsTrabajoActual(), experienciaDTO.getDescripcion(), usuario);
        experienciaService.save(experiencia);

        return new ResponseEntity(new Mensaje("Experiencia agregada."), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody ExperienciaDTO experienciaDTO) {
        if (!experienciaService.existsById(id))
            return new ResponseEntity(new Mensaje("El ID no existe."), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(experienciaDTO.getCargo()))
            return new ResponseEntity(new Mensaje("El cargo es obligatorio."), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(experienciaDTO.getEmpresa()))
            return new ResponseEntity(new Mensaje("El nombre de la empresa donde trabajó es obligatorio."), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(experienciaDTO.getFechaInicio()))
            return new ResponseEntity(new Mensaje("La fecha de inicio es obligatoria."), HttpStatus.BAD_REQUEST);

        if (experienciaDTO.getFechaFin() != null) {
            fechaFinParse = LocalDate.parse(experienciaDTO.getFechaFin(), DATE_TIME_FORMATTER);
        }

        Experiencia experiencia = experienciaRepository.findById(id).orElse(null);

        /*Con las instrucciones del bloque de abajo, lo que hacemos es buscar si ya está asociada la experencia laboral
        al usuario en caso de que el usuario quiera modificar los campos "cargo", "empresa" y
         "fechaInicio" (Evita que se guarde la misma experiencia 2 veces.)*/
        Optional<Experiencia> experienciaComparator = experienciaRepository.findByCargo(experienciaDTO.getCargo());
        if (experienciaService.exists(experienciaDTO.getCargo(), experienciaDTO.getEmpresa(),
                LocalDate.parse(experienciaDTO.getFechaInicio(), DATE_TIME_FORMATTER))
                && experiencia.getId() != experienciaComparator.get().getId())
            return new ResponseEntity(new Mensaje("Esa experiencia ya existe."), HttpStatus.BAD_REQUEST);


        experiencia.setLogo(experienciaDTO.getLogo());
        experiencia.setCargo(experienciaDTO.getCargo());
        experiencia.setEmpresa(experienciaDTO.getEmpresa());
        experiencia.setFechaInicio(LocalDate.parse(experienciaDTO.getFechaInicio(), DATE_TIME_FORMATTER));
        experiencia.setFechaFin(fechaFinParse);
        experiencia.setEsTrabajoActual(experienciaDTO.getEsTrabajoActual());
        experiencia.setDescripcion(experienciaDTO.getDescripcion());

        experienciaService.save(experiencia);
        return new ResponseEntity(new Mensaje("Experiencia actualizada."), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        if (!experienciaService.existsById(id))
            return new ResponseEntity(new Mensaje("El ID no existe."), HttpStatus.BAD_REQUEST);

        experienciaService.delete(id);

        return new ResponseEntity(new Mensaje("Experiencia eliminada."), HttpStatus.OK);
    }
}
