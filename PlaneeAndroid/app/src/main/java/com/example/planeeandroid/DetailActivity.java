package com.example.planeeandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details);
        MyDBAdapter myDataBase = new MyDBAdapter(this);
        Intent intent = getIntent();
        Long idEvent = intent.getLongExtra("EventId", 0);
        myDataBase.open();
        Evenement event = myDataBase.getEvent(idEvent);
        myDataBase.close();
        TextView Name = findViewById(R.id.Details_Name_Event);
        TextView Date = findViewById(R.id.Details_Date_Event);
        Name.setText(event.getNom());
        Date.setText(event.getDateLimite());
        Log.i("Evenement ID -----", "" + idEvent);
        final ListView maListView2 = findViewById(R.id.Details_Task_Liste);
        ArrayList<Tache> taches = event.getTaches();
        final Tache[] TacheArray = new Tache[taches.size()];
        for (int i = 0; i < TacheArray.length; i++) {
            TacheArray[i] = taches.get(i);
            Log.i("Tache1", TacheArray[i].getNom());
            Log.i("Tache2", TacheArray[i].getNomMagasin());
            Log.i("Tache3", TacheArray[i].getSiteMagasin());
        }
        Log.i("lengthTab", "" + TacheArray.length);
        Log.i("listTab", "" + taches.size());
        final MyArrayDetailsAdapter myArrayDetailsAdapter = new MyArrayDetailsAdapter(this, TacheArray);
        maListView2.setAdapter(myArrayDetailsAdapter);
    }

    public void onBackPressed() {
        Intent intentToHome = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intentToHome);
        finish();
    }
}
