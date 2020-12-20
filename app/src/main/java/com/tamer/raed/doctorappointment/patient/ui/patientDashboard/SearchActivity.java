package com.tamer.raed.doctorappointment.patient.ui.patientDashboard;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tamer.raed.doctorappointment.R;

public class SearchActivity extends AppCompatActivity {
    SearchView searchView;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }
        getWindow().setStatusBarColor(ContextCompat.getColor(SearchActivity.this, R.color.white));// set status background white

        searchView = findViewById(R.id.patient_searchView);
        rv = findViewById(R.id.patient_search_rv);
    }

    public void back(View view) {
        onBackPressed();
    }
}