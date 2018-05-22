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
@DiscriminatorValue("A")
public class DemandeAnimal extends DemandeIntervention{
    private String animal;

    public DemandeAnimal(String animal, String description, Date horodate, Client client) {
        super(description, horodate, client);
        this.animal = animal;
    }

    public DemandeAnimal() {
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    @Override
    public String toString() {
        return super.toString() + "DemandeAnimal{" + "animal=" + animal + '}';
    }
}
