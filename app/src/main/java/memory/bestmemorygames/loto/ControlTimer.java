package memory.bestmemorygames.loto;


import android.os.Handler;

public class ControlTimer implements Runnable {

    memory.bestmemorygames.loto.Model m;
    VueLoto v;
    Handler actualHandler; //Permet de savoir quel Handler à enclencher ce Runnable
    long nbMillisecondes; //Permet de savoir tous les combiens de temps il faut exécuter ce "Timer" de manière répété


    public ControlTimer(memory.bestmemorygames.loto.Model m, VueLoto v) {
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

    }


    private void reduitTimer() {

    }

}