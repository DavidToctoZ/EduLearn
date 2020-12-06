package com.app.edulearn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "curso")
public class Curso {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long cursoId;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "imagen", nullable = false)
    private String imagen;

    @Column(name = "area", nullable = false)
    private String area;

    @Transient
    private Float porcentaje;

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Float porcentaje) {
        this.porcentaje = porcentaje;
    }

   

   

    
}
