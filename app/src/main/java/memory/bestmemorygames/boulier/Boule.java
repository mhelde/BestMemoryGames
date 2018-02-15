package memory.bestmemorygames.boulier;

public class Boule {

    private Couleur couleur;
    private boolean active; //Indique si la boule doit être comparée avec une autre ou pas

    public Boule(Couleur couleur) {
        this.couleur = couleur;
        active = true;
    }

    public boolean couleurIdentique(Boule b) {
        return couleur == b.couleur;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void changeCouleur() { //Va changer la couleur selon le cycle suivant: bleu, jaune, rouge, vert, violet, bleu etc...
        switch (couleur) {
            case BLEU:
                couleur = Couleur.JAUNE;
                break;
            case JAUNE:
                couleur = Couleur.ROUGE;
                break;
            case ROUGE:
                couleur = Couleur.VERT;
                break;
            case VERT:
                couleur = Couleur.VIOLET;
                break;
            default: //Dans le cas ou la couleur est violet ou vide
                couleur = Couleur.BLEU;
        }
    }

    public void changeCouleurToVide() {
        couleur = Couleur.VIDE;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
