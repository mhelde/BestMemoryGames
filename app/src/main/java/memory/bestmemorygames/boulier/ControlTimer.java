package memory.bestmemorygames.boulier;


import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import memory.bestmemorygames.R;

public class ControlTimer implements Runnable {

    private static String TAG = "ControlTimer"; //Permet de créer des messages de Debug plus comphréensible avec Log.d

    Model m;
    Vue v;
    Handler actualHandler; //Permet de savoir quel Handler à enclencher ce Runnable
    long nbMillisecondes; //Permet de savoir tous les combiens de temps il faut exécuter ce "Timer" de manière répété

    //Il s'agit de toutes les variables nécessaire à la vérification
    int placeActuVerification;
    List<Integer> indicesFaux;
    int nbBoulesJustes;
    int nbBoulesFausses;


    public ControlTimer(Model m, Vue v) {
        this.m = m;
        this.v = v;
        actualHandler = null;
        nbMillisecondes = 0;

        resetIndices();
        indicesFaux = new ArrayList<>();
    }

    public void start(Handler handler, long nbMillisecondes) {
        actualHandler = handler;
        this.nbMillisecondes = nbMillisecondes;
        prerun();
    }

    protected void prerun() {
        actualHandler.postDelayed(this, nbMillisecondes);
    }

    public void run() { //Pour rendre l'application plus protéger, il aurait été souhaitable que run soit private mais c'est impossible car elle est implémenter
        if(actualHandler == v.handlerDebut) {
            reduitTimer();
            if (m.getNbSecondesVerif() > 0) {
                prerun(); //A la différence du Timer de Java, il faut dire quand est-ce qu'on veut continuer la boucle et pas quand on veut l'arrêter
            } else {
                v.deColorHaut();
                m.setInAction(false);
                v.visibiliteTentative();
            }
        } else if(actualHandler == v.handlerFinEssaie) {
            v.visibiliteTentative();

            //Remet les boules du bas fausses à vide
            m.resetInactivesBas();
            v.colorBas();

            v.cacheResultats();
            m.setInAction(false);
            v.deColorHaut();
            //Pas de 'v.handlerFinEssaie.stop' car je rappelle qu'il faut juste prévenir quand on veut reboucler
        } else if(actualHandler == v.handlerVerification) {
            nbMillisecondes = 1000;
            v.colorHaut(placeActuVerification);
            Boule bouleActuelle = m.getBas().getBoules()[placeActuVerification];
            if(indicesFaux.contains(placeActuVerification)) {
                v.reveleUnResultat(v.resultats[placeActuVerification], R.drawable.faux);
                v.wrong.jouer();
                nbBoulesFausses++;
            } else if(bouleActuelle.isActive()) {
                v.reveleUnResultat(v.resultats[placeActuVerification], R.drawable.correct);
                bouleActuelle.setActive(false); //Etant donné qu'elle est correct, on n'en tiendra plus compte désormais
                v.correct.jouer();
                nbBoulesJustes++;
            } else {
                nbMillisecondes = 10; //Aucun résultat ne se dégage
            }

            placeActuVerification++;
            if(placeActuVerification < Ligne.getNbBoules())
                prerun(); //Relance une noucelle vérification
            else {
                v.finVerication();
            }
        }
    }

    private void reduitTimer() {
        m.reduitNbSecondesVerif();
        v.changeRegardezSequence();
    }

    public void resetIndices() {
        placeActuVerification = 0;
        nbBoulesJustes = 0;
        nbBoulesFausses = 0;
    }
}