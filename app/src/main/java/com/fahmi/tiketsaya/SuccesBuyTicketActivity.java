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

public class SuccesBuyTicketActivity extends AppCompatActivity {

    Button btn_view_ticket,btn_mydashboard;
    Animation app_spalsh,btt,ttb;
    ImageView icon_succes_ticket;
    TextView app_tittle,app_subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_buy_ticket);

        icon_succes_ticket=findViewById(R.id.icon_succes_ticket);
        app_tittle=findViewById(R.id.app_tittle);
        app_subtitle=findViewById(R.id.app_subtitle);
        btn_view_ticket=findViewById(R.id.btn_view_ticket);
        btn_mydashboard=findViewById(R.id.btn_mydashboard);


        //load animation
        app_spalsh= AnimationUtils.loadAnimation(this,R.anim.app_spalsh);
        btt=AnimationUtils.loadAnimation(this,R.anim.btt);
        ttb=AnimationUtils.loadAnimation(this,R.anim.ttb);

        //run animation
        icon_succes_ticket.startAnimation(app_spalsh);

        app_tittle.startAnimation(ttb);
        app_subtitle.startAnimation(ttb);

        btn_view_ticket.startAnimation(btt);
        btn_mydashboard.startAnimation(btt);

        btn_view_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile=new Intent(SuccesBuyTicketActivity.this,MyProfileActivity.class);
                startActivity(gotoprofile);
            }
        });

        btn_mydashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome=new Intent(SuccesBuyTicketActivity.this,HomeActivity.class);
                startActivity(gotohome);
            }
        });

    }
}