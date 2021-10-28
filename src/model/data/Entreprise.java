package model.data;

import java.io.Serializable;
import java.util.*;
import static model.data.Reunion.TypeReunion.*;
import static model.data.TypeEquipement.*;

public class Entreprise implements Serializable {

    private List<Reunion> reunions = new ArrayList<>();
    private List<Salle> locaux = new ArrayList<>();
    private Map<TypeEquipement,Integer> equipementLibres = new HashMap<>();
    private List<Reservation> reservations = new ArrayList<>();

    //Pattern singleton
    private static Entreprise instance = new Entreprise();
    public static Entreprise getInstance(){
        if(instance==null){
            instance = new Entreprise();
        }
        return instance;
    }
    private Entreprise(){}

    //On initialise toute l'entreprise ici vu que dans notre problème la situation est fixe.
    public void initializeCompany(){
        reunions.add(new Reunion(1,9, VC,8));
        reunions.add(new Reunion(2,9, VC,6));
        reunions.add(new Reunion(3,11,RC,4));
        reunions.add(new Reunion(4,11, RS,2));
        reunions.add(new Reunion(5,11,SPEC,9));

        reunions.add(new Reunion(6,9,RC,7));
        reunions.add(new Reunion(7,8, VC,9));
        reunions.add(new Reunion(8,8,SPEC,10));
        reunions.add(new Reunion(9,9,SPEC,5));
        reunions.add(new Reunion(10,9, RS,4));

        reunions.add(new Reunion(11,9,RC,10));
        reunions.add(new Reunion(12,11, VC,12));
        reunions.add(new Reunion(13,11, SPEC,5));
        reunions.add(new Reunion(14,8, VC,3));
        reunions.add(new Reunion(15,8, SPEC,2));

        reunions.add(new Reunion(16,8, VC,12));
        reunions.add(new Reunion(17,10,VC,6));
        reunions.add(new Reunion(18,11,RS,2));
        reunions.add(new Reunion(19,9,RS,4));
        reunions.add(new Reunion(20,9,RC,7));

        locaux.add(new Salle("E1001",23, new HashMap<>(Map.of(Ecran, 0, Pieuvre, 0, Webcam, 0, Tableau, 0))));
        locaux.add(new Salle("E1002",10, new HashMap<>(Map.of(Ecran, 1, Pieuvre, 0, Webcam, 0, Tableau, 0))));
        locaux.add(new Salle("E1003",8, new HashMap<>(Map.of(Ecran, 0, Pieuvre, 1, Webcam, 0, Tableau, 0))));
        locaux.add(new Salle("E1004",4, new HashMap<>(Map.of(Ecran, 0, Pieuvre, 0, Webcam, 0, Tableau, 1))));
        locaux.add(new Salle("E2001",4, new HashMap<>(Map.of(Ecran, 0, Pieuvre, 0, Webcam, 0, Tableau, 0))));
        locaux.add(new Salle("E2002",15,new HashMap<>(Map.of(Ecran, 1, Pieuvre, 0, Webcam, 1, Tableau, 0))));
        locaux.add(new Salle("E2003",7,new HashMap<>(Map.of(Ecran, 0, Pieuvre, 0, Webcam, 0, Tableau, 0))));
        locaux.add(new Salle("E2004",9,new HashMap<>(Map.of(Ecran, 0, Pieuvre, 0, Webcam, 0, Tableau, 1))));
        locaux.add(new Salle("E3001",13,new HashMap<>(Map.of(Ecran, 1, Pieuvre, 1, Webcam, 1, Tableau, 0))));
        locaux.add(new Salle("E3002",8,new HashMap<>(Map.of(Ecran, 0, Pieuvre, 0, Webcam, 0, Tableau, 0))));
        locaux.add(new Salle("E3003",9,new HashMap<>(Map.of(Ecran, 1, Pieuvre, 1, Webcam, 0, Tableau, 0))));
        locaux.add(new Salle("E3004",4,new HashMap<>(Map.of(Ecran, 0, Pieuvre, 0, Webcam, 0, Tableau, 0))));

        equipementLibres = new HashMap<>(Map.of(Ecran, 5, Pieuvre, 4, Webcam, 4, Tableau, 2));
    }

