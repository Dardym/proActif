/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.modele.Client;
import fr.insalyon.dasi.proactif.modele.DemandeIntervention;
import fr.insalyon.dasi.proactif.modele.Statut;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author vrigolle
 */
public class DemandeInterventionDAO {
    public static DemandeIntervention create(DemandeIntervention d){
        JpaUtil.obtenirEntityManager().persist(d);
        return d;
    }
    
    public static DemandeIntervention update(DemandeIntervention d){
        JpaUtil.obtenirEntityManager().merge(d);
        return d;
    }
    
    public static DemandeIntervention findDemandeInterventionById(Long id){
        return JpaUtil.obtenirEntityManager().find(DemandeIntervention.class, id);
    }
    
    public static List<DemandeIntervention> findDemandeInterventionByDate(Date d){
        EntityManager em = JpaUtil.obtenirEntityManager();
        SimpleDateFormat sfh = new SimpleDateFormat("HH:mm:ss");
        Date debut = new Date();
        try {
            debut = sfh.parse("00:00:00");
        } catch (ParseException ex) {
            Logger.getLogger(DemandeInterventionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        debut.setDate(d.getDate());
        debut.setMonth(d.getMonth());
        debut.setYear(d.getYear());
        Date fin = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(debut);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        fin = cal.getTime();
        System.out.println("Debut : " + debut + "\nFin : " + fin);
        Query q = em.createQuery("select d from DemandeIntervention d where d.horodate < :fin and d.horodate >= :debut");
        q.setParameter("fin", fin);
        q.setParameter("debut", debut);
        return (List<DemandeIntervention>) q.getResultList();
    }
    
    public static List<DemandeIntervention> findDemandeEnCoursByClient(Client c) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query q = em.createQuery("select d from DemandeIntervention d where d.client = :c and d.statut = :s");
        q.setParameter("c", c);
        q.setParameter("s", Statut.EN_COURS);
        return (List<DemandeIntervention>) q.getResultList();
    }
    
    public static void remove(DemandeIntervention d) {
        DemandeIntervention toBeRemoved = JpaUtil.obtenirEntityManager().merge(d);
        JpaUtil.obtenirEntityManager().remove(toBeRemoved);
    }
}

