package memory.bestmemorygames.loto;


import android.os.Handler;
import android.util.Log;

import memory.bestmemorygames.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ControlTimer implements Runnable {

    Model m;
    VueLoto v;
    Handler actualHandler; //Permet de savoir quel Handler à enclencher ce Runnable
    long nbMillisecondes; //Permet de savoir tous les combiens de temps il faut exécuter ce "Timer" de manière répété

    private static String TAG = "ControlTimer";


    public ControlTimer(Model m, VueLoto v) {
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

    private void prerun() {
        actualHandler.postDelayed(this, nbMillisecondes);
    }

    public void run() {
        if(actualHandler == v.handlerDebut) {
            reduitTimer();
            if(m.getNbSecondesVerif() <= 0) {
                v.regardezGrille.setVisibility(GONE);
                v.reclame.setVisibility(VISIBLE);
                v.nombre.setVisibility(VISIBLE);
                m.setInAction(false);
                start(v.handlerReact, 5000);
            } else {
                prerun();
            }
        } else if(actualHandler == v.handlerReact) {
            if(m.isInAction()) {
                return;
            }

            Log.d(TAG, "Aucune réaction");

            Case caseCorrespond = m.nombreActuDansGrille();
            if(caseCorrespond == null) {
                Log.d(TAG, "Bonne réponse !");
                m.addNombreActuToStatistiques("AB");
                m.nombreActuNonDispo();
            } else {
                Log.d(TAG, "Mauvaise réponse !");
                m.addNombreActuToStatistiques("AM");
                v.erreurEnEvidence(caseCorrespond);
                v.perdVieAffichage();
            }
            if(!m.isInAction()) { //Si la partie s'est finit par la perte d'une vie, il ne faut pas que le jeu redémarre le timerReact
                v.restartTimerReact();
                v.changeNombre();
            }
        } else if (actualHandler == v.evidenceErreur) {
            for(int x = 0; x < Grille.getNbLignes(); x++) {
                for (int y = 0; y < Grille.getNbColonnes(); y++) {
                    if(m.getGrille().getCases()[x][y].isEstErreur()) {
                        v.grille[x][y].setBackgroundResource(R.drawable.button_background_blanc);
                        m.getGrille().getCases()[x][y].setEstErreur(false);
                    }
                }
            }
        } else if (actualHandler == v.handlerScore) {
            v.montrePointsEnPlus.setText(""); //Supprime le texte
        } else if (actualHandler == v.handlerClignotementGrille) {
            v.colorisationAleatoire();
            m.incrementenbClignotementGrille();
            if(m.getNbClignotementsGrille() >= 20) {
                v.montreGrilleComplete.setText("");
                v.colorisationVertEntier();
                m.nouvelleGrille();
                v.creerDialogGrilleComplete();
            } else {
                prerun();
            }
        } else if(actualHandler == v.handlerColorLigne) {
            v.grille[v.ligneAColorer][m.getPhaseColorisationLigneActu()].setBackgroundResource(R.drawable.button_background_correct);
            m.getGrille().getCases()[v.ligneAColorer][m.getPhaseColorisationLigneActu()].setEstCorrect(true);
            m.incrementePhaseColorisationLigneActu();
            if(m.getPhaseColorisationLigneActu() < Grille.getNbColonnes())
                prerun();
            else {
                m.resetPhaseColorisationLigneActu();
                if (Grille.getNbColonnes() >= Grille.getNbLignes()) { //Est en théorie toujours vrai dans le cadre de cette application
                    m.setInAction(false);
                    v.verifEtEnclencheFinDeGrille(); //Sachant qu'une grille se finit toujours par une ligne/colonne complète et que les lignes sont plus longues que les colonnes (donc théoriquement, leur colorisation est plus longue) on vérifie la fin de la grille après une colorisation de ligne
                } else {
                    if (m.getPhaseColorisationColonneActu() <= 0) {
                        v.changeNombre();
                        v.restartTimerReact();
                        m.setInAction(false);
                    }
                }
            }
        } else if(actualHandler == v.handlerColorColonne) {
            v.grille[m.getPhaseColorisationColonneActu()][v.colonneAColorer].setBackgroundResource(R.drawable.button_background_correct);
            m.getGrille().getCases()[m.getPhaseColorisationColonneActu()][v.colonneAColorer].setEstCorrect(true);
            m.incrementePhaseColorisationColoneActu();
            if(m.getPhaseColorisationColonneActu() < Grille.getNbLignes())
                prerun();
            else {
                m.resetPhaseColorisationColoneActu();
                if(Grille.getNbColonnes() >= Grille.getNbLignes()) { //Est en théorie toujours vrai dans le cadre de cette application
                    if (m.getPhaseColorisationLigneActu() <= 0) {
                        v.changeNombre();
                        v.restartTimerReact();
                        m.setInAction(false);
                    }
                } else {
                    m.setInAction(false);
                    v.verifEtEnclencheFinDeGrille();
                }
            }
        }
    }

    private void reduitTimer() {
        m.reduitNbSecondesVerif();
        v.changeRegardezGrille();
    }

}