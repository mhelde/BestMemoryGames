package memory.bestmemorygames.nombres;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import memory.bestmemorygames.R;

public class VueNombres extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_nombres);

        Model model = new Model();
    }
}
