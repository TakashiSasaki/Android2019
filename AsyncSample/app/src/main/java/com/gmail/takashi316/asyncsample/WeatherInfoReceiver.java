package com.gmail.takashi316.asyncsample;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

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

        //ウェブサービスにアクセスしてresultにJSON文字列を格納する処理
        HttpURLConnection con = null;
        InputStream is = null;

        try {
            Log.d("WeatherInfoReceiver", urlStr);
            URL url = new URL(urlStr);
            con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            //サーバーからレスポンスが返ってくるまでブロックされる
            //AndroidManifest.xmlでパーミッションが不足しているとエラーになる
            is = con.getInputStream();
            result = this.is2String(is);
        }catch(MalformedURLException e){
            //URL文字列が変とか
            Log.d("WeatherInfoReceiver", e.getMessage());
        }catch(IOException e){
            //サーバが応答しないとか。
            //is2Stringがストリームからの読み出しに失敗するとか
            Log.d("WeatherInfoReceiver", e.getMessage());
        }catch(Exception e) {
            Log.d("WeatherInfoReceiver", e.getMessage());
        }

        //return null;
        return result;
    }//doInBackground

    //InputStreamから文字列を取り出す定型処理
    private String is2String(InputStream is) throws IOException{
        //サーバから返ってくるデータはUTF-8であることが分かっている。
        //Javaの内部文字列はUTF-16（特に意識することはないかもしれない）
        BufferedReader reader = new BufferedReader
            (new InputStreamReader(is, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        char[] b = new char[1024];
        int length;
        while(0 <= (length = reader.read(b))){
            sb.append(b, 0, length);
        }//while
        return sb.toString();
    }//is2String

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);  //念のためスーパークラスのメソッドを呼んでおく
        String telop = "";
        String desc = "";

        //JSON文字列を解析する
        try {
            JSONObject rootJSON = new JSONObject(result);
            JSONObject descriptionJson = rootJSON.getJSONObject("description");
            desc = descriptionJson.getString("text");
            telop = rootJSON.getJSONArray("forecasts")
                    .getJSONObject(0).getString("telop");
        } catch(JSONException e){
            //特に何もしない
        }
        //desc = s;

        _tvWeatherTelop.setText(telop);
        _tvWeatherDesc.setText(desc);
    }//onPostExecute
}//WeatherInfoReceiver
