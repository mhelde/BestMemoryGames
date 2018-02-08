package memory.bestmemorygames.boulier;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import memory.bestmemorygames.R;

public class VueTuto extends Vue {

    private static String TAG = "VueTuto"; //Permet de créer des messages de Debug plus comphréensible avec Log.d

    protected ImageView expectedClick;
    protected int phaseTuto;

    public void creerDialogTuto(int resId) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(resId) +
                "\n" + getString(R.string.scoreFinal) + ": " + m.getScore() + ".");
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.tentativeEpuiseesTitre));
        builder.setPositiveButton(getString(R.string.tentativeEpuisseOk), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                phaseTuto++;
                Log.d(TAG, "Phase: " + phaseTuto);
                continueTuto();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        phaseTuto = 0;
        super.onCreate(savedInstanceState);
        expectedClick = null;
        continueTuto();
    }

    public void continueTuto() {
        switch (phaseTuto) {
            case 0:
                creerDialogTuto(R.string.tutoTexte1);
                //A voir pour mettre une bordure à une ImageView pour mettre en évidence un élément
                //imagesBas[0].setBackground();
                //imagesBas[0].setPadding(1, 1, 1, 1);
                break;
            default:
                System.out.println("Cela ne devrait pas se passer");
                break;
        }
    }

    //Partie contrôleur
    @Override
    public void onClick(View view) {
        System.out.println("Ce tuto est totalement vide !");
    }
}
