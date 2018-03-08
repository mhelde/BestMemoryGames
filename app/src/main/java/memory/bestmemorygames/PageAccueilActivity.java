package memory.bestmemorygames;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import memory.bestmemorygames.boulier.Vue;
import memory.bestmemorygames.loto.VueLoto;
import memory.bestmemorygames.memory.MainMemory;
import memory.bestmemorygames.memory.MainMemoryEntrainement;
import memory.bestmemorygames.score2.MainScore;

public class PageAccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_accueil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Button jeu memory
        final ImageButton buttonMemory = (ImageButton) findViewById(R.id.btn_memory);
        buttonMemory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PageAccueilActivity.this);
                alertDialogBuilder.setTitle("Choississez ce que vous voulez faire :");

                // add a list
                String[] choix = {"Jouer une partie normale", "Jouer une partie entraînement", "Connaître règle du jeu"};
                alertDialogBuilder.setItems(choix, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: Intent intent = new Intent(getApplicationContext(), memory.bestmemorygames.memory.MainMemory.class);
                                startActivity(intent);
                                finish();
                            case 1: Intent intent1 = new Intent(getApplicationContext(), memory.bestmemorygames.memory.MainMemoryEntrainement.class);
                                startActivity(intent1);
                                finish();
                            case 2: Intent intent2 = new Intent(getApplicationContext(),memory.bestmemorygames.aide.AideMemory.class);
                                startActivity(intent2);
                                finish();
                        }
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        //Button jeu piano
        ImageButton buttonPiano = (ImageButton) findViewById(R.id.btn_piano);
        buttonPiano.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PageAccueilActivity.this);
                alertDialogBuilder.setTitle("Choississez ce que vous voulez faire :");

                // add a list
                String[] choix = {"Jouer une partie normale", "Jouer une partie entraînement", "Connaître règle du jeu"};
                alertDialogBuilder.setItems(choix, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: Intent intent = new Intent(getApplicationContext(), memory.bestmemorygames.piano.Vue.class);
                                startActivity(intent);
                                finish();
                            case 1: Intent intent1 = new Intent(getApplicationContext(), memory.bestmemorygames.piano.Vue.class);
                                startActivity(intent1);
                                finish();
                            case 2: Intent intent2 = new Intent(getApplicationContext(),memory.bestmemorygames.aide.AidePiano.class);
                                startActivity(intent2);
                                finish();
                        }
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        //Button jeu boulier
        ImageButton buttonBoulier = (ImageButton) findViewById(R.id.btn_boulier);
        buttonBoulier.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PageAccueilActivity.this);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Jouer", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), Vue.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNeutralButton("Entraînement",new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), Vue.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), PageAccueilActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        //Button jeu Loto
        ImageButton buttonLoto = (ImageButton) findViewById(R.id.btn_loto);
        buttonLoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PageAccueilActivity.this);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Jouer", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), VueLoto.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNeutralButton("Entraînement",new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), VueLoto.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), PageAccueilActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        //Button jeu score
        ImageButton buttonScore = (ImageButton) findViewById(R.id.btn_score);
        buttonScore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainScore.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.page_accueil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, ParametreActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
