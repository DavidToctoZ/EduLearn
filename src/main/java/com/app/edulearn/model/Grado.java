package com.app.edulearn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "grado")
public class Grado {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long GradoId;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "imagen", nullable = false)
    private String imagen;

    public Long getGradoId() {
        return GradoId;
    }

    public void setGradoId(Long gradoId) {
        GradoId = gradoId;
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

}
