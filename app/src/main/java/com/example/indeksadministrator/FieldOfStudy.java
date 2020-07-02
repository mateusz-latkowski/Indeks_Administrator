package com.example.indeksadministrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class FieldOfStudy extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private EditText filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_of_study);

        final String[] study = {"Administracja", "Architektura informacji", "Arteterapia", "Bezpieczeństwo narodowe i międzynarodowe", "Biofizyka", "Biologia", "Biotechnologia", "Chemia", "Doradztwo filozoficzne i coaching",
                "Doradztwo filozoficzne i publiczne", "Dziennikarstwo i komunikacja społeczna", "Edukacja artystyczna w zakresie sztuk plastycznych", "Edukacja kulturalna", "Ekonofizyka", "Etnologia i antropologia kulturowa",
                "Filologia angielska", "Filologia germańska", "Filologia klasyczna", "Filologia polska", "Informatyka", "Informatyka Stosowana", "Pedagogika", "Psychologia"};

        listView = findViewById(R.id.listViewAdminGrades);
        filter = findViewById(R.id.editTextSearchAdmin);

        arrayAdapter = new ArrayAdapter<String>(FieldOfStudy.this, R.layout.study_info, R.id.textViewStudyInfo, study);
        listView.setAdapter(arrayAdapter);

        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FieldOfStudy.this.arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String getItem = (String) parent.getItemAtPosition(position);
                Intent kierunek = new Intent(FieldOfStudy.this, Semestr.class);
                kierunek.putExtra("Kierunek", getItem);
                startActivity(kierunek);
            }
        });
    }
}
