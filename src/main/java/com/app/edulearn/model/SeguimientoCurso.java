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
@Table(name= "seguimiento_curso")
public class SeguimientoCurso {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long seguimientoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private GradoCurso gradoCurso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @Column(name = "total_temas")
    private int totalTemas;

    @Column(name = "total_temas_completados")
    private int totalTemasCompletado;

    public Long getSeguimientoId() {
        return seguimientoId;
    }

    public void setSeguimientoId(Long seguimientoId) {
        this.seguimientoId = seguimientoId;
    }



    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

   
   
    public GradoCurso getGradoCurso() {
        return gradoCurso;
    }

    public void setGradoCurso(GradoCurso gradoCurso) {
        this.gradoCurso = gradoCurso;
    }

    public Integer getTotalTemas() {
        return totalTemas;
    }

    public void setTotalTemas(Integer totalTemas) {
        this.totalTemas = totalTemas;
    }

    public Integer getTotalTemasCompletado() {
        return totalTemasCompletado;
    }

    public void setTotalTemasCompletado(Integer totalTemasCompletado) {
        this.totalTemasCompletado = totalTemasCompletado;
    }

}
