package com.emp.captchaService;

import com.sun.speech.freetts.audio.JavaClipAudioPlayer;

import java.io.IOException;
import java.io.OutputStream;

public class StreamAudioPlayer extends JavaClipAudioPlayer {

    private final OutputStream out;

    public StreamAudioPlayer(OutputStream out) {
        super();
        this.out = out;
    }

    @Override
    public boolean write(byte[] audioData) {
        try {
            out.write(audioData);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
