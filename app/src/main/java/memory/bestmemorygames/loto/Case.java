package memory.bestmemorygames.loto;


public class Case {
    private int valeur; // = -1 si elle n'est pas active
    private boolean active; //Faux si elle possède une valeur négatif et qu'elle n'a pas été "réclamé" correctement pour l'instant, Vrai sinon
    private boolean estCorrect; //Permet de savoir si le bouton représentant la case dans la Vue doit être vert ou pas
    private boolean estErreur; //Permet de savoir si le bouton représentant la case dans la Vue doit être rouge ou pas

    public Case(int valeur) {
        this.valeur = valeur;
        if (isValeurNegative())
            active = false;
        else
            active = true;
        estCorrect = false;
        estErreur = false;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getValeur() {
        return valeur;
    }

    public boolean isValeurNegative() {
        return valeur < 0;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isEstCorrect() {
        return estCorrect;
    }

    public void setEstCorrect(boolean estCorrect) {
        this.estCorrect = estCorrect;
    }

    public boolean isEstErreur() {
        return estErreur;
    }

    public void setEstErreur(boolean estErreur) {
        this.estErreur = estErreur;
    }
}