package com.emp.captchaService;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioFileFormat;
import java.io.*;

@Service
public class AudioCaptchaService {

    public void playCaptcha(String captcha, OutputStream responseOut) {

        System.out.println("➡️ AudioCaptchaService.playCaptcha() called");

        File tempFile = null;

        try {
            Voice voice = VoiceManager.getInstance().getVoice("kevin16");

            if (voice == null) {
                System.out.println("❌ Voice kevin16 not found");
                return;
            }

            voice.allocate();

            // 🔹 Create temp WAV file (no extension here!)
            String basePath = System.getProperty("java.io.tmpdir")
                    + File.separator + "captcha_" + System.nanoTime();

            SingleFileAudioPlayer audioPlayer =
                    new SingleFileAudioPlayer(basePath, AudioFileFormat.Type.WAVE);

            voice.setAudioPlayer(audioPlayer);

            for (char c : captcha.toCharArray()) {
                System.out.println("▶ Speaking digit: " + c);
                voice.speak(String.valueOf(c));
            }

            audioPlayer.close();
            voice.deallocate();

            // 🔹 Now read the generated WAV
            tempFile = new File(basePath + ".wav");

            try (InputStream in = new FileInputStream(tempFile)) {
                byte[] buffer = new byte[4096];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    responseOut.write(buffer, 0, len);
                }
                responseOut.flush();
            }

            System.out.println("✅ WAV audio streamed to browser");

        } catch (Exception e) {
            System.out.println("❌ Error generating audio captcha");
            e.printStackTrace();
        } finally {
            // 🔹 Cleanup temp file
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
