package com.example.articlebiases;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new FirstFragment());
        fragmentTransaction.commit();


    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.button1){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new FirstFragment()).commit();
        }
    }


}