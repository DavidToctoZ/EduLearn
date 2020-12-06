package com.app.edulearn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name= "seguimiento_tema")
public class SeguimientoTema {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long seguimientoTemaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tema_id")
    private Tema tema;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    
    @Column(name = "completado")
    private boolean completado;

    public Long getSeguimientoTemaId() {
        return seguimientoTemaId;
    }

    public void setSeguimientoTemaId(Long seguimientoTemaId) {
        this.seguimientoTemaId = seguimientoTemaId;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }




}
