package com.fahmi.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    Animation app_spalsh,btt;
    ImageView app_logo;
    TextView desc_splash_act;

    String USERNAME_KEY="usernamekey";
    String username_key="";
    String username_key_new="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //load animation
        app_spalsh = AnimationUtils.loadAnimation(this, R.anim.app_spalsh);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        //load element
        app_logo = findViewById(R.id.app_logo);
        desc_splash_act = findViewById(R.id.desc_splash_act);

        //run animation
        app_logo.startAnimation(app_spalsh);
        desc_splash_act.startAnimation(btt);

        getUsernameLocal();

    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences=getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new=sharedPreferences.getString(username_key,"");
        if (username_key_new.isEmpty()){

            //sett timer
            Handler handler= new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //pindah class
                    Intent gogetstarted=new Intent(SplashActivity.this,GetStartedActivity.class);
                    startActivity(gogetstarted);
                    finish();

                }
            }, 2000);

        }else { //sett timer
            Handler handler= new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //pindah class
                    Intent gogethome=new Intent(SplashActivity.this,HomeActivity.class);
                    startActivity(gogethome);
                    finish();

                }
            }, 2000);

        }
    }

}