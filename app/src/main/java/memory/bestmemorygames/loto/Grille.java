package memory.bestmemorygames.loto;


public class Grille {

    private static final int NB_CASES_ACTIVES = 15;
    private static int NB_LIGNES = 4;
    private static int NB_COLONNES = 7;
    private Case[][] cases;
    private boolean[] valeursDispo; //Les cases auront une valeur entre 1 et 50, ainsi valeursDispo[0] indique si la valeur 1 peut être prise par une case
    //ATTENTION ! en effet valeursDispo[0] est bien pour 1, ainsi valeursDispo[i] représente i+1


    private boolean lignesCompletes[];
    private boolean colonnesCompletes[];
    //La case [0] du tableau est vrai si toute les cases de la colonne/ligne n°1 ne sont plus actives, faux sinon;
    //Qui plus est cette méthode permet de compter une colonne et une ligne complète en même temps

    public Grille() {
        cases = new Case[NB_LIGNES][NB_COLONNES];
        valeursDispo = new boolean[50];
        lignesCompletes = new boolean[NB_LIGNES];
        colonnesCompletes = new boolean[NB_COLONNES];
        initToNullCases();
        initToTrueValeursDispo();
        initToFalseLignesColonnesCompletes();
        createCases();
    }

    private void initToNullCases() {
        for(int x = 0; x < NB_LIGNES; x++) {
            for(int y = 0; y < NB_COLONNES; y++) {
                cases[x][y] = null;
            }
        }
    }

    public void initToTrueValeursDispo() {
        for(int i = 0; i < valeursDispo.length; i++) {
            valeursDispo[i] = true;
        }
    }

    public void initToFalseLignesColonnesCompletes() {
        for(int i = 0; i < NB_LIGNES; i++) {
            lignesCompletes[i] = false;
        }

        for(int i = 0; i < NB_COLONNES; i++) {
            colonnesCompletes[i] = false;
        }
    }

    private void createCases() {
        int nbCasesRemplis = 0;
        int prochaineLigne = 0;
        int prochaineColonne = 0;
        int prochaineValeur = -1;

        for(int x = 0; x < NB_LIGNES; x++) { //Va placer une case sur chaque ligne (vérification inutile car chaque ligne est censé être totalement vide)
            prochaineLigne = x;
            prochaineColonne = (int) Math.round(Math.random() * ((NB_COLONNES - 1) - 0) + 0);
            prochaineValeur = valeurDispoAleatoire(false);
            createOneCase(prochaineLigne, prochaineColonne, prochaineValeur);
            nbCasesRemplis++;
        }

        for(int y = 0; y < NB_COLONNES; y++) { //Va placer une case sur chaque colonne (avec vérification que la case n'est pas déjà initialisé par le for précédent)
            prochaineLigne = (int) Math.round(Math.random() * ((NB_LIGNES - 1) - 0) + 0);
            prochaineColonne = y;
            if(cases[prochaineLigne][prochaineColonne] == null) { //Si la case n'a pas été initialisé
                prochaineValeur = valeurDispoAleatoire(false);
                createOneCase(prochaineLigne, prochaineColonne, prochaineValeur);
                nbCasesRemplis++;
            }
        }

        while(nbCasesRemplis < NB_CASES_ACTIVES) { //Les prochaines cases à initialisé le seront aléatoirement (avec vérification)
            prochaineLigne = (int) Math.round(Math.random() * ((NB_LIGNES - 1) - 0) + 0);
            prochaineColonne = (int) Math.round(Math.random() * ((NB_COLONNES - 1) - 0) + 0);
            if(cases[prochaineLigne][prochaineColonne] == null) { //Si la case n'a pas été initialisé
                prochaineValeur = valeurDispoAleatoire(false);
                createOneCase(prochaineLigne, prochaineColonne, prochaineValeur);
                nbCasesRemplis++;
            }
        }

        for(int x = 0; x < NB_LIGNES; x++) { //Enfin, toute les cases non initialisés seront les cases grises
            for(int y = 0; y < NB_COLONNES; y++) {
                if(cases[x][y] == null)
                    createOneCase(x, y, -1);
            }
        }
    }

