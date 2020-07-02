package com.example.indeksadministrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Semestr extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semestr);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userEmail = firebaseUser.getEmail();

        listView = findViewById(R.id.listViewSemestr);

        final String[] semestrs = {"Semestr_1", "Semestr_2", "Semestr_3", "Semestr_4", "Semestr_5", "Semestr_6", "Semestr_7"};
        final String kierunek = getIntent().getExtras().get("Kierunek").toString();

        if (kierunek.equals("Informatyka Stosowana")) {
            arrayAdapter = new ArrayAdapter<String>(Semestr.this, R.layout.study_info, R.id.textViewStudyInfo, semestrs);
            listView.setAdapter(arrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (userEmail.equals("admin@admin.com")) {
                        Intent subjectList = new Intent(Semestr.this, AdminSubjectList.class);
                        subjectList.putExtra("Semestr", semestrs[position]);
                        subjectList.putExtra("Kierunek", kierunek);
                        startActivity(subjectList);
                    } else {
                        Intent subjectList = new Intent(Semestr.this, TeacherSubjectList.class);
                        subjectList.putExtra("Semestr", semestrs[position]);
                        subjectList.putExtra("Kierunek", kierunek);
                        startActivity(subjectList);
                    }
                }
            });
        } else {
            Toast.makeText(Semestr.this, "Brak danych", Toast.LENGTH_SHORT).show();
        }
    }
}
