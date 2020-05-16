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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private MyDBAdapter myDataBase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details);
        //Ouverture de la base de données
        //Création de l'intent pour récupérer l'Id de l'évènement choisi par l'utilisateur lors du click sur la page d'accueil
        myDataBase = new MyDBAdapter(this);
        final Intent intent = getIntent();
        final Long idEvent = intent.getLongExtra("EventId", 0);
        myDataBase.open();
        Evenement event = myDataBase.getEvent(idEvent);
        //Récupération de l'évènement et de la liste tâches
        final ListView maListView2 = findViewById(R.id.Details_Task_Liste);
        ArrayList<Tache> taches = event.getTaches();
        if (taches.size() > 0) {
            final Tache[] TacheArray = new Tache[taches.size()];
            //Tableau qui va permettre de contenir l'indice afin d'attribuer une couleur aléatoire à chaque tache lors de l'affichage
            int[] AleatColor = new int[taches.size()];
            //Nombre aléatoire
            int answer = 0;
            //Conversion de la liste de tâches en tableau afin de pouvoir utiliser MyArrayDetailAdapter
            for (int i = 0; i < TacheArray.length; i++) {
                TacheArray[i] = taches.get(i);
                //4 couleurs sont disponibles (voir tableau dans MyArrayDetailsAdapter)
                //Ainsi le nombre de tâche peut être supérieur au nombre de couleur disponible, dans ce cas nous allons juste
                // regarder si l'élément précedent possède le même indice aléatoire. Si l'indice aléatoire est différent, on le conserve
                // sinon on recherche un indice aléatoire
                do {
                    answer = intAleat(0, 4);
                } while (i >= 1 && AleatColor[i - 1] == answer);
                AleatColor[i] = answer;
            }
            final MyArrayDetailsAdapter myArrayDetailsAdapter = new MyArrayDetailsAdapter(this, TacheArray, AleatColor);
            maListView2.setAdapter(myArrayDetailsAdapter);
            //Lors d'un click sur une tâche, celle-ci sera complétée et on pourra l'effacer de l'affichage et de la base de données locale
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
            //Si aucune tâche n'est présente on affiche ce texte
            TextView NoTask = findViewById(R.id.NoTask);
            NoTask.setText(R.string.NoTask);
        }
        //L'appuie sur le bouton flottant permettra de modifier l'évènement
        FloatingActionButton plus = findViewById(R.id.Plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUpdate = new Intent(DetailActivity.this, UpdateActivity.class);
                intentUpdate.putExtra("EventId", idEvent);
                startActivity(intentUpdate);
                finish();
            }
        });
    }

    //Gestion du bouton retour
    public void onBackPressed() {
        Intent intentToHome = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intentToHome);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //fermeture de la base de données
        myDataBase.close();
    }

    //Tirage d'un entier aléatoire entre min et max
    public static int intAleat(int min, int max) {
        int res = 0;
        res = (int) (Math.random() * (max - min + 1) + min);
        return res;
    }

}
