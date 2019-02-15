package org.fhict.csi_week_1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ReportActivity extends AppCompatActivity {
    //initiate global variables
    ImageView imgView;
    Button btnCamera;
    Button btnPhone;
    Button btn_start_gps;
    Button btn_stop_gps;
    LocationManager locationManager;
    LocationListener locationListener;
    TextView gpsText;

    //When created
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        //reference options
        btnCamera = (Button) findViewById(R.id.btnCamera);
        imgView = (ImageView) findViewById(R.id.imgView);
        btnPhone = (Button) findViewById(R.id.btnPhone);
        btn_start_gps = (Button) findViewById(R.id.btnStartGps);
        gpsText = (TextView) findViewById(R.id.textViewGps);

        //Open Dial
        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:42069"));
                startActivity(intent);
            }
        });
        //Open camera
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        //initialize Locationmanager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //Locationlistener functions
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                gpsText.setText("\n "+location.getLatitude() +" "+location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        //Gegenereerd door requestlocationupdates. 
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission. INTERNET
            }, 10);
            return;
        }else{
            configureButton();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 10:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    configureButton();
                }
        }
    }

    private void configureButton() {
        btn_start_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Error niet relevant, want ik check of de user op nee drukt in de if statement waar configurebutton wordt aangeroepen.
                locationManager.requestLocationUpdates("gps", 1000, 1, locationListener);
            }
        });

    }


    public void CloseWindow(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
