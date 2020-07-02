package com.example.indeksadministrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AdminHome extends AppCompatActivity implements View.OnClickListener {

    private Button buttonUpdateSubject;
    private Button buttonUpdateData;
    private Button buttonUpdateGrades;
    private Button buttonSendEmail;
    private Button buttonLogOut;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonUpdateSubject = findViewById(R.id.buttonUpdateSubject);
        buttonUpdateData = findViewById(R.id.buttonUpdateData);
        buttonUpdateGrades = findViewById(R.id.buttonUpdateGrades);
        buttonSendEmail = findViewById(R.id.buttonSendEmail);
        buttonLogOut = findViewById(R.id.buttonLogOut);

        buttonUpdateSubject.setOnClickListener(this);
        buttonUpdateData.setOnClickListener(this);
        buttonUpdateGrades.setOnClickListener(this);
        buttonSendEmail.setOnClickListener(this);
        buttonLogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonUpdateSubject) {
            finish();
            startActivity(new Intent(AdminHome.this, AdminUpdateSubject.class));
        }

        if (v == buttonUpdateData) {
            finish();
            startActivity(new Intent(AdminHome.this, AdminUpdateTeachersList.class));
        }

        if (v == buttonUpdateGrades) {
            finish();
            startActivity(new Intent(AdminHome.this, FieldOfStudy.class));
        }

        if (v == buttonSendEmail) {
            finish();
            startActivity(new Intent(AdminHome.this, SendEmail.class));
        }

        if (v == buttonLogOut) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(AdminHome.this, MainActivity.class));
        }
    }
}
