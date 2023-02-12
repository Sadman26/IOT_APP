package com.example.iot;
import android.speech.tts.TextToSpeech;
import java.util.Locale;
import android.content.Context;
public class TTSManager {
    private TextToSpeech mTTS;
    private boolean isLoaded = false;

    public void init(Context context) {
        mTTS = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.US);
                    isLoaded = true;
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        // Language data is missing or the language is not supported.
                        // Trigger an error message
                    }
                }
            }
        });
    }
    public void speak(String text) {
        if (isLoaded) {
            mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
    public void shutDown() {
        mTTS.shutdown();
    }

}
