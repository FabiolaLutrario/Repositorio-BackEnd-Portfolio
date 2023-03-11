package com.project.portfolio.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.portfolio.security.entity.Usuario;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name= "educacion")
public class Educacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String logo;

    @Column(nullable=false)
    private String titulo;

    @Column(nullable=false)
    private String centroEducativo;

    @Column(nullable=false)
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @Column(nullable=false)
    private Boolean seEncuentraCursando;

    private String descripcion;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

    public Educacion() {
    }

    public Educacion(String logo, String titulo, String centroEducativo, LocalDate fechaInicio,
                     LocalDate fechaFin, Boolean seEncuentraCursando, String descripcion, Usuario usuario) {
        this.logo = logo;
        this.titulo = titulo;
        this.centroEducativo = centroEducativo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.seEncuentraCursando = seEncuentraCursando;
        this.descripcion = descripcion;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCentroEducativo() {
        return centroEducativo;
    }

    public void setCentroEducativo(String centroEducativo) {
        this.centroEducativo = centroEducativo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean getSeEncuentraCursando() {
        return seEncuentraCursando;
    }

    public void setSeEncuentraCursando(Boolean seEncuentraCursando) {
        this.seEncuentraCursando = seEncuentraCursando;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @JsonBackReference
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