    //TODO : Algorithme d'attribution
    public void createPlanning() {
        System.out.println("Create planning : ");

        //On crée et initialise la Map
        Map<Reunion,List<Salle>> sallesPourReunion = new HashMap<>();
        for(Reunion r : reunions){
            sallesPourReunion.put(r,new ArrayList<>(locaux));
        }

        //On réduit les salles à celles de capacité suffisante
        System.out.println("On réduit les salles à celles de capacité suffisante : ");
        for(Map.Entry<Reunion,List<Salle>> spr : sallesPourReunion.entrySet()){
            spr.getValue().removeIf(x->x.getCapacite()*0.7 < spr.getKey().getNbPersonnes());
        }

        //Si une réunion n'a qu'une salle possible, on lui assigne
        System.out.println("On cherche les réunions qui n'ont qu'une salle dispo  : ");
        for(Map.Entry<Reunion,List<Salle>> spr : sallesPourReunion.entrySet()){
            if(spr.getValue().size()==1){
                Reunion r = spr.getKey();
                Salle s = spr.getValue().get(0);
                System.out.println("On a trouvé la réunion : " +r + " qui n'a que la salle : " + s);
                if(isDisponible(s,r.getCreneau())){
                    Reservation newRes = new Reservation(r,s);
                    newRes.setEquipementReserve(new HashMap<>(Map.of(Ecran, 0, Pieuvre, 0, Webcam, 0, Tableau, 0)));
                    //On ajoute le matériel manquant à la salle
                    switch(r.getType()) {
                        case VC:
                            if (s.getEquipements().get(Ecran) == 0)
                                newRes.getEquipementReserve().put(Ecran, 1);
                            if (s.getEquipements().get(Pieuvre) == 0)
                                newRes.getEquipementReserve().put(Pieuvre, 1);
                            if (s.getEquipements().get(Webcam) == 0)
                                newRes.getEquipementReserve().put(Webcam, 1);
                            break;
                        case SPEC:
                            if (s.getEquipements().get(Tableau) == 0)
                                newRes.getEquipementReserve().put(Tableau, 1);
                            break;
                        case RS:
                            break;
                        case RC:
                            if (s.getEquipements().get(Ecran) == 0)
                                newRes.getEquipementReserve().put(Ecran, 1);
                            if (s.getEquipements().get(Pieuvre) == 0)
                                newRes.getEquipementReserve().put(Pieuvre, 1);
                            if (s.getEquipements().get(Tableau) == 0)
                                newRes.getEquipementReserve().put(Tableau, 1);
                    }
                    System.out.println("Elle est dispo, on crée la réservation : " + newRes);
                    reservations.add(newRes);
                }else{
                    //throw new Exception("La seulle salle de bonne capacité n'est plus disponible");
                    System.out.println("La salle n'est pas dispo à ce créneau");
                    reservations.add(new Reservation(r,null));
                }
            }
        }

        //On enlève les réunions assignées de la Map
        System.out.println("On retire les réunions réservées de la Map");
        for(Reservation r : reservations){
            sallesPourReunion.remove(r.getReunion());
        }

        //On enlève les salles déjà prises des salles possible
        System.out.println("On retire les salles déjà prises des réunions restantes");
        for(Map.Entry<Reunion,List<Salle>> spr : sallesPourReunion.entrySet()){
            spr.getValue().removeIf(x -> reservations.stream().anyMatch(y -> y.getSalle()==x &&( y.getReunion().getCreneau()==spr.getKey().getCreneau() || y.getReunion().getCreneau()==spr.getKey().getCreneau()-1)));
        }

        //Si une réunion n'a qu'une salle possible, on lui assigne
        System.out.println("On cherche les réunions qui n'ont qu'une salle dispo  : ");
        for(Map.Entry<Reunion,List<Salle>> spr : sallesPourReunion.entrySet()){
            if(spr.getValue().size()==1){
                Reunion r = spr.getKey();
                Salle s = spr.getValue().get(0);
                System.out.println("On a trouvé la réunion : " +r + " qui n'a que la salle : " + s);
                if(isDisponible(s,r.getCreneau())){
                    Reservation newRes = new Reservation(r,s);
                    newRes.setEquipementReserve(new HashMap<>(Map.of(Ecran, 0, Pieuvre, 0, Webcam, 0, Tableau, 0)));
                    //On ajoute le matériel manquant à la salle
                    switch(r.getType()) {
                        case VC:
                            if (s.getEquipements().get(Ecran) == 0)
                                newRes.getEquipementReserve().put(Ecran, 1);
                            if (s.getEquipements().get(Pieuvre) == 0)
                                newRes.getEquipementReserve().put(Pieuvre, 1);
                            if (s.getEquipements().get(Webcam) == 0)
                                newRes.getEquipementReserve().put(Webcam, 1);
                            break;
                        case SPEC:
                            if (s.getEquipements().get(Tableau) == 0)
                                newRes.getEquipementReserve().put(Tableau, 1);
                            break;
                        case RS:
                            break;
                        case RC:
                            if (s.getEquipements().get(Ecran) == 0)
                                newRes.getEquipementReserve().put(Ecran, 1);
                            if (s.getEquipements().get(Pieuvre) == 0)
                                newRes.getEquipementReserve().put(Pieuvre, 1);
                            if (s.getEquipements().get(Tableau) == 0)
                                newRes.getEquipementReserve().put(Tableau, 1);
                    }
                    System.out.println("Elle est dispo, on crée la réservation : " + newRes);
                    reservations.add(newRes);
                }else{
                    //throw new Exception("La seulle salle de bonne capacité n'est plus disponible");
                    System.out.println("La salle n'est pas dispo à ce créneau");
                    reservations.add(new Reservation(r,null));
                }
            }
        }

        //On enlève les réunions assignées de la Map
        System.out.println("On retire les réunions réservées de la Map");
        for(Reservation r : reservations){
            sallesPourReunion.remove(r.getReunion());
        }

        //TODO Enlever cette partie si l'autre fonctionne
        //Algorithme d'attribution des salles dans l'ordre
        /*for(Reunion r : reunions){
            System.out.println(r);

            List<Salle> sallesPotentielles = new ArrayList<>(locaux);
            //On filtre la disponibilité
            sallesPotentielles.removeIf(x -> reservations.stream().anyMatch(y -> y.getSalle()==x &&( y.getReunion().getCreneau()==r.getCreneau() || y.getReunion().getCreneau()==r.getCreneau()-1)));

            //On filtre la capacité
            sallesPotentielles.removeIf(x->x.getCapacite()*0.7 < r.getNbPersonnes());

            //On filtre le matériel

            //On affiche les salles potentielles.
            for(Salle s : sallesPotentielles){
                System.out.print("Salle : " + s.getNom() + " Capacité : " + s.getCapacite() +".\n");
            }
            System.out.println(" ");

            //On choisit la meilleure salle
            if(sallesPotentielles.size()>0)
                reservations.add(new Reservation(r,sallesPotentielles.get(0)));
        }*/

        for(Reservation rs : reservations){
            System.out.println(rs);
        }
    }

