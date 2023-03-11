package com.project.portfolio.security.service;

import com.project.portfolio.security.dto.UsuarioDTO;
import com.project.portfolio.security.entity.Usuario;
import com.project.portfolio.security.repository.UsuarioRepository;
import com.project.portfolio.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ExperienciaService experienciaService;

    @Autowired
    EducacionService educacionService;

    @Autowired
    PersonaTieneHabilidadService personaTieneHabilidadService;

    @Autowired
    PersonaDominaIdiomaService personaDominaIdiomaService;

    @Autowired
    ProyectoService proyectoService;

    public Optional<Usuario> getByEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO findUser(Integer id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        return mapToDTO(usuario);
    }

    public void save (Usuario usuario){
        usuarioRepository.save(usuario);
    }

    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO mapToDTO (Usuario usuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario.getNombre(), usuario.getApellido(),
                usuario.getEmail(),usuario.getTitulo(),usuario.getSobreMi(),
                usuario.getFoto(), usuario.getBanner(),
                experienciaService.mapToDTOS(usuario.getExperiencias()),
                educacionService.mapToDTOS(usuario.getEstudios()),
                personaTieneHabilidadService.mapToDTOS(usuario.getPersonaHabilidades()),
                personaDominaIdiomaService.mapToDTOS(usuario.getPersonaIdiomas()),
                proyectoService.mapToDTOS(usuario.getProyectos()));

        return usuarioDTO;
    }
}
