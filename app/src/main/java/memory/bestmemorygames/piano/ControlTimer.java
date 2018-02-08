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

                //m.setInAction(false);
                v.lanceAnimationBouton(position); //Remplace le DoClick du prototype java
                //m.setInAction(true);

                Log.d(TAG, "Touche " + m.getPlaceSequence() + "/" + m.getTailleSequence() + ": position: " + (position + 1));
                m.avanceSequence();
                prerun();
            }  else {
                Log.d(TAG, " "); //Permet de mieux voir si la séquence précédent est bien rejouer
                m.setInAction(false);
                m.setTourJoueur(true);
                v.etatAVous();
                m.reinitPlaceSequence();
                //joueToucheDelaiDebut();
            }

        } else if(actualHandler == v.handlerClignotement) {
            for(int i = 0; i < v.piano.length; i++) {
                if(m.getTouches()[i].isEstActif()) {
                    if(m.getTouches()[i].getCouleur() == Couleur.BLANC) {
                        v.piano[i].setBackgroundResource(R.drawable.button_background_blanc);
                    } else if(m.getTouches()[i].getCouleur() == Couleur.NOIR) {
                        v.piano[i].setBackgroundResource(R.drawable.button_background_noir);
                    }
                    m.getTouches()[i].setEstActif(false);

                    if(!m.isTourJoueur() && m.isInParty()) {
                        actualHandler = v.handlerJoueTouche;
                    } else {
                        m.setInAction(false);
                    }

                }
            }
                /*for (int i = 0; i < v.touches.length; i++) {
                    for (int j = 0; j < v.touches.length; j++) {
                        if (m.getTouches()[i][j].isEstActif()) {
                            if(m.getTouches()[i][j].getCouleur() == Couleur.BLANC) {
                                v.touches[i][j].setBackground(Color.WHITE);
                            } else if (m.getTouches()[i][j].getCouleur() == Couleur.NOIR) {
                                v.touches[i][j].setBackground(Color.BLACK);
                            }
                            m.getTouches()[i][j].setEstActif(false);
                            if(!v.timerJoueTouche.isRunning()) //Pour ne pas laisser accidentellement la main à l'utilisateur pendant qu'une séquence est jouer
                                m.setInAction(false);
                            v.timerClignotementTouche.stop();
                        }
                    }*/
        }
    }
}
