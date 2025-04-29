/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agd.model;

/**
 *
 * @author kloug
 */

import java.util.ArrayList;
import java.util.List;
public class Medecin {
        private String nom;
    private String spécialité;
    private List<RendezVous> rendezVousList = new ArrayList<>();

    public Medecin(String nom, String spécialité) {
        this.nom = nom;
        this.spécialité = spécialité;
    }

    public String getNom() { return nom; }
    public String getSpécialité() { return spécialité; }
    public List<RendezVous> getRendezVousList() { return rendezVousList; }

    public void ajouterRendezVous(RendezVous rdv) {
        rendezVousList.add(rdv);
    }

    public void annulerRendezVous(RendezVous rdv) {
        rendezVousList.remove(rdv);
    }

    @Override
    public String toString() {
        return nom + " (" + spécialité + ")";
    }
}
