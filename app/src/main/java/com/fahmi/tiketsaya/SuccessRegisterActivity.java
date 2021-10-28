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

public class SuccessRegisterActivity extends AppCompatActivity {
    Animation app_spalsh,btt,ttb;
    Button btn_explorer;
    ImageView icon_success;
    TextView app_tittle,app_subtittle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        //daftar
        btn_explorer = findViewById(R.id.btn_explore);
        icon_success = findViewById(R.id.icon_success);
        app_tittle =  findViewById(R.id.app_tittle);
        app_subtittle = findViewById(R.id.app_subtittle);

        //load animation
        app_spalsh = AnimationUtils.loadAnimation(this, R.anim.app_spalsh);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        //run Animation
        btn_explorer.startAnimation(btt);
        icon_success.startAnimation(app_spalsh);
        app_tittle.startAnimation(btt);


        btn_explorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(SuccessRegisterActivity.this, HomeActivity.class);
                startActivity(gotohome);
            }
        });
    }
}