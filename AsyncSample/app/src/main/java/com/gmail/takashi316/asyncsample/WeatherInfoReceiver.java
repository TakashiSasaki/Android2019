package com.gmail.takashi316.asyncsample;

import android.os.AsyncTask;
import android.widget.TextView;

public class WeatherInfoReceiver extends AsyncTask<String, String,String>{

    private TextView _tvWeatherTelop;
    private TextView _tvWeatherDesc;

    WeatherInfoReceiver(TextView tvWeatherTelop, TextView tvWeatherDesc){
        this._tvWeatherTelop = tvWeatherTelop;
        this._tvWeatherDesc = tvWeatherDesc;
    }//WeatherInfoReceiver

    @Override
    protected String doInBackground(String... strings) {
        String id = strings[0];
        String urlStr = "http://weather.livedoor.com/forecast/webservice/json/v1?city=" + id;
        String result = "";

        //ウェブサービスにアクセスしてresultにJSON文字列を格納する

        //return null;
        return result;
    }//doInBackground

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);  //念のためスーパークラスのメソッドを呼んでおく
        String telop = "";
        String desc = "";

        //JSON文字列を解析する

        _tvWeatherTelop.setText(telop);
        _tvWeatherDesc.setText(desc);
    }//onPostExecute
}//WeatherInfoReceiver
