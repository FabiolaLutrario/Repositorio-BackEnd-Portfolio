package com.project.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ExperienciaDTO {

    private Integer id;

    private String logo;
    @NotBlank
    private String cargo;
    @NotBlank
    private String empresa;
    @NotBlank
    @JsonAlias("fecha_inicio")
    private String fechaInicio;
    @JsonAlias("fecha_fin")
    private String fechaFin;
    @NotBlank
    @JsonAlias("es_trabajo_actual")
    private Boolean esTrabajoActual;
    private String descripcion;
    @JsonAlias("usuario_id")
    private Integer usuarioId;

    public ExperienciaDTO() {
    }

    public ExperienciaDTO(String logo, String cargo, String empresa, String fechaInicio, String fechaFin,
                          Boolean esTrabajoActual, String descripcion, Integer usuarioId) {
        this.logo = logo;
        this.cargo = cargo;
        this.empresa = empresa;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.esTrabajoActual = esTrabajoActual;
        this.descripcion = descripcion;
        this.usuarioId= usuarioId;
    }

    public ExperienciaDTO(String logo, String cargo, String empresa, String fechaInicio, String fechaFin, Boolean esTrabajoActual, String descripcion) {
        this.logo = logo;
        this.cargo = cargo;
        this.empresa = empresa;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.esTrabajoActual = esTrabajoActual;
        this.descripcion = descripcion;
    }
}
