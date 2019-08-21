package com.zhufk.zfk_live.pusher;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import com.zhufk.zfk_live.live.PushNative;
import com.zhufk.zfk_live.params.AudioParams;

public class AudioPusher extends Pusher {

    private AudioParams audioParams;
    private AudioRecord audioRecord;
    private boolean isPushing = false;
    private int minBufferSize;
    private PushNative pushNative;

    public AudioPusher(AudioParams audioParams, PushNative pushNative) {
        this.audioParams = audioParams;
        this.pushNative = pushNative;

        int channelConfig = audioParams.getChannel() == 1 ?
                AudioFormat.CHANNEL_IN_MONO : AudioFormat.CHANNEL_OUT_STEREO;
        //最小缓冲区大小
        minBufferSize = AudioRecord.getMinBufferSize(audioParams.getSampleRateInHz(), channelConfig, AudioFormat.ENCODING_PCM_16BIT);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                audioParams.getSampleRateInHz(),
                channelConfig,
                AudioFormat.ENCODING_PCM_16BIT, minBufferSize);
    }

    @Override
    public void startPush() {
        isPushing = true;
        pushNative.setAudioOptions(audioParams.getSampleRateInHz(), audioParams.getChannel());
        //启动一个录音子线程
        new Thread(new AudioRecordTask()).start();
    }

    @Override
    public void stopPush() {
        isPushing = false;
        audioRecord.stop();
    }

    @Override
    public void release() {
        if (audioRecord != null) {
            audioRecord.release();
            audioRecord = null;
        }
    }

    class AudioRecordTask implements Runnable {

        @Override
        public void run() {
            //开始录音
            audioRecord.startRecording();

            while (isPushing) {
                //通过AudioRecord不断读取音频数据
                byte[] buffer = new byte[minBufferSize];
                int len = audioRecord.read(buffer, 0, buffer.length);
                if (len > 0) {
                    //传给Native代码，进行音频编码
                    pushNative.fireAudio(buffer, len);
                }
            }
        }
    }
}
