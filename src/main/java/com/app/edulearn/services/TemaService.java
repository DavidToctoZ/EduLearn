package com.app.edulearn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import com.app.edulearn.model.Curso;
import com.app.edulearn.model.Grado;
import com.app.edulearn.model.GradoCurso;
import com.app.edulearn.model.Tema;
import com.app.edulearn.repository.CursoRepo;
import com.app.edulearn.repository.GradoCursoRepo;
import com.app.edulearn.repository.GradoRepo;
import com.app.edulearn.repository.TemaRepo;

@Component
public class TemaService {
    @Autowired
    CursoRepo cursoRepo;
    @Autowired
    GradoCursoRepo gradoCursoRepo;
    @Autowired
    TemaRepo temaRepo;
    @Autowired
    GradoRepo gradoRepo;

    public List<Tema> encontrarTemas(String buscarGrado, Long buscarCurso)
    {
        Grado g = gradoRepo.findByName(buscarGrado);
        Curso c = cursoRepo.findByCursoId(buscarCurso);
        GradoCurso gc = gradoCursoRepo.findByCursoAndGrado(c, g);
        
        List<Tema> temas = temaRepo.findByGradoCurso(gc);
    
        return temas;
    }
}
