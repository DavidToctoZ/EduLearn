package com.app.edulearn.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.app.edulearn.model.UserRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class AppRoleDAO {
    @Autowired
    private EntityManager entityManager;

    public List<String> getRoleNames(Long userId){
        String sql = "Select ur.appRole.name from " + UserRole.class.getName() + " ur "
        + " where ur.appUser.userId = :userId ";
        Query query = this.entityManager.createQuery(sql, String.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
