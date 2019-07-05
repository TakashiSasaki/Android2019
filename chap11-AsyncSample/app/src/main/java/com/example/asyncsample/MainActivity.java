package com.example.asyncsample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView tvCityName;
    ListView lvCityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCityName = findViewById(R.id.tvCityName);
        lvCityList = findViewById(R.id.lvCityList);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
                tvCityName.setText("Imabari");
            }
        });
        // It causes an error of UI thread violation.
        //thread.start();

        List<Map<String, String>> cityList
                = new ArrayList<>();
        Map<String, String> city
                = new HashMap<>();
        city.put("name", "Osaka");
        city.put("id", "270000");
        cityList.add(city);
        city = new HashMap<>();
        city.put("name", "Matsuyama");
        city.put("id", "380010");
        cityList.add(city);

        String[] from = {"name"};
        int[] to = {android.R.id.text1};

        SimpleAdapter adapter = new SimpleAdapter(
                getApplicationContext(),
                cityList,
                android.R.layout.simple_expandable_list_item_1,
                from, to);
        lvCityList.setAdapter(adapter);
        lvCityList.setOnItemClickListener(new ListItemClickListener());
    }

    private class ListItemClickListener
            implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent,
                                View view, int position, long id) {
            Map<String, String> item = (Map<String, String>) parent.getItemAtPosition(position);
            String cityName = item.get("name");
            String cityId = item.get("id");
            ((TextView) findViewById(R.id.tvCityName)).setText("Weather at " + cityName);
            TextView tvWeatherTelop = findViewById(R.id.tvWeatherTelop);
            TextView tvWeatherDesc = findViewById(R.id.tvWeatherDesc);
            WeatherInfoReceiver receiver
                    = new WeatherInfoReceiver(tvWeatherTelop, tvWeatherDesc);
            receiver.execute(cityId);
        }//onItemClick
    }//ListItemClickListener

    private class WeatherInfoReceiver
            extends AsyncTask<String, Integer, String> {

        private TextView _tvWeatherTelop;
        private TextView _tvWeatherDesc;

        WeatherInfoReceiver(TextView tvWeatherTelop, TextView tvWeatherDesc) {
            this._tvWeatherTelop = tvWeatherTelop;
            this._tvWeatherDesc = tvWeatherDesc;
        }

        @Override
        protected String doInBackground(String... strings) {
            final String id = strings[0];
            String urlStr = "http://weather.livedoor.com"
                    + "/forecast/webservice/json/v1?city=" + id;
            String result = "";
            HttpURLConnection con = null;
            InputStream is = null;
            try {
                URL url = new URL(urlStr);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                is = con.getInputStream();
                result = is2String(is);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(con != null){
                    con.disconnect();
                }
                if(is != null){
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }//try
                }//if
            }//finally
            //publishProgress(1);
            //publishProgress(2);
            //publishProgress(123);
            return result;
        }//doInBackground

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.i("onProgressUpdate", "" + values[0]);
        }//onProgressUpdate

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            String telop = "telop";
            String desc = "desc";

            try {
                JSONObject rootJSON = new JSONObject(json);
                JSONObject descriptionJSON =
                        rootJSON.getJSONObject("description");
                desc = descriptionJSON.getString("text");
                JSONArray forecasts = rootJSON.getJSONArray("forecasts");
                JSONObject forecastNow = forecasts.getJSONObject(0);
                telop = forecastNow.getString("telop");
            } catch (JSONException e) {
                e.printStackTrace();
            }//try

            this._tvWeatherTelop.setText(telop);
            this._tvWeatherDesc.setText(desc);
        }//onPostExecute
    }//WeatherInfoReceiver

    static private String is2String(InputStream is)
            throws  IOException {
        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                is, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        char[] b = new char[1024];
        int length;
        while(0 <= (length = reader.read(b))){
            sb.append(b, 0, length);
        }//while
        return sb.toString();
    }//is2String

}
