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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.planeeandroid.Evenement;
import com.example.planeeandroid.MyDBAdapter;
import com.example.planeeandroid.R;
import com.example.planeeandroid.Tache;
import com.example.planeeandroid.ui.home.HomeFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class addFragment extends Fragment {
    private com.example.planeeandroid.ui.add.addViewModel AddViewModel;
    private TextView myDisplayDate;
    private DatePickerDialog.OnDateSetListener myDateSetListener;
    private MyDBAdapter myDbAdapter;
    private int counter;
    private LinearLayout TaskLayout;
    private LinearLayout Taches;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final LayoutInflater myInflater = inflater;
        final ViewGroup myContainer = container;
        AddViewModel =
                ViewModelProviders.of(this).get(addViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_add, container, false);
        counter = 0;
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
                for (int i = 0; i <= counter; i++) {
                }
                counter = 0;
               /* EditText EventName = root.findViewById(R.id.NomEvent);
                taches.add(new Tache("TacheName.getText().toString()", "TESt", "Text2"));
                Evenement evenement = new Evenement(0, EventName.getText().toString(), myDisplayDate.getText().toString(), taches);
                myDbAdapter.InsertUnEvent(evenement);
                Log.i("Insert", "Insert OK");*/
            }
        });
        Button ajoutTask = root.findViewById(R.id.NewTache);

        Taches = root.findViewById(R.id.Taches);
        ajoutTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* final View TaskRoot = myInflater.inflate(R.layout.task_layout, myContainer, false);
                TaskLayout = TaskRoot.findViewById(R.id.TacheLayout);*/
                LinearLayout lin = new LinearLayout(getContext());
                lin.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                lin.setOrientation(LinearLayout.VERTICAL);
                //TaskName
                LinearLayout linearTaskName = new LinearLayout(getContext());
                linearTaskName.setOrientation(LinearLayout.HORIZONTAL);
                linearTaskName.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                TextView taskName = new TextView(getContext());
                taskName.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                taskName.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                taskName.setText(R.string.TaskName);
                counter += 1;
                Taches.addView(lin);
                // Log.i("Test", taskName.getText().toString());
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
        return root;
    }

    @Override
    public void onDestroy() {
        myDbAdapter.close();
        super.onDestroy();
    }
}
