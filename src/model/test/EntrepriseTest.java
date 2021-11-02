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

    //Teste si les réservations respectent la condition de capacité
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

    //Teste si les réservations respectent les conditions d'horaires
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

    //Teste si la quantité de matériel réservée à chaque créneau est cohérente (inférieure ou égale à la quantité de matériel libre)
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
            }
        }
        assertTrue(result);
    }

    //Teste si toutes les réunions sont dans la liste des réservations (même sans salle)
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

    //Teste si les réunions ont une salle réservée
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