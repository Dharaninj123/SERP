package com.example.schoolerp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {

    TextView aboutHeading, aboutText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        // Initialize TextViews
        aboutHeading = rootView.findViewById(R.id.about_heading);
        aboutText = rootView.findViewById(R.id.about_paragraph1);


        // Set text
        aboutHeading.setText(getString(R.string.introduction));
        aboutText.setText(getString(R.string.about_paragraph1));

        return rootView;
    }
}
