package com.example.indeksadministrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Students extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseListAdapter adapter;
    private String zaliczenie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        ListView listViewStudentsList = findViewById(R.id.listViewStudents);

        final String kierunek = getIntent().getExtras().get("Kierunek").toString();
        final String przedmiot = getIntent().getExtras().get("Przedmiot").toString();
        final String semestr = getIntent().getExtras().get("Semestr").toString();

        Query query = FirebaseDatabase.getInstance().getReference().child("Grades").child(kierunek).child("Students_Info");
        FirebaseListOptions<StudentsInfo> students = new FirebaseListOptions.Builder<StudentsInfo>()
                .setLayout(R.layout.user_info)
                .setLifecycleOwner(Students.this)
                .setQuery(query, StudentsInfo.class)
                .build();

        adapter = new FirebaseListAdapter(students) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView name = v.findViewById(R.id.textViewNameUser);
                TextView surname = v.findViewById(R.id.textViewSurnameUser);
                TextView acount = v.findViewById(R.id.textViewUser);

                StudentsInfo studentsInfo = (StudentsInfo) model;
                name.setText("Imię: " + studentsInfo.getName().toString());
                surname.setText("Nazwisko: " + studentsInfo.getSurname().toString());
                acount.setText("ID: " + studentsInfo.getID().toString());

            }
        };
        listViewStudentsList.setAdapter(adapter);

        listViewStudentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StudentsInfo student = (StudentsInfo) parent.getItemAtPosition(position);
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Grades").child(kierunek).child(semestr).child(przedmiot).child(student.getID().toString());
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (przedmiot.equals("ZALICZENIE SEMESTRU")) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(Students.this);
                            LayoutInflater layoutInflater = Students.this.getLayoutInflater();
                            View view = layoutInflater.inflate(R.layout.enter_included, null);

                            final Spinner included = view.findViewById(R.id.spinnerIncluded);
                            ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<CharSequence>(Students.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.zaliczenie));
                            included.setAdapter(arrayAdapter);

                            zaliczenie = dataSnapshot.child("Semestr zaliczony?").getValue().toString();

                            int position = arrayAdapter.getPosition(zaliczenie);
                            included.setSelection(position);

                            builder.setView(view)
                                    .setTitle("SEMESTR ZALICZONY?")
                                    .setNegativeButton("ANULUJ", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .setPositiveButton("ZATWIERDŹ", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            databaseReference.child("Semestr zaliczony?").setValue(String.valueOf(included.getSelectedItem()));
                                            Toast.makeText(Students.this, "Dane zostały zapisane", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                            builder.create().show();

                        } else if (przedmiot.equals("Lektorat języka angielskiego I")) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(Students.this);
                            LayoutInflater layoutInflater = Students.this.getLayoutInflater();
                            View view = layoutInflater.inflate(R.layout.enter_included, null);

                            final Spinner included = view.findViewById(R.id.spinnerIncluded);
                            ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<CharSequence>(Students.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.grades));
                            included.setAdapter(arrayAdapter);

                            zaliczenie = dataSnapshot.child("Lektorat").getValue().toString();

                            int position = arrayAdapter.getPosition(zaliczenie);
                            included.setSelection(position);

                            builder.setView(view)
                                    .setTitle("WPROWADŹ OCENĘ")
                                    .setNegativeButton("ANULUJ", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .setPositiveButton("ZATWIERDŹ", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            databaseReference.child("Lektorat").setValue(String.valueOf(included.getSelectedItem()));
                                            Toast.makeText(Students.this, "Dane zostały zapisane", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                            builder.create().show();
                        } else if (przedmiot.equals("Matematyka I")) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(Students.this);
                            LayoutInflater layoutInflater = Students.this.getLayoutInflater();
                            View view = layoutInflater.inflate(R.layout.enter_included, null);

                            final Spinner included = view.findViewById(R.id.spinnerIncluded);
                            ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<CharSequence>(Students.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.grades));
                            included.setAdapter(arrayAdapter);

                            zaliczenie = dataSnapshot.child("Konwersatorium").getValue().toString();

                            int position = arrayAdapter.getPosition(zaliczenie);
                            included.setSelection(position);

                            builder.setView(view)
                                    .setTitle("WPROWADŹ OCENĘ")
                                    .setNegativeButton("ANULUJ", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .setPositiveButton("ZATWIERDŹ", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            databaseReference.child("Konwersatorium").setValue(String.valueOf(included.getSelectedItem()));
                                            Toast.makeText(Students.this, "Dane zostały zapisane", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                            builder.create().show();
                        } else if (przedmiot.equals("Ochrona własności intelektualnej")) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(Students.this);
                            LayoutInflater layoutInflater = Students.this.getLayoutInflater();
                            View view = layoutInflater.inflate(R.layout.enter_included, null);

                            final Spinner included = view.findViewById(R.id.spinnerIncluded);
                            ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<CharSequence>(Students.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.grades));
                            included.setAdapter(arrayAdapter);

                            zaliczenie = dataSnapshot.child("Wykład").getValue().toString();

                            int position = arrayAdapter.getPosition(zaliczenie);
                            included.setSelection(position);

                            builder.setView(view)
                                    .setTitle("WPROWADŹ OCENĘ")
                                    .setNegativeButton("ANULUJ", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .setPositiveButton("ZATWIERDŹ", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            databaseReference.child("Wykład").setValue(String.valueOf(included.getSelectedItem()));
                                            Toast.makeText(Students.this, "Dane zostały zapisane", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                            builder.create().show();
                        } else if (przedmiot.equals("Podstawy użytkowania systemów komputerowych") || przedmiot.equals("Wstęp do pomiarów i automatyki")) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(Students.this);
                            LayoutInflater layoutInflater = Students.this.getLayoutInflater();
                            View view = layoutInflater.inflate(R.layout.enter_included, null);

                            final Spinner included = view.findViewById(R.id.spinnerIncluded);
                            ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<CharSequence>(Students.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.grades));
                            included.setAdapter(arrayAdapter);

                            zaliczenie = dataSnapshot.child("Laboratorium").getValue().toString();

                            int position = arrayAdapter.getPosition(zaliczenie);
                            included.setSelection(position);

                            builder.setView(view)
                                    .setTitle("WPROWADŹ OCENĘ")
                                    .setNegativeButton("ANULUJ", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .setPositiveButton("ZATWIERDŹ", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            databaseReference.child("Laboratorium").setValue(String.valueOf(included.getSelectedItem()));
                                            Toast.makeText(Students.this, "Dane zostały zapisane", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                            builder.create().show();
                        } else {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(Students.this);
                            LayoutInflater layoutInflater = Students.this.getLayoutInflater();
                            View view = layoutInflater.inflate(R.layout.enter_grades, null);

                            final Spinner lab = view.findViewById(R.id.spinnerLab);
                            final Spinner wyklad = view.findViewById(R.id.spinnerWyklad);
                            final Spinner okm = view.findViewById(R.id.spinnerOKM);

                            ArrayAdapter<CharSequence> arrayLab = new ArrayAdapter<CharSequence>(Students.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.grades));
                            ArrayAdapter<CharSequence> arrayWyklad = new ArrayAdapter<CharSequence>(Students.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.grades));
                            ArrayAdapter<CharSequence> arrayOKM = new ArrayAdapter<CharSequence>(Students.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.grades));

                            lab.setAdapter(arrayLab);
                            wyklad.setAdapter(arrayWyklad);
                            okm.setAdapter(arrayOKM);

                            String laboratory = dataSnapshot.child("Laboratorium").getValue().toString();
                            String lecture = dataSnapshot.child("Wykład").getValue().toString();
                            String module = dataSnapshot.child("OKM").getValue().toString();

                            int position_lab = arrayLab.getPosition(laboratory);
                            lab.setSelection(position_lab);

                            int position_lecture = arrayWyklad.getPosition(lecture);
                            wyklad.setSelection(position_lecture);

                            int position_module = arrayOKM.getPosition(module);
                            okm.setSelection(position_module);

                            builder.setView(view)
                                    .setTitle("WPROWADŹ OCENĘ")
                                    .setNegativeButton("ANULUJ", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .setPositiveButton("ZATWIERDŹ", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            databaseReference.child("Laboratorium").setValue(String.valueOf(lab.getSelectedItem()));
                                            databaseReference.child("Wykład").setValue(wyklad.getSelectedItem());
                                            databaseReference.child("OKM").setValue(okm.getSelectedItem());

                                            Toast.makeText(Students.this, "Dane zostały zapisane", Toast.LENGTH_SHORT).show();


                                        }
                                    });
                            builder.create().show();
                        }
                    }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
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
    }


