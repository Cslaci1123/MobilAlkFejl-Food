package com.example.etel4yourdoor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class KosarActivity extends AppCompatActivity {
    private static final int LOCATION_REQUEST_CODE = 100;
    private static final int  KEY = 979;
    private FusedLocationProviderClient locationClient;
    private TextView locationView;

    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kosar);

        locationView = findViewById(R.id.locationText);

        List<FoodItem> cart = KosarHelper.getInstance().getCart();

        int total = 0;

        LinearLayout cartLayout = findViewById(R.id.cartLayout);
        cartLayout.removeAllViews();

        for (FoodItem item : cart) {
            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView itemText = new TextView(this);
            itemText.setText(item.getName() + " - " + item.getPrice() + " Ft x " + item.getQuantity());
            itemText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2));

            Button plusBtn = new Button(this);
            plusBtn.setText("+");
            plusBtn.setOnClickListener(v -> {
                item.increaseQuantity();
                recreate();
            });

            Button minusBtn = new Button(this);
            minusBtn.setText("-");
            minusBtn.setOnClickListener(v -> {
                item.decreaseQuantity();
                recreate();
            });

            Button deleteBtn = new Button(this);
            deleteBtn.setText("🗑");
            deleteBtn.setOnClickListener(v -> {
                KosarHelper.getInstance().removeFromCart(item);
                recreate();
            });

            itemLayout.addView(itemText);
            itemLayout.addView(plusBtn);
            itemLayout.addView(minusBtn);
            itemLayout.addView(deleteBtn);
            cartLayout.addView(itemLayout);

            total += item.getTotalPrice();
        }

        TextView totalText = findViewById(R.id.cartSummary);
        totalText.setText("Összesen: " + total + " Ft");

        locationClient = LocationServices.getFusedLocationProviderClient(this);
        requestLocation();
    }

    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            return;
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);  // 5 másodperc
        locationRequest.setFastestInterval(2000); // min. 2 másodperc

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    locationView.setText("Friss helyadat nem érhető el.");
                    return;
                }

                Location location = locationResult.getLastLocation();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(KosarActivity.this, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(),
                                location.getLongitude(),
                                1
                        );

                        if (addresses != null && !addresses.isEmpty()) {
                            Address address = addresses.get(0);
                            String fullAddress = address.getAddressLine(0);
                            locationView.setText("Cím: " + fullAddress);
                        } else {
                            locationView.setText("Cím nem található.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        locationView.setText("Hibás helylekérdezés.");
                    }
                }
            }
        };

        locationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestLocation();
        } else {
            Toast.makeText(this, "A helyhozzáférés megtagadva", Toast.LENGTH_SHORT).show();
        }
    }

    public void toFood(View view){
        Intent intent = new Intent(this, FoodActivity.class);
        intent.putExtra("KEY", KEY);
        finish();
        startActivity(intent);
    }
}