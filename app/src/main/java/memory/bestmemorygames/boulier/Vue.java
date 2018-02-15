package memory.bestmemorygames.boulier;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import memory.bestmemorygames.PageAccueilActivity;
import memory.bestmemorygames.R;

//Fait office de vue et de contrôleur en même temps
public class Vue extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "Vue"; //Permet de créer des messages de Debug plus comphréensible avec Log.d

    protected Model m;
    protected Button reponse;

    protected ImageView[] imagesHaut;
    protected ImageView[] resultats;
    protected ImageView[] imagesBas;

    protected TextView showScore;
    protected TextView regardezSequence;
    protected TextView calculScore;
    protected TextView tentativesRestantes;

    protected LinearLayout chooseLigne;
    protected ImageView[] imagesChoose;

    protected Handler handlerDebut;
    protected Handler handlerFinEssaie;
    protected Handler handlerVerification;

    protected ControlTimer ct;

    protected Son correct;
    protected Son wrong;


    //OnClickListener clickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m = new Model();
        setContentView(R.layout.activity_boulier);

        reponse = findViewById(R.id.reponse);
        reponse.setOnClickListener(this);

        showScore = findViewById(R.id.score);
        changeScore();

        regardezSequence = findViewById(R.id.sequence);
        changeRegardezSequence();

        calculScore = findViewById(R.id.calculScore);

        tentativesRestantes = findViewById(R.id.tentativesRestantes);
        changeTentativesRestantes();

        imagesHaut = new ImageView[Ligne.getNbBoules()];
        initImageHaut();

        resultats = new ImageView[Ligne.getNbBoules()];
        initResultats();

        imagesBas = new ImageView[Ligne.getNbBoules()];
        initImageBas();

        imagesChoose = new ImageView[5]; //Il y a 5 couleurs: bleu, jaune, rouge, vert, violet
        initImagesChoose();

        chooseLigne = findViewById(R.id.choose);

        handlerDebut = new Handler();
        handlerFinEssaie = new Handler();
        handlerVerification = new Handler();

        initControlTimer();

        correct = new Son(R.raw.correct, this);
        wrong = new Son(R.raw.wrong, this);

        visibiliteDebutDePartie();
    }

    public void initImageBas() {
        LinearLayout lB = (LinearLayout) findViewById(R.id.bas);
        for(int i = 0; i < imagesBas.length; i++) {
            imagesBas[i] = new ImageView(getApplicationContext());
            initImage(imagesBas[i], lB);
        }
        //Un Listener uniquement aux boutons du bas
        for(int i = 0; i < imagesBas.length; i++) {
            imagesBas[i].setOnClickListener(this);
        }
        colorBas();
    }

    public void initResultats() {
        LinearLayout lC = (LinearLayout) findViewById(R.id.centre);
        for(int i = 0; i < resultats.length; i++) {
            resultats[i] = new ImageView(getApplicationContext());
            resultats[i].setPadding(-((Ligne.getNbBoules()*10)), 0, -(Ligne.getNbBoules()*10), 0); //Les boules sont plus ou moins espacé en fonction du nombre qu'il y a (le maximum est de pouvoir en avoir 15 au maximum)
            resultats[i].setImageResource(R.drawable.faux);
            lC.addView(resultats[i]);
        }
        cacheResultats();
    }

    public void initImageHaut() {
        LinearLayout lH = (LinearLayout) findViewById(R.id.haut);
        for(int i = 0; i < imagesHaut.length; i++) {
            imagesHaut[i] = new ImageView(getApplicationContext());
            initImage(imagesHaut[i], lH);
        }
        colorHaut();
    }

    public void initImage(ImageView image, LinearLayout layout) {
        image.setImageResource(R.drawable.vide);
        image.setPadding(-((Ligne.getNbBoules()*5)), 0, -(Ligne.getNbBoules()*5), 0); //Les boules sont plus ou moins espacé en fonction du nombre qu'il y a (le maximum est de pouvoir en avoir 15 au maximum)
        layout.addView(image);
    }

    public void initImagesChoose() {
        imagesChoose[0] = findViewById(R.id.ibleu);
        imagesChoose[1] = findViewById(R.id.ijaune);
        imagesChoose[2] = findViewById(R.id.irouge);
        imagesChoose[3] = findViewById(R.id.ivert);
        imagesChoose[4] = findViewById(R.id.iviolet);

        for(int i = 0; i < imagesChoose.length; i++) {
            imagesChoose[i].setOnClickListener(this);
        }
    }

    public void visibiliteDebutDePartie() {
        reponse.setVisibility(View.GONE);
        calculScore.setVisibility(View.GONE);
        regardezSequence.setVisibility(View.VISIBLE);
        tentativesRestantes.setVisibility(View.VISIBLE);
        chooseLigne.setVisibility(View.GONE);
    }

    public void visibiliteTentative() {
        reponse.setVisibility(View.VISIBLE);
        calculScore.setVisibility(View.GONE);
        regardezSequence.setVisibility(View.GONE);
        tentativesRestantes.setVisibility(View.GONE);
    }

    public void visibiliteVerification() {
        reponse.setVisibility(View.GONE);
        calculScore.setVisibility(View.VISIBLE);
        regardezSequence.setVisibility(View.GONE);
        tentativesRestantes.setVisibility(View.VISIBLE);
    }

    public void initControlTimer() { //Dans cette version, il lance aussi le handlerDebut
        ct = new ControlTimer(m, this);
        ct.start(handlerDebut, 1000);
    }

    /*public void setClickListener(OnClickListener listener) {
        clickListener = listener;
    }*/

    public void colorHaut() {
        colorLigne(imagesHaut, m.getHaut());
    }

    public void colorHaut(int indice) {
        colorBoule(m.getHaut().getBoules()[indice], imagesHaut[indice]);
    }

    public void deColorHaut() { //Rend la ligne du haut "invisible" (sans pour autant changer le Model)
        for(int i = 0; i < imagesHaut.length; i++) {
            if(m.getBas().getBoules()[i].isActive()) { //Si la boule du bas est correct, la version du haut ne se décolorisera pas
                imagesHaut[i].setImageResource(R.drawable.vide);
            }
        }
    }

    public void colorBas() {
        colorLigne(imagesBas, m.getBas());
    }

    public void colorLigne(ImageView[] ligneImages, Ligne ligne) {
        for(int i = 0; i < ligneImages.length; i++) {
            colorBoule(ligne.getBoules()[i], ligneImages[i]);
        }
    }

    public void colorBoule(Boule bouleAColorer, ImageView imageBoule) {
        if(bouleAColorer.getCouleur() == Couleur.BLEU) {
            if(m.getSelectedBoule() == bouleAColorer)
                imageBoule.setImageResource(R.drawable.sbleu);
            else
                imageBoule.setImageResource(R.drawable.bleu);
        } else if(bouleAColorer.getCouleur() == Couleur.JAUNE) {
            if(m.getSelectedBoule() == bouleAColorer)
                imageBoule.setImageResource(R.drawable.sjaune);
            else
                imageBoule.setImageResource(R.drawable.jaune);
        } else if(bouleAColorer.getCouleur() == Couleur.ROUGE) {
            if(m.getSelectedBoule() == bouleAColorer)
                imageBoule.setImageResource(R.drawable.srouge);
            else
                imageBoule.setImageResource(R.drawable.rouge);
        } else if(bouleAColorer.getCouleur() == Couleur.VERT) {
            if(m.getSelectedBoule() == bouleAColorer)
                imageBoule.setImageResource(R.drawable.svert);
            else
                imageBoule.setImageResource(R.drawable.vert);
        } else if(bouleAColorer.getCouleur() == Couleur.VIOLET) {
            if(m.getSelectedBoule() == bouleAColorer)
                imageBoule.setImageResource(R.drawable.sviolet);
            else
                imageBoule.setImageResource(R.drawable.violet);
        } else if(bouleAColorer.getCouleur() == Couleur.VIDE) {
            if(m.getSelectedBoule() == bouleAColorer)
                imageBoule.setImageResource(R.drawable.svide);
            else
                imageBoule.setImageResource(R.drawable.vide);
        } else
            Log.d(TAG, "Couleur invalide (ajouter une nouvelle image pour montrer ça)");
        //ligneBoutons[i].setBackground(couleurInvalide);
    }

    public void cacheResultats() {
        for(int i = 0; i < resultats.length; i++) {
            resultats[i].setVisibility(View.INVISIBLE);
        }
    }

    public void reveleUnResultat(ImageView resultat, int ressourceInt) {
        resultat.setVisibility(View.VISIBLE);
        resultat.setImageResource(ressourceInt);
    }

    public void changeRegardezSequence() {
        regardezSequence.setText(getString(R.string.sequence) + "(" + m.getNbSecondesVerif() + ")");
    }

    public int changeCalculScore(int juste, int erreur) { //Calcul et renvoie le score à ajouter
        int scoreEnPlus = (juste*2) - erreur;
        calculScore.setText(juste + "X2 - " + erreur + " = " + scoreEnPlus);
        return scoreEnPlus;
    }

    public void changeScore() {
        showScore.setText(getString(R.string.debScore) + ": " + m.getScore());
    }

    public void changeTentativesRestantes() {
        if(m.getNbTentatives() == Model.getNbTentativesMax()) //Si on est au début de la partie
            tentativesRestantes.setText(getString(R.string.debTentative) + " " + m.getNbTentatives() + " " + getString(R.string.endTentative));
        else {
            if (m.getNbTentatives() > 1) //Si on est pas au début de la partie mais qu'il reste plus d'une tentative
                tentativesRestantes.setText(getString(R.string.notLastTentative) + " " + m.getNbTentatives() + ".");
            else //Si il ne reste plus qu'une seule tentative
                tentativesRestantes.setText(getString(R.string.lastTentative));
        }
    }

    public void debutVerification() {
        m.setSelectedBoule(null);
        colorBas();
        reponse.setVisibility(View.GONE);
        chooseLigne.setVisibility(View.GONE);
        ct.resetIndices();

        ct.indicesFaux = m.getErreurs();
        m.setInAction(true); //Empêche l'utilisateur de faire quoi que ce soit pendant qu'on lui remontre la séquence et si la partie est finie
        ct.start(handlerVerification, 500);
    }

    public void finVerication() {

        int scoreEnPlus = changeCalculScore(ct.nbBoulesJustes, ct.nbBoulesFausses);
        m.augmenteScore(scoreEnPlus);
        visibiliteVerification();
        changeScore();
        colorHaut(); //Pendant la vérification, on montre de nouveau la ligne du haut

        if(ct.indicesFaux.isEmpty()) {
            Log.d(TAG, "Séquence complète !");
            creerDialogSequenceComplete();
        } else {
            m.perdTentative();

            Log.d(TAG, "Indice(s) ou les boules ne correspondent pas : ");
            String indicesFauxString = "";
            for (Integer indice : ct.indicesFaux) {
                indicesFauxString += ( indice + ", ");
            }
            Log.d(TAG, indicesFauxString);

            m.printLignes();
            if(m.getNbTentatives() > 0) {
                changeTentativesRestantes();
                tentativesRestantes.setVisibility(View.VISIBLE);
                ct.start(handlerFinEssaie, 5000); //Ce Timer ne s'enclenche que si la partie n'est pas finie
            } else {//La partie est perdue
                creerDialogPlusDeTentatives();
            }
        }
    }

    public void reset() {

        m.setValeurs();
        cacheResultats();

        changeScore();
        changeTentativesRestantes();
        changeRegardezSequence();

        colorHaut();
        colorBas();
        visibiliteDebutDePartie();
        ct.start(handlerDebut, 1000);
    }

    public void creerDialogLigneNonComplete() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.ligneNonCompleteTexte));
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.LigneNonCompleteTitre));
        builder.setPositiveButton(getString(R.string.LigneNonCompleteOk), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG,"Ligne non complète !");
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void creerDialogSequenceComplete() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.sequenceCompleteTexte1) +
                "\n" + getString(R.string.scoreFinal) + ": " + m.getScore() + "." +
                "\n" + getString(R.string.sequenceCompleteTexte2) + ": " + (Model.getNbTentativesMax() - m.getNbTentatives() + 1 + "."));
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.sequenceCompleteTitre));
        builder.setPositiveButton(getString(R.string.sequenceCompleteOk), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "Séquence complète");
                creerDialogReset();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void creerDialogPlusDeTentatives() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.tentativeEpuiseesTexte) +
                "\n" + getString(R.string.scoreFinal) + ": " + m.getScore() + ".");
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.tentativeEpuiseesTitre));
        builder.setPositiveButton(getString(R.string.tentativeEpuisseOk), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "Plus de tentatives");
                creerDialogReset();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void creerDialogReset() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.resetTexte));
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.resetTitre));
        builder.setPositiveButton(getString(R.string.resetOui), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "Recommencer");
                reset();
            }
        });
        builder.setNegativeButton(R.string.resetNon, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "Revenir au menu");
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Partie contrôleur
    @Override
    public void onClick(View view) {
        if(m.isInAction()) {
            Log.d(TAG, "Interdiction d'appuyer sur l'écran");
            return;
        }

        if(view == reponse) {
            if (!m.getBas().isLigneColored())
                creerDialogLigneNonComplete();
            else
                debutVerification();
        } else {
            for(int i = 0; i < imagesBas.length; i++) {
                if(view == imagesBas[i]) {
                    m.setSelectedBoule(m.getBas().getBoules()[i]);
                    chooseLigne.setVisibility(View.VISIBLE);
                    colorBas();
                    return;
                    //m.getBas().getBoules()[i].changeCouleur();
                    //colorBas();
                }
            }

            //Le seul élément touchable à cette étape sont les boules pour choisir la couleur
            if(view == imagesChoose[1])
                m.getSelectedBoule().setCouleur(Couleur.JAUNE);
            else if (view == imagesChoose[2])
                m.getSelectedBoule().setCouleur(Couleur.ROUGE);
            else if(view == imagesChoose[3])
                m.getSelectedBoule().setCouleur(Couleur.VERT);
            else if(view == imagesChoose[4])
                m.getSelectedBoule().setCouleur(Couleur.VIOLET);
            else
                m.getSelectedBoule().setCouleur(Couleur.BLEU);
            //chooseLigne.setVisibility(View.GONE);
            colorBas();
        }
    }
}
