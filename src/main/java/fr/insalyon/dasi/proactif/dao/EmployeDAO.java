/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.modele.DemandeIntervention;
import fr.insalyon.dasi.proactif.modele.Employe;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author vrigolle
 */
public class EmployeDAO {
    public static Employe create(Employe e){
        JpaUtil.obtenirEntityManager().persist(e);
        return e;
    }
    
    public static Employe update(Employe e){
        JpaUtil.obtenirEntityManager().merge(e);
        return e;
    }
    
    public static Employe findEmployeById(Long id){
        return JpaUtil.obtenirEntityManager().find(Employe.class, id);
    }
    
    public static void remove(Employe e) {
        JpaUtil.obtenirEntityManager().remove(e);
    }
    
    public static Employe findEmployeByAdresseMail(String adresseMail) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query q = em.createQuery("select e from Employe e where e.adresseMail = :ad");
        return (Employe) q.setParameter("ad", adresseMail).getSingleResult();
    }
    
    public static List<Employe> findEmployesByDemande(DemandeIntervention demande) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Employe result = null;
        double distance = Double.MAX_VALUE;
        Calendar cal = Calendar.getInstance();
        cal.setTime(demande.getHorodate());
        cal.set(Calendar.YEAR, 1970);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date d = cal.getTime();
        Query q = em.createQuery("select e from Employe e where e.disponible = TRUE AND e.heureDebut <= :heure AND e.heureFin > :heure");
        return (List<Employe>) q.setParameter("heure", d).getResultList();
    }
}
