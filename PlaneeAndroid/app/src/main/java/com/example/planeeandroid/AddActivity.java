package com.example.planeeandroid;

import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    private TextView myDisplayDate;
    private DatePickerDialog.OnDateSetListener myDateSetListener;
    private MyDBAdapter myDbAdapter;
    private int counter;
    private int counterName;
    private int counterMagasin;
    private int counterUrl;
    private LinearLayout TaskLayout;
    private LinearLayout TachesList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add);
        final Context context = this;
        counter = 0;
        counterName = 0;
        counterMagasin = 100;
        counterUrl = 200;
        myDbAdapter = new MyDBAdapter(context);
        myDbAdapter.open();
        myDisplayDate = findViewById(R.id.DatePick);
        Button ajoutEvent = findViewById(R.id.addEvent);
        ajoutEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText EventName = findViewById(R.id.NomEvent);
                if ((!(EventName.getText().toString().equals(""))) && (!(myDisplayDate.getText().toString().equals(R.string.PickDate)))) {
                    ArrayList<Tache> taches = new ArrayList<Tache>();
                    for (int i = 0; i < counter; i++) {
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
                        taches.add(TacheInput);
                        Log.i("name", TacheInput.getNom());
                        Log.i("Magasin", TacheInput.getNomMagasin());
                        Log.i("URL", TacheInput.getSiteMagasin());
                    }
                    counter = 0;
                    counterName = 0;
                    counterMagasin = 100;
                    counterUrl = 200;
                    Evenement evenement = new Evenement(0, EventName.getText().toString(), myDisplayDate.getText().toString(), taches);
                    myDbAdapter.InsertUnEvent(evenement);
                    Toast.makeText(context, R.string.add, Toast.LENGTH_LONG).show();
                    Log.i("Insert", "Insert OK");
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(context, R.string.MissNameDate, Toast.LENGTH_LONG).show();
                }
            }
        });
        //Ajouter une tâche
        Button ajoutTask = findViewById(R.id.NewTache);
        TachesList = findViewById(R.id.Taches);
        ajoutTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View Myroot = getLayoutInflater().inflate(R.layout.task_layout, null);
                LinearLayout TaskModel = Myroot.findViewById(R.id.TacheLayout);
                EditText TaskName = Myroot.findViewById(R.id.Taskname);
                TaskName.setId(counterName);
                EditText TaskMagasin = Myroot.findViewById(R.id.TaskMagasin);
                TaskMagasin.setId(counterMagasin);
                EditText TaskURL = Myroot.findViewById(R.id.TaskURLMagasin);
                TaskURL.setId(counterUrl);
                counterName += 1;
                counterMagasin += 1;
                counterUrl += 1;
                counter += 1;
                TachesList.addView(TaskModel);
            }
        });
        //Boite de Dialogue du calendrier
        myDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog, myDateSetListener, year, month, day);
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
    }

    public void onBackPressed() {
        Intent intentToHome = new Intent(AddActivity.this, MainActivity.class);
        startActivity(intentToHome);
        finish();
    }

}
