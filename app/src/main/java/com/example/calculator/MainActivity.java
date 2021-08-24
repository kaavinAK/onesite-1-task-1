package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragmentb = new bottomcalculator();
        Fragment fragmentu = new topcalculator();
        getSupportFragmentManager().beginTransaction().replace(R.id.bottomfragment,fragmentb).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.upperfragment,fragmentu).commit();
    }
}