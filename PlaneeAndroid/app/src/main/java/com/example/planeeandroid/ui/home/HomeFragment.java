package com.example.planeeandroid.ui.home;

import android.content.Context;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.planeeandroid.Evenement;
import com.example.planeeandroid.MyArrayAdapter;
import com.example.planeeandroid.R;
import com.example.planeeandroid.Tache;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView text;
    // private boolean flag;
    Date today = new Date();
    Tache tache = new Tache("Tache 1", "Magasin", "url");
    ArrayList<Tache> taches = new ArrayList<Tache>();
    Evenement evenement = new Evenement("Test", today, taches);
    Evenement[] evenements = {evenement};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        ListView maListeView = root.findViewById(R.id.List);
        MyArrayAdapter myArray = new MyArrayAdapter(root.getContext(), evenements);
        maListeView.setAdapter(myArray);
      /*  text = root.findViewById(R.id.TextTest);
        bouton = root.findViewById(R.id.Button);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        flag = true;
        View.OnClickListener Texte = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    text.setText("Ceci est un test");
                } else {
                    text.setText("");
                }
                flag = !flag;
            }
        };
        bouton.setOnClickListener(Texte);*/
        return root;
    }

    /*public void CreatCard(ArrayList<Evenement> Evenements, LinearLayout Linear, Context context) {
        for (int i = 1; i <= Evenements.size(); i++) {
            ConstraintLayout constraint = new ConstraintLayout(context);
            Button bouton = new Button(context);
            Linear.addView(constraint);
        }
    }*/
}
