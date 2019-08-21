package com.zhufk.zfk_live.pusher;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PreviewCallback;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;

import com.zhufk.zfk_live.live.PushNative;
import com.zhufk.zfk_live.params.VideoParams;

import java.io.IOException;
import java.lang.reflect.Method;

public class VideoPusher extends Pusher implements SurfaceHolder.Callback, PreviewCallback {
    private SurfaceHolder surfaceHolder;
    private Camera mCamera;
    private VideoParams videoParams;
    private byte[] buffers;
    private boolean isPushing = false;
    private PushNative pushNative;


    public VideoPusher(SurfaceHolder surfaceHolder, VideoParams videoParams, PushNative pushNative) {
        this.surfaceHolder = surfaceHolder;
        this.videoParams = videoParams;
        this.pushNative = pushNative;
        this.surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.addCallback(this);
    }

    @Override
    public void startPush() {
        //设置视频参数
        pushNative.setVideoOptions(videoParams.getWidth(), videoParams.getHeight(), videoParams.getBitrate(), videoParams.getFps());
        isPushing = true;
    }

    @Override
    public void stopPush() {
        isPushing = true;
    }

    @Override
    public void release() {
        stopPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.i("tag", surfaceHolder.toString());
        startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        stopPreview();
    }

    public void switchCamera() {
        if (videoParams.getCameraId() == CameraInfo.CAMERA_FACING_BACK) {
            videoParams.setCameraId(CameraInfo.CAMERA_FACING_FRONT);
        } else {
            videoParams.setCameraId(CameraInfo.CAMERA_FACING_BACK);
        }

        stopPreview();
        startPreview();
    }

    private void startPreview() {
        try {
            mCamera = Camera.open(videoParams.getCameraId());
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewFormat(ImageFormat.NV21);
            parameters.setPictureSize(videoParams.getWidth(), videoParams.getHeight());
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            parameters.setPreviewSize(videoParams.getWidth(), videoParams.getHeight());
//            setDisplay(parameters,mCamera);
            mCamera.setParameters(parameters);
            mCamera.setPreviewDisplay(surfaceHolder);
            buffers = new byte[videoParams.getWidth() * videoParams.getHeight() * 4];
            mCamera.addCallbackBuffer(buffers);
            mCamera.setPreviewCallbackWithBuffer(this);

            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setDisplay(Camera.Parameters parameters, Camera camera) {
        if (Integer.parseInt(Build.VERSION.SDK) >= 8) {
            setDisplayOrientation(camera,90);
        }else {
            parameters.setRotation(90);
        }
    }

    private void setDisplayOrientation(Camera camera, int i) {
        Method downPolymorphic;
        try {
            downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[]{int.class});
            if (downPolymorphic != null) {
                downPolymorphic.invoke(camera, new Object[]{i});
            }
        } catch (Exception e) {

        }
    }

    private void stopPreview() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        Log.i("bytes", bytes.toString());
        if (mCamera != null) {
            mCamera.addCallbackBuffer(buffers);
        }

        if (isPushing) {
            pushNative.fireVideo(bytes);
        }
    }
}
