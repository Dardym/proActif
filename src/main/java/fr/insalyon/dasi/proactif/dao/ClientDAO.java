/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.modele.Client;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author vrigolle
 */
public class ClientDAO {
    public static Client create(Client c){
        JpaUtil.obtenirEntityManager().persist(c);
        return c;
    }
    
    public static Client update(Client c){
        JpaUtil.obtenirEntityManager().merge(c);
        return c;
    }
    
    public static Client findClientById(Long id){
        return JpaUtil.obtenirEntityManager().find(Client.class, id);
    }
    
    public static void remove(Client c) {
        JpaUtil.obtenirEntityManager().remove(c);
    }
    
    public static Client findClientByAdresseMail(String adresseMail) throws NoResultException {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query q = em.createQuery("select c from Client c where c.adresseMail = :ad");
        return (Client) q.setParameter("ad", adresseMail).getSingleResult();
    }
}
