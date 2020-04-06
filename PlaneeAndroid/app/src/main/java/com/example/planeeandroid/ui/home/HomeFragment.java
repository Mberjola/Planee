package com.example.planeeandroid.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView text;
    // private boolean flag;
    MyDBAdapter myDataBase;
    private ArrayList<Evenement> events;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        ListView maListeView = root.findViewById(R.id.List);
        myDataBase = new MyDBAdapter(this.getContext());
        myDataBase.open();
        events = myDataBase.getAllEvent();
        if (events.size() == 0) {
            textView.setText("Vous n'avez pas d'évènements, veuillez en ajouter");
        } else {
            Evenement[] evenements = new Evenement[events.size()];
            for (int i = 0; i < evenements.length; i++) {
                evenements[i] = events.get(i);
            }
            MyArrayAdapter myArray = new MyArrayAdapter(root.getContext(), evenements);
            maListeView.setAdapter(myArray);
        }
        FloatingActionButton plus = root.findViewById(R.id.Plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.HomeFragment, new addFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return root;
    }

    @Override
    public void onDestroy() {
        myDataBase.close();
        super.onDestroy();
    }
}
