package com.app.edulearn.DAO;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.app.edulearn.model.AppUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UserDAO {
    @Autowired
    private EntityManager entityManager;

    public AppUser findEmailAccount(String email){
        try{
            
            String sql = "Select e from " + AppUser.class.getName() + " e " //
                    + " Where e.email = :email";
            Query query = entityManager.createQuery(sql, AppUser.class);
            query.setParameter("email", email);

            

            return (AppUser)query.getSingleResult();

        }catch(NoResultException e){
            return null;
        }

    }

}
