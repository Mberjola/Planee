package com.example.planeeandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private MyDBAdapter myDataBase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details);
        myDataBase = new MyDBAdapter(this);
        Intent intent = getIntent();
        final Long idEvent = intent.getLongExtra("EventId", 0);
        myDataBase.open();
        Evenement event = myDataBase.getEvent(idEvent);
        Log.i("Evenement ID -----", "" + idEvent);
        final ListView maListView2 = findViewById(R.id.Details_Task_Liste);
        ArrayList<Tache> taches = event.getTaches();
        if (taches.size() > 0) {
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
            maListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Tache tache = (Tache) maListView2.getItemAtPosition(position);
                    myDataBase.supprimerTacheId(tache.getId());
                    Toast.makeText(DetailActivity.this, R.string.TaskComplete, Toast.LENGTH_LONG).show();
                    Intent intentRecharge = new Intent(DetailActivity.this, DetailActivity.class);
                    intentRecharge.putExtra("EventId", idEvent);
                    startActivity(intentRecharge);
                    finish();
                }
            });
        } else {
            TextView NoTask = findViewById(R.id.NoTask);
            NoTask.setText(R.string.NoTask);
        }
    }

    public void onBackPressed() {
        Intent intentToHome = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intentToHome);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDataBase.close();
    }
}
