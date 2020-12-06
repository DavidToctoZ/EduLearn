package com.app.edulearn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tema")
public class Tema {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long temaId;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "imagen")
    private String imagen;

    @Transient
    private String estado;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grado_curso_id")
    private GradoCurso gradoCurso;

    public Long getTemaId() {
        return temaId;
    }

    public void setTemaId(Long temaId) {
        this.temaId = temaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public GradoCurso getGradoCurso() {
        return gradoCurso;
    }

    public void setGradoCurso(GradoCurso gradoCurso) {
        this.gradoCurso = gradoCurso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

  
}   
