package com.akshaypatel.markdown;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class BackgroundTask extends AsyncTask<String, Void, String> {
    Context ctx;
    String user_name,name,user_pass,user_type;

    BackgroundTask(Context ctx){
        this.ctx = ctx;
    }




    @Override
    protected String doInBackground(String... params) {
        String regnv_url = "http://192.168.2.192/Markdown_Backend/register.php";
        String loginnv_url = "http://192.168.2.192/Markdown_Backend/login.php";
        String shopRegnv_url = "http://192.168.2.192/Markdown_Backend/shop_register.php";


        String method = params[0];
        switch (method) {
            case "register": {
                name = params[1];
                user_name = params[2];
                user_pass = params[3];
                user_type = params[4];

                try {
                    URL url = new URL(regnv_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("R_name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                            URLEncoder.encode("R_uname", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                            URLEncoder.encode("R_password", "UTF-8") + "=" + URLEncoder.encode(user_pass, "UTF-8") + "&" +
                            URLEncoder.encode("Account_type", "UTF-8") + "=" + URLEncoder.encode(user_type, "UTF-8");
                    bw.write(data);
                    bw.flush();
                    bw.close();
                    InputStream is = httpURLConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
                    String response = "";
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                    br.close();
                    is.close();
                    httpURLConnection.disconnect();
                    os.close();
                    return response;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return "Incorrect URL";
                } catch (IOException e) {
                    e.printStackTrace();
                    return "I/O Error";
                }
            }
            case "Shop Registration": {
                user_name = params[1];
                String shop_name = params[2];
                String shop_address = params[3];
                String shop_city = params[4];
                String shop_state = params[5];
                String shop_pin = params[6];
                String shop_tele = params[7];

                try {
                    URL url = new URL(shopRegnv_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("username","UTF-8") + "=" + URLEncoder.encode(user_name,"UTF-8") + "&" +
                            URLEncoder.encode("shop_name", "UTF-8") + "=" + URLEncoder.encode(shop_name, "UTF-8") + "&" +
                            URLEncoder.encode("shop_address", "UTF-8") + "=" + URLEncoder.encode(shop_address, "UTF-8") + "&" +
                            URLEncoder.encode("shop_city", "UTF-8") + "=" + URLEncoder.encode(shop_city, "UTF-8") + "&" +
                            URLEncoder.encode("shop_state", "UTF-8") + "=" + URLEncoder.encode(shop_state, "UTF-8") + "&" +
                            URLEncoder.encode("shop_pin", "UTF-8") + "=" + URLEncoder.encode(shop_pin, "UTF-8") + "&" +
                            URLEncoder.encode("shop_tele", "UTF-8") + "=" + URLEncoder.encode(shop_tele, "UTF-8");
                    bw.write(data);
                    bw.flush();
                    bw.close();
                    InputStream is = httpURLConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
                    String response = "";
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                    br.close();
                    is.close();
                    httpURLConnection.disconnect();
                    os.close();
                    return response;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return "Incorrect URL";
                } catch (IOException e) {
                    e.printStackTrace();
                    return "I/O Error";
                }
            }
            case "login": {
                user_name = params[1];
                user_pass = params[2];

                try {
                    URL url = new URL(loginnv_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String data = URLEncoder.encode("login_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                            URLEncoder.encode("login_pass", "UTF-8") + "=" + URLEncoder.encode(user_pass, "UTF-8");
                    bw.write(data);
                    bw.flush();
                    bw.close();
                    os.close();

                    InputStream is = httpURLConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
                    String response = "";
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                    br.close();
                    is.close();
                    httpURLConnection.disconnect();
                    return response;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        if(result.startsWith("User") || result.startsWith("Shop")) {
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
        }
        if(result.startsWith("Login"))
        {
            String notification;
            if(result.contains(" Retailer")) {
                notification = result.replace(" Retailer", "");
                Toast.makeText(ctx, notification,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ctx, SaleAdsProcess.class);
                intent.putExtra("username", user_name);
                ctx.startActivity(intent);
            }
            else{
                notification = result.replace("User", "");
                Toast.makeText(ctx, notification,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ctx, SalesAdsProcessUser.class);
                intent.putExtra("username", user_name);
                ctx.startActivity(intent);
            }
        }
        if(result.startsWith("Error"))
        {
            Toast.makeText(ctx,result,Toast.LENGTH_SHORT).show();
        }
        if(result.startsWith("Username"))
        {
            Toast.makeText(ctx,result,Toast.LENGTH_SHORT).show();
        }

    }
}



