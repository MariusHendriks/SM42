package org.fhict.csi_week_1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
    TextView gpsText;

    //When created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        //Set global variables
        Button btnCamera = (Button)findViewById(R.id.btnCamera);
        ImageView imgView = (ImageView)findViewById(R.id.imgView);
        Button btnPhone = (Button)findViewById(R.id.btnPhone);
        Button btn_start_gps = (Button)findViewById(R.id.btnStartGps);
        Button btn_stop_gps = (Button)findViewById(R.id.btnStopGps);
        TextView gpsText = (TextView)findViewById(R.id.textViewGps);

        if(!runtime_permissions()){
            enable_buttons();
        };

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:113"));
                startActivity(intent);
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void enable_buttons() {
        btn_start_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GPS_Service.class);
                startService(intent);
            }
        });
        btn_stop_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private boolean runtime_permissions() {
        //Check if sdk version is 23 or higher AND if there is permission from the manifest (confirmed by user)
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            //Get actual permission.
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},  100);
            return true;
        };
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        imgView.setImageBitmap(bitmap);
    }


    public void CloseWindow(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED  &&   grantResults[1] ==  PackageManager.PERMISSION_GRANTED){
            enable_buttons();
        }else{
            runtime_permissions();
        }
    }
}
