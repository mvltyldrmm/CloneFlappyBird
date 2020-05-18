package com.example.oyuntasarim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SonucEkrani extends AppCompatActivity {
    Intent intent;
    Button basla; //BASLA BUTONU
    String oyun_degeri; //OYUN BITIS-BASLANGIC DEGERİ
    String skor; //KULLANICI SKORU
    String isim; //KULLANICI İSMİ
    TextView yuksekskor;
    TextView max_skoru; //YUKSE SKORUN YAZILMA EKRANI
    Button sorgulabtn; //SORGULAMA BUTONU
    EditText sorgula; //SORGULAMA GİRDİSİ
    TextView sorgulasonuc; //SKOR SORGULAMA EKRANI
    int max_skor =0; //EN YUKSEK SKORUN BULUNMASI
    int min_skor=0; //EN DUSUK SKORUN BULUNMASI


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference oku = FirebaseDatabase.getInstance().getReference();
    ArrayList<String>skorlar = new ArrayList<String>();              //SKORLARIN DATABASEDEN CEKILIP ATILMASI ICIN ARRAYLIST OLUSTURDUM.



    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sonuc_ekrani);
            intent = new Intent(getApplicationContext(),MainActivity.class);
            Intent intental = getIntent();
            Intent intentisimal = getIntent();
            yuksekskor = (TextView)findViewById(R.id.skor2);
            skor = intental.getStringExtra("yuksekskor");
            isim = intentisimal.getStringExtra("isim"); //skor ve isim alindi. (FIRABASE KAYDI ICIN)
            yuksekskor.setText("SKORUNUZ :"+ skor);
            sorgula = (EditText)findViewById(R.id.sorgula);
            max_skoru = (TextView)findViewById(R.id.max_skoru);
            sorgulasonuc = (TextView)findViewById(R.id.sorgulaSonuc);
            myRef.child(isim).setValue(skor);                    //FIRABASE YAZDIRILDI
            findViewById(R.id.sorgulaBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ValueEventListener dinle = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){        //TUM DATABASE ELEMANLARINI GEZIYORUZ

                                String isim_girdisi = sorgula.getText().toString();
                                if(isim_girdisi.equals(postSnapshot.getKey())){              //EGER GIRILEN ISIM DATA BASEDE VARSA ;
                                    sorgulasonuc.setText("Girdiginiz ismin skoru:"+ postSnapshot.getValue());
                                    break;
                                }
                                else if(sorgula.getText().toString().length()<=0){      //EGER BİRSEY GIRILMEDIYSE
                                    Toast.makeText(SonucEkrani.this, "İsim girilmedi.", Toast.LENGTH_SHORT).show();
                                }
                                else{                                                   //GIRILEN ISIM YOKSA
                                    Toast.makeText(SonucEkrani.this, "Boyle bir isim yok.", Toast.LENGTH_SHORT).show();
                                }

                            }
                            for(DataSnapshot postSnapshot2:dataSnapshot.getChildren()){
                                skorlar.add((String) postSnapshot2.getValue()); //arraylist attim
                            }
                            int[] degerler = new int[skorlar.size()];
                            for(int i =0 ; i<skorlar.size();i++){
                                degerler[i] = Integer.parseInt(skorlar.get(i));  //TUM SKORLAR INTEGER'E DONUSTURULDU VE NORMAL BIR LIST'E ATILDI.

                            }
                            for(int i =0;i<degerler.length;i++){
                                if(degerler[i]<min_skor)//degerler[0]dan kucuk bir değer varsa onu min yapar
                                    min_skor=degerler[i];
                                if(degerler[i]>max_skor)//degerler[0]dan buyuk bir değer varsa onu max yapar
                                    max_skor=degerler[i];
                            }
                            System.out.println("en buyuk sayi: "+max_skor);
                            System.out.println("en kucuk sayi: "+min_skor);

                            max_skoru.setText("Yapılmış en yüksek skor :"+max_skor);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };
                    oku.addValueEventListener(dinle);

                }
            });


            findViewById(R.id.basla2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {    //GIRISE GERI DONME INTENT.


                    startActivity(intent);
                }
            });
        }
}
