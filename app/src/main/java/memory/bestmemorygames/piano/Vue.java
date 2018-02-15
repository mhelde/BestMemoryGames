package memory.bestmemorygames.piano;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import memory.bestmemorygames.R;


public class Vue extends AppCompatActivity implements View.OnClickListener {

    protected Button lancer;

    protected Button[] piano;
    protected Son[] sonsPiano;
    protected Son erreur;
    protected Son correct;

    protected Handler handlerClignotement; //Cet handler sert à faire clignoter les touches à différentes couleurs
    protected Handler handlerJoueTouche; //Cet handler sert à faire jouer l'ordi
    protected Handler handlerErreur; //Ressemble au handlerClignotement, à l'exception près qu'il enclenche la fin de la partie
    protected Handler handlerCorrect;
    protected ControlTimer ctClignotement;
    protected ControlTimer ctJoueTouche;

    protected Model m;

    protected TextView etatDeLaPartie;
    protected TextView showScore;

    int tempsDebut;
    int tempsDurantSequence;
    int tempsEntreSequences;

    private static final String TAG = "Vue";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        m = new Model();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_piano);

        etatDeLaPartie = findViewById(R.id.etat);
        showScore = findViewById(R.id.score);

        tempsDebut = 100;
        tempsDurantSequence = 2000;
        tempsEntreSequences = 3500;

        initButtons();
        initSons();

        erreur = new Son(R.raw.erreurpian, this);
        correct = new Son(R.raw.correctpian, this);

        handlerClignotement = new Handler();
        handlerJoueTouche = new Handler();
        handlerErreur = new Handler();
        handlerCorrect = new Handler();
        ctClignotement = new ControlTimer(m, this);
        ctJoueTouche = new ControlTimer(m, this);
    }

    public void initButtons() {
        piano = new Button[m.getNB_TOUCHES()];

        piano[0] = (Button) findViewById(R.id.c1);
        piano[1] = (Button) findViewById(R.id.c11);
        piano[2] = (Button) findViewById(R.id.d1);
        piano[3] = (Button) findViewById(R.id.d11);
        piano[4] = (Button) findViewById(R.id.e1);
        piano[5] = (Button) findViewById(R.id.f1);
        piano[6] = (Button) findViewById(R.id.f11);
        piano[7] = (Button) findViewById(R.id.g1);
        piano[8] = (Button) findViewById(R.id.g11);
        piano[9] = (Button) findViewById(R.id.a1);
        piano[10] = (Button) findViewById(R.id.a11);
        piano[11] = (Button) findViewById(R.id.b1);
        piano[12] = (Button) findViewById(R.id.c2);

        lancer = (Button) findViewById(R.id.lancer);

        for(int i = 0; i < piano.length; i++) {
            piano[i].setOnClickListener(this);
        }

        lancer.setOnClickListener(this);
    }

    public void initSons() {
        sonsPiano = new Son[m.getNB_TOUCHES()];

        for(int i = 0; i < sonsPiano.length; i++) {
            sonsPiano[i] = new Son(m.getTouches()[i].getIdSon(), this);
        }
    }

    public void etatEnCours() {
        etatDeLaPartie.setText(getString(R.string.tourMachine));
    }

    public void etatAVous() {
        etatDeLaPartie.setText(getString(R.string.tourJoueur));
    }

    public void debutPartie() {
        lancer.setVisibility(View.GONE);
        etatEnCours();
        etatDeLaPartie.setVisibility(View.VISIBLE);
        changeScore();
        showScore.setVisibility(View.VISIBLE);
    }

    public void finPartie() {
        lancer.setVisibility(View.VISIBLE);
        etatDeLaPartie.setVisibility(View.GONE);
        showScore.setVisibility(View.GONE);
    }

    public void changeScore() {
        showScore.setText(getString(R.string.debScore) + ": " + m.getScore());
    }

    public void restoreTouches() { //Restore les touches à leurs couleur initial
        for(int i = 0; i < piano.length; i++) {
            if (m.getTouches()[i].isEstActif()) {
                if (m.getTouches()[i].getCouleur() == Couleur.BLANC) {
                    piano[i].setBackgroundResource(R.drawable.button_background_blanc);
                } else if (m.getTouches()[i].getCouleur() == Couleur.NOIR) {
                    piano[i].setBackgroundResource(R.drawable.button_background_noir);
                }
                m.getTouches()[i].setEstActif(false);
            }
        }
    }

    public void creerDialogPerdu() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.perduTexte1p1) + "\n" + getString(R.string.perduTexte1p2) + " " + m.getScore() + " " + getString(R.string.perduTexte2) + " " + m.getNbTouchesReussies() + " " + getString(R.string.perduTexte3));
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.perduTitre));
        builder.setPositiveButton(getString(R.string.perduOk), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "Perdu");
                //creerDialogReset();
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
        if(view == lancer) {
            debutPartie();
            m.setInAction(true);
            m.setInParty(true);
            ctJoueTouche.start(handlerJoueTouche, tempsDebut);
            Log.d(TAG, "lancer");
        } else {
            for(int i = 0; i < piano.length; i++) {
                Button touchePiano = null;
                if(view == piano[i]) {
                    touchePiano = piano[i];
                    if(m.isTourJoueur()) {
                        Touche toucheAVerifier = m.getTouches()[i];
                        if(!m.verifToucheJoueur(toucheAVerifier)) {
                            lanceAnimationPerdue(i);
                            return;
                        } else {
                            m.avanceSequence();
                            m.augmenteNbTouchesReussies();
                            lanceAnimationBouton(i, R.drawable.button_background_correct);
                            if(m.getPlaceSequence() >= m.getTailleSequence()) {
                                m.setTourJoueur(false);
                                ctJoueTouche.start(handlerCorrect, 1000);
                            }
                        }
                    } else {
                        lanceAnimationBouton(i, R.drawable.button_background_notingame);
                    }
                }
            }
        }
    }
    public void lanceAnimationBouton(int i, int couleurClignotement) {
        piano[i].setBackgroundResource(couleurClignotement);
        sonsPiano[i].jouer();
        m.getTouches()[i].setEstActif(true);
        m.setInAction(true);
        ctClignotement.start(handlerClignotement, 500);
    }

    public void lanceAnimationPerdue(int i) {
        int indice = trouverBonneTouche();

        if(indice < 0) {
            Log.e(TAG, "Erreur: la bonne touche n'a pas été trouvé");
            return;
        }

        piano[indice].setBackgroundResource(R.drawable.button_background_correct);
        m.getTouches()[indice].setEstActif(true);
        piano[i].setBackgroundResource(R.drawable.button_background_erreur);
        m.getTouches()[i].setEstActif(true);
        m.setInAction(true);
        erreur.jouer();
        ctClignotement.start(handlerErreur, 3000);
    }

    public int trouverBonneTouche() { //Renvoie l'index de la touche sur laquelle il fallait appuyer
        for(int x = 0; x < piano.length; x++) {
            if(m.verifToucheJoueur(m.getTouches()[x]))
                return x;
        }
        return -1;
    }
}