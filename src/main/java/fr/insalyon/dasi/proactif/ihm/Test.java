/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.ihm;

import fr.insalyon.dasi.proactif.dao.JpaUtil;
import fr.insalyon.dasi.proactif.modele.DemandeAnimal;
import fr.insalyon.dasi.proactif.modele.DemandeIntervention;
import fr.insalyon.dasi.proactif.modele.DemandeLivraison;
import fr.insalyon.dasi.proactif.service.ResultatConnexion;
import fr.insalyon.dasi.proactif.modele.Statut;
import fr.insalyon.dasi.proactif.service.StatutResultatConnexion;
import fr.insalyon.dasi.proactif.service.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author vrigolle
 */
public class Test {
    public static void main(String[] args) {
        JpaUtil.init();
        
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sfh = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sft = new SimpleDateFormat("dd/MM/yyyy,HH:mm:ss");
        
        boolean fini = false;
        int choix = 0;
        ResultatConnexion connex = new ResultatConnexion();
        Scanner sc = new Scanner(System.in);
        
        System.out.println("-- [ PROACT'IF - IHM provisoire ] --");
        System.out.println("");
        
        while (!fini) {
            System.out.println("Entrez un nombre pour éxecuter un service : ");
            System.out.println("1.  Initialiser des employés");
            System.out.println("2.  Inscrire un client");
            System.out.println("3.  Se connecter");
            System.out.println("4.  Créer une demande d'intervention");
            System.out.println("5.  Trouver une demande associée à un employé");
            System.out.println("6.  Clôturer une demande d'intervention");
            System.out.println("7.  Afficher toutes les demandes du jour");
            System.out.println("8.  Afficher demandes en cours du client");
            System.out.println("9.  Afficher historique demandes client");
            System.out.println("10. Annuler une demande d'intervention");
            System.out.println("11. Mettre à jour une demande");
            System.out.println("12. Quitter");
            System.out.print("> ");
            choix = Integer.parseInt(sc.nextLine());
            switch (choix) {
                case 1:
                    Service.creerListeEmployes();
                    break;
                case 2:
                    System.out.print("Nom : ");
                    String nom = sc.nextLine();
                    System.out.print("Prénom : ");
                    String prenom = sc.nextLine();
                    System.out.print("Date de naissance (dd/MM/yyyy) : ");
                    Date dateNaissance;
                    try {
                        dateNaissance = sf.parse(sc.nextLine());
                    } catch (ParseException ex) {
                        dateNaissance = new Date();
                    }
                    System.out.print("Civilite (M/Mme) : ");
                    String civilite = sc.nextLine();
                    System.out.print("Adresse : ");
                    String adresse = sc.nextLine();
                    System.out.print("Téléphone : ");
                    String telephone = sc.nextLine();
                    System.out.print("Adresse mail : ");
                    String mail = sc.nextLine();
                    Service.inscrireClient(nom, prenom, dateNaissance, civilite, adresse, telephone, mail);
                    break;
                case 3:
                    System.out.print("Adresse mail : ");
                    String adresseMail = sc.nextLine();
                    System.out.print("Mot de passe : ");
                    String mdp = sc.nextLine();
                    connex = Service.connexion(adresseMail, mdp);
                    System.out.println(connex);
                    break;
                case 4:
                    if (connex.getStatut() != StatutResultatConnexion.CLIENT) {
                        System.out.println("Vous devez vous connecter en tant que client");
                    } else {
                        System.out.println("Quel type de demande ?");
                        System.out.println("1. Incident");
                        System.out.println("2. Animal");
                        System.out.println("3. Livraison");
                        System.out.print("> ");
                        int choixDemande = Integer.parseInt(sc.nextLine());
                        DemandeIntervention demande = new DemandeIntervention();
                        System.out.print("Description : ");
                        String description = sc.nextLine();
                        switch (choixDemande) {
                            case 1:
                                demande = new DemandeIntervention(description, new Date(), connex.getClient());
                                break;
                            case 2:
                                System.out.print("Animal : ");
                                String animal = sc.nextLine();
                                demande = new DemandeAnimal(animal, description, new Date(), connex.getClient());
                                break;
                            case 3:
                                System.out.print("Objet : ");
                                String objet = sc.nextLine();
                                System.out.print("Entreprise : ");
                                String entreprise = sc.nextLine();
                                demande = new DemandeLivraison(objet, entreprise, description, new Date(), connex.getClient());
                                break;
                        }
                        System.out.println(demande);
                        try {
                            Service.creerDemande(demande);
                        } catch (Exception ex) {
                            System.out.println("Impossible de créer la demande");
                        }
                    }
                    break;
                case 5:
                    if (connex.getStatut() != StatutResultatConnexion.EMPLOYE) {
                        System.out.println("Vous devez être connecté en tant qu'employé");
                    } else {
                        DemandeIntervention demande2 = Service.getDemandeInterventionEmploye(connex.getEmploye());
                        System.out.println(demande2);
                    }
                    break;
                case 6:
                    if (connex.getStatut() != StatutResultatConnexion.EMPLOYE) {
                        System.out.println("Vous devez être connecté en tant qu'employé");
                    } else {
                        DemandeIntervention demande3 = Service.getDemandeInterventionEmploye(connex.getEmploye());
                        if (demande3 == null) {
                            System.out.println("Vous n'avez pas de demande en cours");
                        } else {
                            System.out.println("Statut de la demande : ");
                            System.out.println("1. Résolue");
                            System.out.println("2. Problème rencontré");
                            System.out.print("> ");
                            int choixStatut = Integer.parseInt(sc.nextLine());
                            Statut statut = (choixStatut == 1) ? Statut.FINI : Statut.PROBLEME;
                            System.out.print("Commentaire : ");
                            String commentaire = sc.nextLine();
                            connex.setEmploye(Service.cloturerDemande(demande3, statut, commentaire, new Date()));
                        }
                    }
                    break;
                case 7 :
                    for (DemandeIntervention d : Service.getDemandesDuJour()) {
                        System.out.println(d);
                    }
                    break;
                case 8 :
                    if (connex.getStatut() != StatutResultatConnexion.CLIENT) {
                        System.out.println("Vous devez vous connecter en tant que client");
                    } else {
                        for (DemandeIntervention d : Service.getDemandesEnCoursClient(connex.getClient())) {
                            System.out.println(d);
                        }
                    }
                    break;
                case 9 :
                    if (connex.getStatut() != StatutResultatConnexion.CLIENT) {
                        System.out.println("Vous devez vous connecter en tant que client");
                    } else {
                        for (DemandeIntervention d : connex.getClient().getDemandeInterventions()) {
                            System.out.println(d);
                        }
                    }
                    break;
                case 10:
                    if (connex.getStatut() != StatutResultatConnexion.CLIENT) {
                        System.out.println("Vous devez vous connecter en tant que client");
                    } else {
                        List<DemandeIntervention> demandesEnCours = Service.getDemandesEnCoursClient(connex.getClient());
                        System.out.println("Quelle demande annuler ?");
                        for (int i = 0; i < demandesEnCours.size(); i++) {
                            System.out.println((i+1) + ".\t" +  demandesEnCours.get(i));
                        }
                        System.out.print("> ");
                        int choixDemande = Integer.parseInt(sc.nextLine());
                        connex.setClient(Service.annulerDemande(demandesEnCours.get(choixDemande-1)));
                    }
                    break;
                case 11:
                    if (connex.getStatut() != StatutResultatConnexion.CLIENT) {
                        System.out.println("Vous devez vous connecter en tant que client");
                    } else {
                        List<DemandeIntervention> demandesEnCours = Service.getDemandesEnCoursClient(connex.getClient());
                        System.out.println("Quelle demande mettre à jour ?");
                        for (int i = 0; i < demandesEnCours.size(); i++) {
                            System.out.println((i+1) + ".\t" +  demandesEnCours.get(i));
                        }
                        System.out.print("> ");
                        int choixDemande = Integer.parseInt(sc.nextLine());
                        DemandeIntervention demandeMAJ = demandesEnCours.get(choixDemande-1);
                        System.out.print("Nouvelle description (laisser vide pour conserver) : ");
                        String nDescription = sc.nextLine();
                        if (!nDescription.equals("")) {
                            demandeMAJ.setDescription(nDescription);
                        }
                        if (demandeMAJ instanceof DemandeAnimal) {
                            System.out.print("Nouvel animal (laisser vide pour conserver) : ");
                            String nAnimal = sc.nextLine();
                            if (!nAnimal.equals("")) {
                                ((DemandeAnimal) demandeMAJ).setAnimal(nAnimal);
                            }
                        } else if (demandeMAJ instanceof DemandeLivraison) {
                            System.out.print("Nouvel objet (laisser vide pour conserver) : ");
                            String nObjet = sc.nextLine();
                            if (!nObjet.equals("")) {
                                ((DemandeLivraison) demandeMAJ).setObjet(nObjet);
                            }
                            System.out.print("Nouvelle entreprise (laisser vide pour conserver) : ");
                            String nEntreprise = sc.nextLine();
                            if (!nEntreprise.equals("")) {
                                ((DemandeLivraison) demandeMAJ).setEntreprise(nEntreprise);
                            }
                        }
                        connex.setClient(Service.mettreAJourDemande(demandeMAJ));
                    }
                    break;
                case 12:
                    fini = true;
                    break;
                default:
                    System.out.println("Option non reconnue");
                    break;
            }
        }
        System.out.println("Au revoir !");
        
        sc.close();
        JpaUtil.destroy();
    }
}
