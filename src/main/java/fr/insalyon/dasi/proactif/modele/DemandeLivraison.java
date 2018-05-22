/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.modele;

import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author vrigolle
 */
@Entity
@DiscriminatorValue("L")
public class DemandeLivraison extends DemandeIntervention{
    private String objet;
    private String entreprise;

    public DemandeLivraison(String objet, String entreprise, String description, Date horodate, Client client) {
        super(description, horodate, client);
        this.objet = objet;
        this.entreprise = entreprise;
    }

    public DemandeLivraison() {
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    @Override
    public String toString() {
        return super.toString() + "DemandeLivraison{" + "objet=" + objet + ", entreprise=" + entreprise + '}';
    }
}
