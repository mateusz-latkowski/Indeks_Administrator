package com.example.indeksadministrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextMail;
    private EditText editTextPassword;
    private TextView textViewSignUp;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(Registration.this, TeacherHome.class));
        }

        buttonRegister = findViewById(R.id.buttonRegistration);
        editTextMail = findViewById(R.id.editTextMailRegistration);

        editTextPassword = findViewById(R.id.editTextPasswordRegistration);
        textViewSignUp = findViewById(R.id.textViewSignUp);

        buttonRegister.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
    }

    private void registerUser() {
        String email = editTextMail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Wpisz adres e-mail!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Wpisz hasło!", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(Registration.this, RegistrationInfo.class));
                        } else {
                            Toast.makeText(Registration.this, "Błąd rejestracji, spróbuj ponownie!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            registerUser();
        }

        if (v == textViewSignUp) {
            startActivity(new Intent(Registration.this, MainActivity.class));
        }
    }
}
