package memory.bestmemorygames.loto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import memory.bestmemorygames.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class VueLoto extends AppCompatActivity implements View.OnClickListener {

    Model m;

    protected Handler handlerDebut;
    protected Handler handlerReact;
    protected Handler evidenceErreur; //Fait passer une case au rouge une demi-seconde si le nombreActu du model y correspondait mais qu'on a rien fait
    protected Handler handlerScore;
    protected Handler handlerClignotementGrille;

    //Les deux handlers qui suivent permettent de coloriser les lignes et le colonnes
    protected Handler handlerColorLigne;
    protected Handler handlerColorColonne;
    protected int tempsColorisation;
    protected int ligneAColorer;
    protected int colonneAColorer;


    protected TextView showScore;
    protected TextView montrePointsEnPlus; //Montre pourquoi des points sont gagnés
    protected TextView montreGrilleComplete;

    protected LinearLayout hautGrille;
    protected Button[][] grille;

    protected Button reclame;
    protected TextView nombre; //Désigne le nombre qui s'affichera en haut
    protected TextView regardezGrille;
    protected ImageView[] imageVies;

    protected LinearLayout vies;

    protected ControlTimer ct;
    protected ControlTimer ctClignotement;
    protected ControlTimer ctScore;

    protected ControlTimer ctLigne;
    protected ControlTimer ctColonne;


    protected Son erreur;
    protected Son scorePlus1;
    protected Son scoreLigneColonne;
    protected Son finGrille;

    private static String TAG = "VueLoto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loto);

        //Grille.petiteGrille(); //Permet de tester le changement de la taille de grille via une méthode

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        m = new Model();

        hautGrille = findViewById(R.id.grille);
        createGrille();

        reclame = findViewById(R.id.reclame);
        reclame.setBackgroundColor(Color.WHITE);
        reclame.setVisibility(GONE);
        reclame.setOnClickListener(this);

        nombre = findViewById(R.id.nombre);
        nombre.setVisibility(GONE);
        changeNombre();
        regardezGrille = findViewById(R.id.regardezgrille);
        changeRegardezGrille();

        vies = findViewById(R.id.vies);
        createVies();
        changeAffichageVies();

        showScore = findViewById(R.id.score);
        showScore.setText(getString(R.string.debScore) + ": " + m.getScore());

        montrePointsEnPlus = findViewById(R.id.explicationscore);
        montreGrilleComplete = findViewById(R.id.explicationsgrillecomplete);

        ct = new ControlTimer(m, this);
        ctClignotement = new ControlTimer(m, this);
        ctScore = new ControlTimer(m, this);

        ctLigne = new ControlTimer(m, this);
        ctColonne = new ControlTimer(m, this);


        handlerDebut = new Handler();
        handlerReact = new Handler();
        evidenceErreur = new Handler();
        handlerScore = new Handler();
        handlerClignotementGrille = new Handler();

        handlerColorLigne = new Handler();
        handlerColorColonne = new Handler();
        tempsColorisation = 100;
        ligneAColorer = -1;
        colonneAColorer = -1;

        erreur = new Son(R.raw.erreur2, this);
        scorePlus1 = new Son(R.raw.bip, this);
        scoreLigneColonne = new Son(R.raw.correct, this);
        finGrille = new Son(R.raw.applaudissement, this);
        ct.start(handlerDebut, 1000);
    }

    public void createGrille() {
        grille = new Button[Grille.getNbLignes()][Grille.getNbColonnes()];
        LinearLayout[] colonnes = new LinearLayout[Grille.getNbColonnes()];
        for(int y = 0; y < Grille.getNbColonnes(); y++) { //Rajoute des LinearLayout pour créer des lignes
            colonnes[y] = new LinearLayout(getApplicationContext());
            colonnes[y].setOrientation(LinearLayout.VERTICAL);
            hautGrille.addView(colonnes[y]);
            for(int x = 0; x < Grille.getNbLignes(); x++) { //Met les cases dans les LinearLayout
                grille[x][y] = new Button(getApplicationContext());
                //grille[x][y].setText(x + "-" + y); //Permet de savoir dans un premier temps si les cases ont étés créées correctement
                //grille[x][y].setBackground(getDrawable(R.drawable.caseblanche)); //Requiert l'API 21 (et une image qui se nomme caseblanche)
                grille[x][y].setBackgroundResource(R.drawable.button_background_blanc);
                colonnes[y].addView(grille[x][y]);
            }
        }
        adaptButtonsCasesToGrille();
    }

    private void adaptButtonsCasesToGrille() {
        Case caseActuelle = null;
        Button boutonActuel = null;
        for(int x = 0; x < Grille.getNbLignes(); x++) {
            for (int y = 0; y < Grille.getNbColonnes(); y++) {
                caseActuelle = m.getGrille().getCases()[x][y];
                boutonActuel = grille[x][y];
                if(!caseActuelle.isValeurNegative()) {
                    boutonActuel.setText("" + caseActuelle.getValeur());
                    boutonActuel.setEnabled(true);
                    boutonActuel.setBackgroundResource(R.drawable.button_background_blanc);
                } else {
                    boutonActuel.setText("");
                    boutonActuel.setEnabled(false);
                    boutonActuel.setBackgroundResource(R.drawable.button_background_desactive);
                }
            }
        }
    }

    public void createVies() {
        imageVies = new ImageView[Model.getNbViesMax()];
        for(int i = 0; i < Model.getNbViesMax(); i++) {
            imageVies[i] = new ImageView(getApplicationContext());
            vies.addView(imageVies[i]);
        }
    }

    public void changeAffichageVies() {
        for(int i = 0; i < m.getNbVies(); i++) { //Vies actuelles
            imageVies[i].setImageResource(R.drawable.coeur);
        }
        for(int i = m.getNbVies(); i < Model.getNbViesMax(); i++) { //Vies perdues
            imageVies[i].setImageResource(R.drawable.coeurvide);
        }
    }

    public void changeRegardezGrille() {
        regardezGrille.setText(getString(R.string.regardez) + "(" + m.getNbSecondesVerif() + ")");
    }

    public void perdVieAffichage() {
        m.perdVie();
        changeAffichageVies();
        verifEtEnclencheFinDePartie();
    }

    public void verifEtEnclencheFinDePartie() {
        if(m.finDePartie()) {
            handlerReact.removeCallbacks(ct);
            creerDialogPerdu();
        }
    }

    public void verifEtEnclencheFinDeGrille() {
        if(m.getGrille().isGrilleComplete()) {
            finGrille.jouer();
            m.setInAction(true);
            m.augmenteScore(50);
            showScore.setText(getString(R.string.debScore) + ": " + m.getScore());
            montreGrilleComplete.setText(getString(R.string.plus50));
            handlerReact.removeCallbacks(ct);
            m.printAndClearStatistique();
            ctClignotement.start(handlerClignotementGrille, 150);
        } else {
            changeNombre();
            restartTimerReact();
        }
    }

    public void restartTimerReact() {
        handlerReact.removeCallbacks(ct); //Annule tous les postDelayed de handlerReact envers ct (logiquement, il doit en avoir q'un seul)
        ct.start(handlerReact, 5000);
    }

    public void changeScore() {
        int scoreEnPlus = 1;
        Son sonAJouer = scorePlus1;
        int idTextPointsEnPlus = R.string.plus1; //Représente l'id du texte qui sera utilsé (par défaut "+1"
        ligneAColorer = m.getGrille().nouvelleLigneComplete();
        colonneAColorer = m.getGrille().nouvelleColonneComplete();

        if(ligneAColorer >= 0) {
            sonAJouer = scoreLigneColonne;
            scoreEnPlus += 5; //Points bonus pour ligne complète
            coloriseLigne(ligneAColorer);
            idTextPointsEnPlus = R.string.plus5lig;
        }

        if(colonneAColorer >= 0) {
            sonAJouer = scoreLigneColonne;
            scoreEnPlus += 5; //Points bonus pour colonne Complète
            coloriseColonne(colonneAColorer);
            if(ligneAColorer >= 0) //Si il y a une ligne et une colonne complète
                idTextPointsEnPlus = R.string.plus10;
            else //Si il y a seulement une colonne complète
                idTextPointsEnPlus = R.string.plus5col;
        }

        if(ligneAColorer <= 0 && colonneAColorer <= 0) { //Si rien n'a été complété
            changeNombre();
            restartTimerReact();
        }

        m.augmenteScore(scoreEnPlus);
        showScore.setText(getString(R.string.debScore) + ": " + m.getScore());
        sonAJouer.jouer();
        montrePointsEnPlus.setText(getString(idTextPointsEnPlus));
        handlerScore.removeCallbacks(ctScore);
        ctScore.start(handlerScore, 2000);
    }

    public void changeNombre() {
        m.setNombreActu();
        nombre.setText("" + m.getNombreActu());
    }

    public void coloriseLigne(int numeroLigne) {
        m.setInAction(true);
        ctLigne.start(handlerColorLigne, tempsColorisation);
        /*for(int y = 0; y < Grille.getNbColonnes(); y++) {
            grille[numeroLigne][y].setBackgroundResource(R.drawable.button_background_correct);
            m.getGrille().getCases()[numeroLigne][y].setEstCorrect(true);
        }*/
    }

    public void coloriseColonne(int numeroColonne) {
        m.setInAction(true);
        ctColonne.start(handlerColorColonne, tempsColorisation);
        /*for(int x = 0; x < Grille.getNbLignes(); x++) {
            grille[x][numeroColonne].setBackgroundResource(R.drawable.button_background_correct);
            m.getGrille().getCases()[x][numeroColonne].setEstCorrect(true);
        }*/
    }

    public void coloriseRouge(int x, int y) {
        grille[x][y].setBackgroundResource(R.drawable.button_background_erreur);
        m.getGrille().getCases()[x][y].setEstErreur(true);
    }

    public void colorisationAleatoire() {
        int numeroCouleurAleatoire = 0;
        int idCouleur = 0;
        for(int x = 0; x < Grille.getNbLignes(); x++) {
            for(int y = 0; y < Grille.getNbColonnes(); y++) {
                numeroCouleurAleatoire = (int) Math.round(Math.random() * ((4 - 1) - 0) + 0);
                switch (numeroCouleurAleatoire) {
                    case 0:
                        idCouleur = R.drawable.button_background_blanc;
                        break;
                    case 1:
                        idCouleur = R.drawable.button_background_bleu;
                        break;
                    case 2:
                        idCouleur = R.drawable.button_background_correct;
                        break;
                    case 3:
                        idCouleur = R.drawable.button_background_erreur;
                        break;
                    default:
                        idCouleur = R.drawable.button_background_jaune;
                        break;
                }
                grille[x][y].setBackgroundResource(idCouleur);
            }

            /*numeroCouleurAleatoire = (int) Math.round(Math.random() * ((4 - 1) - 0) + 0);
            switch (numeroCouleurAleatoire) {
                case 0:
                    idCouleur = R.color.blanc;
                    break;
                case 1:
                    idCouleur = R.color.bleu;
                    break;
                case 2:
                    idCouleur = R.color.correct;
                    break;
                case 3:
                    idCouleur = R.color.erreur;
                    break;
                default:
                    idCouleur = R.color.jaune;
                    break;
            }
            montreGrilleComplete.setTextColor(getColor(idCouleur));*/ //GetColor requiert l'API 23
        }
    }

    public void colorisationVertEntier() {
        for(int x = 0; x < Grille.getNbLignes(); x++) {
            for (int y = 0; y < Grille.getNbColonnes(); y++) {
                grille[x][y].setBackgroundResource(R.drawable.button_background_correct);
            }
        }
    }

    public void changeButtonsCases() {
        Case caseActuelle = null;
        Button boutonActuel = null;
        for(int x = 0; x < Grille.getNbLignes(); x++) {
            for (int y = 0; y < Grille.getNbColonnes(); y++) {
                caseActuelle = m.getGrille().getCases()[x][y];
                boutonActuel = grille[x][y];
                if(!caseActuelle.isActive() && !caseActuelle.isEstCorrect()) {
                    boutonActuel.setEnabled(false);
                    boutonActuel.setBackgroundResource(R.drawable.button_background_desactive);
                }
            }
        }
    }

    public void erreurEnEvidence(Case caseAClignoter) {
        erreur.jouer();

        if(caseAClignoter != null) {
            Case caseActu = null;
            for(int x = 0; x < Grille.getNbLignes(); x++) {
                for (int y = 0; y < Grille.getNbColonnes(); y++) {
                    caseActu = m.getGrille().getCases()[x][y];
                    if(caseActu == caseAClignoter) {
                        coloriseRouge(x, y);
                        ctClignotement.start(evidenceErreur, 500);
                    }
                }
            }
        }
    }

    public void creerDialogPerdu() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.perduTexte));
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.perduTitre));
        builder.setPositiveButton(getString(R.string.perduOk), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "Perdu");
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void creerDialogGrilleComplete() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.completTexte));
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.completTitre));
        builder.setPositiveButton(getString(R.string.completOk), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "GrileComplete");
                nombre.setVisibility(GONE);
                adaptButtonsCasesToGrille();
                changeRegardezGrille();
                reclame.setVisibility(GONE);
                regardezGrille.setVisibility(VISIBLE);
                ct.start(handlerDebut, 1000);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Partie contrôleur
    @Override
    public void onClick(View view) {
        if(m.isInAction()) {
            Log.d(TAG, "Ce n'est pas le moment pour ça");
            return;
        }

        if(view == reclame) {
            Log.d(TAG, "Réaction");

            Case caseCorrespond = m.nombreActuDansGrille();
            if(caseCorrespond != null) {
                handlerReact.removeCallbacks(ct); //Le handlerReact se met en pause le temps que les animations sont effectués ou pas
                Log.d(TAG, "Bonne réponse !");
                m.addNombreActuToStatistiques("RB");
                m.nombreActuNonDispo();
                caseCorrespond.setActive(false); //La case ne fait plus partie de la partie
                changeButtonsCases();
                changeScore();
            } else {
                Log.d(TAG, "Mauvaise réponse !");
                m.addNombreActuToStatistiques("RM");
                erreurEnEvidence(caseCorrespond);
                perdVieAffichage();
                if(!m.isInAction()) { //Si la partie s'est finit par la perte d'une vie, il ne faut pas que le jeu redémarre le timerReact
                    changeNombre();
                    restartTimerReact();
                }
            }
        }
    }
}
