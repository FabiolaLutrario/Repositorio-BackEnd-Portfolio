package com.project.portfolio.security.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.project.portfolio.dto.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class UsuarioDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank
    private String email;

    @NotBlank
    private String titulo;

    @JsonAlias("sobre_mi")
    private String sobreMi;

    private String foto;

    private String banner;

    private List<ExperienciaDTO> experiencias;

    private List<EducacionDTO> estudios;

    private List<PersonaTieneHabilidadDTO> personaHabilidades;

    private List <PersonaDominaIdiomaDTO> personaIdiomas;

    private List <ProyectoDTO> proyectos;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String nombre, String apellido, String email, String titulo, String sobreMi,
                      String foto, String banner, List<ExperienciaDTO> experiencias,
                      List<EducacionDTO> estudios, List<PersonaTieneHabilidadDTO> personaHabilidades,
                      List<PersonaDominaIdiomaDTO> personaIdiomas, List<ProyectoDTO> proyectos) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.titulo = titulo;
        this.sobreMi = sobreMi;
        this.foto = foto;
        this.banner = banner;
        this.experiencias=experiencias;
        this.estudios=estudios;
        this.personaHabilidades=personaHabilidades;
        this.personaIdiomas =personaIdiomas;
        this.proyectos=proyectos;
    }
}
