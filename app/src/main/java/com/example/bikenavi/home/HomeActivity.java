package com.example.bikenavi.home;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bikenavi.R;
import com.example.bikenavi.authentication.LoginActivity;
import com.example.bikenavi.navigation.MapActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Set;

public class HomeActivity extends AppCompatActivity {

    // constants
    private static final String TAG = "HomeActivity";
    private static final int REQUEST_ENABLE_BT = 0;

    // widgets
    private Toolbar myToolBar;
    private TextView welcomeText, mConnectedDevices;
    private ImageView bluetoothStatusImage;
    private SwitchMaterial mSwitchBluetooth;


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

        Log.i(TAG, fAuth.getCurrentUser().getUid());

        DocumentReference documentReference = fireStore.collection("Users").document(fAuth.getCurrentUser().getUid());
        documentReference.addSnapshotListener(this, (value, error) -> {
            welcomeText.setText("Hi, " + value.getString("firstName") + "!");
        });

        bluetoothStatusImage = findViewById(R.id.imgBluetooth);
        mSwitchBluetooth = findViewById(R.id.switchBluetooth);
        mConnectedDevices = findViewById(R.id.txtConnectedDevices);

        // adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, getString(R.string.bt_not_available), Toast.LENGTH_LONG).show();
        }

        if (!mBluetoothAdapter.isEnabled()) {

            bluetoothStatusImage.setImageResource(R.drawable.ic_ring_light);
            Toast.makeText(this, getString(R.string.connect_ring_light), Toast.LENGTH_LONG).show();

        } else {

            bluetoothStatusImage.setImageResource(R.drawable.bluetooth_on);
            Toast.makeText(this, getString(R.string.bt_already_on), Toast.LENGTH_LONG).show();
        }

        mSwitchBluetooth.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {

                if (!mBluetoothAdapter.isEnabled()) {
                    showToastMessage(getString(R.string.msg_bt_on));

                    // intent to turn BT on
                    Intent intentEnableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intentEnableBluetooth, REQUEST_ENABLE_BT);

                } else {

                    showToastMessage(getString(R.string.msg_bt_connected));
                }
            } else {

                mBluetoothAdapter.disable();
                showToastMessage(getString(R.string.msg_bt_off));
                bluetoothStatusImage.setImageResource(R.drawable.bluetooth_off);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                // BT is on
                bluetoothStatusImage.setImageResource(R.drawable.bluetooth_on);
                showToastMessage(getString(R.string.msg_bt_turned_on));
            } else {
                // user denied to turn BT on
                showToastMessage(getString(R.string.msg_bt_not_turned_on));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onClickShowConnectedDevices(View view) {

        if (mBluetoothAdapter.isEnabled()) {

            mConnectedDevices.setText(getString(R.string.paired_devices));
            Set<BluetoothDevice> bluetoothDeviceSet = mBluetoothAdapter.getBondedDevices();

            for (BluetoothDevice bluetoothDevice : bluetoothDeviceSet) {

                mConnectedDevices.append("\nDevices: " + bluetoothDevice.getName()
                        + ", " + bluetoothDevice);
            }

        } else {

            // bluetooth is off so could not get paired devices
            showToastMessage(getString(R.string.turn_to_get_device));
        }
    }

    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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