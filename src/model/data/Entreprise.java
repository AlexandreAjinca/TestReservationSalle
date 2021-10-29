package model.data;

import java.util.*;
import java.util.stream.Collectors;

import static model.data.Reunion.TypeReunion.*;
import static model.data.TypeEquipement.*;

public class Entreprise {

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

    public List<Reunion> getReunions() {
        return reunions;
    }

    public void setReunions(List<Reunion> reunions) {
        this.reunions = reunions;
    }

    public List<Salle> getLocaux() {
        return locaux;
    }

    public void setLocaux(List<Salle> locaux) {
        this.locaux = locaux;
    }

    public Map<TypeEquipement, Integer> getEquipementLibres() {
        return equipementLibres;
    }

    public void setEquipementLibres(Map<TypeEquipement, Integer> equipementLibres) {
        this.equipementLibres = equipementLibres;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    //TODO : Algorithme d'attribution
    public void createPlanning() {
        System.out.println("Create planning : ");

        //On crée et initialise la Map
        Map<Reunion,List<Salle>> ReunionsNonReservees = new TreeMap<>();
        for(Reunion r : reunions){
            ReunionsNonReservees.put(r,new ArrayList<>(locaux));
        }

        //On réduit les salles à celles de capacité suffisante
        System.out.println("On réduit les salles à celles de capacité suffisante : ");
        for(Map.Entry<Reunion,List<Salle>> rnr : ReunionsNonReservees.entrySet()){
            rnr.getValue().removeIf(x->x.getCapacite()*0.7 < rnr.getKey().getNbPersonnes());
        }

        //Tant qu'il reste des réunions a assigner
        while(ReunionsNonReservees.size()>0){

            //Tant qu'il y a des réunions avec une seule salle possible, on les réserve.
            while(ReunionsNonReservees.values().stream().anyMatch(x -> x.size()==1)){
                //On parcourt les Reunions, si une réunion n'a qu'une salle possible, on lui assigne
                System.out.println("On cherche les réunions qui n'ont qu'une salle dispo  : ");
                for(Map.Entry<Reunion,List<Salle>> rnr : ReunionsNonReservees.entrySet()){
                    if(rnr.getValue().size()==1){
                        Reunion r = rnr.getKey();
                        Salle s = rnr.getValue().get(0);
                        System.out.println("On a trouvé la réunion : " +r + " qui n'a que la salle : " + s);
                        //Si la salle est disponible, on crée une nouvelle réservation
                        if(isSalleDisponible(s,r.getCreneau()) && isMaterielDisponible(r,s)){
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
                            System.out.println("Elle est dispo, nouvelle réservation : " + newRes);
                            reservations.add(newRes);
                        }else{
                            //throw new Exception("La seulle salle de bonne capacité n'est plus disponible");
                            System.out.println("La salle ou le matériel ne sont pas dispo à ce créneau");
                            reservations.add(new Reservation(r,null));
                        }
                    }
                }

                //On enlève les réunions assignées de la Map
                System.out.println("On retire les réunions réservées de la Map");
                for(Reservation r : reservations){
                    ReunionsNonReservees.remove(r.getReunion());
                }

                //On enlève les salles déjà prises des salles possible
                System.out.println("On retire les salles déjà prises des réunions restantes");
                for(Map.Entry<Reunion,List<Salle>> rnr : ReunionsNonReservees.entrySet()){
                    rnr.getValue().removeIf(x -> reservations.stream().anyMatch(y -> y.getSalle()==x &&( y.getReunion().getCreneau()==rnr.getKey().getCreneau() || y.getReunion().getCreneau()==rnr.getKey().getCreneau()+1 || y.getReunion().getCreneau()==rnr.getKey().getCreneau()-1)));
                }
            }

            //Quand il n'y a plus de réunions avec une seule salle possible, on cherche celles avec le moins de salles :
            int min = Collections.min(ReunionsNonReservees.values().stream().map(x -> x.size()).collect(Collectors.toList()));
            System.out.println("Le minimum de salle est maintenant de : " + min );
            for(Map.Entry<Reunion,List<Salle>> rnr : ReunionsNonReservees.entrySet()){
                if(rnr.getValue().size()==min){
                    //On assigne la salle ayant déjà le matos? COMPLEXE
                    //On assigne la salle la plus grande? SIMPLE, on choisit cette option.
                    Reunion r =  rnr.getKey();
                    System.out.println("On trouve la réunion : " + r + " qui a : " + min + " salles.");
                    Salle s = rnr.getValue().stream().reduce((x,y) -> {
                        if(x.getCapacite() >=y.getCapacite())
                            return x;
                        else
                            return y;
                    }).get();
                    //Si la salle est disponible, on crée une nouvelle réservation
                    if(isSalleDisponible(s,r.getCreneau()) && isMaterielDisponible(r,s)){
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
                        System.out.println("Elle est dispo, nouvelle réservation : " + newRes);
                        reservations.add(newRes);
                    }else{
                        rnr.getValue().remove(s);
                        //throw new Exception("La seulle salle de bonne capacité n'est plus disponible");
                        System.out.println("La salle n'est pas dispo à ce créneau");
                    }
                }
            }

            //On enlève les réunions assignées de la Map
            System.out.println("On retire les réunions réservées de la Map");
            for(Reservation r : reservations){
                ReunionsNonReservees.remove(r.getReunion());
            }

            //On enlève les salles déjà prises des salles possible
            System.out.println("On retire les salles déjà prises des réunions restantes");
            for(Map.Entry<Reunion,List<Salle>> rnr : ReunionsNonReservees.entrySet()){
                rnr.getValue().removeIf(x -> reservations.stream().anyMatch(y -> y.getSalle()==x &&( y.getReunion().getCreneau()==rnr.getKey().getCreneau() || y.getReunion().getCreneau()==rnr.getKey().getCreneau()+1 || y.getReunion().getCreneau()==rnr.getKey().getCreneau()-1)));
            }
        }

        for(Reservation rs : reservations){
            System.out.println(rs);
        }
    }

    //Retourne false si la salle est réservée à ce créneau (ou celui d'avant à cause des restrictions COVID)
    public boolean isSalleDisponible(Salle s, int creneau){
        for(Reservation r : reservations){
            if(r.getSalle()==s && (r.getReunion().getCreneau()==creneau || r.getReunion().getCreneau()-1==creneau || r.getReunion().getCreneau()+1==creneau)) {
                return false;
            }
        }
        return true;
    }

    //On vérifie si le matériel est disponible à l'emprunt pour cette réunion, à cette salle et dans ce créneau
    public boolean isMaterielDisponible(Reunion r, Salle s){
        int creneau = r.getCreneau();
        boolean result = true;
        Map<TypeEquipement,Integer> equipementReserveCeCreneau = new HashMap<>();
        for(TypeEquipement te : TypeEquipement.values()){
            int total = 0;
            for(Reservation res : reservations){
                if(res.getSalle()==null)
                    continue;
                if(res.getReunion().getCreneau() == creneau){
                    total += res.getEquipementReserve().get(te);
                }
            }
            equipementReserveCeCreneau.put(te,total);
        }
        switch(r.getType()){
            case VC:
                if (s.getEquipements().get(Ecran) == 0 && equipementReserveCeCreneau.get(Ecran)>=equipementLibres.get(Ecran))
                    result = false;
                if (s.getEquipements().get(Pieuvre) == 0 && equipementReserveCeCreneau.get(Pieuvre)>=equipementLibres.get(Pieuvre))
                    result = false;
                if (s.getEquipements().get(Webcam) == 0 && equipementReserveCeCreneau.get(Webcam)>=equipementLibres.get(Webcam))
                    result = false;
                break;
            case SPEC:
                if (s.getEquipements().get(Tableau) == 0 && equipementReserveCeCreneau.get(Tableau)>=equipementLibres.get(Tableau))
                    result = false;
                break;
            case RS:
                break;
            case RC:
                if (s.getEquipements().get(Ecran) == 0 && equipementReserveCeCreneau.get(Ecran)>=equipementLibres.get(Ecran))
                    result = false;
                if (s.getEquipements().get(Pieuvre) == 0 && equipementReserveCeCreneau.get(Pieuvre)>=equipementLibres.get(Pieuvre))
                    result = false;
                if (s.getEquipements().get(Tableau) == 0 && equipementReserveCeCreneau.get(Tableau)>=equipementLibres.get(Tableau))
                    result = false;
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
