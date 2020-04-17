package com.example.planeeandroid.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.planeeandroid.ui.details.DetailFragment;
import com.example.planeeandroid.ui.settings.SettingsFragment;
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
        if (container != null) {
            container.removeAllViews();
        }
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        final ListView maListeView = root.findViewById(R.id.List);
        myDataBase = new MyDBAdapter(getContext());
        myDataBase.open();
        events = myDataBase.getAllEvent();
        if (events.size() == 0) {
            textView.setText("Vous n'avez pas d'évènements, veuillez en ajouter");
        } else {
            final Evenement[] evenements = new Evenement[events.size()];
            for (int i = 0; i < events.size(); i++) {
                evenements[i] = events.get(i);
            }
            final MyArrayAdapter myArray = new MyArrayAdapter(getActivity(), evenements);
            maListeView.setAdapter(myArray);
            maListeView.setClickable(true);
            maListeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object o = maListeView.getItemAtPosition(position);
                    Evenement event = (Evenement) o;
                    DetailFragment DetFrag = new DetailFragment();
                    Bundle Save = new Bundle();
                    Save.putLong("EventId", event.getId());
                    DetFrag.setArguments(Save);
                    Log.i("Test Bouton List", "" + event.getId());
                    getFragmentManager().beginTransaction().replace(R.id.HomeFragment, DetFrag).addToBackStack("HomeToDetailPage").commit();
                }
            });
        }
        FloatingActionButton plus = root.findViewById(R.id.Plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("click", "555555555555555555555555");
                getFragmentManager().beginTransaction().replace(R.id.HomeFragment, new addFragment()).addToBackStack("HomeToAddPage").commit();
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
