package com.akshaypatel.markdown;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Shop_Registration extends AppCompatActivity {

    String shopName, shopAddr, shopCity, shopState, shopPin, shopTel, username;
    EditText shop_name, shop_addr, shop_city, shop_state, shop_pin, shop_tel;
    Button shopReg_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.akshaypatel.markdown.R.layout.activity_shop__registration);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        shop_name = (EditText)findViewById(com.akshaypatel.markdown.R.id.shop_name);
        shop_addr = (EditText)findViewById(com.akshaypatel.markdown.R.id.shop_address);
        shop_city = (EditText)findViewById(com.akshaypatel.markdown.R.id.shop_City);
        shop_state = (EditText)findViewById(com.akshaypatel.markdown.R.id.shop_State);
        shop_pin = (EditText)findViewById(com.akshaypatel.markdown.R.id.shop_pincode);
        shop_tel = (EditText)findViewById(com.akshaypatel.markdown.R.id.shop_tele);
        shopReg_btn = (Button)findViewById(com.akshaypatel.markdown.R.id.REG_SHOP);
    }

    public void shopReg(View view){
        if(isNumberValid(shop_tel.getText().toString()) && isPinValid(shop_pin.getText().toString())){
            BackgroundTask bgt = new BackgroundTask(this);
            String method = "Shop Registration";
            shopName = shop_name.getText().toString();
            shopAddr = shop_addr.getText().toString();
            shopCity = shop_city.getText().toString();
            shopPin = shop_pin.getText().toString();
            shopState = shop_state.getText().toString();
            shopTel = shop_tel.getText().toString();
            bgt.execute(method, username, shopName, shopAddr, shopCity, shopState, shopPin, shopTel);
            finish();
        }else{
            Toast.makeText(getApplicationContext(),"TELEPHONE NUMBER MUST BE 10 DIGITS LONG.",Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),"PIN CODE MUST BE SIX DIGITS LONG.",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.akshaypatel.markdown.R.menu.menu_shop__registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.akshaypatel.markdown.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isNumberValid(final String phoneNumber){
        Pattern pattern;
        Matcher matcher;
        final String MOBILE_PATTERN = "^((\\+){0,1}91(\\s){0,1}(\\-){0,1}(\\s){0,1}){0,1}9[0-9](\\s){0,1}(\\-){0,1}(\\s){0,1}[1-9]{1}[0-9]{7}$";
        pattern = Pattern.compile(MOBILE_PATTERN);
        matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public boolean isPinValid(final String pinNumber){
        Pattern pattern;
        Matcher matcher;
        final String PIN_PATTERN = "([0-9]{6}|[0-9]{3}\\s[0-9]{3})";
        pattern = Pattern.compile(PIN_PATTERN);
        matcher = pattern.matcher(pinNumber);
        return matcher.matches();
    }
}
