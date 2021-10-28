package com.fahmi.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GetStartedActivity extends AppCompatActivity {

    Button btn_sign_in, btn_new_account_creat;
    Animation ttb, btt;
    ImageView emblem_app;
    TextView sub_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        //load animation
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        //daftar
        emblem_app = findViewById(R.id.emblem_app);
        sub_app = findViewById(R.id.sub_app);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        btn_new_account_creat = findViewById(R.id.btn_new_account_creat);


        //run animation
        emblem_app.startAnimation(ttb);
        sub_app.startAnimation(ttb);
        btn_sign_in.startAnimation(btt);
        btn_new_account_creat.startAnimation(btt);



        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosign = new Intent(GetStartedActivity.this, SignInActivity.class);
                startActivity(gotosign);
            }
        });


        btn_new_account_creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotocreatccount = new Intent(GetStartedActivity.this, RegisterOneActivity.class);
                startActivity(gotocreatccount);
            }
        });
    }
}