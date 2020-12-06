package com.app.edulearn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "contacto")
public class Contacto {
    
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long contactoID;

    @Column(name = "nombre", nullable = false)
    private String contNombre;

    @Column(name = "email")
    private String contEmail;

    @Column(name = "asunto")
    private String contAsunto;

    @Column(name = "mensaje")
    private String contMensaje;


    public Long getContactoID() {
        return contactoID;
    }

    public void setContactoID(Long contactoID) {
        this.contactoID = contactoID;
    }

    public String getContNombre() {
        return contNombre;
    }

    public void setContNombre(String contNombre) {
        this.contNombre = contNombre;
    }

    public String getContEmail() {
        return contEmail;
    }

    public void setContEmail(String contEmail) {
        this.contEmail = contEmail;
    }

    public String getContAsunto() {
        return contAsunto;
    }

    public void setContAsunto(String contAsunto) {
        this.contAsunto = contAsunto;
    }

    public String getContMensaje() {
        return contMensaje;
    }

    public void setContMensaje(String contMensaje) {
        this.contMensaje = contMensaje;
    }

}
