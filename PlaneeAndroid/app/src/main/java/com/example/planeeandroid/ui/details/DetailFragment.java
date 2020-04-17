package com.example.planeeandroid.ui.details;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.planeeandroid.Evenement;
import com.example.planeeandroid.MainActivity;
import com.example.planeeandroid.MyArrayAdapter;
import com.example.planeeandroid.MyDBAdapter;
import com.example.planeeandroid.R;
import com.example.planeeandroid.Tache;
import com.example.planeeandroid.ui.add.addFragment;
import com.example.planeeandroid.ui.details.DetailViewModel;
import com.example.planeeandroid.ui.settings.SettingsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class DetailFragment extends Fragment {

    private DetailViewModel detailViewModel;
    private TextView text;
    // private boolean flag;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        detailViewModel =
                ViewModelProviders.of(this).get(DetailViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_details, container, false);
        MyDBAdapter myDataBase = new MyDBAdapter(getContext());
        Long idEvent = this.getArguments().getLong("EventId");
        myDataBase.open();
        Evenement event = myDataBase.getEvent(idEvent);
        myDataBase.close();
        TextView Name = root.findViewById(R.id.Details_Name_Event);
        TextView Date = root.findViewById(R.id.Details_Date_Event);
        Name.setText(event.getNom());
        Date.setText(event.getDateLimite());
        Log.i("Evenement ID -----", "" + idEvent);
        ListView maListView = root.findViewById(R.id.Details_Task_Liste);
        return root;
    }
}
