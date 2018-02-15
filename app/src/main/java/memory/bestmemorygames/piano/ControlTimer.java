package memory.bestmemorygames.piano;


import android.os.Handler;
import android.util.Log;

import memory.bestmemorygames.R;


public class ControlTimer implements Runnable {

    Model m;
    Vue v;
    Handler actualHandler; //Permet de savoir quel Handler à enclencher ce Runnable
    long nbMillisecondes; //Permet de savoir tous les combiens de temps il faut exécuter ce "Timer" de manière répété

    private static final String TAG = "ControlTimer";


    public ControlTimer(Model m, Vue v) {
        this.m = m;
        this.v = v;
        actualHandler = null;
        nbMillisecondes = 0;
    }

    public void start(Handler handler, long nbMillisecondes) {
        actualHandler = handler;
        this.nbMillisecondes = nbMillisecondes;
        prerun();
    }

    protected void prerun() {
        actualHandler.postDelayed(this, nbMillisecondes);
    }

    public void run() {
        if(actualHandler == v.handlerJoueTouche) {
            if (m.getPlaceSequence() == 0) {
                nbMillisecondes = v.tempsDurantSequence; //Ceci permet de faire correctement la transition entre le premier bouton qui s'active rapidement, et les autres appuie plus long à s'enclencher
                m.avanceSequence();
                prerun();
            } else if (m.getPlaceSequence() <= m.getTailleSequence()) { //On appuie sur un bouton si la séquence qu'on est en train de jouer n'est pas assez longue pour le model
                int position = 0;
                //Ces variables décideront sur quelle bouton appuyer ensuite

                Touche nextTouche = m.getSequenceOrdi()[m.getPlaceSequence() - 1];
                if (nextTouche != null) {
                    for (int x = 0; x < v.piano.length; x++) {
                        if (m.getTouches()[x] == nextTouche) {
                            position = x;
                        }
                    }
                } else { //Création d'une nouvelle touche dans la séquence
                    position = (int) Math.round(Math.random() * ((m.getNB_TOUCHES() - 1) - 0) + 0); //On prend une case au hasard entre [0] ou [4]
                    m.addSequenceOrdi(m.getPlaceSequence() - 1, m.getTouches()[position]); //Important de mettre une touche DEJA présente dans les touches du model
                }

                v.lanceAnimationBouton(position, R.drawable.button_background_tourordi); //Remplace le DoClick du prototype java

                Log.d(TAG, "Touche " + m.getPlaceSequence() + "/" + m.getTailleSequence() + ": position: " + (position + 1));
                m.avanceSequence();
                prerun();
            }  else {
                Log.d(TAG, " "); //Permet de mieux voir si la séquence précédent est bien rejouer
                m.setInAction(false);
                m.setTourJoueur(true);
                v.etatAVous();
                m.reinitPlaceSequence();
            }

        } else if(actualHandler == v.handlerClignotement || actualHandler == v.handlerErreur) {

            v.restoreTouches();

            if(actualHandler == v.handlerClignotement) {
                if (!m.isTourJoueur() && m.isInParty()) {
                    actualHandler = v.handlerJoueTouche;
                } else {
                    m.setInAction(false); //Cela empêche le joueur d'avoir la main avant que l'ordi commence à jouer
                }
            } else if (actualHandler == v.handlerErreur) {
                v.creerDialogPerdu();
                m.reinitPiano();
                m.setTourJoueur(false);
                v.finPartie();
            }
        } else if (actualHandler == v.handlerCorrect) {
            m.succesReproductionSequence();
            v.changeScore();
            v.etatEnCours();
            v.correct.jouer();
            v.ctJoueTouche.start(v.handlerJoueTouche, v.tempsEntreSequences);
        }
    }
}