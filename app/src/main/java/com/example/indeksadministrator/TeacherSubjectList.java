package com.example.indeksadministrator;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class TeacherSubjectList extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private DatabaseReference databaseReference;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_subject_list);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Teachers").child("Teachers_Subject").child(userID);

        final String semestr = getIntent().getExtras().get("Semestr").toString();
        final String kierunek = getIntent().getExtras().get("Kierunek").toString();


        listView = findViewById(R.id.listViewSubjectList);

        if (semestr.equals("Semestr_1")) {
            final String[] semestr_1 = {"Wstęp do informatyki", "WF", "Matematyka I", "Lektorat języka angielskiego I", "Ochrona własności intelektualnej", "Podstawy użytkowania systemów komputerowych",
            "Wstęp do pomiarów i automatyki", "Wstęp do programowania"};
            arrayAdapter = new ArrayAdapter<String>(TeacherSubjectList.this, R.layout.study_info, R.id.textViewStudyInfo, semestr_1);
            listView.setAdapter(arrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String subject_permissions = Objects.requireNonNull(dataSnapshot.child(semestr_1[position]).getValue()).toString();
                            if (subject_permissions.equals("1")) {
                                Intent studentsList = new Intent(TeacherSubjectList.this, Students.class);
                                studentsList.putExtra("Przedmiot", semestr_1[position]);
                                studentsList.putExtra("Semestr", semestr);
                                studentsList.putExtra("Kierunek", kierunek);
                                startActivity(studentsList);
                            } else {
                                Toast.makeText(TeacherSubjectList.this, "Brak uprawnień", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });
        }
    }
}
