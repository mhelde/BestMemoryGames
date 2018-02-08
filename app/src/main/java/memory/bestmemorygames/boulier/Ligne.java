package memory.bestmemorygames.boulier;

import java.util.ArrayList;
import java.util.List;

public class Ligne {
    private static int NB_BOULES = 10;
    private Boule[] boules;

    public Ligne(boolean isColored) { //Constructeur qui initialise une Ligne active colorée aléatoirement ou pas colorée
        boules = new Boule[NB_BOULES];

        if(isColored)
            initBoulesToColor();
        else
            initBoulesToVide();
    }

    public void initBoulesToColor() {
        Couleur[] couleursDispo = {Couleur.BLEU, Couleur.JAUNE, Couleur.ROUGE, Couleur.VERT, Couleur.VIOLET};
        int numeroCouleur = 0;
        for(int i = 0; i < NB_BOULES; i++) {
            numeroCouleur  = (int) Math.round(Math.random() * ((couleursDispo.length - 1) - 0) + 0);
            boules[i] = new Boule(couleursDispo[numeroCouleur]); //Affecte à la boule une couleur choisie aléatoirement
        }
    }

    public void initBoulesToVide() {
        for(int i = 0; i < NB_BOULES; i++) {
            boules[i] = new Boule(Couleur.VIDE);
        }
    }

    public List<Integer> compareLignes (Ligne l) { //Renvoie la liste des indices de boules qui ne sont pas identiques et rend non actives les boules corrects
        if (boules.length != NB_BOULES || l.boules.length != NB_BOULES) {
            return null;
        }

        List<Integer> indicesFaux = new ArrayList<Integer>();

        for(int i = 0; i < NB_BOULES; i++) {
            if(!boules[i].couleurIdentique(l.boules[i])) {
                indicesFaux.add(i);
            } else
                boules[i].setActive(false);
        }
        return indicesFaux;
    }

    public boolean isLigneColored() { //Renvoie faux si une seule boule n'est pas colorée, vrai sinon
        for(int i = 0; i < NB_BOULES; i++) {
            if(boules[i].getCouleur() == null || boules[i].getCouleur() == Couleur.VIDE)
                return false;
        }
        return true;
    }

    public static int getNbBoules() {
        return NB_BOULES;
    }

    public static void setNbBoules(int nbBoules) {
        NB_BOULES = nbBoules;
    }

    public Boule[] getBoules() {
        return boules;
    }

    public void setBoules(Boule[] boules) {
        this.boules = boules;
    }

    public void print() { //Montre la couleur de toute les boules de la Ligne
        System.out.print(" | ");
        for(int i  = 0; i < NB_BOULES; i++) {
            System.out.print( boules[i].getCouleur() + " | ");
        }
        System.out.println("");
    }

    public String toString() {
        String infos = " | ";
        for(int i  = 0; i < NB_BOULES; i++) {
            infos += ( boules[i].getCouleur() + " | ");
        }
        return infos;
    }
}
