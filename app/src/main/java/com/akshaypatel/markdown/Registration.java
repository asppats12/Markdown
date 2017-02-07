package com.akshaypatel.markdown;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Registration extends AppCompatActivity{
    EditText nameText, userText, passText;
    String name, username, password,userType;
    Spinner accSpinner;
    ArrayAdapter<CharSequence> spinAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.akshaypatel.markdown.R.layout.activity_registration);

        nameText = (EditText)findViewById(com.akshaypatel.markdown.R.id.R_name);
        userText = (EditText)findViewById(com.akshaypatel.markdown.R.id.R_uname);
        passText = (EditText)findViewById(com.akshaypatel.markdown.R.id.R_password);
        accSpinner = (Spinner)findViewById(com.akshaypatel.markdown.R.id.Account_type);
        spinAdapter = ArrayAdapter.createFromResource(this, com.akshaypatel.markdown.R.array.types, android.R.layout.simple_spinner_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accSpinner.setAdapter(spinAdapter);

    }

    public void userReg(View view) {
        if(!(isValidUsername(userText.getText().toString()) && isValidPassword(passText.getText().toString()))){
            String method = "register";
            name = nameText.getText().toString();
            username = userText.getText().toString();
            password = passText.getText().toString();
            userType = accSpinner.getSelectedItem().toString();
            if(userType.equals("Retailer"))
            {
                Intent shopIntent = new Intent(this,Shop_Registration.class);
                shopIntent.putExtra("username",username);
                BackgroundTask bgt = new BackgroundTask(this);
                bgt.execute(method, name, username, password, userType);
                startActivity(shopIntent);
                finish();
            }
            else{
                BackgroundTask bgt = new BackgroundTask(this);
                bgt.execute(method, name, username, password, userType);
                finish();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Username must be at least 3 characters, no more than 15 characters, and no special characters are allowed.", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Password must be at least 4 characters, no more than 8 characters, and must include at least one upper case letter, one lower case letter, and one numeric digit.", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isValidUsername(final String username){
        Pattern pattern;
        Matcher matcher;
        final String USERNAME_PATTERN = "^[a-zA-Z0-9_]{3,15}$";

        pattern = Pattern.compile(USERNAME_PATTERN);
        matcher = pattern.matcher(username);

        return matcher.matches();
    }

    public boolean isValidPassword(final String password){
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
