package com.barisproduction.aldimbunu.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.barisproduction.aldimbunu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class GirisActivity extends AppCompatActivity {

    EditText email,sifre;
    private FirebaseAuth auth;
    TextView sifremiUnuttum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

       // getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        sifre = findViewById(R.id.sifre);
        sifremiUnuttum = findViewById(R.id.sifremiUnuttum);

    }

    public void girisyap(View view) {

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

        auth.signInWithEmailAndPassword(kullaniciEmail,kullaniciSifre)
                .addOnCompleteListener(GirisActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(GirisActivity.this, "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(GirisActivity.this, MainActivity.class));
                        }else {
                            Toast.makeText(GirisActivity.this, "Hata: "+task.getException(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void kayitol(View view) {
        startActivity(new Intent(GirisActivity.this, KayitActivity.class));
    }

    public void sifremiUnuttum(View view) {
        // startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
        EditText resetmail = new EditText(view.getContext());
        AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
        passwordResetDialog.setTitle("Şifrenizi mi Unutttunuz?");
        passwordResetDialog.setMessage("Email Adresinizi Girin.");
        passwordResetDialog.setView(resetmail);

        passwordResetDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mail = resetmail.getText().toString();
                auth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(GirisActivity.this, "Şifre Yenileme Bağlantısı " + mail + " 'a gönderildi.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(GirisActivity.this, "Hata Oluştu! Tekrar Deneyin." + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        passwordResetDialog.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        passwordResetDialog.create().show();
    }
}