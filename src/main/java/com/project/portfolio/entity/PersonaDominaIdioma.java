package com.project.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.portfolio.security.entity.Usuario;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name= "persona_domina_idioma")
public class PersonaDominaIdioma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="idioma_id")
    private Idioma idioma;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="porcentaje_id")
    private Porcentaje porcentaje;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

    public PersonaDominaIdioma() {
    }

    public PersonaDominaIdioma(Idioma idioma, Porcentaje porcentaje, Usuario usuario) {
        this.idioma = idioma;
        this.porcentaje = porcentaje;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Porcentaje getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Porcentaje porcentaje) {
        this.porcentaje = porcentaje;
    }

    @JsonBackReference
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
