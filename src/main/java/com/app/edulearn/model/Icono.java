package com.app.edulearn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "iconos")
public class Icono {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long iconoID;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    public Long getIconoID() {
        return iconoID;
    }

    public void setIconoID(Long iconoID) {
        this.iconoID = iconoID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
}

