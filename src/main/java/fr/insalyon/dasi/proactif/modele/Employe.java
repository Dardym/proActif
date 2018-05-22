/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.modele;

import com.google.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author vrigolle
 */
@Entity
public class Employe {
    @Version
    private Long version;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long numeroEmploye;
    private String nom;
    private String prenom;
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    private String civilite;
    private String adresse;
    private String numeroTelephone;
    @Column(unique = true)
    private String adresseMail;
    private LatLng coordonnees;
    private boolean disponible;
    @Temporal(TemporalType.TIME)
    private Date heureDebut;
    @Temporal(TemporalType.TIME)
    private Date heureFin;
    @OneToMany(mappedBy = "employe", fetch=FetchType.EAGER)
    private List<DemandeIntervention> demandeInterventions;

    public Employe(String nom, String prenom, Date dateNaissance, String civilite, String adresse, String numeroTelephone, String adresseMail, Date heureDebut, Date heureFin, LatLng coordonnees) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.civilite = civilite;
        this.adresse = adresse;
        this.numeroTelephone = numeroTelephone;
        this.adresseMail = adresseMail;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.disponible = true;
        this.coordonnees = coordonnees;
        this.demandeInterventions = new ArrayList<>();
    }

    public Employe() {
    }
    
    public void addDemandeIntervention(DemandeIntervention d) {
        this.demandeInterventions.add(d);
        d.setEmploye(this);
    }
    
    public void removeDemandeIntervention(DemandeIntervention d) {
        this.demandeInterventions.remove(d);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Date getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Date heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Date getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Date heureFin) {
        this.heureFin = heureFin;
    }

    public Long getNumeroEmploye() {
        return numeroEmploye;
    }

    public LatLng getCoordonnees() {
        return coordonnees;
    }

    public List<DemandeIntervention> getDemandeInterventions() {
        return demandeInterventions;
    }

    @Override
    public String toString() {
        return "Employe{" + "numeroEmploye=" + numeroEmploye + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance=" + dateNaissance + ", civilite=" + civilite + ", adresse=" + adresse + ", numeroTelephone=" + numeroTelephone + ", adresseMail=" + adresseMail + ", coordonnees=" + coordonnees + ", disponible=" + disponible + ", heureDebut=" + heureDebut + ", heureFin=" + heureFin + '}';
    }
}
