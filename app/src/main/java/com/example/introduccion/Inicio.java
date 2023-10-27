package com.example.introduccion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.Ach2290.firstdesign.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

public class Inicio extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


        TextView appNameSplash = findViewById(R.id.appNameSplah);
        Animation fadeAnimation = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        logoSplash.startAnimation(fadeAnimation);
        appNameSplash.startAnimation(fadeAnimation);

        ImageView background = findViewById(R.id.backgroundSplash);

        Glide.with(this)
                /* .load(R.drawable.pija)*/
                .load("https://images.unsplash.com/photo-1579952363873-27f3bade9f55?auto=format&fit=crop&q=80&w=1935&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
                .transition (DrawableTransitionOptions.withCrossFade(2000))
                .centerCrop()
                .into(background)

        ;

    }
}