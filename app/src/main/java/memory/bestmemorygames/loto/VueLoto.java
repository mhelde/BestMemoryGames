package memory.bestmemorygames.loto;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import memory.bestmemorygames.R;

public class VueLoto extends AppCompatActivity {

        protected memory.bestmemorygames.loto.Model m;

        protected Handler handlerDebut;
        protected Handler handlerFinEssaie;

        protected ControlTimer ct;


        public void OnCreate (Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_loto);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            m = new Model();


            ct = new memory.bestmemorygames.loto.ControlTimer(m,this);

            handlerDebut = new Handler();
            handlerFinEssaie = new Handler();
            ct.start(handlerDebut, 1000);


        }
}
