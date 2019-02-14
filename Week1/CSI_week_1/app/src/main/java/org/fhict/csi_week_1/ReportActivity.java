package org.fhict.csi_week_1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ReportActivity extends AppCompatActivity {
    //Set global variables
    ImageView imgView;
    Button btnCamera;

    //When created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Button btnCamera = (Button)findViewById(R.id.btnCamera);
        ImageView imgView = (ImageView)findViewById(R.id.imgView);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
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
}