    //Vérifie dans la liste des réservations si la salle est réservée à ce créneau (ou celui d'avant à cause des restrictions COVID)
    public boolean isDisponible(Salle s, int creneau){
        boolean result = true;
        for(Reservation r : reservations){
            if(r.getSalle()==s && (r.getReunion().getCreneau()==creneau || r.getReunion().getCreneau()-1==creneau)) {
                return false;
            }
        }
        return result;
    }

    //TODO : getter and setters
    @Override
    public String toString() {
        String result = "REUNIONS : \n";
        int count = 1;
        for (Reunion r:
                reunions) {
            result+="Reunion " + count + " : " + r.getCreneau() + "h ; Type : " + r.getType() +" ; Nombre : " + r.getNbPersonnes() + "\n";
            count++;
        }
        result+="\nSALLES : \n";
        for (Salle s:
                locaux) {
            result+="Salle " + s.getNom() + " : " + s.getCapacite() + " personnes Max ; Equipement : ";
            for (Map.Entry<TypeEquipement,Integer> e :
                 s.getEquipements().entrySet()) {
                result+= e.getKey() + " " + e.getValue() + " ";
            }
            result+="\n";
        }
        result+="\nEQUIPEMENTS LIBRES : \n";
        for (Map.Entry<TypeEquipement,Integer> el:
                equipementLibres.entrySet()) {
            result+= el.getKey() + " " + el.getValue() + " ";
            result+="\n";
        }
        return result;
    }
}
