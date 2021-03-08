package com.example.bikenavi.start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bikenavi.R;
import com.example.bikenavi.authentication.LoginActivity;

public class MainActivity extends AppCompatActivity {

    // constants
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}