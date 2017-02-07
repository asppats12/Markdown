package com.akshaypatel.markdown;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNewSale extends AppCompatActivity {

    EditText saleTitle, saleDesc, startDate, lastDate;
    Button addSale;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.akshaypatel.markdown.R.layout.activity_add_new_sale);
        saleTitle = (EditText)findViewById(com.akshaypatel.markdown.R.id.sale_title);
        saleDesc = (EditText)findViewById(com.akshaypatel.markdown.R.id.sale_details);
        startDate = (EditText)findViewById(com.akshaypatel.markdown.R.id.sale_starting);
        lastDate = (EditText)findViewById(com.akshaypatel.markdown.R.id.sale_ending);
        addSale = (Button) findViewById(com.akshaypatel.markdown.R.id.addSale);
        username = getIntent().getStringExtra("username");
    }


    public void submitSale(View view) {
        String sale_title, sale_desc, start_date, last_date;
        sale_title = saleTitle.getText().toString();
        sale_desc = saleDesc.getText().toString();
        start_date = startDate.getText().toString();
        last_date = lastDate.getText().toString();

        SaleAddition SAtask = new SaleAddition(this);
        SAtask.execute(username,sale_title,sale_desc,start_date,last_date);
        finish();

    }

    public class SaleAddition extends AsyncTask<String,Void,String>
    {
        Context ctx;
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat fmt1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fmt2 = new SimpleDateFormat("MM-dd-yyyy");
        Date startD, lastD;
        public SaleAddition(Context ctx) {
            this.ctx = ctx;
        }

        String salenv_url = "http://192.168.2.192/Markdown/add_sale.php";

        @Override
        protected String doInBackground(String... params) {
            username = params[0];
            String saleTitle = params[1];
            String saleDesc = params[2];
            String startDate = params[3];
            String lastDate = params[4];

            try {
                URL url = new URL(salenv_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();

                startD = fmt.parse(startDate);
                lastD =fmt.parse(lastDate);
                startDate = fmt1.format(startD);
                lastDate = fmt1.format(lastD);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                        URLEncoder.encode("saletitle", "UTF-8") + "=" + URLEncoder.encode(saleTitle, "UTF-8") + "&" +
                        URLEncoder.encode("saledesc", "UTF-8") + "=" + URLEncoder.encode(saleDesc, "UTF-8") + "&" +
                        URLEncoder.encode("startdate", "UTF-8") + "=" + URLEncoder.encode(startDate, "UTF-8")+ "&" +
                        URLEncoder.encode("lastdate","UTF-8") + "=" + URLEncoder.encode(lastDate,"UTF-8");
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
            } catch (IOException | ParseException e) {
                e.printStackTrace();
                return "Error: Sale not Submitted";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("Sale Submitted"))
            {
                Toast.makeText(ctx,"Sale Submitted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(ctx,result,Toast.LENGTH_SHORT).show();
            }
        }


    }
}

