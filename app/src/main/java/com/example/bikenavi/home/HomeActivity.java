package com.example.bikenavi.home;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bikenavi.R;
import com.example.bikenavi.authentication.LoginActivity;
import com.example.bikenavi.navigation.MapActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    // constants
    private static final String TAG = "HomeActivity";
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 0;

    // widgets
    Toolbar myToolBar;
    TextView welcomeText;
    ImageView bluetoothStatusImage;

    // variables
    BluetoothAdapter mBluetoothAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        myToolBar = findViewById(R.id.toolbarHome);
        setSupportActionBar(myToolBar);
        myToolBar.setTitle(R.string.app_name);

        FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        welcomeText = findViewById(R.id.txtWelcome);

        DocumentReference documentReference = fireStore.collection("Users").document(fAuth.getCurrentUser().getUid());
        documentReference.addSnapshotListener(this, (value, error) -> {
            welcomeText.setText("Hi, " + value.getString("firstName") + "!");
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuHomeProfile) {

            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));

        } else if (id == R.id.menuHomeLogout) {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();

        }

        return true;
    }

    public void onClickNavigation(View view) {
        startActivity(new Intent(HomeActivity.this, MapActivity.class));
    }

    public void onClickBluetoothSettings(View view) {
        startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
    }
}