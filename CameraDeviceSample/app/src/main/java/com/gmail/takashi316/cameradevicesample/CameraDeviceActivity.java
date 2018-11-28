package com.gmail.takashi316.cameradevicesample;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.media.ImageReader;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import java.util.Arrays;

public class CameraDeviceActivity extends AppCompatActivity {

    TextureView textureView;
    CameraDevice cameraDevice;
    Surface surface;
    ImageReader imageReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_device);

        // パーミッションのチェック
        if (ActivityCompat.checkSelfPermission
                (getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions
                    (CameraDeviceActivity.this, permissions, 2000);
            //return;
        }//if
        CameraManager cameraManager
                = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraManager.openCamera("0", new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@androidx.annotation.NonNull @NonNull CameraDevice camera) {
                    cameraDevice = camera;
                }//onOpened

                @Override
                public void onDisconnected(@androidx.annotation.NonNull @NonNull CameraDevice camera) {
                    cameraDevice = null;
                }//onDisconnected

                @Override
                public void onError(@androidx.annotation.NonNull @NonNull CameraDevice camera, int error) {
                    cameraDevice = null;
                }//onError
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
            finish(); //カメラが開けなかったらアクティビティを終了
        }

        this.textureView = findViewById(R.id.textureView);

        this.textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {

            @Override
            public void onSurfaceTextureAvailable(final SurfaceTexture surfaceTexture, int width, int height) {
                surface = new Surface(surfaceTexture);
            }//onSurfaceTextureAvailable

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
                surface = null;
            }//onSurfaceTextureSizeChanged

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                surface = null;
                return false;
            }//onSurfaceTextureDestroyed

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            }
        });

    }//onCreate

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("onRequestPermissionsResult", grantResults.toString());
    }

    // まさにコールバック地獄
    public void onPreviewButtonClick(View view) throws CameraAccessException {
        cameraDevice.createCaptureSession(Arrays.asList(surface),
                new CameraCaptureSession.StateCallback() {
                    @Override
                    public void onConfigured(@androidx.annotation.NonNull @NonNull CameraCaptureSession session) {
                        try {
                            CaptureRequest.Builder captureRequestBuilder
                                    = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                            captureRequestBuilder.addTarget(surface);
                            CaptureRequest captureRequest = captureRequestBuilder.build();
                            session.setRepeatingRequest(captureRequest, new CameraCaptureSession.CaptureCallback() {
                                @Override
                                public void onCaptureStarted(@androidx.annotation.NonNull @NonNull CameraCaptureSession session, @androidx.annotation.NonNull @NonNull CaptureRequest request, long timestamp, long frameNumber) {
                                    super.onCaptureStarted(session, request, timestamp, frameNumber);
                                }

                                @Override
                                public void onCaptureCompleted(@androidx.annotation.NonNull @NonNull CameraCaptureSession session, @androidx.annotation.NonNull @NonNull CaptureRequest request, @androidx.annotation.NonNull @NonNull TotalCaptureResult result) {
                                    super.onCaptureCompleted(session, request, result);
                                    Bitmap bitmap = textureView.getBitmap();
                                }
                            }, null);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }//try
                    }// onConfigured

                    @Override
                    public void onConfigureFailed(@androidx.annotation.NonNull @NonNull CameraCaptureSession session) {
                    }// onConfigureFailed
                }, null);
    }//onPreviewButtonClick
}//CameraDeviceActivity
