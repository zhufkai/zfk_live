package com.zhufk.zfk_live.pusher;

import android.hardware.Camera.CameraInfo;
import android.util.Log;
import android.view.SurfaceHolder;
import com.zhufk.zfk_live.live.PushNative;
import com.zhufk.zfk_live.params.AudioParams;
import com.zhufk.zfk_live.params.VideoParams;

public class LivePusher implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private VideoPusher videoPusher;
    private AudioPusher audioPusher;
    private PushNative pushNative;

    public LivePusher(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        surfaceHolder.addCallback(this);
        prepare();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.i("tag", surfaceHolder.toString());
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    private void prepare() {
        pushNative = new PushNative();
        //实例化视频推流器
        VideoParams videoParams = new VideoParams(480, 320, CameraInfo.CAMERA_FACING_BACK);
        videoPusher = new VideoPusher(surfaceHolder, videoParams, pushNative);

        //实例化音频推流器
        AudioParams audioParams=new AudioParams();
        audioPusher=new AudioPusher(audioParams,pushNative);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        stopPush();
        release();
    }

    public void switchCamera() {
        videoPusher.switchCamera();
    }

    public void starPush(String url) {
        videoPusher.startPush();
        audioPusher.startPush();
        pushNative.startPush(url);
//        pushNative.setLiveStateChangeListener(liveStateChangeListener);
    }

    public void stopPush() {
        videoPusher.stopPush();
        audioPusher.stopPush();
        pushNative.stopPush();
    }

    private void release() {
        videoPusher.release();
        audioPusher.release();
        pushNative.release();
    }
}
