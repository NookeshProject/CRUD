package com.emp.captchaService;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

@Service
public class AudioCaptchaService {

    public void playCaptcha(String captcha, OutputStream out) {

        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        voice.allocate();

        StreamAudioPlayer player = new StreamAudioPlayer(out);
        voice.setAudioPlayer(player);

        // Speak clearly digit by digit
        for (char c : captcha.toCharArray()) {
            voice.speak(String.valueOf(c));
        }

        voice.deallocate();
    }
}
