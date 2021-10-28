package com.fahmi.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    TextView btn_new_account;
    Button btn_sign_in;
    EditText xusername,xpassword;

    DatabaseReference reference;

    String USERNAME_KEY="usernamekey";
    String username_key="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btn_new_account = findViewById(R.id.btn_new_acoount);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        xusername=findViewById(R.id.xusername);
        xpassword=findViewById(R.id.xpassword);

        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregisterone = new Intent(SignInActivity.this, RegisterOneActivity.class);
                startActivity(gotoregisterone);
            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String username= xusername.getText().toString();
                final String password=xpassword.getText().toString();

                if ( username.isEmpty()) {//tidak pake boolena
                    Toast.makeText(getApplicationContext(), " Username Kosong! \n Kek Hati awkowko", Toast.LENGTH_SHORT).show();
                    //ubah state/button menjadi loading
                    btn_sign_in.setEnabled(true);
                    btn_sign_in.setText("SIGN IN");
                }else {
                    if (password.isEmpty()){
                        Toast.makeText(getApplicationContext(), " Password Kosong ", Toast.LENGTH_SHORT).show();
                        btn_sign_in.setEnabled(true);
                        btn_sign_in.setText("SIGN IN");
                    }else {
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
                        //ubah state/button menjadi loading
                        btn_sign_in.setEnabled(false);
                        btn_sign_in.setText("Loading .....");

                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    //ambil data password firebase
                                    String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();

                                    //validasi password firebase
                                    if (password.equals(passwordFromFirebase)) {

                                        //simpan username key kepada local
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, xusername.getText().toString());
                                        editor.apply();

                                        //pindah acrivity
                                        Intent gotohome = new Intent(SignInActivity.this, HomeActivity.class);
                                        startActivity(gotohome);
                                    } else {
                                        Toast.makeText(getApplicationContext(), " Password Salah! \n PASSWORD AJA LUPA APALAGI BERCINTA \n AWKOWKOW :* ", Toast.LENGTH_SHORT).show();
                                        //ubah state/button menjadi loading
                                        btn_sign_in.setEnabled(true);
                                        btn_sign_in.setText("SIGN IN");
                                    }
                                    Toast.makeText(getApplicationContext(), "Username Ada!", Toast.LENGTH_SHORT).show();
                                    //pindah activity


                                } else {
                                    Toast.makeText(getApplicationContext(), "Username tidak tersedia!", Toast.LENGTH_SHORT).show();
                                    //ubah state/button menjadi loading
                                    btn_sign_in.setEnabled(true);
                                    btn_sign_in.setText("SIGN IN");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
            }
        });
    }
}