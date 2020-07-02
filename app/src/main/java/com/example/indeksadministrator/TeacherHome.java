package com.example.indeksadministrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherHome extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private Button buttonUpdateGrades;
    private Button buttonSendEmail;
    private Button buttonUpdateInfo;
    private Button buttonLogOut;

    private EditText editTextRoomUpdate;
    private EditText editTextPhoneNumberUpdate;

    private FirebaseUser user;

    private String userID;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        buttonUpdateGrades = findViewById(R.id.buttonGrades);
        buttonSendEmail = findViewById(R.id.buttonSendMail);
        buttonUpdateInfo = findViewById(R.id.buttonInformation);
        buttonLogOut = findViewById(R.id.buttonLogout);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(TeacherHome.this, MainActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Teachers").child("Teachers_Info").child(userID);

        buttonUpdateGrades.setOnClickListener(this);
        buttonSendEmail.setOnClickListener(this);
        buttonUpdateInfo.setOnClickListener(this);
        buttonLogOut.setOnClickListener(this);

        checkStatusAccount();
    }

    private void checkStatusAccount() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String status = dataSnapshot.child("ActivateAccount").getValue().toString();

                if (status.equals("NIE AKTYWNE")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TeacherHome.this);
                    builder.setMessage("Twoje konto jest nieaktywne. \nSkontaktuj się z administratorem!");
                    builder.setTitle("KONTO NIEAKTYWNE");
                    builder.setCancelable(false);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            firebaseAuth.signOut();
                            finish();
                            startActivity(new Intent(TeacherHome.this, MainActivity.class));
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateInfo() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.update_info, null);

        editTextRoomUpdate = view.findViewById(R.id.editTextRoomUpdate);
        editTextPhoneNumberUpdate = view.findViewById(R.id.editTextPhoneNumberUpdate);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String room = dataSnapshot.child("Room").getValue().toString();
                String phone = dataSnapshot.child("PhoneNumber").getValue().toString();

                editTextRoomUpdate.setText(room);
                editTextPhoneNumberUpdate.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        builder.setView(view)
                .setTitle("AKTUALIZUJ INFORMACJE")
                .setNegativeButton("ANULUJ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("AKTUALIZUJ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("Room").setValue(editTextRoomUpdate.getText().toString());
                        databaseReference.child("PhoneNumber").setValue(editTextPhoneNumberUpdate.getText().toString());

                        Toast.makeText(getApplicationContext(), "Dane zostały zaktualizowane!", Toast.LENGTH_SHORT).show();
                    }
                });

        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonLogOut) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(TeacherHome.this, MainActivity.class));
        }

        if (v == buttonUpdateGrades) {
            finish();
            startActivity(new Intent(TeacherHome.this, FieldOfStudy.class));
        }

        if (v == buttonSendEmail) {
            finish();
            startActivity(new Intent(TeacherHome.this, SendEmail.class));
        }

        if (v == buttonUpdateInfo) {
            updateInfo();
        }
    }
}
