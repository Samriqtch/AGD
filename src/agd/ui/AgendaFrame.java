/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author kloug
 */package agd.ui;

import agd.model.*;
import agd.Exception.*;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
 


public class AgendaFrame extends JFrame {
    private JComboBox<Medecin> medecinComboBox;
    private JTextField patientNomField;
    private JTextField patientPrenomField;
    private JSpinner dateSpinner;
    private JSpinner heureSpinner;
    private JDateChooser dateChooser;
    private DefaultListModel<RendezVous> rendezVousModel;
    private JList<RendezVous> rendezVousList;

    private List<Medecin> medecins = new ArrayList<>();

    public AgendaFrame() {
        super("Agenda Médical");

        setLayout(new BorderLayout());

        // Médecins par défaut
        medecins.add(new Cardiologue("Dupont"));
        medecins.add(new Cardiologue("Martin"));

        JPanel panelFormulaire = new JPanel(new GridLayout(6, 2));
        patientNomField = new JTextField();
        patientPrenomField = new JTextField();
        medecinComboBox = new JComboBox<>(medecins.toArray(new Medecin[0]));

        /*dateSpinner = new JSpinner(new SpinnerDateModel());
        heureSpinner = new JSpinner(new SpinnerDateModel());*/
        
        dateChooser = new JDateChooser();
        heureSpinner= new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(heureSpinner, "HH:mm");
        heureSpinner.setEditor(timeEditor);
        heureSpinner.setValue(new Date());
        
        

        rendezVousModel = new DefaultListModel<>();
        rendezVousList = new JList<>(rendezVousModel);

        panelFormulaire.add(new JLabel("Nom du Patient :"));
        panelFormulaire.add(patientNomField);
        panelFormulaire.add(new JLabel("Prénom du Patient :"));
        panelFormulaire.add(patientPrenomField);
        panelFormulaire.add(new JLabel("Médecin :"));
        panelFormulaire.add(medecinComboBox);
        panelFormulaire.add(new JLabel("Date :"));
        panelFormulaire.add(dateChooser);
        panelFormulaire.add(new JLabel("Heure :"));
        panelFormulaire.add(heureSpinner);

        JButton boutonAjouter = new JButton("Prendre RDV");
        boutonAjouter.addActionListener(e -> ajouterRendezVous());

        JButton boutonAnnuler = new JButton("Annuler RDV");
        boutonAnnuler.addActionListener(e -> annulerRendezVous());

        panelFormulaire.add(boutonAjouter);
        panelFormulaire.add(boutonAnnuler);

        add(panelFormulaire, BorderLayout.NORTH);
        add(new JScrollPane(rendezVousList), BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
    }

    private void ajouterRendezVous() {
        try {
            String nom = patientNomField.getText();
            String prenom = patientPrenomField.getText();
            Patient patient = new Patient(nom, prenom);

            Medecin medecin = (Medecin) medecinComboBox.getSelectedItem();
            
            //Ajout d'un nouvelle ligne pour calendrier interactif
            
            Date selectedDate = dateChooser.getDate();
            if(selectedDate==null){
                JOptionPane.showMessageDialog(this,"Veuillez sélectionner une date","Erreur",JOptionPane.ERROR_MESSAGE);
                return;
            }

            /*LocalDate date = ((java.util.Date) dateSpinner.getValue()).toInstant()
                        .atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            LocalTime heure = ((java.util.Date) heureSpinner.getValue()).toInstant()
                        .atZone(java.time.ZoneId.systemDefault()).toLocalTime();*/
            
            //Ajout de Gemini 
            
            LocalDate date = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Date heureDate = (Date) heureSpinner.getValue();
            LocalTime heure = heureDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime().withSecond(0).withNano(0);

            LocalDateTime dateTime = LocalDateTime.of(date, heure);

            // Vérification conflit horaire
            for (RendezVous rdv : medecin.getRendezVousList()) {
                if (rdv.getDateHeure().equals(dateTime)) {
                    throw new ConflitHoraireException("Conflit de rendez-vous !");
                }
            }

            RendezVous rendezVous = new RendezVous(patient, medecin, dateTime);
            medecin.ajouterRendezVous(rendezVous);
            
            boolean added = false;
            for(int i = 0; i < rendezVousModel.getSize();i++){
                RendezVous existingRendezVous = rendezVousModel.getElementAt(i);
                if(rendezVous.compareTo(existingRendezVous)<0){
                    rendezVousModel.insertElementAt(rendezVous, i);
                            added=true;
                            break;
                }
            }
            if(!added){
                rendezVousModel.addElement(rendezVous);
            }
            
            //rendezVousModel.addElement(rendezVous);

            JOptionPane.showMessageDialog(this, "Rendez-vous ajouté !");
        } catch (ConflitHoraireException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Données invalides", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void annulerRendezVous() {
        RendezVous selected = rendezVousList.getSelectedValue();
        if (selected != null) {
            selected.getMedecin().annulerRendezVous(selected);
            rendezVousModel.removeElement(selected);
            JOptionPane.showMessageDialog(this, "Rendez-vous annulé !");
        }
    }
}

