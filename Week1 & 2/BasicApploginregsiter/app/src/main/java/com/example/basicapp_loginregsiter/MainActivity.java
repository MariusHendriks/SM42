package com.example.basicapp_loginregsiter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.BasicAppLoginregister.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClickEventLogin(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        Button editText = (Button) findViewById(R.id.button);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    public void onClickEventRegister(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        Button editText = (Button) findViewById(R.id.button2);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
