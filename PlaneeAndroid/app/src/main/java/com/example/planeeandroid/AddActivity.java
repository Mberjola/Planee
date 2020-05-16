package com.example.planeeandroid;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
    private LinearLayout TachesList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add);
        //Pour des raisons de gestions sur les champs de saisies pour les tâches, l'utilisateur ne peut pas être en paysage lors de l'ajout d'un évènement
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Context context = this;
        createNotificationChannel();
        //Mise en place des compteurs afin de d'attribuer une id à chaque élément tâche
        counter = 0;
        counterName = 0;
        counterMagasin = 100;
        counterUrl = 200;
        //Ouverture de la Base de données
        myDbAdapter = new MyDBAdapter(context);
        myDbAdapter.open();
        myDisplayDate = findViewById(R.id.DatePick);
        //Récupération des éléments dans les différents champs du formulaire
        Button ajoutEvent = findViewById(R.id.addEvent);
        ajoutEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText EventName = findViewById(R.id.NomEvent);
                EditText Heure = findViewById(R.id.TimeHeure);
                TextView h = findViewById(R.id.h);
                EditText Min = findViewById(R.id.TimeMin);
                //Vérification que les champs Nom date et heure ont bien été remplis
                if ((!(EventName.getText().toString().equals(""))) && (!(myDisplayDate.getText().toString().equals(R.string.PickDate))) || (!(Heure.getText().toString().equals(""))) || (!(Min.getText().toString().equals("")))) {
                    ArrayList<Tache> taches = new ArrayList<Tache>();
                    //Afin de faciliter la récupération des éléments en rapport avec les tâches les id des noms de tâches iront de 0 à 99, les ids des noms de magasins
                    // iront de 100 à 199 et les ids d'URL de magasin iront de 200 à 299.
                    for (int i = 0; i < counter; i++) {
                        Tache TacheInput = new Tache();
                        EditText textName = (EditText) findViewById(i);
                        EditText textMagasin = (EditText) findViewById(100 + i);
                        EditText textUrl = (EditText) findViewById(200 + i);
                        //Pour chaque tâche on vérifie si l'utilisateur a rempli le champs. Si c'est le cas on enregistre la tâches sinon la tache sera vide
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
                    }
                    //Remise à zéro des compteurs
                    counter = 0;
                    counterName = 0;
                    counterMagasin = 100;
                    counterUrl = 200;
                    //Création et insertion de l'évènement dans la base de données
                    Evenement evenement = new Evenement(0, EventName.getText().toString(), myDisplayDate.getText().toString(), taches, Heure.getText().toString() + h.getText() + Min.getText().toString());
                    myDbAdapter.InsertUnEvent(evenement);
                    Toast.makeText(context, R.string.add, Toast.LENGTH_LONG).show();
                    Log.i("Insert", "Insert OK");
                    Toast.makeText(AddActivity.this, "Reminder set", Toast.LENGTH_SHORT).show();
                    //Mise en place de l'alarm grâce à AlarmManager
                    Intent intent = new Intent(AddActivity.this, ReminderBroadcast.class);
                    intent.putExtra("IdEvent", myDbAdapter.getEventID(evenement.getNom(), evenement.getDateLimite()));
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(AddActivity.this, 0, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    long timeAtButtonClick = System.currentTimeMillis();
                    long tenSecondes = 1000 * 10;
                    alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick + tenSecondes, pendingIntent);
                    //Retour à l'accueil
                    Intent Myintent = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(Myintent);
                    finish();
                } else {
                    Toast.makeText(context, R.string.MissNameDate, Toast.LENGTH_LONG).show();
                }
            }
        });
        //Ajouter une tâche
        //Lors de l'ajout d'une nouvelle tâche on va ajouter à un LinearLayout présent dans le xml un "formulaire" type pour les tâches
        //Afin de pouvoir les récupérer nous avons décidé de donner un id à chaque élément.
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

    //Gestion du bouton retour
    public void onBackPressed() {
        Intent intentToHome = new Intent(AddActivity.this, MainActivity.class);
        startActivity(intentToHome);
        finish();
    }

    //Création du channel de Notification
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "LemubitReminderChannel";
            String description = "Channel for Lemubit Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyLemubit", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
