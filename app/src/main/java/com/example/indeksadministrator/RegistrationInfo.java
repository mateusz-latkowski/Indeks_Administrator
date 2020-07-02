package com.example.indeksadministrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationInfo extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextAcademicTitle;
    private EditText editTextRoom;
    private EditText editTextPhoneNumber;
    private Button buttonSaveInfo;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_info);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(RegistrationInfo.this, MainActivity.class));
        }

        user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Teachers");

        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextRoom = findViewById(R.id.editTextRoom);
        editTextAcademicTitle = findViewById(R.id.editTextAcademicTitle);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

        buttonSaveInfo = findViewById(R.id.buttonSaveInfo);
        buttonSaveInfo.setOnClickListener(this);
    }

    private void saveTeacherInformation() {
        String email = user.getEmail();
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();
        String academictitle = editTextAcademicTitle.getText().toString().trim();
        String room = editTextRoom.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();

        String activateAccount = "NIE AKTYWNE";

        TeachersInformation teachersInformation = new TeachersInformation(name, surname, academictitle, room, phoneNumber, email, userID, activateAccount);
        databaseReference.child("Teachers_Info").child(userID).setValue(teachersInformation);

        databaseReference.child("Teachers_Subject").child(userID).child("Lektorat języka angielskiego I").setValue("0");
        databaseReference.child("Teachers_Subject").child(userID).child("Matematyka I").setValue("0");
        databaseReference.child("Teachers_Subject").child(userID).child("Wstęp do informatyki").setValue("0");
        databaseReference.child("Teachers_Subject").child(userID).child("WF").setValue("0");
        databaseReference.child("Teachers_Subject").child(userID).child("Lektorat języka angielskiego II").setValue("0");
        databaseReference.child("Teachers_Subject").child(userID).child("Ochrona własności intelektualnej").setValue("0");
        databaseReference.child("Teachers_Subject").child(userID).child("Podstawy użytkowania systemów komputerowych").setValue("0");
        databaseReference.child("Teachers_Subject").child(userID).child("Wstęp do pomiarów i automatyki").setValue("0");
        databaseReference.child("Teachers_Subject").child(userID).child("Wstęp do programowania").setValue("0");
        databaseReference.child("Teachers_Subject").child(userID).child("Algorytmy i programowanie").setValue("0");
        databaseReference.child("Teachers_Subject").child(userID).child("Architektura komputerów").setValue("0");
        databaseReference.child("Teachers_Subject").child(userID).child("Fizyka").setValue("0");

        if (!name.isEmpty() && !surname.isEmpty() && !academictitle.isEmpty() && !room.isEmpty() && !phoneNumber.isEmpty()) {
            Toast.makeText(RegistrationInfo.this, "Zapisywanie danych...", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(RegistrationInfo.this, MainActivity.class));
        } else {
            Toast.makeText(RegistrationInfo.this, "Pola nie mogą być puste!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonSaveInfo) {
            saveTeacherInformation();
        }
    }
}
