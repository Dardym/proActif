/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.service;

import fr.insalyon.dasi.proactif.modele.Client;
import fr.insalyon.dasi.proactif.modele.Client;
import fr.insalyon.dasi.proactif.modele.Employe;
import fr.insalyon.dasi.proactif.modele.Employe;

/**
 *
 * @author Valentin
 */
public class ResultatConnexion {
    private Client client;
    private Employe employe;
    private StatutResultatConnexion statut;

    public ResultatConnexion(Client client) {
        this.statut = StatutResultatConnexion.CLIENT;
        this.client = client;
        this.employe = null;
    }
    
    public ResultatConnexion(Employe employe) {
        this.statut = StatutResultatConnexion.EMPLOYE;
        this.employe = employe;
        this.client = null;
    }
    
    public ResultatConnexion() {
        this.statut = StatutResultatConnexion.ECHEC;
        this.employe = null;
        this.client = null;
    }

    public Client getClient() {
        return client;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public StatutResultatConnexion getStatut() {
        return statut;
    }

    @Override
    public String toString() {
        return "ResultatConnexion{" + "client=" + client + ", employe=" + employe + ", statut=" + statut + '}';
    }
}
