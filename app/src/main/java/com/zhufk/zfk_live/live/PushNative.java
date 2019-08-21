package com.zhufk.zfk_live.live;

import com.zhufk.zfk_live.listener.LiveStateChangeListener;

public class PushNative {
    public static final int CONNECT_FAILED = 101;
    public static final int INIT_FAILED = 102;

    LiveStateChangeListener liveStateChangeListener;

    /**
     * 接收Native层抛出的错误
     * @param code
     */
    public void throwNativeError(int code){
        if(liveStateChangeListener != null){
            liveStateChangeListener.onError(code);
        }
    }

    public native void startPush(String c);
    public native void stopPush();
    public native void release();

    public native void setVideoOptions(int width,int height,int bitrate,int fps);

    public native void setAudioOptions(int sampleRateInHz,int channel);

    public native void fireVideo(byte[] data);

    public native void fireAudio(byte[] data,int len);

    public void setLiveStateChangeListener(LiveStateChangeListener liveStateChangeListener) {
        this.liveStateChangeListener = liveStateChangeListener;
    }

    public void removeLiveStateChangeListener(){
        this.liveStateChangeListener = null;
    }

    static {
        System.loadLibrary("live");
    }
}
