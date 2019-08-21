package com.zhufk.zfk_live;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.zhufk.zfk_live.pusher.LivePusher;

public class MainActivity extends AppCompatActivity {

    static final String URL = "rtmp://140.143.237.149:1935/live/zhufukai";
    private LivePusher live;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        //相机图像的预览
        live = new LivePusher(surfaceView.getHolder());
    }

    /**
     * 开始直播
     * @param
     */
    public void mStartLive(View view) {
        Button btn = (Button)view;
        if(btn.getText().equals("开始直播")){
            live.starPush(URL);
            btn.setText("停止直播");
        }else{
            live.stopPush();
            btn.setText("开始直播");
        }
    }

    /**
     * 切换摄像头
     * @param btn
     */
    public void mSwitchCamera(View btn) {
        live.switchCamera();
    }
}
