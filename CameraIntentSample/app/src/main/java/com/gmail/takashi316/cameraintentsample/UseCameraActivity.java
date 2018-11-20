package com.gmail.takashi316.cameraintentsample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class UseCameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_camera);
    }

    @Override
    public void onActivityResult
            (int requestCode,int resultCode,Intent intent){
        if(requestCode != 200) return;
        if(resultCode != RESULT_OK) return;
        Bitmap bitmap = intent.getParcelableExtra("data");
        ImageView imageView = findViewById(R.id.ivCamera);
        imageView.setImageBitmap(bitmap);
    }//onActivityResult

    public void onCameraImageClick(View view){
        Intent intent = new Intent
                (MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 200);
    }
}
