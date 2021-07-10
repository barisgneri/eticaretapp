package com.barisproduction.aldimbunu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager.widget.ViewPager;

import com.barisproduction.aldimbunu.R;
import com.barisproduction.aldimbunu.adapters.SliderAdapter;

public class OnBoarding extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout noktaLayout;
    Button btn;
    SliderAdapter sliderAdapter;
    TextView[] nokta;

    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //Durum Çubuğunu Gizleme
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding);

//        getSupportActionBar().hide();

        viewPager = findViewById(R.id.slider);
        noktaLayout = findViewById(R.id.nokta);
        btn = findViewById(R.id.hadi_baslayalim_btn);
        noktaEkle(0);

        viewPager.addOnPageChangeListener(changeListener);

        //Adpteri Çağır
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OnBoarding.this, KayitActivity.class));
                finish();
            }
        });
    }

    private void noktaEkle(int position){

        nokta = new TextView[3];
        noktaLayout.removeAllViews();
        for (int i = 0; i < nokta.length; i++){
            nokta[i] = new TextView(this);
            nokta[i].setText(Html.fromHtml("&#8226"));
            nokta[i].setTextSize(35);
            noktaLayout.addView(nokta[i]);
        }

        if (nokta.length > 0){
            nokta[position].setTextColor(getResources().getColor(R.color.pink));
        }

    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            noktaEkle(position);

            if (position == 0){

                btn.setVisibility(View.INVISIBLE);

            }else if (position == 1){
                btn.setVisibility(View.INVISIBLE);
            }else {
                animation = AnimationUtils.loadAnimation(OnBoarding.this,R.anim.slider_animation);
                btn.setAnimation(animation);
                btn.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
