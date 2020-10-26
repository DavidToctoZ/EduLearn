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
@Table(name="dinamica")
public class Dinamica {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long dinamicaId;

    @Column(name = "orden_mostrar")
    private int ordenMostrar;

    @Column(name = "subtitulo")
    private String subtitulo;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "texto")
    private String texto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contenido")
    private Contenido contenido;    

    public Long getDinamicaId() {
        return dinamicaId;
    }

    public void setDinamicaId(Long dinamicaId) {
        this.dinamicaId = dinamicaId;
    }

    public int getOrden_mostrar() {
        return ordenMostrar;
    }

    public void setOrden_mostrar(int ordenMostrar) {
        this.ordenMostrar = ordenMostrar;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

}
