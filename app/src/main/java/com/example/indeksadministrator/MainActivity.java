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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLogin;
    private EditText editTextMail;
    private EditText editTextPassword;
    private TextView textViewSignIn;
    private TextView textViewResetPassword;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        editTextMail = findViewById(R.id.editTextMailLogin);
        editTextPassword = findViewById(R.id.editTextPasswordLogin);
        buttonLogin = findViewById(R.id.buttonSignIn);
        textViewSignIn = findViewById(R.id.textViewSignIn);
        textViewResetPassword = findViewById(R.id.textViewRecoveryPass);

        buttonLogin.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);
        textViewResetPassword.setOnClickListener(this);
    }

    private void userLogin() {
        final String email = editTextMail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Wpisz adres e-mail!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Wpisz hasło!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (email.equals("admin@admin.com")) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                finish();
                                startActivity(new Intent(MainActivity.this, AdminHome.class));
                            } else {
                                Toast.makeText(MainActivity.this, "Niepoprawny adres e-mail lub hasło, spróbuj ponownie!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                finish();
                                startActivity(new Intent(MainActivity.this, TeacherHome.class));
                            } else {
                                Toast.makeText(MainActivity.this, "Niepoprawny adres e-mail lub hasło, spróbuj ponownie!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonLogin) {
            userLogin();
        }

        if (v == textViewSignIn) {
            startActivity(new Intent(MainActivity.this, Registration.class));
        }

        if (v == textViewResetPassword) {
            startActivity(new Intent(MainActivity.this, ResetPassword.class));
        }
    }
}
