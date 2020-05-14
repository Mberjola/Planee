package com.example.planeeandroid;

import android.content.Context;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayDetailsAdapter extends ArrayAdapter<Tache> {
    private final Context context;
    private int[] AleaColor;

    public MyArrayDetailsAdapter(Context context, Tache[] Taches, int[] AleaColor) {
        super(context, R.layout.cell_taches_details, Taches);
        this.context = context;
        this.AleaColor = AleaColor;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        // Gestion optimisée de la mémoire (réutilisation de cellules):
        View cellView = convertView;
        if (cellView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cellView = inflater.inflate(R.layout.cell_taches_details, parent, false);
        }
        // Remplissage de la cellule:
        TextView TaskName = cellView.findViewById(R.id.Details_Task_Name);
        TextView TaskStore = cellView.findViewById(R.id.Details_Task_Store);
        TextView TaskURL = cellView.findViewById(R.id.Details_Task_Url);
        Tache tache = getItem(position);
        TaskName.setText(tache.getNom());
        TaskStore.setText(tache.getNomMagasin());
        TaskURL.setText(tache.getSiteMagasin());
        ImageView imageView = cellView.findViewById(R.id.imageView2);
        imageView.setImageResource(R.drawable.card_detail_style);
        int[] ColorArray = {R.color.bleu, R.color.jaune, R.color.mauve, R.color.rose, R.color.vert};
        int index = AleaColor[position];
        int couleur = context.getResources().getColor(ColorArray[index]);
        Log.i("couleur", "" + couleur);
        imageView.setColorFilter(couleur);
        Log.i("Position", "" + position);
        Log.i("Tach5", tache.getNom());
        Log.i("Tach6", tache.getNomMagasin());
        Log.i("Tach7", tache.getSiteMagasin());
        return cellView;
    }
}
