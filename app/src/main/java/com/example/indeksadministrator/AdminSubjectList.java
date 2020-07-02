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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminSubjectList extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_subject_list);

        listView = findViewById(R.id.listViewSubjectList);

        final String semestr = getIntent().getExtras().get("Semestr").toString();
        final String kierunek = getIntent().getExtras().get("Kierunek").toString();

        if (semestr.equals("Semestr_1")) {
            final String[] semestr_1 = {"Wstęp do informatyki", "WF", "Matematyka I", "Lektorat języka angielskiego I", "Ochrona własności intelektualnej", "Podstawy użytkowania systemów komputerowych",
                    "Wstęp do pomiarów i automatyki", "Wstęp do programowania", "ZALICZENIE SEMESTRU"};

            arrayAdapter = new ArrayAdapter<String>(AdminSubjectList.this, R.layout.study_info, R.id.textViewStudyInfo, semestr_1);
            listView.setAdapter(arrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent adminStudentsList = new Intent(AdminSubjectList.this, Students.class);
                    adminStudentsList.putExtra("Przedmiot", semestr_1[position]);
                    adminStudentsList.putExtra("Semestr", semestr);
                    adminStudentsList.putExtra("Kierunek", kierunek);
                    startActivity(adminStudentsList);
                }
            });
        }
    }
}
