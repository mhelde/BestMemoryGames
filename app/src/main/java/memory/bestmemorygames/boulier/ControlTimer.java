package memory.bestmemorygames.boulier;


import android.os.Handler;

public class ControlTimer implements Runnable {

    Model m;
    Vue v;
    Handler actualHandler; //Permet de savoir quel Handler à enclencher ce Runnable
    long nbMillisecondes; //Permet de savoir tous les combiens de temps il faut exécuter ce "Timer" de manière répété


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

    public void run() { //Pour rendre l'application plus protéger, il aurait été souhaitable que run soit private mais c'est impossible car elle est implémenter
        if (actualHandler == v.handlerDebut) {
            reduitTimer();
            if (m.getNbSecondesVerif() > 0) {
                prerun(); //A la différence du Timer de Java, il faut dire quand est-ce qu'on veut continuer la boucle et pas quand on veut l'arrêter
            } else {
                v.deColorHaut();
                m.setInAction(false);
                v.visibiliteTentative();
            }
        } else if (actualHandler == v.handlerFinEssaie) {
            v.visibiliteTentative();

            //Remet les boules du bas fausses à vide
            m.resetInactivesBas();
            v.colorBas();

            v.cacheResultats();
            m.setInAction(false);
            v.deColorHaut();
            //Pas de 'v.handlerFinEssaie.stop' car je rappelle qu'il faut juste prévenir quand on veut reboucler
        }
    }

    private void reduitTimer() {
        m.reduitNbSecondesVerif();
        v.changeRegardezSequence();
    }
}