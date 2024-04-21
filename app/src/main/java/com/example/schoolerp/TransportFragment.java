package com.example.schoolerp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

    public class TransportFragment extends Fragment {

        public TransportFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_transport, container, false);

            // Find the navigation item
            Button btnRoute = view.findViewById(R.id.routeFragment);
            Button btnStop = view.findViewById(R.id.stopFragment);
            Button btnTrack = view.findViewById(R.id.trackFragment);

            // Set OnClickListener for the buttons
            btnRoute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openRouteFragment();
                }
            });

            btnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openStopFragment();
                }
            });

            btnTrack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openTrackFragment();
                }
            });

            return view;
        }

        private void openRouteFragment() {
            // Implement method to open RouteFragment
        }

        private void openStopFragment() {
            // Implement method to open StopFragment
        }

        private void openTrackFragment() {
            // Implement method to open TrackFragment
        }
    }


