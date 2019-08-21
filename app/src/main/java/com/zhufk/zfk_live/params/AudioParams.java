package com.zhufk.zfk_live.params;

public class AudioParams {
    private int sampleRateInHz = 44100;
    private int channel = 1;

    public int getSampleRateInHz() {
        return sampleRateInHz;
    }

    public void setSampleRateInHz(int sampleRateInHz) {
        this.sampleRateInHz = sampleRateInHz;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public AudioParams() {

    }

    public AudioParams(int sampleRateInHz, int channel) {
        super();
        this.sampleRateInHz = sampleRateInHz;
        this.channel = channel;
    }
}

