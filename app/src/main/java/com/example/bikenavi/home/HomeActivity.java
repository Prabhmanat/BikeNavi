package com.example.bikenavi.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bikenavi.R;
import com.example.bikenavi.authentication.LoginActivity;
import com.example.bikenavi.navigation.MapActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    // widgets
    Toolbar myToolBar;

    // variables
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        myToolBar = findViewById(R.id.toolbarHome);
        myToolBar.setTitle(R.string.app_name);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuHomeProfile){

            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));

        } else if (id == R.id.menuHomeLogout){

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();

        }
        return true;
    }

    public void onClickNavigation(View view) {
        startActivity(new Intent(HomeActivity.this, MapActivity.class));
    }
}