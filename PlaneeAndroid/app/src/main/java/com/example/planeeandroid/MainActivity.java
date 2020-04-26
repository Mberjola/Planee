package com.example.planeeandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private TextView text;
    MyDBAdapter myDataBase;
    private ArrayList<Evenement> events;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        final Context context = this;
        final TextView textView = findViewById(R.id.text_home);
        final ListView maListeView = findViewById(R.id.List);
        myDataBase = new MyDBAdapter(context);
        myDataBase.open();
        events = myDataBase.getAllEvent();
        if (events.size() == 0) {
            textView.setText("Vous n'avez pas d'évènements, veuillez en ajouter");
        } else {
            final Evenement[] evenements = new Evenement[events.size()];
            for (int i = 0; i < events.size(); i++) {
                evenements[i] = events.get(i);
            }
            final MyArrayAdapter myArray = new MyArrayAdapter(context, evenements);
            maListeView.setAdapter(myArray);
            maListeView.setClickable(true);
            maListeView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    new AlertDialog.Builder(context)
                            .setTitle(R.string.deleteTitle)
                            .setMessage(R.string.deleteMessage)
                            .setPositiveButton(R.string.positif, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Evenement event = (Evenement) maListeView.getItemAtPosition(position);
                                    myDataBase.supprimerEvent(event.getId());
                                    Toast.makeText(context, R.string.deleteOK, Toast.LENGTH_LONG).show();
                                    Intent intentRecharge = new Intent(MainActivity.this, MainActivity.class);
                                    startActivity(intentRecharge);
                                    finish();
                                }
                            })
                            .setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context, R.string.cancel, Toast.LENGTH_LONG).show();
                                }
                            })
                            .show();
                    return true;
                }
            });
            maListeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object o = maListeView.getItemAtPosition(position);
                    Evenement event = (Evenement) o;
                    Intent intentDetails = new Intent(MainActivity.this, DetailActivity.class);
                    intentDetails.putExtra("EventId", event.getId());
                    Log.i("Test Bouton List", "" + event.getId());
                    startActivity(intentDetails);
                    finish();
                }
            });

        }
        FloatingActionButton plus = findViewById(R.id.Plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("click", "555555555555555555555555");
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.QuitTitle)
                .setMessage(R.string.QuitMessage)
                .setPositiveButton(R.string.positif, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplication(), R.string.cancel, Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

}