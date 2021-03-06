package com.app.edulearn.config;

import javax.sql.DataSource;

import com.app.edulearn.services.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private DataSource dataSource;

     @Bean
     public BCryptPasswordEncoder passwordEncoder(){
         BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
         return bCryptPasswordEncoder;
     }

     @Autowired
     public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
         auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
     }

     @Override
     protected void configure(HttpSecurity http) throws Exception{
         http.csrf().disable();

         //Paginas que no requieren login
         http.authorizeRequests().antMatchers("/", "/login", "/logout", "/registro").permitAll();

         //Paginas que requieren login como USER o ADMIN
         //Si no estan logeados se redirigira a login

         //ACTIVAR
        http.authorizeRequests().antMatchers("/grados").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')");
          //ACTIVAR
        //Solo para ADMIN
        http.authorizeRequests().antMatchers("/prueba").access("hasRole('ROLE_ADMIN')");

        //Cuando un usuario trata de ingresar a una pagina no autorizada
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
        
        //Configuracion para el login
        http.authorizeRequests().and().formLogin()//
                //URL de la pagina login

                .loginProcessingUrl("/j_spring_security_check")
                .loginPage("/")
                .defaultSuccessUrl("/default")
                .failureUrl("/?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
                //Configuracion de la pagina de cerrar sesion
                .and().logout().logoutSuccessUrl("/");

        //Configurar Recuerdame
        http.authorizeRequests().and()
            .rememberMe().tokenRepository(this.persistentTokenRepository())
            .tokenValiditySeconds(1*24*60*60); //24h
     }

     @Bean
     public PersistentTokenRepository persistentTokenRepository()
     {
         JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
         db.setDataSource(dataSource);
         return db;
     }



}
