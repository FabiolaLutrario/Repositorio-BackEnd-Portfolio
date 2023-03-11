package com.project.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaTieneHabilidadDTO {

    private Integer id;

    private String habilidad;

    @JsonAlias("porcentaje_id")
    private Integer porcentajeId;

    @JsonAlias("usuario_id")
    private Integer usuarioId;

    public PersonaTieneHabilidadDTO() {
    }

    public PersonaTieneHabilidadDTO(String habilidad, Integer porcentajeId) {
        this.habilidad = habilidad;
        this.porcentajeId = porcentajeId;
    }

    public PersonaTieneHabilidadDTO(String habilidad, Integer porcentajeId, Integer usuarioId) {
        this.habilidad = habilidad;
        this.porcentajeId = porcentajeId;
        this.usuarioId = usuarioId;
    }
}
