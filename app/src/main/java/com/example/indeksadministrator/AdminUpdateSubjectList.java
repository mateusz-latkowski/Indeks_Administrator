package com.example.indeksadministrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminUpdateSubjectList extends AppCompatActivity {

    TextView textViewLektorat1;
    Switch switchLektorat1;

    TextView textViewMatematyka1;
    Switch switchMatematyka1;

    TextView textViewInformatyka;
    Switch switchInformatyka;

    TextView textViewWF;
    Switch switchWF;

    TextView textViewLektorat2;
    Switch switchLektorat2;

    TextView textViewOchronaWlasnosci;
    Switch switchOW;

    TextView textViewPUSK;
    Switch switchPUSK;

    TextView textViewWDPA;
    Switch switchWDPA;

    TextView textViewWDP;
    Switch switchWDP;

    TextView textViewAP;
    Switch switchAP;

    TextView textViewAK;
    Switch switchAK;

    TextView textViewFIZYKA;
    Switch switchFIZYKA;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_subjects_list);

        firebaseAuth = FirebaseAuth.getInstance();

        textViewLektorat1 = findViewById(R.id.textViewLektorat1);
        textViewMatematyka1 = findViewById(R.id.textViewMatematyka1);
        textViewInformatyka = findViewById(R.id.textViewInformatyka);
        textViewWF = findViewById(R.id.textViewWF);
        textViewLektorat2 = findViewById(R.id.textViewLektorat2);
        textViewOchronaWlasnosci = findViewById(R.id.textViewOchronaWlasnosci);
        textViewPUSK = findViewById(R.id.textViewPUSK);
        textViewWDPA = findViewById(R.id.textViewWDPA);
        textViewWDP = findViewById(R.id.textViewWDP);
        textViewAP = findViewById(R.id.textViewAP);
        textViewAK = findViewById(R.id.textViewAK);
        textViewFIZYKA = findViewById(R.id.textViewFIZYKA);

        switchLektorat1 = findViewById(R.id.switchLektorat1);
        switchMatematyka1 = findViewById(R.id.switchMatematyka1);
        switchInformatyka = findViewById(R.id.switchInformatyka);
        switchWF = findViewById(R.id.switchWF);
        switchLektorat2 = findViewById(R.id.switchLektorat2);
        switchOW = findViewById(R.id.switchOchronaWlasnosci);
        switchPUSK = findViewById(R.id.switchPUSK);
        switchWDPA = findViewById(R.id.switchWDPA);
        switchWDP = findViewById(R.id.switchWDP);
        switchAP = findViewById(R.id.switchAP);
        switchAK = findViewById(R.id.switchAK);
        switchFIZYKA = findViewById(R.id.switchFIZYKA);

        String userID = getIntent().getExtras().get("UserID").toString();

        textViewLektorat1.setText("Lektorat języka angielskiego I");
        textViewMatematyka1.setText("Matematyka I");
        textViewInformatyka.setText("Wstęp do informatyki");
        textViewWF.setText("WF");
        textViewLektorat2.setText("Lektorat języka angielskiego II");
        textViewOchronaWlasnosci.setText("Ochrona własności intelektualnej");
        textViewPUSK.setText("Podstawy użytkowania systemów komputerowych");
        textViewWDPA.setText("Wstęp do pomiarów i automatyki");
        textViewWDP.setText("Wstęp do programowania");
        textViewAP.setText("Algorytmy i programowanie");
        textViewAK.setText("Architektura komputerów");
        textViewFIZYKA.setText("FIZYKA");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Teachers").child("Teachers_Subject").child(userID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                String lektorat1 = dataSnapshot.child("Lektorat języka angielskiego I").getValue().toString();
                String matematyka1 = dataSnapshot.child("Matematyka I").getValue().toString();
                String informatyka = dataSnapshot.child("Wstęp do informatyki").getValue().toString();
                String wf = dataSnapshot.child("WF").getValue().toString();
                String lektorat2 = dataSnapshot.child("Lektorat języka angielskiego II").getValue().toString();
                String ow = dataSnapshot.child("Ochrona własności intelektualnej").getValue().toString();
                String pusk = dataSnapshot.child("Podstawy użytkowania systemów komputerowych").getValue().toString();
                String wdpa = dataSnapshot.child("Wstęp do pomiarów i automatyki").getValue().toString();
                String wdp = dataSnapshot.child("Wstęp do programowania").getValue().toString();
                String ap = dataSnapshot.child("Algorytmy i programowanie").getValue().toString();
                String ak = dataSnapshot.child("Architektura komputerów").getValue().toString();
                String fizyka = dataSnapshot.child("Fizyka").getValue().toString();

                final String[] subject = {lektorat1, matematyka1, informatyka, wf, lektorat2, ow, pusk, wdpa, wdp, ap, ak, fizyka};
                Switch[] switchSubject = {switchLektorat1, switchMatematyka1, switchInformatyka, switchWF, switchLektorat2, switchOW, switchPUSK, switchWDPA, switchWDP, switchAP, switchAK, switchFIZYKA};

                for (int i = 0; i < subject.length; i++) {
                    if (subject[i].equals("1")) {
                        switchSubject[i].setChecked(true);
                    } else {
                        switchSubject[i].setChecked(false);
                    }
                }

                final String[] subjectName = {"Lektorat języka angielskiego I", "Matematyka I", "Wstęp do informatyki", "WF", "Lektorat języka angielskiego II",
                        "Ochrona własności intelektualnej", "Podstawy użytkowania systemów komputerowych", "Wstęp do pomiarów i automatyki", "Wstęp do programowania",
                        "Algorytmy i programowanie", "Architektura komputerów", "Fizyka"};

                for (int j = 0; j < switchSubject.length; j++) {
                    final int finalJ = j;
                    switchSubject[j].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                dataSnapshot.getRef().child(subjectName[finalJ]).setValue("1");
                            } else {
                                dataSnapshot.getRef().child(subjectName[finalJ]).setValue("0");
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
