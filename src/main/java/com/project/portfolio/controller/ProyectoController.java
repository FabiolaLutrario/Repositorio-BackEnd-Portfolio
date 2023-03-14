package com.project.portfolio.controller;

import com.project.portfolio.dto.ProyectoDTO;
import com.project.portfolio.entity.Proyecto;
import com.project.portfolio.repository.ProyectoRepository;
import com.project.portfolio.security.controller.Mensaje;
import com.project.portfolio.security.entity.Usuario;
import com.project.portfolio.security.repository.UsuarioRepository;
import com.project.portfolio.service.ProyectoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/proyecto")
@CrossOrigin(origins = "https://frontendportfolio-bff8c.web.app")
public class ProyectoController {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    ProyectoService proyectoService;

    @Autowired
    ProyectoRepository proyectoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/detail/{id}")
    public ResponseEntity<ProyectoDTO> getById(@PathVariable("id") Integer id) {
        if (!proyectoService.existsById(id))
            return new ResponseEntity(new Mensaje("El proyecto no existe."), HttpStatus.NOT_FOUND);
        ProyectoDTO proyectoDTO = proyectoService.getOne(id);
        return new ResponseEntity(proyectoDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProyectoDTO proyectoDTO) {
        if (StringUtils.isBlank(proyectoDTO.getNombre()))
            return new ResponseEntity(new Mensaje("El nombre del proyecto es obligatorio."), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(proyectoDTO.getFechaRealizacion()))
            return new ResponseEntity(new Mensaje("La fecha de realización del proyecto es obligatoria."), HttpStatus.BAD_REQUEST);

        if (proyectoService.exists(proyectoDTO.getNombre(),
                LocalDate.parse(proyectoDTO.getFechaRealizacion(), DATE_TIME_FORMATTER),
                proyectoDTO.getLink()))
            return new ResponseEntity(new Mensaje("Ese proyecto ya fue agregado a su perfil."), HttpStatus.BAD_REQUEST);

        Usuario usuario = usuarioRepository.findById(proyectoDTO.getUsuarioId()).orElse(null);
        Proyecto proyecto = new Proyecto(proyectoDTO.getLogo(),
                proyectoDTO.getNombre(), LocalDate.parse(proyectoDTO.getFechaRealizacion(), DATE_TIME_FORMATTER),
                proyectoDTO.getDescripcion(), proyectoDTO.getLink(), proyectoDTO.getImagen(), usuario);
        proyectoService.save(proyecto);

        return new ResponseEntity(new Mensaje("Proyecto agregado."), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody ProyectoDTO proyectoDTO) {
        if (!proyectoService.existsById(id))
            return new ResponseEntity(new Mensaje("El ID no existe."), HttpStatus.BAD_REQUEST);

        if (StringUtils.isBlank(proyectoDTO.getNombre()))
            return new ResponseEntity(new Mensaje("El nombre del proyecto es obligatorio."), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(proyectoDTO.getFechaRealizacion()))
            return new ResponseEntity(new Mensaje("La fecha de realización del proyecto es obligatoria."), HttpStatus.BAD_REQUEST);

        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);

        /*Con las instrucciones del bloque de abajo, lo que hacemos es buscar si ya está asociado el proyecto
        al usuario en caso de que el usuario quiera modificar los campos "nombre", "fechaRealizacion" y
         "link" (Evita que se guarde el mismo proyecto 2 veces.)*/
        Optional<Proyecto> proyectoComparator = proyectoRepository.findByNombre(proyectoDTO.getNombre());
        if (proyectoService.exists(proyectoDTO.getNombre(),
                LocalDate.parse(proyectoDTO.getFechaRealizacion(), DATE_TIME_FORMATTER),
        proyectoDTO.getLink())
                && proyecto.getId() != proyectoComparator.get().getId())
            return new ResponseEntity(new Mensaje("Ese proyecto ya fue agregado a su perfil."), HttpStatus.BAD_REQUEST);


        proyecto.setLogo(proyectoDTO.getLogo());
        proyecto.setNombre(proyectoDTO.getNombre());
        proyecto.setFechaRealizacion(LocalDate.parse(proyectoDTO.getFechaRealizacion(), DATE_TIME_FORMATTER));
        proyecto.setDescripcion(proyectoDTO.getDescripcion());
        proyecto.setLink(proyectoDTO.getLink());
        proyecto.setImagen(proyectoDTO.getImagen());

        proyectoService.save(proyecto);
        return new ResponseEntity(new Mensaje("Proyecto actualizado."), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        if (!proyectoService.existsById(id))
            return new ResponseEntity(new Mensaje("El ID no existe."), HttpStatus.BAD_REQUEST);

        proyectoService.delete(id);

        return new ResponseEntity(new Mensaje("Proyecto eliminado."), HttpStatus.OK);
    }
}
