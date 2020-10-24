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
@Table(name = "contenido")
public class Contenido {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long contenidoId;

    @Column(name = "orden_mostrar")
    private int orden_mostrar;

    @Column(name = "subtitulo")
    private String subtitulo;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "texto")
    private String texto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tema_id")
    private Tema tema;
    
    public Long getContenidoId() {
        return contenidoId;
    }

    public void setContenidoId(Long contenidoId) {
        this.contenidoId = contenidoId;
    }

    public int getOrden_mostrar() {
        return orden_mostrar;
    }

    public void setOrden_mostrar(int orden_mostrar) {
        this.orden_mostrar = orden_mostrar;
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
