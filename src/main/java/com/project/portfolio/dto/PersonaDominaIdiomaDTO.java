package com.project.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaDominaIdiomaDTO {

    private Integer id;

    private String idioma;

    @JsonAlias("idioma_id")
    private Integer idiomaId;

    @JsonAlias("porcentaje_id")
    private Integer porcentajeId;

    @JsonAlias("usuario_id")
    private Integer usuarioId;

    public PersonaDominaIdiomaDTO() {
    }

    public PersonaDominaIdiomaDTO(Integer idiomaId, String idioma, Integer porcentajeId) {
        this.idiomaId = idiomaId;
        this.idioma=idioma;
        this.porcentajeId = porcentajeId;
    }
}
