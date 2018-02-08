package memory.bestmemorygames.loto;


public class Case {
    private int valeur; // = -1 si elle n'est pas active
    private boolean active; //Faux si elle possède une valeur négatif et qu'elle n'a pas été "réclamé" correctement pour l'instant, Vrai sinon

    public Case(int valeur) {
        this.valeur = valeur;
        if (isValeurNegative())
            active = false;
        else
            active = true;
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
}
