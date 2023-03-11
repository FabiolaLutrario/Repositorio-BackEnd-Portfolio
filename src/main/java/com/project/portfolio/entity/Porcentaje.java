package com.project.portfolio.entity;

import javax.persistence.*;

@Entity
@Table(name= "porcentaje")
public class Porcentaje {

    @Id
    private Integer id;

    public Porcentaje() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
