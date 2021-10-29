package model.test;

import model.data.Entreprise;
import model.data.Reservation;
import model.data.Reunion;
import model.data.TypeEquipement;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EntrepriseTest {

    private Entreprise e;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        e = Entreprise.getInstance();
        e.initializeCompany();
        e.createPlanning();
    }

    @org.junit.jupiter.api.Test
    void testCapaciteRespectee() {
        boolean result = true;
        for(Reservation r : e.getReservations()){
            if(r.getSalle()==null)
                continue;
            if(r.getReunion().getNbPersonnes()>r.getSalle().getCapacite()*0.7){
                result = false;
            }
        }
        assertTrue(result);
    }

    @org.junit.jupiter.api.Test
    void testHorairesNonSuperposees() {
        boolean result = true;
        for(Reservation r : e.getReservations()){
            if(r.getSalle()==null){
                continue;
            }
            else if(e.getReservations().stream().anyMatch( x -> x!=r &&  r.getSalle() == x.getSalle() && ( r.getReunion().getCreneau()==x.getReunion().getCreneau() || r.getReunion().getCreneau()==x.getReunion().getCreneau()-1))){
                result = false;
            }
        }
        assertTrue(result);
    }

    @org.junit.jupiter.api.Test
    void testQuantiteMaterielCoherent() {
        boolean result = true;
        int[] creneaux = {8,9,10,11};
        for(int c : creneaux){
            for(TypeEquipement te : TypeEquipement.values()){
                int total = 0;
                for(Reservation r : e.getReservations()){
                    if(r.getSalle()==null)
                        continue;
                    if(r.getReunion().getCreneau() == c){
                        total += r.getEquipementReserve().get(te);
                    }
                }
                if(total>e.getEquipementLibres().get(te)){
                    result = false;
                }
                System.out.println("AU creneau " + c + " il y a " + total + " " + te + " empruntés.");
            }
        }
        assertTrue(result);
    }

    @org.junit.jupiter.api.Test
    void testToutesReunionsTraitees() {
        boolean result = true;
        for(Reunion r : e.getReunions()){
            result = false;
            for(Reservation res : e.getReservations()){
                if(res.getReunion().getIdReunion()==r.getIdReunion()){
                    result = true;
                }
            }
            if(result == false)
                break;
        }
        assertTrue(result);
    }

    @org.junit.jupiter.api.Test
    void testToutesReunionsAssignees() {
        boolean result = true;
        for(Reservation r : e.getReservations()){
            if(r.getSalle() == null){
                result = false;
            }
        }
        assertTrue(result);
    }

}