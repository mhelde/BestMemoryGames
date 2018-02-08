package memory.bestmemorygames.piano;

public class Touche { //représente une touche de piano
    private int idSon; //Les objets de types Son sont valable que pour la Vue
    private Couleur couleur; //La vue se référera à cette valeur pour colorer la touche
    private boolean estActif; //Montre si c'est la touche sur laquelle on appuie

    public Touche(int idSon, Couleur couleur) {
        this.idSon = idSon;
        this.couleur = couleur;
        estActif = false;
    }

    public int getIdSon() {
        return idSon;
    }

    public void setIdSon(int idSon) {
        this.idSon = idSon;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    public boolean isEstActif() {
        return estActif;
    }

    public void setEstActif(boolean estActif) {
        this.estActif = estActif;
    }

}
