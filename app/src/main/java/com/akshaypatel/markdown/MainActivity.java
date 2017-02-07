package com.akshaypatel.markdown;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    String login_name, login_pass;
    EditText eTxtName, eTxtPwd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.akshaypatel.markdown.R.layout.activity_main);
        eTxtName = (EditText)findViewById(com.akshaypatel.markdown.R.id.uname);
        eTxtPwd = (EditText)findViewById(com.akshaypatel.markdown.R.id.pword);

    }

    public void userLogin(View view){
        login_name = eTxtName.getText().toString();
        login_pass = eTxtPwd.getText().toString();
        String method = "login";
        BackgroundTask bgt = new BackgroundTask(this);
        bgt.execute(method, login_name, login_pass);
        eTxtName.setText("");
        eTxtPwd.setText("");
    }

    /*public void userSignUp(View view){
        Intent intent = new Intent(MainActivity.this,Registration.class);
        startActivity(intent);
    }*/
}
