package com.fahmi.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class TicketCheckOutActivity extends AppCompatActivity {
    Button btn_pay_now, btnmines, btnpls;
    TextView textjmlticket, texttotalharga, textmybalance, miskin,nama_wisata,lokasi,ketentuan;
    ImageView notice_uang;
    Integer valueJmlTicket = 1;
    Integer mybalance = 0;
    Integer valuetotalharga = 0;
    Integer valuehargatiket = 0;
    Integer sisa_balance=0;

    DatabaseReference reference,reference2,reference3,reference4;

    String USERNAME_KEY = "usernamekey";
    String username_key="";
    String username_key_new="";

    String date_wisata ="";
    String time_wisata="";

    //generate nomor integer secara random
    //karena kita ingin membuat transaksi secara unik
    Integer nomor_transaksi=new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_check_out);

        getUsernameLocal();

        btnmines = findViewById(R.id.btnmines);
        btnpls = findViewById(R.id.btnpls);
        textjmlticket = findViewById(R.id.textjmlticket);
        btn_pay_now = findViewById(R.id.btn_pay_now);
        texttotalharga = findViewById(R.id.texttotalharga);
        textmybalance = findViewById(R.id.textmybalance);
        notice_uang = findViewById(R.id.notice_uang);

        nama_wisata=findViewById(R.id.nama_wisata);
        lokasi=findViewById(R.id.lokasi);
        ketentuan=findViewById(R.id.ketentuan);

        //set value baru untuk menambahkan komponen
        textjmlticket.setText(valueJmlTicket.toString());


        notice_uang.setVisibility(View.GONE);

        //hide value
        btnmines.animate().alpha(0).setDuration(300).start();
        btnmines.setEnabled(false);

        //mengambil data dari intent
        Bundle bundle =getIntent().getExtras();
        final  String jenis_tiket_baru =bundle.getString("jenis_tiket");

        //mengambil data user dari firebase
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mybalance = Integer.valueOf(snapshot.child("user_balance").getValue().toString());
                textmybalance.setText("US$ " + mybalance+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //mengambil data dari firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                getUsernameLocal();
                //menimpa data yang ada dengan data yang baru

                nama_wisata.setText(snapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(snapshot.child("lokasi").getValue().toString());
                ketentuan.setText(snapshot.child("ketentuan").getValue().toString());

                date_wisata=snapshot.child("date_wisata").getValue().toString();
                time_wisata=snapshot.child("time_wisata").getValue().toString();

                valuehargatiket = Integer.valueOf(snapshot.child("harga_tiket").getValue().toString());

                valuetotalharga=valuehargatiket * valueJmlTicket;
                texttotalharga.setText("US$"+valuetotalharga+"");



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnpls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueJmlTicket+=1;
                textjmlticket.setText(valueJmlTicket.toString());
                if (valueJmlTicket > 1){
                    btnmines.animate().alpha(1).setDuration(300).start();
                    btnmines.setEnabled(true);
                }
                valuetotalharga=valuehargatiket * valueJmlTicket;
                texttotalharga.setText("US$"+valuetotalharga+"");
                if(valuetotalharga > mybalance){
                    btn_pay_now.animate().translationY(250).alpha(0).setDuration(300).start();
                    btn_pay_now.setEnabled(false);
                    textmybalance.setTextColor(Color.parseColor("#D1206B"));
                    notice_uang.setVisibility(View.VISIBLE);

                }
            }
        });

        btnmines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueJmlTicket-=1;
                textjmlticket.setText(valueJmlTicket.toString());
                if (valueJmlTicket < 2){
                    btnmines.animate().alpha(0).setDuration(300).start();
                    btnmines.setEnabled(false);

                }
                valuetotalharga=valuehargatiket * valueJmlTicket;
                texttotalharga.setText("US$"+valuetotalharga+"");
                if(valuetotalharga < mybalance){
                    btn_pay_now.animate().translationY(0).alpha(1).setDuration(300).start();
                    btn_pay_now.setEnabled(true);
                    textmybalance.setTextColor(Color.parseColor("#203DD1"));
                    notice_uang.setVisibility(View.GONE);

                }
            }
        });


        btn_pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menyimpan data user kepada firebase dan membuat table baru my ticket
                reference3 = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(username_key_new).child(nama_wisata.getText().toString()+ nomor_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("jumlah_tiket").setValue(valueJmlTicket.toString());
                        reference3.getRef().child("date_wisata").setValue(date_wisata);
                        reference3.getRef().child("time_wisata").setValue(time_wisata);
                        reference3.getRef().child("id_ticket").setValue(nama_wisata.getText().toString()+nomor_transaksi);

                        Intent gotosuccesbuyticket = new Intent(TicketCheckOutActivity.this,SuccesBuyTicketActivity.class);
                        startActivity(gotosuccesbuyticket);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                //update data balance kepada user yang sedang login saat ini
                reference4=FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        sisa_balance= mybalance-valuetotalharga;
                        reference4.getRef().child("user_balance").setValue(sisa_balance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}