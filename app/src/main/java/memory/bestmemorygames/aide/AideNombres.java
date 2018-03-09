package memory.bestmemorygames.aide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import memory.bestmemorygames.R;

public class AideNombres extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aide_nombres);

        final Button buttonJouer = (Button) findViewById(R.id.id_button_jouer);
        buttonJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), memory.bestmemorygames.nombres.VueNombres.class);
                startActivity(intent);
                finish();
            }
        });

        final Button buttonEntrainement = (Button) findViewById(R.id.id_button_entrainement);
        buttonEntrainement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), memory.bestmemorygames.nombres.VueNombres.class);
                startActivity(intent);
                finish();
            }
        });

        final Button buttonAccueil = (Button) findViewById(R.id.id_button_accueil);
        buttonAccueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), memory.bestmemorygames.PageAccueilActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
