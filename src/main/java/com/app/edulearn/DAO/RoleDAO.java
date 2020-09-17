package com.app.edulearn.DAO;
import com.app.edulearn.model.RoleUser;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class RoleDAO {
    @Autowired
    private EntityManager entityManager;

    public List<String> getRoleNames(final Long userId) {
        

        String sql = "Select ur.role.roleName from " + RoleUser.class.getName() +" ur "+ " Where ur.user.userId = :userId";
        
        Query query = this.entityManager.createQuery(sql, String.class);
        
        query.setParameter("userId", userId);
        
        return query.getResultList();
    }
}
