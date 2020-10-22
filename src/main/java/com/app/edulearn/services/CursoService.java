package com.app.edulearn.services;

import java.util.ArrayList;
import java.util.List;

import com.app.edulearn.model.Curso;
import com.app.edulearn.model.Grado;
import com.app.edulearn.model.GradoCurso;
import com.app.edulearn.repository.GradoCursoRepo;
import com.app.edulearn.repository.GradoRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CursoService {
    @Autowired
    GradoRepo gradoRepo;

    @Autowired
    GradoCursoRepo gradoCursoRepo;

   
    public List<GradoCurso> encontrarListaDeGrados(String buscar)
    {
        Grado g = gradoRepo.findByName(buscar);     
        List<GradoCurso> gc = gradoCursoRepo.findByGrado(g);
        
        return gc;
        
    }

    public List<Curso> encontrarCursosHabilitados(String grado){

        List<GradoCurso> gc = encontrarListaDeGrados(grado);
        if(gc.isEmpty()){
            return null;
        }else{
            List<Curso> c = new ArrayList<>();
            for(GradoCurso temp : gc){
                if(temp.isHabilitado()){
                    System.out.println(temp.getCurso().getName());
                    c.add(temp.getCurso());
                }
            }
            return c;
        }
    }

    public List<Curso> encontrarCursosDeshabilitados(String grado){
        List<GradoCurso> gc = encontrarListaDeGrados(grado);
        if(gc.isEmpty()){
            return null;
        }else{
            List<Curso> c = new ArrayList<>();
            for(GradoCurso temp : gc){
                if(!temp.isHabilitado()){
                    c.add(temp.getCurso());
                }
            }
            return c;
        }
    }
}
