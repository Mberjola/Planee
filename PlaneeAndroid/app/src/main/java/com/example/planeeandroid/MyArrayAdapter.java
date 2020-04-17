package com.example.planeeandroid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.planeeandroid.ui.add.addFragment;

import java.util.ArrayList;
import java.util.Date;

public class MyArrayAdapter extends ArrayAdapter<Evenement> {
    private final Context context;

    public MyArrayAdapter(Context context, Evenement[] evenements) {
        super(context, R.layout.cell_layout, evenements);
        this.context = context;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        // Gestion optimisée de la mémoire (réutilisation de cellules):
        View cellView = convertView;
        if (cellView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cellView = inflater.inflate(R.layout.cell_layout, parent, false);
        }
        // Remplissage de la cellule:
        ConstraintLayout layout = cellView.findViewById(R.id.constraintLayout);
        TextView Titre = (TextView) cellView.findViewById(R.id.Titre);
       //Button suite = (Button) cellView.findViewById(R.id.suite);
        Evenement event = getItem(position);
        String dateLimite = event.getDateLimite();
        TextView date = (TextView) cellView.findViewById(R.id.Date);
        String titre = event.getNom();
        Titre.setText(titre);
        date.setText(dateLimite);
        layout.setBackgroundResource(R.drawable.ikki);
        /*switch (titre) {
            case "Mariage":
                imageView.setImageResource(R.drawable.prio0);
                break;
            case 1:
                imageView.setImageResource(R.drawable.prio1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.prio2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.prio3);
                break;
            case 4:
                imageView.setImageResource(R.drawable.prio4);
                break;
        }*/
        return cellView;
    }
}
