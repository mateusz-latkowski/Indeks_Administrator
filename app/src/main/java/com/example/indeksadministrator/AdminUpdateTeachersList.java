package com.example.indeksadministrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AdminUpdateTeachersList extends AppCompatActivity {

    private ListView listView;
    private FirebaseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_teachers_list);

        listView = findViewById(R.id.listViewTeachers);
        Query query = FirebaseDatabase.getInstance().getReference().child("Teachers").child("Teachers_Info");
        FirebaseListOptions<TeachersInformation> options = new FirebaseListOptions.Builder<TeachersInformation>()
                .setLayout(R.layout.user_info)
                .setLifecycleOwner(AdminUpdateTeachersList.this)
                .setQuery(query, TeachersInformation.class)
                .build();

        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView name = v.findViewById(R.id.textViewNameUser);
                TextView surname = v.findViewById(R.id.textViewSurnameUser);
                TextView acount = v.findViewById(R.id.textViewUser);

                TeachersInformation teachersInformation = (TeachersInformation) model;
                name.setText("Imię: " + teachersInformation.getName().toString());
                surname.setText("Nazwisko: " + teachersInformation.getSurname().toString());
                acount.setText("Status konta: " + teachersInformation.getActivateAccount().toString());

            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent teachersUpdate = new Intent(AdminUpdateTeachersList.this, AdminUpdateTeachers.class);
                TeachersInformation teachersInformation = (TeachersInformation) parent.getItemAtPosition(position);
                teachersUpdate.putExtra("Imię", teachersInformation.getName());
                teachersUpdate.putExtra("Nazwisko", teachersInformation.getSurname());
                teachersUpdate.putExtra("Tytuł", teachersInformation.getAcademicTitle());
                teachersUpdate.putExtra("Pokój", teachersInformation.getRoom());
                teachersUpdate.putExtra("Numer telefonu", teachersInformation.getPhoneNumber());
                teachersUpdate.putExtra("Email", teachersInformation.getEmail());
                teachersUpdate.putExtra("UserID", teachersInformation.getUserID());
                teachersUpdate.putExtra("Status konta", teachersInformation.getActivateAccount());

                startActivity(teachersUpdate);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public boolean OnCreateOptionsMenu(Menu menu) {
        return true;
    }
}
