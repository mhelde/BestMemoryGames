package memory.bestmemorygames.loto;


import java.util.ArrayList;

public class Model {
    private static int NB_VIES_MAX = 3;
    private int nbVies;
    private int score;
    private int nombreActu; //Le nombre actuellement joué
    private int nbSecondesVerif; //Utilisé en début de partie
    private Grille grille;

    private ArrayList<String> statistiques; //Pour le debug: contiendra tous les nombres passé en tant que nombreActu avec B pour "Bonne Réaction" et M pour "Mauvaise Réaction"

    private boolean inAction;

    public Model() {
        setViesAtMax();
        score = 0;
        nombreActu = 0;
        nouvelleGrille();
        statistiques = new ArrayList<String>();
    }

    public Case nombreActuDansGrille() {
        Case caseActu = null;
        for(int x = 0; x < Grille.getNbLignes(); x++) {
            for (int y = 0; y < Grille.getNbColonnes(); y++) {
                caseActu = grille.getCases()[x][y];
                if(caseActu.getValeur() == nombreActu)
                    return caseActu;
            }
        }
        return null;
    }

    public void nombreActuNonDispo() { //rend le nombre actuel plus disponible jusqu'à la fin de la partie
        grille.setDisponibiliteValeur(nombreActu, false);
    }

    public boolean finDePartie() {
        if(nbVies <= 0) {
            setInAction(true);
            printAndClearStatistique();
            return true;
        }
        return false;
    }

    public void addNombreActuToStatistiques(String resultat) {
        if(!resultat.equals("AB") && !resultat.equals("AM") && !resultat.equals("RB") && !resultat.equals("RM")) {
            System.out.println("Seulement B ou M (avec A ou R derrière) autorisé dans addNombreActuToStatistiques");
            return;
        }
        statistiques.add(nombreActu + resultat);
    }

    public void printAndClearStatistique() { //Fait apparaître toutes les statistiques et vide la liste
        int i = 1;
        for(String s: statistiques) {
            System.out.print("Résultat n°" + i + ": " + s + " | ");
            if(i%5 == 0) //Tous les 5 résultats il y a un saut de ligne
                System.out.println("");
            i++;
        }
        System.out.println("");
        statistiques.clear();
    }

    public void augmenteScore(int scoreEnplus) { //Cette méthode à totalement été changé pour que ce soit la vue qui puisse s'inpirer des nouvelles lignes/colonnes
        if(scoreEnplus < 0) {
            System.out.println("Le scoreEnPlus ne peut pas être négatif");
        }
        score += scoreEnplus;
    }

    public void nouvelleGrille() { //Génère une nouvelle Grille aléatoire et prépare le début de la manche suivante
        nbSecondesVerif = 15;
        grille = new Grille();
        grille.print();
        grille.initToTrueValeursDispo(); //Cela permet de reprendre les valeurs pour
        inAction = true; //VueLoto qu'on commence par montrer les nombres, l'utilisateur ne doit rien faire
    }

    public static int getNbViesMax() {
        return NB_VIES_MAX;
    }

    public int getNbVies() {
        return nbVies;
    }

    public int getScore() {
        return score;
    }

    public int getNombreActu() {
        return nombreActu;
    }

    public Grille getGrille() {
        return grille;
    }

    public void setViesAtMax() {
        nbVies = NB_VIES_MAX;
    }

    public void perdVie() {
        nbVies--;
    }

    public boolean isInAction() {
        return inAction;
    }

    public void setInAction(boolean inAction) {
        this.inAction = inAction;
    }

    public void setNombreActu() {
        nombreActu = grille.valeurDispoAleatoire(true);
    }

    public int getNbSecondesVerif() {
        return nbSecondesVerif;
    }

    public void reduitNbSecondesVerif() {
        nbSecondesVerif--;
    }
}
