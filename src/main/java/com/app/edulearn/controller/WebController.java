package com.app.edulearn.controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.app.edulearn.model.AppUser;
import com.app.edulearn.model.Contacto;
import com.app.edulearn.model.Contenido;
import com.app.edulearn.model.Curso;
import com.app.edulearn.model.Grado;
import com.app.edulearn.model.GradoCurso;
import com.app.edulearn.model.Icono;
import com.app.edulearn.model.Matricula;
import com.app.edulearn.model.SeguimientoTema;
import com.app.edulearn.model.Tema;
import com.app.edulearn.repository.ContenidoRepo;
import com.app.edulearn.repository.CursoRepo;
import com.app.edulearn.repository.GradoCursoRepo;
import com.app.edulearn.repository.GradoRepo;
import com.app.edulearn.repository.IconoRepo;
import com.app.edulearn.repository.TemaRepo;
import com.app.edulearn.repository.UserRepo;
import com.app.edulearn.repository.UserRoleRepo;
import com.app.edulearn.repository.contactoRepo;
import com.app.edulearn.repository.MatriculaRepo;
import com.app.edulearn.repository.SeguimientoTemaRepo;
import com.app.edulearn.services.CursoService;
import com.app.edulearn.services.TemaService;
import com.app.edulearn.services.UserRoleService;
import com.app.edulearn.services.UserService;
import com.app.edulearn.utils.EncryptedPasswordUtils;
import com.app.edulearn.utils.WebUtils;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class WebController {
    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;
    
    @Autowired
    GradoRepo gradoRepo;

    @Autowired
    UserRoleRepo userRoleRepo;

    @Autowired
    CursoRepo cursoRepo;

    @Autowired
    IconoRepo iconoRepo;

    @Autowired
    CursoService cursoService;
    
    @Autowired
    GradoCursoRepo gradoCursoRepo;

    @Autowired
    TemaRepo temaRepo;

    @Autowired
    contactoRepo contRepo;

    @Autowired
    TemaService temaService;

    @Autowired
    ContenidoRepo contenidoRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ServletContext servletContext;

    @Autowired
    MatriculaRepo matriculaRepo;

    @Autowired
    SeguimientoTemaRepo seguimientoTemaRepo;
    
    String nombreUsuarioActivo;
    String nombre;
    String apellido;
    String email;

    boolean menuCurso;
    boolean eliminado;
    //ACTIVAR 

    Long tmpGradoId;
    Long tmpCursoId;
    Long tmpTemaId;
    
    //---------------------------------------------------------------------------------------------------------
    //INICIO DE SESIÓN

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login(Model model) {
        if(eliminado == true){
            model.addAttribute("usuarioEliminado", eliminado);
            eliminado = false;
        }
        return "login";
     }
     

    

    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request, Model model) {     
        if (request.isUserInRole("ROLE_ADMIN")) {
            
            return "redirect:/prueba";
        }
        AppUser usuarioActivo = userRepo.findByEmail(request.getUserPrincipal().getName());
        nombreUsuarioActivo = usuarioActivo.getUserName() + " " + usuarioActivo.getFullname();

        nombre = usuarioActivo.getUserName();
        
        apellido = usuarioActivo.getFullname();
        email = request.getUserPrincipal().getName();
        
        List<Matricula> gradosMatriculados = matriculaRepo.findByAppUser(usuarioActivo);

        if(gradosMatriculados.size() == 0){
            return "redirect:/matricula";
        }
        
        return "redirect:/grados";
    }

    @RequestMapping("/matricula")
    public String pagMatricula(Model model){

        menuCurso = false;
        AppUser a = userRepo.findByEmail(email);
        List<Matricula> m = matriculaRepo.findByAppUser(a);
        model.addAttribute("grados", m);

        model.addAttribute("gradosT", gradoRepo.findAll());//Para el menu layout
        model.addAttribute("nombreUsuarioActivo", nombreUsuarioActivo);//Mostrar usuario Activo

        if(menuCurso== true ){
            model.addAttribute("menuCurso", menuCurso);
        }
        
        return "paginaMatricula";
    }

    
    @RequestMapping("/matriculaGrado")
    public String seleccionGrado(@RequestParam String buscarGrado, Model model){
        Grado g = gradoRepo.findByName(buscarGrado);
        
        AppUser a = userRepo.findByEmail(email);

        if(matriculaRepo.findByAppUserAndGrado(a, g).size() == 0)
        {
            Matricula s = new Matricula();
        

            s.setAppUser(a);
            s.setGrado(g);
            matriculaRepo.save(s);
            model.addAttribute("gradoMatriculado", g.getName());
    
    
            return "PaginaMatriculaExitosa";
        }

        return "PaginaMatriculaFallida";
    }


    //---------------------------------------------------------------------------------------------------------
    //INICIO DEL REGISTRO

    @RequestMapping(value= "/registro", method = RequestMethod.GET)
    public String registro(Model model){
        model.addAttribute("userForm", new AppUser());
        
        return "Registro";
    }

    @RequestMapping(value="/registro", method = RequestMethod.POST)
    public String registrarUsuario(@ModelAttribute AppUser user, ModelMap model) throws Exception {
        model.addAttribute("userForm", user);
        
        
        user.setEnabled(true);

        user.setEncryptedPassword(EncryptedPasswordUtils.encryptePassword(user.getEncryptedPassword()));
        
        boolean isCreated = userService.addUser(user);
        
        if(isCreated)
        {
            userRoleService.addUserRole(user); 
            return "redirect:/";  
        }
        else{
            model.addAttribute("userForm", new AppUser());
            model.addAttribute("error", "Error de creacion");
            return "Registro";
        }
        
    }

    //---------------------------------------------------------------------------------------------------------

    //INICIO DE ELIMINAR USUARIO

    @RequestMapping(value="/eliminarUsuario")
    public String eliminarUsuario(@RequestParam String buscarEmail, Model model){
      
        AppUser ap = userRepo.findByEmail(buscarEmail);
        userRoleRepo.deleteByAppUser(ap);
        userRepo.deleteByEmail(buscarEmail);
        eliminado = true;
        
        return "redirect:/";
    }

     //---------------------------------------------------------------------------------------------------------

    //INICIO DE GRADOS

    @RequestMapping(value = "/grados", method = RequestMethod.GET)
    public String listaGrados(Model model) {
        AppUser a = userRepo.findByEmail(email);

        List<Matricula> m = matriculaRepo.findByAppUser(a);

        if(m.size() == 0){
            model.addAttribute("noGrados", "No se encuentra matriculado en ningun curso");
            return "PaginaMatricula";
        }

        funcionLayout(model, menuCurso);

        return "PaginaGrados";

     }

    //Funcion q agregara parametros especifios para el layout
    public void funcionLayout(Model model, boolean menuCurso){
        AppUser a = userRepo.findByEmail(email);
        List<Matricula> m = matriculaRepo.findByAppUser(a);
    

        boolean eGrado = true;
        if(m.size()!=0){
            model.addAttribute("existeGrado", eGrado);
            model.addAttribute("grados", m);//Para el menu layout
           
        }
        else{
            eGrado = false;
            model.addAttribute("existeCurso", eGrado);
        }
       
        
        
        model.addAttribute("nombreUsuarioActivo", nombreUsuarioActivo);//Mostrar usuario Activo
        if(menuCurso== true ){
            model.addAttribute("menuCurso", menuCurso);
        }
        
    }

     String seleccion;
    //PAGINA DE ADMINISTRADOR - CREAR CURSO
    @RequestMapping(value = "/prueba", method = RequestMethod.GET)
    public String prueba(Model model) {

        model.addAttribute("curso", new Curso());
        model.addAttribute("iconos", iconoRepo.findAll());
        model.addAttribute("seleccion", seleccion);
        return "PaginaAdmin";
     }
    
    @RequestMapping(value = "/prueba", method = RequestMethod.POST)
    public String crearCurso(@ModelAttribute Curso curso, @ModelAttribute Icono icono, ModelMap model) throws Exception {

        model.addAttribute("curso", curso);
        model.addAttribute("iconos", icono);
        model.addAttribute("iconos", iconoRepo.findAll());
        model.addAttribute("seleccion", seleccion);
        cursoRepo.save(curso);
        model.addAttribute("mensaje", "Curso creado exitosamente!");
        model.addAttribute("curso", new Curso());

        return "PaginaAdmin";
    }

    //---------------------------------------------------------------------------------------------------------

    //INICIO DE ASIGNAR CURSO A GRADO - ADMIN

    @RequestMapping(value = "/gradoCurso", method = RequestMethod.GET)
    public String inicioAsig(Model model) {

        model.addAttribute("curso", cursoRepo.findAll());
        model.addAttribute("grado", gradoRepo.findAll());
        model.addAttribute("gc", new GradoCurso());

        return "AdminGradoCurso";
    }
    
    @RequestMapping(value = "/gradoCurso", method = RequestMethod.POST)
    public String finAsig(@ModelAttribute Grado grado, @ModelAttribute Curso curso, @ModelAttribute GradoCurso gc, ModelMap model) throws Exception {

        model.addAttribute("gc", gc);
        model.addAttribute("curso", curso);
        model.addAttribute("grado", grado);

        GradoCurso gc1 = gradoCursoRepo.findByCursoAndGrado(curso, grado);

        if(gc1 == null) {
            gradoCursoRepo.save(gc);
            model.addAttribute("curso", cursoRepo.findAll());
            model.addAttribute("grado", gradoRepo.findAll());
            model.addAttribute("gc", new GradoCurso());
            model.addAttribute("mensaje", "Grado y curso asignado exitosamente!");
            return "AdminGradoCurso";
        } else {
            model.addAttribute("error", "Ya existe este curso para ese grado");
            model.addAttribute("curso", cursoRepo.findAll());
            model.addAttribute("grado", gradoRepo.findAll());
            model.addAttribute("gc", new GradoCurso());
            return "AdminGradoCurso";
        }
        
    }
    
    //---------------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------------
    
    @RequestMapping(value = "/cursos")
    public String listaCursos(@RequestParam String buscarGrado, Model model) {
        boolean menuCurso = true;
        funcionLayout(model, menuCurso);

        List<Curso> cursos = cursoService.encontrarCursosHabilitados(buscarGrado);
        List<Curso> cursosProx = cursoService.encontrarCursosDeshabilitados(buscarGrado);
        String titulo = "Cursos de " + buscarGrado;
        String gradoSub = "> Cursos de " +buscarGrado +  " disponibles:";
        String nomGrado = buscarGrado;
        List<SeguimientoTema> sg = seguimientoTemaRepo.findByAppUser(userRepo.findByEmail(email));
        
        


        String tituloMenu = "Curso de " + buscarGrado;

        model.addAttribute("tituloMenu", tituloMenu);
        
        model.addAttribute("titulo", titulo);
        model.addAttribute("gradoSub", gradoSub);
        model.addAttribute("cursosProximos", cursosProx);
        model.addAttribute("cursos", cursos);
        model.addAttribute("nombreGrado", nomGrado);
        


        if(cursos == null && cursosProx == null){
            return "cursos/CursosGradoVacio";
        }
 
        if(cursos.isEmpty() && !cursosProx.isEmpty()){
            
           
            return "cursos/CursosGradoParciaProx";
        }
        if(!cursos.isEmpty() && cursosProx.isEmpty()){
            for(Curso tmp : cursos){
                
                float cantTemasTotal = 0;
                float cantTemasComp = 0;
                for(SeguimientoTema tmpSg: sg){
                    cantTemasTotal = cantTemasComp + 1;
                    if(tmpSg.getTema().getGradoCurso().getCurso().getCursoId() == tmp.getCursoId() )
                    {
                        cantTemasComp = cantTemasComp + 1;
                    }
                }
                float cal = (cantTemasComp/cantTemasTotal) * 100;
                
                DecimalFormat formato1 = new DecimalFormat("#.00");
                System.out.println(cantTemasTotal); 
                System.out.println(cantTemasComp);
                tmp.setPorcentaje(cal);
            }

            

            return "cursos/CursosGradoParcialDisp";
        }
        
        for(Curso tmp : cursos){
                
            float cantTemasTotal = 0;
            float cantTemasComp = 0;
            for(SeguimientoTema tmpSg: sg){
                cantTemasTotal = cantTemasComp + 1;
                if(tmpSg.getTema().getGradoCurso().getCurso().getCursoId() == tmp.getCursoId() )
                {
                    cantTemasComp = cantTemasComp + 1;
                }
            }
            float cal = (cantTemasComp/cantTemasTotal) * 100;
          
            System.out.println(cantTemasTotal); 
            System.out.println(cantTemasComp);
            tmp.setPorcentaje(cal);
        }
    
        return "cursos/CursosGradoTodo";
      }
    
    @RequestMapping(value = "/tema")
    public String pagTema(@RequestParam String buscarGrado, @RequestParam Long buscarCurso, Model model){
        menuCurso = true;
        funcionLayout(model, menuCurso);

        Grado g = gradoRepo.findByName(buscarGrado);
        Curso c = cursoRepo.findByCursoId(buscarCurso);
        List<Tema> temas = temaService.encontrarTemas(buscarGrado, buscarCurso);
        List<SeguimientoTema> sg = seguimientoTemaRepo.findByAppUser(userRepo.findByEmail(email));

        String titulo = "Curso de "+g.getName() + ": " + c.getName();

        for(Tema temp : temas){
            for(SeguimientoTema tempSg : sg){
                
                if(temp.getTemaId() == tempSg.getTema().getTemaId()){
                        temp.setEstado("Completado");
                }
                else{
                    if(temp.getEstado() != "Completado"){
                        temp.setEstado("No Completado");
                    }
                    
                }

            }
        }
       
        String tituloMenu = "Curso de " + g.getName();

        model.addAttribute("tituloMenu", tituloMenu);
        model.addAttribute("nombreGrado", g.getName());
       
        
        model.addAttribute("cursos", cursoService.encontrarCursosHabilitados(g.getName()));
        model.addAttribute("buscarGrado", buscarGrado);
        model.addAttribute("titulo", titulo);
        model.addAttribute("nombreCurso", c.getName());

        model.addAttribute("listaTemas", temas);

      
        

        return "PaginaTema";
        
    }

    @RequestMapping(value = "/guardarFin")
    public String guardarTema(@RequestParam Long buscarTema, Model model){
        Tema t = temaRepo.findByTemaId(buscarTema);
        AppUser a = userRepo.findByEmail(email);
        SeguimientoTema st = new SeguimientoTema();
        boolean tComp = true;
        st.setAppUser(a);
        st.setTema(t);
        st.setCompletado(tComp);
        if(seguimientoTemaRepo.findByTemaAndAppUser(t, a).size() == 0){
            seguimientoTemaRepo.save(st);
        }
        
        model.addAttribute("cursoId", t.getGradoCurso().getCurso().getCursoId());
        model.addAttribute("nombreGrado", t.getGradoCurso().getGrado().getName());


        return "PaginaTemaFinalizado";
    }


    @RequestMapping(value = "/contenido")
    public String pagContenido(@RequestParam Long buscarTema, @RequestParam String buscarGrado,Model model){
        menuCurso = true;
        funcionLayout(model, menuCurso);

        Grado g = gradoRepo.findByName(buscarGrado);
        String tituloMenu = "Curso de " + g.getName();

        model.addAttribute("tituloMenu", tituloMenu);
        
        
        model.addAttribute("cursos", cursoService.encontrarCursosHabilitados(buscarGrado));
        model.addAttribute("nombreGrado", g.getName());
        Tema t = temaRepo.findByTemaId(buscarTema);
        
         
        List<Contenido> c = contenidoRepo.findByTemaOrderByOrdenMostrar(t);
        
        model.addAttribute("listaContenido", c);
        model.addAttribute("temaNombre", t.getNombre());
        model.addAttribute("temaId", t.getTemaId());
        

        return "PaginaTemasCurso";
        
    }
    





    //---------------------------------------------------------------------------------------------------------
    //INICIO DE CONTACTO

    @RequestMapping(value = "/contacto", method = RequestMethod.GET)
      public String contacto(Model model) {
        menuCurso = false;
        funcionLayout(model, menuCurso);
        model.addAttribute("contacto", new Contacto());
        return "contacto1";
    }

    @RequestMapping(value = "/contacto", method = RequestMethod.POST)
    public String enviarContacto(@ModelAttribute Contacto contacto, ModelMap model) throws Exception { 

        model.addAttribute("contacto", contacto);
        model.addAttribute("grados", gradoRepo.findAll());

        contRepo.save(contacto);
        model.addAttribute("mensaje", "Gracias por contactarnos!");
        model.addAttribute("contacto", new Contacto());

        return "contacto1";

    }

    //---------------------------------------------------------------------------------------------------------

    //INICIO DE PAGINA PERFIL

    @RequestMapping(value = "/paginaperfil", method = RequestMethod.GET)
    public String paginaperfil(Model model) {
        menuCurso = false;
        funcionLayout(model, menuCurso);
        

        model.addAttribute("nombre", nombre);
        model.addAttribute("apellido", apellido);
        model.addAttribute("email", email);

        return "paginaperfil";
    } 
     
    //---------------------------------------------------------------------------------------------------------

    //INICIO DE PAGINA DE ERROR
   
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {
 
        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();
 
            String userInfo = WebUtils.toString(loginedUser);
 
            model.addAttribute("userInfo", userInfo);
 
            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);
 
        }
 
        return "403Page";
    }

    
    @RequestMapping(value = "/seleccionarGrado")
    public String seleccionarGrado(@RequestParam String sel, Model model){
        seleccion = sel;
        model.addAttribute("seleccion", seleccion);
        model.addAttribute("gradoSel", new Grado());
        model.addAttribute("grados", gradoRepo.findAll());
        
        return "AdminSeleccionarGrado";
    }
    
    @RequestMapping(value = "/seleccionarCurso")
    public String seleccionarCurso(@RequestParam Long gradoSel, Model model){
        boolean hayCurso;
        tmpGradoId = gradoSel;
        Grado g = gradoRepo.findByGradoId(gradoSel);

        List<Curso> cursos = cursoService.encontrarCursosHabilitados(g.getName());
        
        if(cursos==null){
            hayCurso = false;
            System.out.println("No hay cursos");
        }
        else if(cursos.size() == 0){
            hayCurso = false;
        }
        else{
            hayCurso = true; 
            
            System.out.println("Hay cursos");
        }
        model.addAttribute("nombreGrado", g.getName());
        model.addAttribute("cursos", cursos);
        model.addAttribute("hayCurso", hayCurso);
        model.addAttribute("seleccion", seleccion);//Opvcion 1 = grado, opcion 2 = curso
        
        //model.addAttribute("gradoSel", )
        
        return "AdminSeleccionarCurso";
    }
    GradoCurso tmpGradoCurso;

    @RequestMapping(value = "/redireccionCurso")
    public String redireccionCurso(@RequestParam Long cursoSel){
        
        
        tmpCursoId = cursoSel;
        
        Grado g = gradoRepo.findByGradoId(tmpGradoId);
        Curso c = cursoRepo.findByCursoId(tmpCursoId);
       
        GradoCurso gc = gradoCursoRepo.findByCursoAndGrado(c,g);
        
        tmpGradoCurso = gc;
        if(seleccion.equals("2")){
            
            return "redirect:/seleccionarTema";
        }
        return "redirect:/crearTema";
    }
    
    @RequestMapping(value = "/seleccionarTema")
    public String seleccionarTema(Model model){
        boolean hayTema;
        
        
        List<Tema> temas = temaRepo.findByGradoCurso(tmpGradoCurso);
        if(temas==null){
            hayTema = false;
            
        }
        else{
            hayTema = true; 
        }
        model.addAttribute("nombreGrado", gradoRepo.findByGradoId(tmpGradoId).getName());
        model.addAttribute("nombreCurso", cursoRepo.findByCursoId(tmpCursoId).getName());

        model.addAttribute("temas", temas);
        model.addAttribute("hayTema", hayTema);
        model.addAttribute("seleccion", seleccion);//Opvcion 1 = grado, opcion 2 = curso

        return "AdminSeleccionarTema";
    }

   
    //Crear tema
    @RequestMapping(value = "/crearTema", method = RequestMethod.GET)
    public String obtenertema(Model model) {
        model.addAttribute("nombreGrado", gradoRepo.findByGradoId(tmpGradoId).getName());
        model.addAttribute("nombreCurso", cursoRepo.findByCursoId(tmpCursoId).getName());

        model.addAttribute("tema", new Tema());
        
        return "AdminCrearTema";
    }

    @RequestMapping(value = "/crearTema", method = RequestMethod.POST)
    public String enviarTema(@ModelAttribute Tema tema, ModelMap model, 
    @RequestParam("file1") MultipartFile imagen) throws Exception {

        model.addAttribute("tema", tema);
        tema.setGradoCurso(tmpGradoCurso);

        if(!imagen.isEmpty()) {
            Path directorioImagenes = Paths.get("src//main//resources//static/images");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

            try {
                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);

                tema.setImagen(imagen.getOriginalFilename());

            } catch (Exception e) {
                e.printStackTrace();
            }   
        } else {
            model.addAttribute("error", "Debe seleccionar una imagen");
            model.addAttribute("tema", new Tema());
        }
        
        temaRepo.save(tema);
        model.addAttribute("nombreGrado", gradoRepo.findByGradoId(tmpGradoId).getName());
        model.addAttribute("nombreCurso", cursoRepo.findByCursoId(tmpCursoId).getName());
        
        model.addAttribute("mensaje", "Tema creado exitosamente!");
        model.addAttribute("tema", new Tema());

        return "AdminCrearTema";
    }

    
    @RequestMapping(value="/seleccionarContenido")
    public String SeleccionarContenido(@RequestParam Long temaSel, Model model) {
        tmpTemaId = temaSel;
        model.addAttribute("nombreGrado", gradoRepo.findByGradoId(tmpGradoId).getName());
        model.addAttribute("nombreCurso", cursoRepo.findByCursoId(tmpCursoId).getName());
        model.addAttribute("nombreTema", temaRepo.findByTemaId(tmpTemaId).getNombre());
        model.addAttribute("contenido", new Contenido());

        return "AdminCrearContenido";
    }


    //INICIO DE CREAR CONTENIDO  - ADMIN
    @RequestMapping(value="/crearContenido", method = RequestMethod.GET)
    public String obtenerContenido(Model model) {
        model.addAttribute("nombreGrado", gradoRepo.findByGradoId(tmpGradoId).getName());
        model.addAttribute("nombreCurso", cursoRepo.findByCursoId(tmpCursoId).getName());
        model.addAttribute("nombreTema", temaRepo.findByTemaId(tmpTemaId).getNombre());
        
        model.addAttribute("contenido", new Contenido());

        return "AdminCrearContenido";
    }

    @RequestMapping(value="/crearContenido", method = RequestMethod.POST)
    public String crearContenido(@ModelAttribute Contenido contenido,
    ModelMap model, @RequestParam("file") MultipartFile imagen) {

        model.addAttribute("nombreGrado", gradoRepo.findByGradoId(tmpGradoId).getName());
        model.addAttribute("nombreCurso", cursoRepo.findByCursoId(tmpCursoId).getName());
        model.addAttribute("nombreTema", temaRepo.findByTemaId(tmpTemaId).getNombre());

        Tema tema = temaRepo.findByTemaId(tmpTemaId);
        contenido.setTema(tema);

        if(!imagen.isEmpty()) {
            Path directorioImagenes = Paths.get("src//main//resources//static/images");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

            try {
                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);

                contenido.setImagen(imagen.getOriginalFilename());

            } catch (Exception e) {
                e.printStackTrace();
            }   
        } else {
            contenido.setImagen("null");
            contenidoRepo.save(contenido);
            model.addAttribute("mensaje", "Contenido guardado!");
            model.addAttribute("contenido", new Contenido());
        }

        contenidoRepo.save(contenido);
        model.addAttribute("mensaje", "Contenido guardado!");
        model.addAttribute("contenido", new Contenido());

        return "AdminCrearContenido";
    }

}
