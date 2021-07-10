package com.barisproduction.aldimbunu.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.barisproduction.aldimbunu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class KayitActivity extends AppCompatActivity {

    EditText isim,email,sifre;
    private FirebaseAuth auth;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

       // getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null){
            startActivity(new Intent(KayitActivity.this, MainActivity.class));
            finish();
        }

        isim = findViewById(R.id.isim);
        email = findViewById(R.id.email);
        sifre = findViewById(R.id.sifre);
        sharedPreferences = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);

        boolean isIlkGiris = sharedPreferences.getBoolean("ilkGiris",true);

        if (isIlkGiris){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("ilkGiris",false);
            editor.commit();

            Intent intent = new Intent(KayitActivity.this, OnBoarding.class);
            startActivity(intent);
            finish();
        }
    }

    public void kayitol(View view) {

        String kullaniciEmail = email.getText().toString();
        String kullaniciSifre = sifre.getText().toString();

        if (TextUtils.isEmpty(kullaniciEmail)){
            Toast.makeText(this, "Email Alanı Boş Bırakılamaz", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(kullaniciSifre)){
            Toast.makeText(this, "Şifre Boş Bırakılamaz", Toast.LENGTH_SHORT).show();
            return;
        }
        if (kullaniciSifre.length() < 6){
            Toast.makeText(this, "Şifre 6 karakterden kısa olamaz", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(kullaniciEmail,kullaniciSifre)
                .addOnCompleteListener(KayitActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(KayitActivity.this, "Kayıt Başarılı!", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(KayitActivity.this,MainActivity.class));
                            
                        }else {
                            Toast.makeText(KayitActivity.this, "Kayıt Sırasında Hata Oluştu!"+task.getException(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    public void girisyap(View view) {
        startActivity(new Intent(KayitActivity.this, GirisActivity.class));
    }
}