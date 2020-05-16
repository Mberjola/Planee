package com.example.planeeandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        ImageView imageView = cellView.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.card_style);
        TextView Titre = (TextView) cellView.findViewById(R.id.Titre);
        Evenement event = getItem(position);
        String dateLimite = event.getDateLimite();
        TextView date = (TextView) cellView.findViewById(R.id.Date);
        String titre = event.getNom();
        Titre.setText(titre);
        date.setText(dateLimite);
        //Sur la page d'accueil, la couleur de la carte changera selon l'évènement
        int index;
        //On regarde si le titre contient une de ces chaînes et on affectera une valeur à l'index en fonction
        if ((titre.contains("mariage")) || (titre.contains("Mariage"))
                || (titre.contains("wedding")) || (titre.contains("Wedding"))) {
            index = 0;
        } else if ((titre.contains("Anniversaire")) || (titre.contains("anniversaire"))
                || titre.contains("birthday") || titre.contains("Birthday")) {
            index = 1;
        } else if ((titre.contains("naissance")) || titre.contains("Naissance")
                || (titre.contains("naissances")) || titre.contains("Naissances")
                || (titre.contains("birth")) || titre.contains("Birth")
                || (titre.contains("births")) || titre.contains("Births")) {
            index = 2;
        } else if ((titre.contains("baptême")) || (titre.contains("Baptême"))
                || (titre.contains("bapteme")) || (titre.contains("Bapteme"))
                || (titre.contains("baptêmes")) || (titre.contains("Baptêmes"))
                || (titre.contains("baptemes")) || (titre.contains("Baptemes"))
                || titre.contains("baptism") || titre.contains("Baptism")) {
            index = 3;
        } else {
            index = 4;
        }
        //Tableau de couleur
        int[] ColorArray = {R.color.rose, R.color.vert, R.color.bleu, R.color.jaune, R.color.mauve};
        int couleur = context.getResources().getColor(ColorArray[index]);
        //Changement de la couleur de la carte
        imageView.setColorFilter(couleur);
        return cellView;
    }
}
