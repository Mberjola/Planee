package com.example.planeeandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
        final ListView maListView2 = findViewById(R.id.Details_Task_Liste);
        ArrayList<Tache> taches = event.getTaches();
        if (taches.size() > 0) {
            final Tache[] TacheArray = new Tache[taches.size()];
            int[] AleatColor = new int[taches.size()];
            int answer = 0;
            for (int i = 0; i < TacheArray.length; i++) {
                TacheArray[i] = taches.get(i);
                do {
                    answer = intAleat(0, 4);
                } while (i >= 1 && AleatColor[i - 1] == answer);
                AleatColor[i] = answer;
            }
            final MyArrayDetailsAdapter myArrayDetailsAdapter = new MyArrayDetailsAdapter(this, TacheArray, AleatColor);
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

    public static int intAleat(int min, int max) {
        int res = 0;
        res = (int) (Math.random() * (max - min + 1) + min);
        return res;
    }

}
