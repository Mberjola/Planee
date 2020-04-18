package com.example.planeeandroid.ui.add;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;

import com.example.planeeandroid.Evenement;
import com.example.planeeandroid.MyDBAdapter;
import com.example.planeeandroid.R;
import com.example.planeeandroid.Tache;
import com.example.planeeandroid.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class addFragment extends Fragment {
    private com.example.planeeandroid.ui.add.addViewModel AddViewModel;
    private TextView myDisplayDate;
    private DatePickerDialog.OnDateSetListener myDateSetListener;
    private MyDBAdapter myDbAdapter;
    private int counter;
    private int counterName;
    private int counterMagasin;
    private int counterUrl;
    private LinearLayout TaskLayout;
    private LinearLayout TachesList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        final LayoutInflater myInflater = inflater;
        final ViewGroup myContainer = container;
        AddViewModel =
                ViewModelProviders.of(this).get(addViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_add, container, false);
        counter = 0;
        counterName = 0;
        counterMagasin = 100;
        counterUrl = 200;
        myDbAdapter = new MyDBAdapter(getContext());
        myDbAdapter.open();
        myDisplayDate = root.findViewById(R.id.DatePick);
        //final TextView textView = root.findViewById(R.id.text_gallery);
        /*AddViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
*/
        Button ajoutEvent = root.findViewById(R.id.addEvent);
        ajoutEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText EventName = root.findViewById(R.id.NomEvent);
                if ((!(EventName.getText().toString().equals(""))) && (!(myDisplayDate.getText().toString().equals(R.string.PickDate)))) {
                    ArrayList<Tache> taches = new ArrayList<Tache>();
                    for (int i = 0; i < counter; i++) {
                        Tache TacheInput = new Tache();
                        EditText textName = (EditText) root.findViewById(i);
                        EditText textMagasin = (EditText) root.findViewById(100 + i);
                        EditText textUrl = (EditText) root.findViewById(200 + i);
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
                    Toast.makeText(getContext(), R.string.add, Toast.LENGTH_LONG).show();
                    Log.i("Insert", "Insert OK");
                    getFragmentManager().beginTransaction().replace(R.id.AddFragment, new HomeFragment()).addToBackStack(null).commit();
                } else {
                    Toast.makeText(getContext(), R.string.MissNameDate, Toast.LENGTH_LONG).show();
                }
            }
        });
        //Ajouter une tâche
        Button ajoutTask = root.findViewById(R.id.NewTache);
        TachesList = root.findViewById(R.id.Taches);
        ajoutTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View Myroot = myInflater.inflate(R.layout.task_layout, myContainer, false);
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
                DatePickerDialog dialog = new DatePickerDialog(root.getContext(), android.R.style.Theme_Holo_Light_Dialog, myDateSetListener, year, month, day);
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
        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                getFragmentManager().beginTransaction().replace(R.id.AddFragment, new HomeFragment()).addToBackStack(null).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        return root;
    }

    @Override
    public void onDestroy() {
        myDbAdapter.close();
        super.onDestroy();
    }
}
