package com.example.indeksadministrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AdminUpdateSubject extends AppCompatActivity {

    private ListView listView;
    private FirebaseListAdapter firebaseListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_subject);

        listView = findViewById(R.id.listViewAdminUpdateSubject);

        Query query = FirebaseDatabase.getInstance().getReference().child("Teachers").child("Teachers_Info");
        FirebaseListOptions<TeachersInformation> options = new FirebaseListOptions.Builder<TeachersInformation>()
                .setLayout(R.layout.user_info)
                .setLifecycleOwner(AdminUpdateSubject.this)
                .setQuery(query, TeachersInformation.class)
                .build();

        firebaseListAdapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView nameTeacher = v.findViewById(R.id.textViewNameUser);
                TextView surnameTeacher = v.findViewById(R.id.textViewSurnameUser);
                TextView accountTeacher = v.findViewById(R.id.textViewUser);

                TeachersInformation teachersInformation = (TeachersInformation) model;
                nameTeacher.setText("Imię: " + teachersInformation.getName().toString());
                surnameTeacher.setText("Nazwisko: " + teachersInformation.getSurname().toString());
                accountTeacher.setText("Status konta: " + teachersInformation.getActivateAccount().toString());
            }
        };
        listView.setAdapter(firebaseListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent teacherSujectList = new Intent(AdminUpdateSubject.this, AdminUpdateSubjectList.class);
                TeachersInformation teachersInformation = (TeachersInformation) parent.getItemAtPosition(position);
                teacherSujectList.putExtra("UserID", teachersInformation.getUserID());
                startActivity(teacherSujectList);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseListAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseListAdapter.stopListening();
    }
}
