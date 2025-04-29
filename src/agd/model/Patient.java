/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agd.model;

/**
 *
 * @author kloug
 */
public class Patient {
        private String nom;
    private String prenom;

    public Patient(String nom, String prenom) {
        if(nom == null || prenom == null) {
            throw new IllegalArgumentException("Nom ou prénom ne peut pas être null");
        }
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }

    @Override
    public String toString() {
        return prenom + " " + nom;
    }
}
