import model.data.Entreprise;

public class Main {
    public static void main(String[] args){
        Entreprise e = Entreprise.getInstance();
        e.initializeCompany();
        //System.out.println(e);
        e.createPlanning();
        e.displayReservations();
    }
}
