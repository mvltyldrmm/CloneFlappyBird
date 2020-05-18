package com.example.oyuntasarim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.sax.StartElementListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    Button basla;
    Intent intent;
    String oyun_degeri;  //Oyunun baslama bitme durumu
    EditText isim; //Girilen kullanici ismi
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isim = (EditText)findViewById(R.id.kullaniciadi);

        intent = new Intent(getApplicationContext(),OyunEkrani.class);  //Oyun ekranina gidecek intent.

        findViewById(R.id.basla).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ad = isim.getText().toString();
                if(isim.getText().toString().length()<=0){
                    ad = "isimsiz";                              //Eger kullanici bir isim girmezse uygulama icinde bir isim olustur. (isimsiz)
                }
                oyun_degeri = "basla";
                intent.putExtra("deger",oyun_degeri);
                intent.putExtra("isim",ad);              //İsim ve oyunun baslama degeri oyunekrani intentine gidiyor.
                startActivity(intent);
            }
        });
    }
}
