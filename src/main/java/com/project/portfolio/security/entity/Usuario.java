package com.project.portfolio.security.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.portfolio.entity.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name= "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min=1, max=45, message = "Debe ingresar un mínimo de 1 caracter, y un máximo de 45")
    private String nombre;

    @NotNull
    @Size(min=1, max=45, message = "Debe ingresar un mínimo de 1 caracter, y un máximo de 45")
    private String apellido;

    @NotNull
    @Column(unique=true)
    private String email;

    @NotNull
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="usuario_rol", joinColumns=@JoinColumn(name="usuario_id"),
            inverseJoinColumns = @JoinColumn(name="rol_id"))
    private Set<Rol> roles = new HashSet<>();

    @NotNull
    @Size(min=1, max=50, message = "Debe ingresar un mínimo de 1 caracter, y un máximo de 50")
    private String titulo;

    @Column(name="sobre_mi")
    @Size(max=1000, message = "Debe ingresar un máximo de 1000 caracteres")
    private String sobreMi;

    private String foto;

    private String banner;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    //EL REMOVE elimina todas las experiencias asociadas a una persona en caso de que se elimine a la persona.
    private List<Experiencia> experiencias;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private List<Educacion> estudios;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private List<PersonaDominaIdioma> personaIdiomas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private List<PersonaTieneHabilidad> personaHabilidades;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private List<Proyecto> proyectos;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String email, String password, String titulo, String foto,
                   String banner) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.titulo = titulo;
        this.foto = foto;
        this.banner=banner;
    }

    public Usuario(String nombre, String apellido, String titulo, String sobreMi, String foto, String banner) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.titulo = titulo;
        this.sobreMi = sobreMi;
        this.foto = foto;
        this.banner=banner;
    }

    public Usuario(String nombre, String apellido, String email, String password, Set<Rol> roles,
                   String titulo, String sobreMi, String foto, String banner, List<Experiencia> experiencias,
                   List<Educacion> estudios, List<PersonaDominaIdioma> personaIdiomas,
                   List<PersonaTieneHabilidad> personaHabilidades, List<Proyecto> proyectos) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.titulo = titulo;
        this.sobreMi = sobreMi;
        this.foto = foto;
        this.banner=banner;
        this.experiencias = experiencias;
        this.estudios = estudios;
        this.personaIdiomas = personaIdiomas;
        this.personaHabilidades = personaHabilidades;
        this.proyectos=proyectos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSobreMi() {
        return sobreMi;
    }

    public void setSobreMi(String sobreMi) {
        this.sobreMi = sobreMi;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    @JsonManagedReference
    public List<Experiencia> getExperiencias() {
        return experiencias;
    }

    public void setExperiencias(List<Experiencia> experiencias) {
        this.experiencias = new ArrayList<>();
    }

    @JsonManagedReference
    public List<Educacion> getEstudios() {
        return estudios;
    }

    public void setEstudios(List<Educacion> estudios) {
        this.estudios = new ArrayList<>();
    }

    @JsonManagedReference
    public List<PersonaDominaIdioma> getPersonaIdiomas() {
        return personaIdiomas;
    }

    public void setPersonaIdiomas(List<PersonaDominaIdioma> personaIdiomas) {
        this.personaIdiomas = new ArrayList<>();
    }

    @JsonManagedReference
    public List<PersonaTieneHabilidad> getPersonaHabilidades() {
        return personaHabilidades;
    }

    public void setPersonaHabilidades(List<PersonaTieneHabilidad> personaHabilidades) {
        this.personaHabilidades = new ArrayList<>();
    }

    @JsonManagedReference
    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = new ArrayList<>();
    }
}
