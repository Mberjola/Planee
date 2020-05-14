package com.example.planeeandroid;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {
    private MyDBAdapter myDataBase;
    private long idEvent;
    private Evenement event;
    private ArrayList<Tache> taches;
    private EditText EventName;
    private TextView myDisplayDate;
    private DatePickerDialog.OnDateSetListener myDateSetListener;
    private EditText Heure;
    private EditText Minutes;

    private LinearLayout TachesList;

    private int counterOld = 0;
    private int countOldTaskNameId = 0;
    private int countOldTaskStoreId = 100;
    private int countOldTaskURLId = 200;

    private int counterNewTaskNameId;
    private int counterNewTaskStoreId;
    private int counterNewTaskURLId;
    private int counterNew;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add);
        myDataBase = new MyDBAdapter(this);
        final Intent intent = getIntent();
        idEvent = intent.getLongExtra("EventId", 0);
        myDataBase.open();
        event = myDataBase.getEvent(idEvent);
        EventName = findViewById(R.id.NomEvent);
        EventName.setText(event.getNom());
        myDisplayDate = findViewById(R.id.DatePick);
        myDisplayDate.setText(event.getDateLimite());
        //Boite de Dialogue du calendrier
        myDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(UpdateActivity.this, android.R.style.Theme_Holo_Light_Dialog, myDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });
        myDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + (month < 10 ? "0" + month : month) + "/" + year;
                myDisplayDate.setText(date);
            }
        };
        String heure = "";
        heure += event.getHeure().charAt(0);
        heure += event.getHeure().charAt(1);
        String minutes = "";
        minutes += event.getHeure().charAt(3);
        minutes += event.getHeure().charAt(4);
        Heure = findViewById(R.id.TimeHeure);
        Heure.setText(heure);
        Minutes = findViewById(R.id.TimeMin);
        Minutes.setText(minutes);
        TachesList = findViewById(R.id.Taches);
        taches = event.getTaches();
        counterOld = 0;
        countOldTaskNameId = 0;
        countOldTaskStoreId = 100;
        countOldTaskURLId = 200;
        for (int i = 0; i < taches.size(); i++) {
            View Myroot = getLayoutInflater().inflate(R.layout.task_layout, null);
            LinearLayout TaskModel = Myroot.findViewById(R.id.TacheLayout);
            Tache tache = taches.get(i);
            EditText TaskName = Myroot.findViewById(R.id.Taskname);
            TaskName.setText(tache.getNom());
            TaskName.setId(countOldTaskNameId);
            EditText TaskMagasin = Myroot.findViewById(R.id.TaskMagasin);
            TaskMagasin.setText(tache.getNomMagasin());
            TaskMagasin.setId(countOldTaskStoreId);
            EditText TaskURL = Myroot.findViewById(R.id.TaskURLMagasin);
            TaskURL.setText(tache.getSiteMagasin());
            TaskURL.setId(countOldTaskURLId);
            TachesList.addView(TaskModel);
            countOldTaskNameId += 1;
            countOldTaskStoreId += 1;
            countOldTaskURLId += 1;
            counterOld += 1;
        }
        //Ajout de nouvelles tâches
        Button ajoutTask = findViewById(R.id.NewTache);
        TachesList = findViewById(R.id.Taches);
        counterNewTaskNameId = 400;
        counterNewTaskStoreId = 500;
        counterNewTaskURLId = 600;
        counterNew = 0;
        ajoutTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View Myroot = getLayoutInflater().inflate(R.layout.task_layout, null);
                LinearLayout TaskModel = Myroot.findViewById(R.id.TacheLayout);
                EditText TaskName = Myroot.findViewById(R.id.Taskname);
                TaskName.setId(counterNewTaskNameId);
                EditText TaskMagasin = Myroot.findViewById(R.id.TaskMagasin);
                TaskMagasin.setId(counterNewTaskStoreId);
                EditText TaskURL = Myroot.findViewById(R.id.TaskURLMagasin);
                TaskURL.setId(counterNewTaskURLId);
                counterNewTaskNameId += 1;
                counterNewTaskStoreId += 1;
                counterNewTaskURLId += 1;
                counterNew += 1;
                TachesList.addView(TaskModel);
            }
        });
        Button updateButton = findViewById(R.id.addEvent);
        updateButton.setText(R.string.UpdateEvent);
        //Mise à jour des éléments
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Tache> OldTaskUpdate = new ArrayList<>();
                for (int i = 0; i < counterOld; i++) {
                    Tache TacheInput = new Tache();
                    EditText textName = (EditText) findViewById(i);
                    EditText textMagasin = (EditText) findViewById(100 + i);
                    EditText textUrl = (EditText) findViewById(200 + i);
                    if (!(textName == null)) {
                        TacheInput.setNom(textName.getText().toString());
                    } else {
                        TacheInput.setNom("");
                    }
                    if (!(textMagasin == null)) {
                        TacheInput.setNomMagasin(textMagasin.getText().toString());
                    } else {
                        TacheInput.setNomMagasin("");
                    }
                    if (!(textUrl == null)) {
                        TacheInput.setSiteMagasin(textUrl.getText().toString());
                    } else {
                        TacheInput.setSiteMagasin("");
                    }
                    OldTaskUpdate.add(TacheInput);
                }
                counterOld = 0;
                counterNewTaskNameId = 0;
                counterNewTaskStoreId = 0;
                counterNewTaskURLId = 0;
                counterNew = 0;
                Evenement evenement = new Evenement(0, EventName.getText().toString(), myDisplayDate.getText().toString(), OldTaskUpdate, Heure.getText().toString() + Heure.getText().toString() + Minutes.getText().toString());
                myDataBase.UpdateEvent(evenement, idEvent);
                Intent IntentToDetail = new Intent(UpdateActivity.this, DetailActivity.class);
                IntentToDetail.putExtra("EventId", idEvent);
                startActivity(IntentToDetail);
                finish();
            }
        });
    }

    public void onBackPressed() {
        Intent intentToDetail = new Intent(UpdateActivity.this, DetailActivity.class);
        intentToDetail.putExtra("EventId", idEvent);
        startActivity(intentToDetail);
        finish();
    }

    @Override
    protected void onDestroy() {
        myDataBase.close();
        super.onDestroy();
    }
}
