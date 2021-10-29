package model.data;

public class Reunion implements Comparable {

    public enum TypeReunion{
        VC,
        SPEC,
        RS,
        RC
    }

    private int idReunion;
    private TypeReunion type;
    private int nbPersonnes;
    private int creneau;

    public Reunion(int i,int c, TypeReunion t, int n ){
        idReunion = i;
        type = t;
        nbPersonnes = n;
        creneau = c;
    }

    public int getIdReunion() {
        return idReunion;
    }

    public void setIdReunion(int idReunion) {
        this.idReunion = idReunion;
    }

    public TypeReunion getType() {
        return type;
    }

    public void setType(TypeReunion type) {
        this.type = type;
    }

    public int getNbPersonnes() {
        return nbPersonnes;
    }

    public void setNbPersonnes(int nbPersonnes) {
        this.nbPersonnes = nbPersonnes;
    }

    public int getCreneau() {
        return creneau;
    }

    public void setCreneau(int creneau) {
        this.creneau = creneau;
    }

    @Override
    public int compareTo(Object o) {
        Reunion element = (Reunion) o;
        return this.idReunion-element.idReunion;
    }

    @Override
    public String toString(){
        return "[REUNION n°" + idReunion +" ; " + creneau + "h ; " + nbPersonnes + " personnes ; Type : " + type + "]";
    }
}
