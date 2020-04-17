package com.example.planeeandroid.ui.details;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.planeeandroid.Evenement;
import com.example.planeeandroid.MyDBAdapter;
import com.example.planeeandroid.R;
import com.example.planeeandroid.ui.home.HomeFragment;

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
        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                getFragmentManager().beginTransaction().replace(R.id.DetailsFragment, new HomeFragment()).addToBackStack(null).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        return root;
    }


}
