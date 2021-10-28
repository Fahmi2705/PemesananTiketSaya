package com.fahmi.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import io.github.florent37.shapeofview.shapes.CircleView;

public class HomeActivity extends AppCompatActivity {

    LinearLayout btn_ticket_pisa,btn_ticket_tori,btn_ticket_pagoda,btn_ticket_candi,btn_ticket_spinx,btn_ticket_monas;
    ImageView photo_home_user;
    TextView user_balance, username, bio;
    CircleView btn_to_profile;

    DatabaseReference reference;

    String USERNAME_KEY="usernamekey";
    String username_key="";
    String username_key_new="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUsernameLocal();

        btn_ticket_pisa = findViewById(R.id.btn_ticket_pisa);
        btn_ticket_tori = findViewById(R.id.btn_ticket_tori);
        btn_ticket_pagoda=findViewById(R.id.btn_ticket_pagoda);
        btn_ticket_candi=findViewById(R.id.btn_ticket_candi);
        btn_ticket_spinx=findViewById(R.id.btn_ticket_spinx);
        btn_ticket_monas=findViewById(R.id.btn_ticket_monas);

        btn_to_profile = findViewById(R.id.btn_to_profile);

        photo_home_user=findViewById(R.id.photo_home_user);
        user_balance= findViewById(R.id.user_balance);
        username =findViewById(R.id.username);
        bio=findViewById(R.id.bio);

        reference= FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username.setText(dataSnapshot.child("username").getValue().toString());
                bio.setText((dataSnapshot.child("bio").getValue().toString()));
                user_balance.setText("US$ " + dataSnapshot.child("user_balance").getValue().toString());
                Picasso.with(HomeActivity.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo_home_user);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(HomeActivity.this,MyProfileActivity.class);
                startActivity(gotoprofile);
            }
        });

        btn_ticket_pisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeActivity.this,TicketDetailActivity.class);
                gotopisaticket.putExtra("jenis_tiket","Pisa");
                startActivity(gotopisaticket);
            }
        });

        btn_ticket_tori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeActivity.this,TicketDetailActivity.class);
                gotopisaticket.putExtra("jenis_tiket","Torri");
                startActivity(gotopisaticket);
            }
        });

        btn_ticket_pagoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeActivity.this,TicketDetailActivity.class);
                gotopisaticket.putExtra("jenis_tiket","Pagoda");
                startActivity(gotopisaticket);
            }
        });

        btn_ticket_candi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeActivity.this,TicketDetailActivity.class);
                gotopisaticket.putExtra("jenis_tiket","Candi");
                startActivity(gotopisaticket);
            }
        });

        btn_ticket_spinx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeActivity.this,TicketDetailActivity.class);
                gotopisaticket.putExtra("jenis_tiket","Sphinx");
                startActivity(gotopisaticket);
            }
        });

        btn_ticket_monas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeActivity.this,TicketDetailActivity.class);
                gotopisaticket.putExtra("jenis_tiket","Monas");
                startActivity(gotopisaticket);
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences=getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new=sharedPreferences.getString(username_key,  "");
    }
}