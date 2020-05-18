package com.example.oyuntasarim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;

public class OyunEkrani extends AppCompatActivity {
    String oyun_degeri;
    ImageView kus;
    ImageView dusman1,dusman2,dusman3;
    ImageView coin1,coin2;
    int coin1x,coin1y,coin2x,coin2y; //ALTINLARIN X VE Y EKSENDEKI DEGERLERİ
    Button btn2;
    Timer timer;
    Intent intent2;
    TextView yazi;
    ConstraintLayout ekranin;        //EKRANA TİKLAYABİLMEK İCİN
    int kusx,kusy;
    int dusman1x,dusman1y;
    int dusman2x,dusman2y;          //> KARSİDAN RANDOM GELEN DUSMANLARİN X VE Y KOORDINATLARI
    int dusman3x,dusman3y;
    int skor;
    float hiz = (float) 5;
    float yercekimi = (float)2.5;
    String enyuksekskor ;
    String oyuncu_ismi;
    int ziplama = 80;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyun_ekrani);
        Intent intent = getIntent();
        oyuncu_ismi =intent.getStringExtra("isim");
        oyun_degeri = intent.getStringExtra("deger");

        System.out.println("**********"+ oyun_degeri + "*********");

        kus = (ImageView)findViewById(R.id.button);
        intent2= new Intent(getApplicationContext(),SonucEkrani.class);
        timer = new Timer(100000,1);

        ekranin = findViewById(R.id.ekran);
        dusman1 = findViewById(R.id.imageView);
        dusman2 = findViewById(R.id.imageView2);
        dusman3 = findViewById(R.id.imageView3);
        yazi = findViewById(R.id.yazi2);
        coin1 = findViewById(R.id.imageView4);
        coin2 = findViewById(R.id.imageView5);
        if(oyun_degeri.equals("basla")){
            timer.start();


            ekranin.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction()==MotionEvent.ACTION_DOWN){
                        kus.setY((float) kus.getY()-ziplama); //ziplama uzunlugu

                    }


                    return false;
                }


            });
        }

    }
    class Timer extends CountDownTimer {
        public Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);


        }

        @Override
        public void onTick(long millisUntilFinished) {

            if (oyun_degeri.equals("bitir")){
                int tutucu = skor;
                enyuksekskor = String.valueOf(tutucu);
                //diger intent
                timer.cancel();
                intent2.putExtra("yuksekskor",enyuksekskor);
                intent2.putExtra("isim",oyuncu_ismi);
                startActivity(intent2);             //EGER OYUN DEGERİ BITIRSE, DIGER INTENTE GEC.
            }

            kus.setY((float) (kus.getY()+yercekimi));    //YERCEKİMİ

            Random rand =  new Random();
            int a = rand.nextInt(999)+1;     //EKRANA SIGMASI ICIN RANDOM DEGERLER
            dusman1.setX((float)dusman1.getX()-hiz);
            dusman2.setX((float) dusman2.getX()-hiz);
            dusman3.setX((float)dusman3.getX()-hiz);

            coin1.setX((float) coin1.getX()-hiz);
            coin2.setX((float)coin2.getX()-hiz);

            if(dusman1x<=0){  //DUSMAN EKRANDAN CIKTIYSA

                dusman1.setX(1200);
                dusman2.setX(1200);
                dusman3.setX(1200);

                dusman3.setY(a);
                dusman3.setY(a);       //DUSMANLARIN Y EKSENI UZERINDE RANDOM GELMESI ICIN DEGERLER
                dusman3.setY(a);
                hiz = (float) (hiz + 0.3); //hiz artti
                yercekimi= (float) (yercekimi + 0.3);   //HIZI YERCEKIMI VE ZIPLAMA BOYU OYUN ILERLEDIKCE ARTIYOR.
                ziplama +=3;

            }

            if(coin1x<=0){

                int c = rand.nextInt(1300)+800;
                coin1.setX(c);
                int b = rand.nextInt(999)+1;
                coin1.setY(b);
            }
                                                            //<ALTINLAR EKRANDAN CIKTIYSA YENIDEN OLUSTURULUYOR
            if(coin2x<=0){
                int c = rand.nextInt(1300)+800;
                coin2.setX(c);
                int b = rand.nextInt(999)+1;
                coin2.setY(b);
            }

            coin1x = (int)coin1.getX();
            coin1y = (int)coin1.getY();
            coin2x = (int)coin2.getX();
            coin2y = (int)coin2.getY();

            kusx = (int)kus.getX();
            kusy = (int) kus.getY();
            dusman1x = (int)dusman1.getX();
            dusman2x = (int)dusman2.getX();
            dusman3x = (int)dusman3.getX();
            dusman1y = (int)dusman1.getY();
            dusman2y = (int)dusman2.getY();
            dusman3y = (int)dusman3.getY();

            int d1merkezx = dusman1x+dusman1.getWidth()/2-200;
            int d1merkezy = dusman1y +dusman1.getHeight()/2;
            int d2merkezx = dusman2x+ dusman2.getWidth()/2-200;   //MERKEZ KISIMLARI OLUSTURULUYOR
            int d2merkezy = dusman2y +dusman2.getHeight()/2 ;
            int d3merkezx = dusman3x+ dusman3.getWidth()/2-200;
            int d3merkezy = dusman3y +dusman3.getHeight()/2 ;

            int c1merkezx = coin1x+coin1.getWidth()/2 -200;
            int c1merkezy = coin1y+coin1.getHeight()/2;
            int c2merkezx = coin2x+coin2.getWidth()/2 -200;
            int c2merkezy = coin2y+coin2.getHeight()/2;



            if(kusy>= ekranin.getHeight() - kus.getHeight()){  //
                oyun_degeri = "bitir";
            }
            if(kusy<=0){
                oyun_degeri = "bitir";
            }

            if(0<= d1merkezx && d1merkezx <= kus.getWidth()&& kus.getY() <=
                    d1merkezy && d1merkezy<=kus.getY()+kus.getHeight() ||0<= d2merkezx && d2merkezx <= kus.getWidth()&& kus.getY() <=
                    d2merkezy && d2merkezy<=kus.getY()+kus.getHeight()||0<= d3merkezx && d3merkezx <= kus.getWidth()&& kus.getY() <=        //CARPMA ANLAMASİ
                    d3merkezy && d3merkezy<=kus.getY()+kus.getHeight()){
                oyun_degeri = "bitir";

            }


            if(0<= c1merkezx && c1merkezx <= kus.getWidth()&& kus.getY() <=
                    c1merkezy && c1merkezy<=kus.getY()+kus.getHeight()){                       //ALTİNA CARPTIGIMIZDA, ALTINI EKRAN DISINA AT.
                skor +=10;
                yazi.setText(String.valueOf(skor));
                coin1.setY(-200);


            }

            if(0<= c2merkezx && c2merkezx <= kus.getWidth()&& kus.getY() <=
                    c2merkezy && c2merkezy<=kus.getY()+kus.getHeight()){                    //ALTİNA CARPTIGIMIZDA, ALTINI EKRAN DISINA AT.
                skor +=10;
                yazi.setText(String.valueOf(skor));
                coin2.setY(-200);
            }

        }

        @Override
        public void onFinish() {

        }
    }
}
