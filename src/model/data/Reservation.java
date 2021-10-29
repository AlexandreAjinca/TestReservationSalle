package model.data;

import java.util.HashMap;
import java.util.Map;

public class Reservation {
    private Reunion reunion;
    private Salle salle;
    private Map<TypeEquipement,Integer> equipementReserve = new HashMap<>();

    public Reservation(Reunion reunion, Salle salle) {
        this.reunion = reunion;
        this.salle = salle;
    }

    public Reunion getReunion() {
        return reunion;
    }

    public void setReunion(Reunion reunion) {
        this.reunion = reunion;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Map<TypeEquipement,Integer> getEquipementReserve() {
        return equipementReserve;
    }

    public void setEquipementReserve(Map<TypeEquipement,Integer> equipementReserve) {
        this.equipementReserve = equipementReserve;
    }

    @Override
    public String toString(){
        return "[RESERVATION : " + reunion + " ; " + salle + " ; equipement réservé : " + equipementReserve +"]";
    }

}
