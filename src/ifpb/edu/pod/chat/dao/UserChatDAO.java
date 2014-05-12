/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ifpb.edu.pod.chat.dao;

import ifpb.edu.pod.chat.model.UserChat;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author magdiel-bruno
 */
public class UserChatDAO {
    
    
    public void persist(UserChat user) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Chat-P2PPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public UserChat findUser(String log, String pass){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Chat-P2PPU");
        EntityManager em = emf.createEntityManager();
        UserChat user = null;
        em.getTransaction().begin();
        try {
            Query query = em.createQuery("select u from UserChat u where u.login = :login and u.password = :password");
            query.setParameter("login", log);
            query.setParameter("password", pass);
            user = (UserChat) query.getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return user;
    }
}
