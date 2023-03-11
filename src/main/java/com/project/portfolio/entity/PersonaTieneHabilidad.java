package com.project.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.portfolio.security.entity.Usuario;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name= "persona_tiene_habilidad")
public class PersonaTieneHabilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String habilidad;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="porcentaje_id")
    private Porcentaje porcentaje;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

    public PersonaTieneHabilidad() {
    }

    public PersonaTieneHabilidad(String habilidad, Porcentaje porcentaje, Usuario usuario) {
        this.habilidad = habilidad;
        this.porcentaje = porcentaje;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHabilidad() {
        return habilidad;
    }

    public void setHabilidad(String habilidad) {
        this.habilidad = habilidad;
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
