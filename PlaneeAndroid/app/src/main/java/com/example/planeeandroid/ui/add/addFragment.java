package com.example.planeeandroid.ui.add;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.planeeandroid.Evenement;
import com.example.planeeandroid.MyDBAdapter;
import com.example.planeeandroid.R;
import com.example.planeeandroid.Tache;

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
    private LinearLayout Taches;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        final LayoutInflater myInflater = inflater;
        final ViewGroup myContainer = container;
        AddViewModel =
                ViewModelProviders.of(this).get(addViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_add, container, false);
        counter = 0;
        counterName = 0;
        counterMagasin = 100;
        counterUrl = 200;
        myDbAdapter = new MyDBAdapter(this.getContext());
        myDbAdapter.open();
        myDisplayDate = root.findViewById(R.id.DatePick);
        //final TextView textView = root.findViewById(R.id.text_gallery);
        AddViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        Button ajoutEvent = root.findViewById(R.id.addEvent);
        ajoutEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                }
                counter = 0;
                counterName = 0;
                counterMagasin = 100;
                counterUrl = 200;
                EditText EventName = root.findViewById(R.id.NomEvent);
                Evenement evenement = new Evenement(0, EventName.getText().toString(), myDisplayDate.getText().toString(), taches);
                myDbAdapter.InsertUnEvent(evenement);
                Log.i("Insert", "Insert OK");
            }
        });
        Button ajoutTask = root.findViewById(R.id.NewTache);

        Taches = root.findViewById(R.id.Taches);
        ajoutTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout lin = new LinearLayout(getActivity());
                lin.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                lin.setOrientation(LinearLayout.VERTICAL);
                //TaskName
                LinearLayout linearTaskName = new LinearLayout(getActivity());
                linearTaskName.setOrientation(LinearLayout.HORIZONTAL);
                linearTaskName.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                TextView taskName = new TextView(getActivity());
                taskName.setWidth(LayoutParams.WRAP_CONTENT);
                taskName.setHeight(LayoutParams.WRAP_CONTENT);
                taskName.setText(R.string.TaskName);
                EditText editTaskName = new EditText(getActivity());
                editTaskName.setWidth(LayoutParams.MATCH_PARENT);
                editTaskName.setHeight(LayoutParams.MATCH_PARENT);
                editTaskName.setHint(R.string.TaskName);
                editTaskName.setId(counterName);
                counterName += 1;
                linearTaskName.addView(taskName);
                linearTaskName.addView(editTaskName);
                //TaskMagasin
                LinearLayout linearTaskMagasin = new LinearLayout(getActivity());
                linearTaskMagasin.setOrientation(LinearLayout.HORIZONTAL);
                linearTaskMagasin.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                TextView taskMagasin = new TextView(getActivity());
                taskMagasin.setWidth(LayoutParams.WRAP_CONTENT);
                taskMagasin.setHeight(LayoutParams.WRAP_CONTENT);
                taskMagasin.setText(R.string.TaskStore);
                EditText editTaskMagasin = new EditText(getActivity());
                editTaskMagasin.setWidth(LayoutParams.WRAP_CONTENT);
                editTaskMagasin.setHeight(LayoutParams.WRAP_CONTENT);
                editTaskMagasin.setId(counterMagasin);
                counterMagasin += 1;
                linearTaskMagasin.addView(taskMagasin);
                linearTaskMagasin.addView(editTaskMagasin);
                //TaskURL
                LinearLayout linearTaskURL = new LinearLayout(getActivity());
                linearTaskURL.setOrientation(LinearLayout.HORIZONTAL);
                linearTaskURL.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                TextView taskURL = new TextView(getActivity());
                taskURL.setWidth(LayoutParams.WRAP_CONTENT);
                taskURL.setHeight(LayoutParams.WRAP_CONTENT);
                taskURL.setText(R.string.TaskUrl);
                EditText editTaskURL = new EditText(getActivity());
                editTaskURL.setWidth(LayoutParams.WRAP_CONTENT);
                editTaskURL.setHeight(LayoutParams.WRAP_CONTENT);
                editTaskURL.setId(counterUrl);
                counterUrl += 1;
                linearTaskURL.addView(taskURL);
                linearTaskURL.addView(editTaskURL);
                //add Layout
                lin.addView(linearTaskName);
                lin.addView(linearTaskMagasin);
                lin.addView(linearTaskURL);
                Taches.addView(lin);
                counter += 1;
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
        }

        ;
        return root;
    }

    @Override
    public void onDestroy() {
        myDbAdapter.close();
        super.onDestroy();
    }
}
