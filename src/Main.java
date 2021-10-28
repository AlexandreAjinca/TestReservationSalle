import model.data.Entreprise;

public class Main {
    //TODO : Classes de test
    //TODO : Classe réservation? avec Salle, créneau, Reunion
    public static void main(String[] args){
        Entreprise e = Entreprise.getInstance();
        e.initializeCompany();
        //System.out.println(e);
        e.createPlanning();
    }
}
