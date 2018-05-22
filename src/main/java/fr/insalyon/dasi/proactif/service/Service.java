/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.service;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import fr.insalyon.dasi.proactif.dao.ClientDAO;
import fr.insalyon.dasi.proactif.dao.DemandeInterventionDAO;
import fr.insalyon.dasi.proactif.dao.EmployeDAO;
import fr.insalyon.dasi.proactif.dao.JpaUtil;
import fr.insalyon.dasi.proactif.ihm.Test;
import fr.insalyon.dasi.proactif.modele.Client;
import fr.insalyon.dasi.proactif.modele.DemandeAnimal;
import fr.insalyon.dasi.proactif.modele.DemandeIntervention;
import fr.insalyon.dasi.proactif.modele.DemandeLivraison;
import fr.insalyon.dasi.proactif.modele.Employe;
import fr.insalyon.dasi.proactif.modele.Statut;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;

/**
 *
 * @author vrigolle
 */
public class Service {
    
    final static GeoApiContext MON_CONTEXTE_GEOAPI =
        new GeoApiContext.Builder().apiKey("AIzaSyDcVVJjfmxsNdbdUYeg9MjQoJJ6THPuap4").build();
    
    public static void inscrireClient(String nom, String prenom, Date dateNaissance, String civilite, String adresse, String numeroTelephone, String adresseMail) {
        JpaUtil.creerEntityManager();
        LatLng coords = getLatLng(adresse);
        Client c = new Client(nom, prenom, dateNaissance, civilite, adresse, numeroTelephone, adresseMail, coords);
        try {
            JpaUtil.ouvrirTransaction();
            ClientDAO.create(c);
            JpaUtil.validerTransaction();
            envoyerMail(c, true);
        } catch (RollbackException e) {
            JpaUtil.annulerTransaction();
            envoyerMail(c, false);
        }
        JpaUtil.fermerEntityManager();
    }
    
