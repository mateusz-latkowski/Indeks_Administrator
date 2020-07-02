package com.example.indeksadministrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminUpdateTeachers extends AppCompatActivity implements  View.OnClickListener{

    private EditText Name;
    private EditText Surname;
    private EditText AcademicTitle;
    private EditText Room;
    private EditText PhoneNumber;
    private EditText Email;
    private EditText UserID;
    private Switch ActivateAccount;

    private Button buttonUpdate;

    private String new_status;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_teachers);

        Name = findViewById(R.id.editTextName);
        Surname = findViewById(R.id.editTextSurname);
        AcademicTitle = findViewById(R.id.editTextAcademicTitle);
        Room = findViewById(R.id.editTextRoom);
        PhoneNumber = findViewById(R.id.editTextPhoneNumber);
        Email = findViewById(R.id.editTextEmail);
        UserID = findViewById(R.id.editTextUserID);
        ActivateAccount = findViewById(R.id.switchActivateAccount);

        buttonUpdate = findViewById(R.id.buttonUpdateTeacherInfo);

        String userID = getIntent().getExtras().get("UserID").toString();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Teachers").child("Teachers_Info").child(userID);

        Name.setText(getIntent().getStringExtra("Imię"));
        Surname.setText(getIntent().getStringExtra("Nazwisko"));
        AcademicTitle.setText(getIntent().getStringExtra("Tytuł"));
        Room.setText(getIntent().getStringExtra("Pokój"));
        PhoneNumber.setText(getIntent().getStringExtra("Numer telefonu"));
        Email.setText(getIntent().getStringExtra("Email"));
        Email.setEnabled(false);
        UserID.setText(getIntent().getStringExtra("UserID"));
        UserID.setEnabled(false);

        String account_status = getIntent().getStringExtra("Status konta");

        if (account_status.equals("NIE AKTYWNE")) {
            ActivateAccount.setChecked(false);
        } else if (account_status.equals("AKTYWNE")) {
            ActivateAccount.setChecked(true);
        }

        ActivateAccount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new_status = "AKTYWNE";
                } else {
                    new_status = "NIE AKTYWNE";
                }
            }
        });

        buttonUpdate.setOnClickListener(this);
    }

    private void update() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("Name").setValue(Name.getText().toString());
                dataSnapshot.getRef().child("Surname").setValue(Surname.getText().toString());
                dataSnapshot.getRef().child("AcademicTitle").setValue(AcademicTitle.getText().toString());
                dataSnapshot.getRef().child("Room").setValue(Room.getText().toString());
                dataSnapshot.getRef().child("PhoneNumber").setValue(PhoneNumber.getText().toString());
                dataSnapshot.getRef().child("Email").setValue(Email.getText().toString());
                dataSnapshot.getRef().child("UserID").setValue(UserID.getText().toString());
                dataSnapshot.getRef().child("ActivateAccount").setValue(new_status);

                Toast.makeText(AdminUpdateTeachers.this, "Dane zostały zaktualizowane!", Toast.LENGTH_SHORT).show();
                AdminUpdateTeachers.this.finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == buttonUpdate) {
            update();
        }
    }
}
