import model.data.Entreprise;

public class Main {
    public static void main(String[] args){
        //On crée l'objet Entreprise
        Entreprise e = Entreprise.getInstance();

        //Initialise l'entreprise avec les valeurs initiales
        e.initializeCompany();

        //Crée le planning
        e.createPlanning();

        //Affiche le résultat
        e.displayReservations();
    }
}
