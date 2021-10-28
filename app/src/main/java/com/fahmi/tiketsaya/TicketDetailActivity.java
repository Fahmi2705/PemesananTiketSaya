package com.fahmi.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TicketDetailActivity extends AppCompatActivity {

    Button btn_buy_ticket;
    TextView title_ticket,location_ticket,photo_spot_ticket,wifi_ticket,festival_ticket,short_desc_ticket;
    ImageView header_ticket_detail;

    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        header_ticket_detail=findViewById(R.id.header_ticket_detail);

        title_ticket = findViewById(R.id.title_ticket);
        photo_spot_ticket = findViewById(R.id.photo_spot_ticket);
        location_ticket = findViewById(R.id.location_ticket);
        wifi_ticket = findViewById(R.id.wifi_ticket);
        festival_ticket = findViewById(R.id.festival_ticket);
        short_desc_ticket = findViewById(R.id.short_desc_ticket);


        //mengambil data dari firebase dari intent
        Bundle bundle = getIntent().getExtras();
        String jenis_tiket_baru =bundle.getString("jenis_tiket");

        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            //menimpa data yang ada dengan data yang baru
                location_ticket.setText(snapshot.child("lokasi").getValue().toString());
                title_ticket.setText(snapshot.child("nama_wisata").getValue().toString());
                photo_spot_ticket.setText(snapshot.child("is_photo_spot").getValue().toString());
                wifi_ticket.setText(snapshot.child("is_wifi").getValue().toString());
                festival_ticket.setText(snapshot.child("is_festival").getValue().toString());
                short_desc_ticket.setText(snapshot.child("short_desc").getValue().toString());

                Picasso.with(TicketDetailActivity.this).load(snapshot.child("url_thumbnail").getValue().toString()).centerCrop().fit().into(header_ticket_detail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Toast.makeText(getApplicationContext(), "Jenis Tiket : "+jenis_tiket_baru, Toast.LENGTH_SHORT).show();

        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotobuyticket = new Intent(TicketDetailActivity.this,TicketCheckOutActivity.class);
                //meletakan data pada intent
                gotobuyticket.putExtra("jenis_tiket",jenis_tiket_baru);
                startActivity(gotobuyticket);
            }
        });
    }
}