    public int valeurDispoAleatoire(boolean valeurEncoreDispoApres) {
        int valeur = -1;
        boolean valeurValable = false;
        while(!valeurValable) { //Tant qu'on a pas trouvé une bonne valeur
            valeur = (int) Math.round(Math.random() * ((valeursDispo.length - 1) - 0) + 0);
            valeurValable = valeursDispo[valeur];
        }
        valeursDispo[valeur] = valeurEncoreDispoApres;
        return valeur+1; //valeur se trouvant entre 0 et 49 et on veut qu'il envoie entre 1 et 50
    }

    /*Les méthodes suivante permettent de voir si une ligne et/ou nouvelle colonne est nouvellement complétée
     (elles marchent car chaque case fait parti d'une seule ligne et une seule colonne, donc pas besoin de vérifier
     toute les cases une fois qu'on a trouvé une ligne et une colonne complète)*/
    public int nouvelleColonneComplete() {
        for(int i = 0; i < NB_COLONNES; i++) {
            if(!colonnesCompletes[i] && isColonneComplete(i)) { //Vérifie si la colonne i n'a pas été marqué comme complète avant alors qu'elle l'est
                colonnesCompletes[i] = true;
                return i;
            }
        }
        return -1;
    }

    private boolean isColonneComplete(int numeroColonne) { //Verifie si toute les cases [x][numeroColonne] ne sont pas actives
        for(int x = 0; x < NB_LIGNES; x++) {
            if(cases[x][numeroColonne].isActive())
                return false;
        }
        return true;
    }

    public int nouvelleLigneComplete() {
        for(int i = 0; i < NB_LIGNES; i++) {
            if(!lignesCompletes[i] && isLigneComplete(i)) { //Vérifie si la ligne i n'a pas été marqué comme complète avant alors qu'elle l'est
                lignesCompletes[i] = true;
                return i;
            }
        }
        return -1;
    }

    private boolean isLigneComplete(int numeroLigne) { //Verifie si toute les cases [numeroLigne][y] ne sont pas actives
        for(int y = 0; y < NB_COLONNES; y++) {
            if(cases[numeroLigne][y].isActive())
                return false;
        }
        return true;
    }

    public boolean isGrilleComplete() { //Pourquoi vérifier si chaque Case n'est pas active (7 X 4 = 28 valeurs à vérifier) alors qu'on peut juste vérifier si tous les booléens lignes/colonnes complètes sont à true (7 + 4 = 11 valeurs à vérifier)
        for(int x = 0; x < NB_LIGNES; x++) {
            if(!lignesCompletes[x])
                return false;
        }

        for(int y = 0; y < NB_COLONNES; y++) {
            if(!colonnesCompletes[y])
                return false;
        }
        return true;
    }

    private void createOneCase(int ligne, int colonne, int valeur) {
        cases[ligne][colonne] = new Case(valeur);
    }

    public Case[][] getCases() {
        return cases;
    }

    public void setDisponibiliteValeur(int valeur, boolean etat) {
        valeursDispo[valeur-1] = etat;
    }

    public static int getNbLignes() {
        return NB_LIGNES;
    }

    public static int getNbColonnes() {
        return NB_COLONNES;
    }

    //Méthode renvoyant des booléens pour savoir si ligne/colonne/grille sont remplis

    public void print() {
        Case caseActu = null;
        for(int x = 0; x < NB_LIGNES; x++) {
            for (int y = 0; y < NB_COLONNES; y++) {
                caseActu = cases[x][y];
                System.out.print("|");
                if(caseActu != null) {
                    if(caseActu.isValeurNegative()) //Case grise
                        System.out.print("  ");
                    else {                       //Case normal
                        if(caseActu.getValeur() < 10) //Espace suplémentaire pour Case avec 1 chiffre
                            System.out.print(" ");
                        System.out.print(caseActu.getValeur());
                    }
                } else                          //Case invalide (problème à corriger si cela arrive)
                    System.out.print("null");
            }
            System.out.println("|");
        }
    }
}
