/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agd.model;

import java.time.LocalDateTime;

/**
 *
 * @author kloug
 */
public class RendezVous implements Comparable<RendezVous> {
    private Patient patient;
    private Medecin medecin;
    private LocalDateTime dateHeure;

    public RendezVous(Patient patient, Medecin medecin, LocalDateTime dateHeure) {
        this.patient = patient;
        this.medecin = medecin;
        this.dateHeure = dateHeure;
    }
       public Patient getPatient() { return patient; }
    public Medecin getMedecin() { return medecin; }
    public LocalDateTime getDateHeure() { return dateHeure; }

    @Override
    public String toString() {
        return dateHeure + " - " + patient.toString() + " avec Dr. " + medecin.getNom();
    }
    
    @Override 
    public int compareTo(RendezVous autreRendezVous){
        return this.dateHeure.compareTo(autreRendezVous.dateHeure);
    }
    
}
