package memory.bestmemorygames.memory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;

import memory.bestmemorygames.PageAccueilActivity;
import memory.bestmemorygames.R;

public class MainMemory extends AppCompatActivity {

    TextView tv_p1;
    ImageView iv_11, iv_12, iv_13, iv_14, iv_21, iv_22, iv_23, iv_24, iv_31, iv_32, iv_33, iv_34;

    //Array for images
    Integer[] cardsArray = {101,102,103,104,105,106,201,202,203,204,205,206};

    int image101,image102,image103,image104,image105,image106,image201,image202,image203,image204,image205,image206;

    int firstCards, secondCards;
    int clickFirst, clickSecond;
    int cardNumber =1;
    int turn =1;
    public int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_memory);

        tv_p1 =(TextView) findViewById(R.id.tv_p1);

        iv_11 =(ImageView) findViewById(R.id.iv_11);
        iv_12 =(ImageView) findViewById(R.id.iv_12);
        iv_13 =(ImageView) findViewById(R.id.iv_13);
        iv_14 =(ImageView) findViewById(R.id.iv_14);

        iv_21 =(ImageView) findViewById(R.id.iv_21);
        iv_22 =(ImageView) findViewById(R.id.iv_22);
        iv_23 =(ImageView) findViewById(R.id.iv_23);
        iv_24 =(ImageView) findViewById(R.id.iv_24);

        iv_31 =(ImageView) findViewById(R.id.iv_31);
        iv_32 =(ImageView) findViewById(R.id.iv_32);
        iv_33 =(ImageView) findViewById(R.id.iv_33);
        iv_34 =(ImageView) findViewById(R.id.iv_34);

        iv_11.setTag("0");
        iv_12.setTag("1");
        iv_13.setTag("2");
        iv_14.setTag("3");

        iv_21.setTag("4");
        iv_22.setTag("5");
        iv_23.setTag("6");
        iv_24.setTag("7");

        iv_31.setTag("8");
        iv_32.setTag("9");
        iv_33.setTag("10");
        iv_34.setTag("11");

        //load the cards images
        frontOfCardsRessources();

        Collections.shuffle(Arrays.asList(cardsArray));

        iv_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_11, theCard);
            }
        });
        iv_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_12, theCard);
            }
        });
        iv_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_13, theCard);
            }
        });
        iv_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_14, theCard);
            }
        });
        iv_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_21, theCard);
            }
        });
        iv_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_22, theCard);
            }
        });
        iv_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_23, theCard);
            }
        });
        iv_24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_24, theCard);
            }
        });
        iv_31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_31, theCard);
            }
        });
        iv_32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_32, theCard);
            }
        });
        iv_33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_33, theCard);
            }
        });
        iv_34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_34, theCard);
            }
        });
    }
    private void frontOfCardsRessources(){
        image101 = R.mipmap.ic_image101;
        image102 = R.mipmap.ic_image102;
        image103 = R.mipmap.ic_image103;
        image104 = R.mipmap.ic_image104;
        image105 = R.mipmap.ic_image105;
        image106 = R.mipmap.ic_image106;

        image201 = R.mipmap.ic_image201;
        image202 = R.mipmap.ic_image202;
        image203 = R.mipmap.ic_image203;
        image204 = R.mipmap.ic_image204;
        image205 = R.mipmap.ic_image205;
        image206 = R.mipmap.ic_image206;
    }

    private void checkEnd(){
        if(iv_11.getVisibility() == View.INVISIBLE &&
                iv_12.getVisibility() == View.INVISIBLE &&
                iv_13.getVisibility() == View.INVISIBLE &&
                iv_14.getVisibility() == View.INVISIBLE &&
                iv_21.getVisibility() == View.INVISIBLE &&
                iv_22.getVisibility() == View.INVISIBLE &&
                iv_23.getVisibility() == View.INVISIBLE &&
                iv_24.getVisibility() == View.INVISIBLE &&
                iv_31.getVisibility() == View.INVISIBLE &&
                iv_32.getVisibility() == View.INVISIBLE &&
                iv_33.getVisibility() == View.INVISIBLE &&
                iv_34.getVisibility() == View.INVISIBLE
                ) {
            final EditText name = new EditText(this);
            name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            name.setLayoutParams(lp);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainMemory.this);
            alertDialogBuilder
                    .setMessage("Bravo vous avez gagné !!\n" +"Vous avez eu "+ score+"points.\n"+"Votre nom :")
                    .setView(name)
                    .setCancelable(false)
                    .setPositiveButton("Rejouer", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getApplicationContext(), MainMemory.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("Arrêter", new DialogInterface.OnClickListener() {
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
    }

    //calcul points
    private void calculate(){
        if(firstCards == secondCards){
            if(clickFirst ==0){
                iv_11.setVisibility(View.INVISIBLE);
            }else if(clickFirst ==1){
                iv_12.setVisibility(View.INVISIBLE);
            }else if(clickFirst ==2){
                iv_13.setVisibility(View.INVISIBLE);
            }else if(clickFirst ==3){
                iv_14.setVisibility(View.INVISIBLE);
            }else if(clickFirst ==4){
                iv_21.setVisibility(View.INVISIBLE);
            }else if(clickFirst ==5){
                iv_22.setVisibility(View.INVISIBLE);
            }else if(clickFirst ==6){
                iv_23.setVisibility(View.INVISIBLE);
            }else if(clickFirst ==7){
                iv_24.setVisibility(View.INVISIBLE);
            }else if(clickFirst ==8){
                iv_31.setVisibility(View.INVISIBLE);
            }else if(clickFirst ==9){
                iv_32.setVisibility(View.INVISIBLE);
            }else if(clickFirst ==10){
                iv_33.setVisibility(View.INVISIBLE);
            }else if(clickFirst ==11){
                iv_34.setVisibility(View.INVISIBLE);
            }

            if(clickSecond ==0){
                iv_11.setVisibility(View.INVISIBLE);
            }else if(clickSecond ==1){
                iv_12.setVisibility(View.INVISIBLE);
            }else if(clickSecond ==2){
                iv_13.setVisibility(View.INVISIBLE);
            }else if(clickSecond ==3){
                iv_14.setVisibility(View.INVISIBLE);
            }else if(clickSecond ==4){
                iv_21.setVisibility(View.INVISIBLE);
            }else if(clickSecond ==5){
                iv_22.setVisibility(View.INVISIBLE);
            }else if(clickSecond ==6){
                iv_23.setVisibility(View.INVISIBLE);
            }else if(clickSecond ==7){
                iv_24.setVisibility(View.INVISIBLE);
            }else if(clickSecond ==8){
                iv_31.setVisibility(View.INVISIBLE);
            }else if(clickSecond ==9){
                iv_32.setVisibility(View.INVISIBLE);
            }else if(clickSecond ==10){
                iv_33.setVisibility(View.INVISIBLE);
            }else if(clickSecond ==11){
                iv_34.setVisibility(View.INVISIBLE);
            }

            if(turn ==1){
                score++;
                tv_p1.setText("Points :"+score );

            }

        }else {
            iv_11.setImageResource(R.mipmap.ic_back);
            iv_12.setImageResource(R.mipmap.ic_back);
            iv_13.setImageResource(R.mipmap.ic_back);
            iv_14.setImageResource(R.mipmap.ic_back);

            iv_21.setImageResource(R.mipmap.ic_back);
            iv_22.setImageResource(R.mipmap.ic_back);
            iv_23.setImageResource(R.mipmap.ic_back);
            iv_24.setImageResource(R.mipmap.ic_back);

            iv_31.setImageResource(R.mipmap.ic_back);
            iv_32.setImageResource(R.mipmap.ic_back);
            iv_33.setImageResource(R.mipmap.ic_back);
            iv_34.setImageResource(R.mipmap.ic_back);


        }
        iv_11.setEnabled(true);
        iv_12.setEnabled(true);
        iv_13.setEnabled(true);
        iv_14.setEnabled(true);

        iv_21.setEnabled(true);
        iv_22.setEnabled(true);
        iv_23.setEnabled(true);
        iv_24.setEnabled(true);

        iv_31.setEnabled(true);
        iv_32.setEnabled(true);
        iv_33.setEnabled(true);
        iv_34.setEnabled(true);

        checkEnd();
    }

    private void doStuff(ImageView iv, int card){
        if(cardsArray[card]==101){
            iv.setImageResource(image101);
        }else if(cardsArray[card]==102){
            iv.setImageResource(image102);
        }else if(cardsArray[card]==103){
            iv.setImageResource(image103);
        }else if(cardsArray[card]==104){
            iv.setImageResource(image104);
        }else if(cardsArray[card]==105){
            iv.setImageResource(image105);
        }else if(cardsArray[card]==106){
            iv.setImageResource(image106);
        }else if(cardsArray[card]==201){
            iv.setImageResource(image201);
        }else if(cardsArray[card]==202){
            iv.setImageResource(image202);
        }else if(cardsArray[card]==203){
            iv.setImageResource(image203);
        }else if(cardsArray[card]==204){
            iv.setImageResource(image104);
        }else if(cardsArray[card]==204){
            iv.setImageResource(image205);
        }else if(cardsArray[card]==206){
            iv.setImageResource(image206);
        }

        if(cardNumber ==1){
            firstCards = cardsArray[card];
            if(firstCards>200){
                firstCards = firstCards-100;
            }
            cardNumber =2;
            clickFirst = card;

            iv.setEnabled(false);
        }else if (cardNumber ==2){
            secondCards = cardsArray[card];
            if(secondCards>200){
                secondCards = secondCards-100;
            }
            cardNumber=1;
            clickSecond =card;

            iv_11.setEnabled(false);
            iv_12.setEnabled(false);
            iv_13.setEnabled(false);
            iv_14.setEnabled(false);

            iv_21.setEnabled(false);
            iv_22.setEnabled(false);
            iv_23.setEnabled(false);
            iv_24.setEnabled(false);

            iv_31.setEnabled(false);
            iv_32.setEnabled(false);
            iv_33.setEnabled(false);
            iv_34.setEnabled(false);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    calculate();
                }
            },2000);


        }
    }
}
