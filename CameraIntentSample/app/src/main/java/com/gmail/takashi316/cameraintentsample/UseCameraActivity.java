package com.gmail.takashi316.cameraintentsample;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UseCameraActivity extends AppCompatActivity {

    private Uri _imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_camera);
    }

    @Override
    public void onActivityResult
            (int requestCode,int resultCode,Intent intent){
        Log.d("onActivityResult", "requestCode = " + requestCode);
        //教科書ではカメラアプリからビットマップを得る方法と
        //MediaStoreコンテントプロバイダ経由で画像を得る方法を
        //排他的に実装していたが、ここでは両方残し、requestCodeで区別する。
        if(requestCode == 200 && resultCode == RESULT_OK) {
            ImageView imageView = findViewById(R.id.ivCamera);
            Bitmap bitmap = intent.getParcelableExtra("data");
            imageView.setImageBitmap(bitmap);
        }//if
        if(requestCode == 300 && resultCode == RESULT_OK){
            //startActivityForResultの際に指定したURIに保存されているはずなので、
            //onActivityResultのintentから getParcelableExtra する必要は無い。
            ImageView imageView = findViewById(R.id.ivCamera);
            imageView.setImageURI(this._imageUri);
        }//if
    }//onActivityResult

    public void onCameraImageClick(View view){
        Intent intent = new Intent
                (MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 200);
    }//onCameraImageClick

    //教科書ではMediaStoreコンテントプロバイダ経由で画像を取得するコードを
    // onCameraImageClick に記述していたが、専用のボタンをレイアウトに配置し
    //そのイベントハンドラに同コードを記述する。
    public void onButtonClick(View view){
        //onButtonClick
        if(ActivityCompat.checkSelfPermission
                (this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions, 2000);
        }//if

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date(System.currentTimeMillis());
        String nowStr = dateFormat.format(now);
        String fileName = "UseActivityPhoto_" + nowStr + ".jpg";

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, fileName);
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        ContentResolver contentResolver = this.getContentResolver();
        this._imageUri = contentResolver.insert
                (MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        //Log.d("onButtonClick", this._imageUri.toString());
        TextView textViewUri = findViewById(R.id.textViewUri);
        textViewUri.setText(this._imageUri.toString());

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, _imageUri);
        startActivityForResult(intent, 300);
    }//onButtonClick
}//UseCameraActivity
