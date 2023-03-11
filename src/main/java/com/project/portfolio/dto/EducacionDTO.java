package com.project.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EducacionDTO {

    private Integer id;

    private String logo;

    @NotBlank
    private String titulo;

    @NotBlank
    @JsonAlias("centro_educativo")
    private String centroEducativo;

    @NotBlank
    @JsonAlias("fecha_inicio")
    private String fechaInicio;

    @JsonAlias("fecha_fin")
    private String fechaFin;

    @NotBlank
    @JsonAlias("se_enceuntra_cursando")
    private Boolean seEncuentraCursando;

    private String descripcion;

    @JsonAlias("usuario_id")
    private Integer usuarioId;

    public EducacionDTO() {
    }

    public EducacionDTO(String logo, String titulo, String centroEducativo, String fechaInicio, String fechaFin, Boolean seEncuentraCursando, String descripcion, Integer usuarioId) {
        this.logo = logo;
        this.titulo = titulo;
        this.centroEducativo = centroEducativo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.seEncuentraCursando = seEncuentraCursando;
        this.descripcion = descripcion;
        this.usuarioId = usuarioId;
    }

    public EducacionDTO(String logo, String titulo, String centroEducativo, String fechaInicio, String fechaFin, Boolean seEncuentraCursando, String descripcion) {
        this.logo = logo;
        this.titulo = titulo;
        this.centroEducativo = centroEducativo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.seEncuentraCursando = seEncuentraCursando;
        this.descripcion = descripcion;
    }
}
