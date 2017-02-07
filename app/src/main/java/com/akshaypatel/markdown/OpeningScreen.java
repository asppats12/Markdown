package com.akshaypatel.markdown;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OpeningScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.akshaypatel.markdown.R.layout.activity_opening_screen);
    }

    public void SignUp(View view){
        Intent intent = new Intent(OpeningScreen.this,Registration.class);
        startActivity(intent);
    }

    public void LogIn(View view){
        Intent intent = new Intent(OpeningScreen.this, MainActivity.class);
        startActivity(intent);
    }
}
