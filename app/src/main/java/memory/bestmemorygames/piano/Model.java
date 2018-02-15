package memory.bestmemorygames.piano;


import memory.bestmemorygames.R;

public class Model {
    //Rappel: dans le Model: TOUT en private
    private final int NB_TOUCHES = 13; //A cause de l'esthétique d'un piano, il ne sera pas possible de faire varier le nom de touche

    private int score;
    private int nbTouchesReussies; //Indique le nombre de touches réussites à la séquence raté
    private Touche[] touches; //Il s'agit des touches présentent sur l'écran

    private Touche[] sequenceOrdi; //Il s'agit de la séquence de touches créer par l'ordi

    private boolean inAction; //Indique si l'application est en train de faire quelque chose que l'utilisateur ne doit pas interrompre
    private boolean tourJoueur; //Indique si le joueur doit essayer de reproduire la séquence ou pas
    private boolean inParty; //Indique si le joueur est dans une partie ou si il s'amuse simplement avec les touches

    private int tailleSequence;
    private int placeSequence; //Ce sera l'endroit où on est dans la séquence (que cela soit pour l'ordi qui joue une séquence ou pour l'utilisateur qui doit la reproduire

    public Model() {
        score = 0;
        nbTouchesReussies = 0;
        tailleSequence = 1;
        placeSequence = 0;
        sequenceOrdi = new Touche[tailleSequence];
        sequenceOrdi[0] = null;
        inAction = false;
        inParty = false;
        tourJoueur = false;
        initTouches();
    }

    public void initTouches() {
        touches = new Touche[NB_TOUCHES];

        touches[0] = new Touche(R.raw.son1, Couleur.BLANC);
        touches[1] = new Touche(R.raw.son2, Couleur.NOIR);
        touches[2] = new Touche(R.raw.son3, Couleur.BLANC);
        touches[3] = new Touche(R.raw.son4, Couleur.NOIR);
        touches[4] = new Touche(R.raw.son5, Couleur.BLANC);
        touches[5] = new Touche(R.raw.son6, Couleur.BLANC);
        touches[6] = new Touche(R.raw.son7, Couleur.NOIR);
        touches[7] = new Touche(R.raw.son8, Couleur.BLANC);
        touches[8] = new Touche(R.raw.son9, Couleur.NOIR);
        touches[9] = new Touche(R.raw.son10, Couleur.BLANC);
        touches[10] = new Touche(R.raw.son11, Couleur.NOIR);
        touches[11] = new Touche(R.raw.son12, Couleur.BLANC);
        touches[12] = new Touche(R.raw.son13, Couleur.BLANC);
    }

    private Couleur reverseCouleurIndice(Couleur couleurIndice) {
        if (couleurIndice == Couleur.NOIR)
            return Couleur.BLANC;
        return Couleur.NOIR;
    }

    public void agrandiSequence() {
        Touche[] exSequence = sequenceOrdi; //On sauvegarde l'état de la séquence avant
        sequenceOrdi = new Touche[tailleSequence];
        for(int i = 0; i < exSequence.length; i++) {
            sequenceOrdi[i] = exSequence[i]; //copie
        }
        sequenceOrdi[tailleSequence-1] = null; //La dernière touche qui n'est pas encore défini vaut null
    }

    public void succesReproductionSequence() {
        score++;
        nbTouchesReussies = 0;
        augmenteTailleSequence();
        agrandiSequence();
        reinitPlaceSequence();
    }

    public void reinitPiano() {
        reinitSequence();
        reinitPlaceSequence();
        score = 0;
        nbTouchesReussies = 0;
        inParty = false;
        inAction = false;
    }

    public boolean verifToucheJoueur(Touche touche) { //Vérifie si la touche correspond à celle attendu actuellement dans la séquence
        return touche == sequenceOrdi[placeSequence];
    }

    public int getScore() {
        return score;
    }

    public int getNbTouchesReussies() {
        return nbTouchesReussies;
    }

    public Touche[] getSequenceOrdi() {
        return sequenceOrdi;
    }

    public void setSequenceOrdi(Touche[] sequenceOrdi) {
        this.sequenceOrdi = sequenceOrdi;
    }

    public void addSequenceOrdi(int place, Touche touche) {
        sequenceOrdi[place] = touche;
    }

    public int getNB_TOUCHES() {
        return NB_TOUCHES;
    }

    public Touche[] getTouches() {
        return touches;
    }

    public void setTouches(Touche[] touches) {
        this.touches = touches;
    }

    public int getTailleSequence() {
        return tailleSequence;
    }

    public void augmenteTailleSequence() {
        this.tailleSequence += 1;
    }

    public int getPlaceSequence() {
        return placeSequence;
    }

    public void avanceSequence() {
        placeSequence += 1;
    }

    public void augmenteNbTouchesReussies() {
        nbTouchesReussies ++;
    }

    public void reinitPlaceSequence() {
        placeSequence = 0;
    }

    public void reinitSequence() {
        this.tailleSequence = 1;
        sequenceOrdi = new Touche[tailleSequence];
        sequenceOrdi[0] = null;
    }

    public boolean isInAction() {
        return inAction;
    }

    public void setInAction(boolean inAction) {
        this.inAction = inAction;
    }

    public boolean isTourJoueur() {
        return tourJoueur;
    }

    public void setTourJoueur(boolean tourJoueur) {
        this.tourJoueur = tourJoueur;
    }

    public boolean isInParty() {
        return inParty;
    }

    public void setInParty(boolean inParty) {
        this.inParty = inParty;
    }
}
