package com.example.schoolerp.ui.home;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.schoolerp.CustomBaseAdapter;
import com.example.schoolerp.R;

public class HomeActivitiy extends AppCompatActivity {
    String itemList[]={"Profile","Transport","Notification","Attendance","Report Card"};
    int itemImages[] = {R.drawable.baseline_account_circle_24, R.drawable.baseline_bookmarks_24};

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_home);

       listView=(ListView) findViewById(R.id.customListView);
       CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getApplicationContext(),itemList,itemImages);
       listView.setAdapter(customBaseAdapter);
}
}

