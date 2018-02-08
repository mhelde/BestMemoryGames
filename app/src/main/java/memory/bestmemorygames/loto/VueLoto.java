package memory.bestmemorygames.loto;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import memory.bestmemorygames.R;
import memory.bestmemorygames.loto.*;
import memory.bestmemorygames.loto.Model;

public class VueLoto extends AppCompatActivity implements View.OnClickListener{

    protected memory.bestmemorygames.loto.Model m;

    protected Handler handlerDebut;
    protected Handler handlerFinEssaie;

    protected ControlTimer ct;

    @Override
    public void onClick(View v) {

    }


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
