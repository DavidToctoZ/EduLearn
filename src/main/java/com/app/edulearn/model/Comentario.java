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
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long comentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser usuario;

    @Column(name = "comentario", nullable = false)
    private String coment;

    public Long getComentId() {
        return comentId;
    }

    public void setComentId(Long comentId) {
        this.comentId = comentId;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public AppUser getUsuario() {
        return usuario;
    }

    public void setUsuario(AppUser usuario) {
        this.usuario = usuario;
    }

    

}
