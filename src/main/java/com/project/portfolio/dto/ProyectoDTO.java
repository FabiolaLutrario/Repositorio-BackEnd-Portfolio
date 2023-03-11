package com.project.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProyectoDTO {

    private Integer id;

    private String logo;

    @NotBlank
    private String nombre;

    @NotBlank
    @JsonAlias("fecha_realizacion")
    private String fechaRealizacion;

    private String descripcion;

    private String link;

    private String imagen;

    @JsonAlias("usuario_id")
    private Integer usuarioId;

    public ProyectoDTO() {
    }

    public ProyectoDTO(String logo, String nombre, String fechaRealizacion, String descripcion, String link, String imagen) {
        this.logo = logo;
        this.nombre = nombre;
        this.fechaRealizacion = fechaRealizacion;
        this.descripcion = descripcion;
        this.link = link;
        this.imagen = imagen;
    }

    public ProyectoDTO(String logo, String nombre, String fechaRealizacion, String descripcion, String link, String imagen, Integer usuarioId) {
        this.logo = logo;
        this.nombre = nombre;
        this.fechaRealizacion = fechaRealizacion;
        this.descripcion = descripcion;
        this.link = link;
        this.imagen = imagen;
        this.usuarioId = usuarioId;
    }
}
