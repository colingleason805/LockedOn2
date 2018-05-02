package com.example.colingleason.lockedon2;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SavedLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_saved_location);

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        if(MainActivity.unlockLocation == null) {
            Intent intent1 = new Intent(getApplicationContext(), DisplayMessageActivity.class);
            startActivity(intent1);
        }

        try {
            addresses = geocoder.getFromLocation(MainActivity.unlockLocation.latitude,
                    MainActivity.unlockLocation.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality() + '\n';
            String state = addresses.get(0).getAdminArea()+ '\n';
            String country = addresses.get(0).getCountryName()+ '\n';
            String postalCode = addresses.get(0).getPostalCode()+ '\n';
            String knownName = addresses.get(0).getFeatureName()+ '\n'; // Only if available else return NULL

            EditText text = (EditText) findViewById(R.id.editText5);
            text.setText(address);
            text.append(city);
            text.append(state);
            text.append(country);
            text.append(postalCode);
            text.append(knownName);
        }
        catch(IOException e){
            Toast.makeText(this, "IOException occured, please ensure you are connected to the network",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }

    }

}
