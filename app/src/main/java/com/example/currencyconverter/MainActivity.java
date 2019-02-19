package com.example.currencyconverter;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {


    TextView euroText;
    TextView cadText;
    TextView tryText;
    TextView usdText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        euroText = findViewById(R.id.euroText);
        cadText = findViewById(R.id.cadText);
        tryText = findViewById(R.id.tryText);
        usdText = findViewById(R.id.usdText);
    }

    public void getRates(View view){

        DownloadData downloadData = new DownloadData();

        try{

            String url = "http://data.fixer.io/api/latest?access_key=2d04134e00b41b19cdef157670a4a703&format=1";

            downloadData.execute(url);

        }catch (Exception e){


        }

    }






    private class DownloadData extends AsyncTask<String,Void,String>{ //<url,proses,cevap>
        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try{

                url = new URL(strings[0]);   //urlyi sabit almak yerine Async içinden alıcaz
                httpURLConnection = (HttpURLConnection) url.openConnection();   //connectionı aç
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while(data>0){    //hala alıcak datamız var

                    char character = (char) data;
                    result += character;

                    data = inputStreamReader.read();

                }

            } catch (Exception e){
                return null;
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {   //işlem bittikten sonra ne olsun
            super.onPostExecute(s);

           // System.out.println("Alınan data:" + s);

            try{

                JSONObject jsonObject = new JSONObject(s);

                String base = jsonObject.getString("base");
                System.out.println("base: " + base);


                String rates = jsonObject.getString("rates");
                System.out.println("rates: " + rates);

                JSONObject jsonObject1 = new JSONObject(rates);

                String tl = jsonObject1.getString("TRY");
                tryText.setText("TL: " + tl);

                String euro = jsonObject1.getString("TRY");
                tryText.setText("EURO: " + euro);

                String usd = jsonObject1.getString("TRY");
                tryText.setText("USD: " + usd);

                String cad = jsonObject1.getString("TRY");
                tryText.setText("CAD: " + cad);

            }catch (Exception e){


            }
        }
    }
}
