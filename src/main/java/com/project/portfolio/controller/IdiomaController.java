package com.project.portfolio.controller;

import com.project.portfolio.dto.PersonaDominaIdiomaDTO;
import com.project.portfolio.entity.Idioma;
import com.project.portfolio.entity.PersonaDominaIdioma;
import com.project.portfolio.entity.Porcentaje;
import com.project.portfolio.repository.IdiomaRepository;
import com.project.portfolio.repository.PersonaDominaIdiomaRepository;
import com.project.portfolio.repository.PorcentajeRepository;
import com.project.portfolio.security.controller.Mensaje;
import com.project.portfolio.security.entity.Usuario;
import com.project.portfolio.security.repository.UsuarioRepository;
import com.project.portfolio.service.IdiomaService;
import com.project.portfolio.service.PersonaDominaIdiomaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/idioma")
@CrossOrigin(origins = {"https://frontendportfolio-bff8c.web.app", "http://localhost:4200"})
public class IdiomaController {

    @Autowired
    PersonaDominaIdiomaService personaDominaIdiomaService;

    @Autowired
    PersonaDominaIdiomaRepository personaDominaIdiomaRepository;

    @Autowired
    IdiomaService idiomaService;

    @Autowired
    IdiomaRepository idiomaRepository;

    @Autowired
    PorcentajeRepository porcentajeRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    //Retorna la lista de idiomas de la base de datos.
    @GetMapping("/list")
    public ResponseEntity<List<Idioma>> list(){
        List<Idioma> list = idiomaService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<PersonaDominaIdiomaDTO> getById(@PathVariable("id") Integer id) {
        if (!personaDominaIdiomaService.existsById(id))
            return new ResponseEntity(new Mensaje("El idioma que intenta seleccionar no existe."), HttpStatus.NOT_FOUND);
        PersonaDominaIdiomaDTO personaDominaIdiomaDTO = personaDominaIdiomaService.getOne(id);
        return new ResponseEntity(personaDominaIdiomaDTO, HttpStatus.OK);
    }

    //Guarda un idioma en un usuario
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PersonaDominaIdiomaDTO personaDominaIdiomaDTO) {
        if (StringUtils.isBlank(personaDominaIdiomaDTO.getIdiomaId().toString()))
            return new ResponseEntity(new Mensaje("El idioma es obligatorio."), HttpStatus.BAD_REQUEST);
        String porcentajeId = personaDominaIdiomaDTO.getPorcentajeId().toString();
        if (StringUtils.isBlank(porcentajeId))
            return new ResponseEntity(new Mensaje("El porcentaje del dominio del idioma es obligatorio."), HttpStatus.BAD_REQUEST);

        if (personaDominaIdiomaService.existsByIdiomaId(personaDominaIdiomaDTO.getIdiomaId()))
            return new ResponseEntity(new Mensaje("Ese idioma que intenta asociar a su perfil ya existe."), HttpStatus.BAD_REQUEST);

        Idioma idioma = idiomaRepository.findById(personaDominaIdiomaDTO.getIdiomaId()).orElse(null);
        Usuario usuario = usuarioRepository.findById(personaDominaIdiomaDTO.getUsuarioId()).orElse(null);
        Porcentaje porcentaje = porcentajeRepository.findById(personaDominaIdiomaDTO.getPorcentajeId()).orElse(null);

        PersonaDominaIdioma personaDominaIdioma = new PersonaDominaIdioma(idioma, porcentaje, usuario);
        personaDominaIdiomaService.save(personaDominaIdioma);

        return new ResponseEntity(new Mensaje("Idioma agregado."), HttpStatus.OK);
    }

    //Actualiza un idioma en un usuario
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody PersonaDominaIdiomaDTO personaDominaIdiomaDTO) {
        if (StringUtils.isBlank(personaDominaIdiomaDTO.getIdiomaId().toString()))
            return new ResponseEntity(new Mensaje("Debe seleccionar un idioma."), HttpStatus.BAD_REQUEST);
        String porcentajeId = personaDominaIdiomaDTO.getPorcentajeId().toString();
        if (StringUtils.isBlank(porcentajeId))
            return new ResponseEntity(new Mensaje("El porcentaje del dominio del idioma es obligatorio."), HttpStatus.BAD_REQUEST);

        PersonaDominaIdioma personaDominaIdioma = personaDominaIdiomaRepository.findById(id).orElse(null);
        Idioma idioma = idiomaRepository.findById(personaDominaIdiomaDTO.getIdiomaId()).orElse(null);
        Porcentaje porcentaje = porcentajeRepository.findById(personaDominaIdiomaDTO.getPorcentajeId()).orElse(null);

/*        Con las instrucciones del bloque de abajo, lo que hacemos es buscar si ya está asociado el idioma
        al usuario en caso de que el usuario quiera modificar el campo "idioma" (Evita que se guarde el mismo
        idioma 2 veces.)*/
        Optional<PersonaDominaIdioma> personaIdiomaComparator = personaDominaIdiomaRepository.findByIdiomaId(personaDominaIdiomaDTO.getIdiomaId());
        if (personaDominaIdiomaService.existsByIdiomaId(personaDominaIdiomaDTO.getIdiomaId())
        && personaDominaIdioma.getId()!= personaIdiomaComparator.get().getId())
            return new ResponseEntity(new Mensaje("Ese idioma ya existe."), HttpStatus.BAD_REQUEST);

        personaDominaIdioma.setIdioma(idioma);
        personaDominaIdioma.setPorcentaje(porcentaje);

        personaDominaIdiomaService.save(personaDominaIdioma);
        return new ResponseEntity(new Mensaje("Idioma actualizado."), HttpStatus.OK);
    }

    //Elimina un idioma en un usuario
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        if (!personaDominaIdiomaService.existsById(id))
            return new ResponseEntity(new Mensaje("El ID del idioma que intenta eliminar no existe."), HttpStatus.BAD_REQUEST);

        personaDominaIdiomaService.delete(id);

        return new ResponseEntity(new Mensaje("Idioma eliminado."), HttpStatus.OK);
    }
}