    public static void creerListeEmployes() {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sfh = new SimpleDateFormat("HH:mm:ss");
        
        try {
            EmployeDAO.create(new Employe("Doe", "John", sf.parse("25/03/1986"), "M", 
                "8 Rue Arago, Villeurbanne", "0328178508", "rborrotimatiasdantas4171@free.fr",
                sfh.parse("08:00:00"), sfh.parse("16:00:00"), getLatLng("8 Rue Arago, Villeurbanne")));
            EmployeDAO.create(new Employe("OLMEADA MARAIS", "Nor", sf.parse("09/12/1983"), "Mme", 
                "5 Rue Léon Fabre, Villeurbanne", "0418932546", "nolmeadamarais1551@gmail.com",
                sfh.parse("10:00:00"), sfh.parse("18:00:00"), getLatLng("5 Rue Léon Fabre, Villeurbanne")));
            EmployeDAO.create(new Employe("RAYES GEMEZ", "Olena", sf.parse("28/08/1992"), "Mme", 
                "12 Rue de la Prevoyance, Villeurbanne", "0532731620", "orayesgemez5313@outlook.com",
                sfh.parse("19:00:00"), sfh.parse("21:00:00"), getLatLng("12 Rue de la Prevoyance, Villeurbanne")));
             EmployeDAO.create(new Employe("SING", "Ainhoa", sf.parse("11/09/1982"), "Mme", 
                "4 Rue Phelypeaux, Villeurbanne", "0705224200", "asing8183@free.fr",
                sfh.parse("05:00:00"), sfh.parse("21:00:00"), getLatLng("4 Rue Phelypeaux, Villeurbanne")));
             EmployeDAO.create(new Employe("RINERD", "Julien", sf.parse("16/05/1989"), "M", 
                "4 Rue de la Jeunesse, Villeurbanne", "	0727252485", "jrinerd5241@yahoo.com",
                sfh.parse("09:00:00"), sfh.parse("16:00:00"), getLatLng("4 Rue de la Jeunesse, Villeurbanne")));
            EmployeDAO.create(new Employe("MIE", "Romain", sf.parse("17/11/1994"), "M", 
                "1 Rue Denis Papin, Villeurbanne", "0307363387", "romain.mie@free.fr",
                sfh.parse("19:00:00"), sfh.parse("21:00:00"), getLatLng("1 Rue Denis Papin, Villeurbanne")));
            EmployeDAO.create(new Employe("THEE THIEN NGHIA", "Yann", sf.parse("16/12/1975"), "M", 
                "1 Rue Alexandre Dumas, Villeurbanne", "0998247431", "ytheethiennghia8688@laposte.net",
                sfh.parse("11:00:00"), sfh.parse("15:00:00"), getLatLng("1 Rue Alexandre Dumas, Villeurbanne")));
            EmployeDAO.create(new Employe("LOU", "Silvia", sf.parse("22/01/1985"), "Mme", 
                "19 Avenue Paul Kruger, Villeurbanne", "0378851388", "silvia.lou@laposte.net",
                sfh.parse("14:00:00"), sfh.parse("19:00:00"), getLatLng("19 Avenue Paul Kruger, Villeurbanne")));
            EmployeDAO.create(new Employe("PRIMEULT", "Cheng", sf.parse("03/10/1971"), "M", 
                "10 Impasse Bayet, Villeurbanne", "0618703838", "cprimeult7807@hotmail.com",
                sfh.parse("09:00:00"), sfh.parse("13:00:00"), getLatLng("10 Impasse Bayet, Villeurbanne")));
            EmployeDAO.create(new Employe("SOUMOELLIN", "Stéphanie", sf.parse("22/05/1983"), "Mme", 
                "16 Rue Sylvestre, Villeurbanne", "0239372281", "ssoumoellin3265@yahoo.com",
                sfh.parse("06:00:00"), sfh.parse("18:00:00"), getLatLng("16 Rue Sylvestre, Villeurbanne")));
        } catch (ParseException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }
    
    public static Client trouverClient(String adresseMail) {
        JpaUtil.creerEntityManager();
        Client c;
        try {
            c = ClientDAO.findClientByAdresseMail(adresseMail);
        } catch (NoResultException e) {
            c = null;
        }
        JpaUtil.fermerEntityManager();
        return c;
    }
    
    public static Employe trouverEmploye(String adresseMail) {
        JpaUtil.creerEntityManager();
        Employe e;
        try {
            e = EmployeDAO.findEmployeByAdresseMail(adresseMail);
        } catch (NoResultException exc) {
            e = null;
        }
        JpaUtil.fermerEntityManager();
        return e;
    }
    
    public static DemandeIntervention trouverDemande(long id) {
        System.out.println("J'suis dans le service");
        JpaUtil.creerEntityManager();
        System.out.println("J'suis dans le service");
        DemandeIntervention di;
        try {
            di = DemandeInterventionDAO.findDemandeInterventionById(id);
        } catch (NoResultException exc) {
            di = null;
        }
        System.out.println(di);
        JpaUtil.fermerEntityManager();
        return di;
    }
    
    public static ResultatConnexion connexion(String adresseMail, String mdp) {
        Client c = trouverClient(adresseMail);
        if (c != null) {
            if (c.getNumeroClient() == Long.parseLong(mdp)) {
                return new ResultatConnexion(c);
            }
            return new ResultatConnexion();
        }
        
        Employe e = trouverEmploye(adresseMail);
        if (e != null) {
            if (e.getNumeroEmploye() == Long.parseLong(mdp)) {
                return new ResultatConnexion(e);
            }
            return new ResultatConnexion();
        }
        return new ResultatConnexion();
    }
    
    private static void envoyerMail(Client c, boolean succes){
        System.out.println("Expéditeur : contact@proact.if");
        System.out.println("Pour : "+ c.getAdresseMail());
        System.out.println("Sujet : Bienvenue chez PROACT'IF");
        System.out.println("\nCorps");
        System.out.println("Bonjour " + c.getPrenom() + ",");
        if(succes){
            System.out.println("Nous vous confirmons votre inscription au service PROACT'IF. Votre numéro de client est "+ c.getNumeroClient() + ".");
        }
        else{
            System.out.println("Votre inscription au service PROACT'IF a malencontreusement échoué... Merci de recommencer ultérieurment.");
        }
    }
    
    public static void creerDemande(DemandeIntervention demande) throws Exception {
        JpaUtil.creerEntityManager();
        
        boolean fini = false;
        
        while (!fini) {
            fini = true;
            List<Employe> emps = EmployeDAO.findEmployesByDemande(demande);
            Employe e = null;
            double distance = Double.MAX_VALUE;
            try {
                for (Employe emp : emps) {
                    LatLng empCoords = emp.getCoordonnees();
                    LatLng clientCoords = demande.getClient().getCoordonnees();
                    double nouvelleDistance = Service.getTripDurationByBicycleInMinute(empCoords, clientCoords);
                    if (nouvelleDistance < distance) {
                        distance = nouvelleDistance;
                        e = emp;
                    }
                }
            } catch (NullPointerException ex) {
                throw new Exception("Lieu non accessible en vélo");
            }
            if (e == null) {
                throw new Exception("Aucun employé trouvé");
            }
            e.addDemandeIntervention(demande);
            demande.getClient().addDemandeIntervention(demande);

            try {
                JpaUtil.ouvrirTransaction();

                e.setDisponible(false);
                DemandeInterventionDAO.create(demande);

                Scanner sc = new Scanner(System.in);
                
                if(sc.hasNextLine()){
                    sc.nextLine();
                }
                
                JpaUtil.validerTransaction();
            } catch (RollbackException ex) {
                fini = false;
                JpaUtil.annulerTransaction();
            }
        }
        JpaUtil.fermerEntityManager();
        
        envoyerNotification(demande);
    }
    
    private static void envoyerNotification(DemandeIntervention d) {
        SimpleDateFormat sft = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        System.out.print("Intervention ");
        if (d instanceof DemandeAnimal) {
            System.out.print("Animal ");
        } else if (d instanceof DemandeLivraison) {
            System.out.print("Livraison ");
        } else {
            System.out.print("Incident ");
        }
        System.out.print("demandée le " + sft.format(d.getHorodate()) + " pour "
                + d.getClient().getPrenom() + " " + d.getClient().getNom() + " (#"
                + d.getClient().getNumeroClient() + "), " + d.getClient().getAdresse() + " ("
                + getTripDistanceByBicycleInKm(d.getEmploye().getCoordonnees(), d.getClient().getCoordonnees())
                + "km) : " + d.getDescription());
        if (d instanceof DemandeAnimal) {
            System.out.print(" - [Animal : " + ((DemandeAnimal) d).getAnimal() + "]");
        } else if (d instanceof DemandeLivraison) {
            System.out.print(" - [Objet : " + ((DemandeLivraison) d).getObjet()
                    + ", Entreprise : " + ((DemandeLivraison) d).getEntreprise() + "]");
        }
        System.out.println();
    }
    
    public static DemandeIntervention getDemandeInterventionEmploye(Employe e){
        if(e.isDisponible()== true){
            return null;
        }
        else{
            for(DemandeIntervention d : e.getDemandeInterventions()){
                if(d.getStatut() == Statut.EN_COURS){
                    return d;
                }
            }
        }
        return null;
    }
    
    public static Employe cloturerDemande(DemandeIntervention d, Statut s, String commentaire, Date heureDeFin){
        JpaUtil.creerEntityManager();
        
        Employe empMaj = null;
        
        d.setStatut(s);
        d.setCommentaire(commentaire);
        d.setHeureFin(heureDeFin);
        d.getEmploye().setDisponible(true);
        
        boolean fini = false;
        while (!fini) {
            fini = true;

            try {
                JpaUtil.ouvrirTransaction();

                DemandeInterventionDAO.update(d);
                empMaj = EmployeDAO.update(d.getEmploye());

                JpaUtil.validerTransaction();
            } catch (RollbackException e) {
                JpaUtil.annulerTransaction();
                fini = false;
            }
        }
        JpaUtil.fermerEntityManager();
        
        envoyerNotificationFin(d);
        
        return empMaj;
    }
    
     private static void envoyerNotificationFin(DemandeIntervention d) {
        SimpleDateFormat sft = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        d.getHeureFin().setYear(d.getHorodate().getYear());
        d.getHeureFin().setMonth(d.getHorodate().getMonth());
        d.getHeureFin().setDate(d.getHorodate().getDate());
        System.out.print("Intervention ");
        if (d instanceof DemandeAnimal) {
            System.out.print("Animal ");
        } else if (d instanceof DemandeLivraison) {
            System.out.print("Livraison ");
        } else {
            System.out.print("Incident ");
        }
        System.out.print("clôturée le " + sft.format(d.getHeureFin()) + " par "
                + d.getEmploye().getPrenom() + " " + d.getEmploye().getNom() + " (#"
                + d.getEmploye().getNumeroEmploye() + "), Statut : ");
        if(d.getStatut() == Statut.FINI){
            System.out.print("L'intervention a été résolue, ");
        }else{
            System.out.print("Un problème a été rencontré, ");
        }
        System.out.println("Commentaire : " + d.getCommentaire());
    }
     
    public static List<DemandeIntervention> getDemandesDuJour(){
        JpaUtil.creerEntityManager();
        Date dateDuJour = new Date();
        List<DemandeIntervention> result = DemandeInterventionDAO.findDemandeInterventionByDate(dateDuJour);
        JpaUtil.fermerEntityManager();
        return result;
    }
    
    public static List<DemandeIntervention> getDemandesEnCoursClient(Client c) {
        JpaUtil.creerEntityManager();
        
        List<DemandeIntervention> resultats = DemandeInterventionDAO.findDemandeEnCoursByClient(c);
        
        JpaUtil.fermerEntityManager();
        
        return resultats;
    }
    
    public static Client annulerDemande(DemandeIntervention d) {
        JpaUtil.creerEntityManager();

        Client clientMaj = null;
        
        d.getEmploye().setDisponible(true);
        d.getEmploye().removeDemandeIntervention(d);
        d.getClient().removeDemandeIntervention(d);
        
        boolean fini = false;
        while (!fini) {
        fini = true;
            try {
                JpaUtil.ouvrirTransaction();
                EmployeDAO.update(d.getEmploye());
                clientMaj = ClientDAO.update(d.getClient());
                DemandeInterventionDAO.remove(d);

                JpaUtil.validerTransaction();
            } catch (RollbackException e) {
                JpaUtil.annulerTransaction();
                fini = false;
            }
        }
        JpaUtil.fermerEntityManager();
        
        envoyerNotificationAnnulation(d);
        
        return clientMaj;
    }
    
    public static Client mettreAJourDemande(DemandeIntervention d) {
        JpaUtil.creerEntityManager();
        
        Client clientMaj = null;
        
        boolean fini = false;
        while (!fini) {
            fini = true;
            
            try {
                JpaUtil.ouvrirTransaction();

                DemandeInterventionDAO.update(d);
                clientMaj = ClientDAO.update(d.getClient());
                
                JpaUtil.validerTransaction();
            } catch (RollbackException e) {
                JpaUtil.annulerTransaction();
                fini = false;
            }
        }
        JpaUtil.fermerEntityManager();
        
        envoyerNotificationMiseAJour(d);
        return clientMaj;
    }
    
    private static void envoyerNotificationAnnulation(DemandeIntervention d) {
        SimpleDateFormat sft = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        System.out.print("/!\\ Annulation de l'intervention ");
        if (d instanceof DemandeAnimal) {
            System.out.print("Animal ");
        } else if (d instanceof DemandeLivraison) {
            System.out.print("Livraison ");
        } else {
            System.out.print("Incident ");
        }
        System.out.println("demandée le " + sft.format(d.getHorodate()) + " pour "
                + d.getClient().getPrenom() + " " + d.getClient().getNom() + " (#"
                + d.getClient().getNumeroClient() + ") : " + d.getDescription());
    }
    
    private static void envoyerNotificationMiseAJour(DemandeIntervention d) {
        SimpleDateFormat sft = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        System.out.print("/!\\ Mise à jour de l'intervention ");
        if (d instanceof DemandeAnimal) {
            System.out.print("Animal ");
        } else if (d instanceof DemandeLivraison) {
            System.out.print("Livraison ");
        } else {
            System.out.print("Incident ");
        }
        System.out.print("demandée le " + sft.format(d.getHorodate()) + " pour "
                + d.getClient().getPrenom() + " " + d.getClient().getNom() + " (#"
                + d.getClient().getNumeroClient() + ") : " + d.getDescription());
        if (d instanceof DemandeAnimal) {
            System.out.print(" - [Animal : " + ((DemandeAnimal) d).getAnimal() + "]");
        } else if (d instanceof DemandeLivraison) {
            System.out.print(" - [Objet : " + ((DemandeLivraison) d).getObjet()
                    + ", Entreprise : " + ((DemandeLivraison) d).getEntreprise() + "]");
        }
        System.out.println();
    }
    
    private static Double getTripDurationByBicycleInMinute(LatLng origin, LatLng destination) {
        return getTripDurationOrDistance(TravelMode.BICYCLING, true, origin, destination);
    }
    
    private static Double getTripDistanceByBicycleInKm(LatLng origin, LatLng destination) {
        return getTripDurationOrDistance(TravelMode.BICYCLING, false, origin, destination);
    }
    
    private static Double getTripDurationOrDistance(TravelMode mode, boolean duration, LatLng origin, LatLng destination, LatLng... steps) {
        DirectionsApiRequest request = DirectionsApi.getDirections(MON_CONTEXTE_GEOAPI, origin.toString(), destination.toString());
        request.mode(mode);
        request.region("fr");

        if (steps.length > 0) {

            String[] stringSteps = new String[steps.length];
            for (int i = 0; i < steps.length; i++) {
                stringSteps[i] = steps[i].toString();
            }

            request.waypoints(stringSteps);
        }

        double cumulDistance = 0.0;
        double cumulDuration = 0.0;

        try {
            DirectionsResult result = request.await();
            DirectionsRoute[] directions = result.routes;

            for (int legIndex = 0; legIndex < directions[0].legs.length; legIndex++) {

                cumulDistance += directions[0].legs[legIndex].distance.inMeters / 1000.0;
                cumulDuration += Math.ceil(directions[0].legs[legIndex].duration.inSeconds / 60.0);
            }

        } catch (Exception ex) {
            return null;
        }

        if (duration) {
            return cumulDuration;
        } else {
            return cumulDistance;
        }
    }
    
    private static LatLng getLatLng(String adresse) {
        try {
            GeocodingResult[] results =
                GeocodingApi.geocode(MON_CONTEXTE_GEOAPI,adresse).await();
            return results[0].geometry.location;
        } catch (Exception ex) {
            return null;
        }
    }
}
