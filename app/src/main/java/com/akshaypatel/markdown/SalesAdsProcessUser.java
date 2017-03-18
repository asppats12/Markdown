package com.akshaypatel.markdown;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class SalesAdsProcessUser extends AppCompatActivity{

    private static final String retrievenv_url = "http://192.168.2.192/Markdown_Backend/JSONParse.php";
    private static final String TAG_TITLE = "saletitle";
    private static final String TAG_DESC = "saledesc";
    private static final String TAG_STARTD = "startdate";
    private static final String TAG_LASTD = "lastdate";
    private static final String TAG_USER= "username";

    private static final String TAG_NAME = "shop_name";
    private static final String TAG_ADDR = "shop_address";
    private static final String TAG_CITY = "shop_city";
    private static final String TAG_STATE = "shop_state";
    private static final String TAG_PIN = "shop_pin";
    private static final String TAG_TELE = "shop_tele";
    private static final String TAG_CSP = "shop_csp";


    Intent intent;
    String username;

    ArrayList<HashMap<String, String>> saleList;
    ListView lv;
    ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.akshaypatel.markdown.R.layout.activity_sales_ads_process_user);
        lv = (ListView) findViewById(com.akshaypatel.markdown.R.id.listView);
        saleList = new ArrayList<>();

        intent = getIntent();
        username = intent.getStringExtra("username");

        ShopAsyncTask sat = new ShopAsyncTask();
        sat.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.akshaypatel.markdown.R.menu.menu_main_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == com.akshaypatel.markdown.R.id.refresh){
            saleList.clear();
            ShopAsyncTask sat = new ShopAsyncTask();
            sat.execute();
        }
        if (id == com.akshaypatel.markdown.R.id.logout) {
            Intent intent = new Intent(this, MainActivity.class);
            this.finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void addSale(View view) {
        Intent intent = new Intent(this,AddNewSale.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public class ShopAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            try{
                URL url = new URL(retrievenv_url);
                HttpURLConnection url_conn = (HttpURLConnection)url.openConnection();
                url_conn.setDoInput(true);

                InputStream is = url_conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = br.readLine())!=null)
                {
                    sb.append(line).append("\n");
                }
                br.close();
                is.close();
                json = sb.toString();
                url_conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try{
                JSONObject jObj = new JSONObject(json);
                JSONArray jArray = jObj.getJSONArray("Sales");
                JSONArray jsonShopArray = jObj.getJSONArray("Shop");
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat fmt1 = new SimpleDateFormat("dd-MM-yyyy");

                Date sDATE, lDATE;

                for(int i=0;i<jArray.length();i++) {
                    JSONObject jobject = jArray.getJSONObject(i);
                    sDATE = fmt.parse(jobject.getString(TAG_STARTD));
                    lDATE = fmt.parse(jobject.getString(TAG_LASTD));

                    String sTitle = jobject.getString(TAG_TITLE);
                    //String user_name = "Posted by: " + username;
                    String sDesc = "Desc: " +jobject.getString(TAG_DESC);
                    String sSDate = "Start Date: " +fmt1.format(sDATE);
                    String sLDate = "Last Date: " +fmt1.format(lDATE);

                    JSONObject jObject = jsonShopArray.getJSONObject(i);

                    String ShopName = jObject.getString(TAG_NAME);
                    String ShopAddress = jObject.getString(TAG_ADDR);
                    String Shopcsp = jObject.getString(TAG_CITY) + ", " + jObject.getString(TAG_STATE) + " " + jObject.getString(TAG_PIN);
                    String ShopTel = jObject.getString(TAG_TELE);

                    HashMap<String,String> map = new HashMap<>();

                    map.put(TAG_TITLE,sTitle);
                    //map.put(TAG_USER,user_name);
                    map.put(TAG_DESC,sDesc);
                    map.put(TAG_STARTD,sSDate);
                    map.put(TAG_LASTD,sLDate);
                    map.put(TAG_NAME,ShopName);
                    map.put(TAG_ADDR,ShopAddress);
                    map.put(TAG_CSP,Shopcsp);
                    map.put(TAG_TELE, ShopTel);

                    saleList.add(map);
                }

            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s){
            adapter = new SimpleAdapter(SalesAdsProcessUser.this, saleList,
                    com.akshaypatel.markdown.R.layout.list_item,
                    new String[] { TAG_TITLE,TAG_USER,TAG_DESC, TAG_STARTD, TAG_LASTD, TAG_NAME, TAG_ADDR, TAG_CSP, TAG_TELE},
                    new int[] {com.akshaypatel.markdown.R.id.listTitle, com.akshaypatel.markdown.R.id.listUsername, com.akshaypatel.markdown.R.id.listDesc, com.akshaypatel.markdown.R.id.listStartDate, com.akshaypatel.markdown.R.id.listLastDate, com.akshaypatel.markdown.R.id.shopNameInfo,
                            com.akshaypatel.markdown.R.id.shopAddressInfo, com.akshaypatel.markdown.R.id.shopCityStatePinInfo, com.akshaypatel.markdown.R.id.shopTeleInfo});
            lv.setAdapter(adapter);
            super.onPostExecute(s);
        }
    }
}
