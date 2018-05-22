/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.modele;

import java.util.Date;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author vrigolle
 */

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE", discriminatorType=DiscriminatorType.STRING,length=20)
@DiscriminatorValue("I")
public class DemandeIntervention {
    @Version
    protected Long version;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    protected Statut statut;
    protected String description;
    @Temporal(TemporalType.TIMESTAMP)
    protected Date horodate;
    @Temporal(TemporalType.TIME)
    protected Date heureFin;
    protected String commentaire;
    @ManyToOne
    protected Client client;
    @ManyToOne
    protected Employe employe;

    public DemandeIntervention(String description, Date horodate, Client client) {
        this.statut = Statut.EN_COURS;
        this.description = description;
        this.horodate = horodate;
        this.client = client;
    }

    public DemandeIntervention() {
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getHorodate() {
        return horodate;
    }

    public void setHorodate(Date horodate) {
        this.horodate = horodate;
    }

    public Date getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Date heureFin) {
        this.heureFin = heureFin;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    @Override
    public String toString() {
        return "DemandeIntervention{" + "id=" + id + ", statut=" + statut + ", description=" + description + ", horodate=" + horodate + ", heureFin=" + heureFin + ", commentaire=" + commentaire + ", client=" + client + ", employe=" + employe + '}';
    }
}
