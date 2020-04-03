package com.example.planeeandroid.ui.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.planeeandroid.R;
import com.example.planeeandroid.ui.settings.SettingsViewModel;

public class addFragment extends Fragment {
    private com.example.planeeandroid.ui.add.addViewModel AddViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddViewModel =
                ViewModelProviders.of(this).get(addViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add, container, false);
        //final TextView textView = root.findViewById(R.id.text_gallery);
        AddViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}
