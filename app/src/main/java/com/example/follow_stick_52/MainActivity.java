package com.example.follow_stick_52;

import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button settingbtn;
    TextToSpeech tts;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingbtn = findViewById(R.id.settingbtn);

        //tts 설정
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != android.speech.tts.TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                }
            } //onInit
        }); //TextToSpeech

        settingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), settingFPage.class);
                startActivity(intent);

                overridePendingTransition(0,0);

                Toast.makeText(getApplicationContext(), "스크린 리더 설정", Toast.LENGTH_SHORT).show();
                String text = "스크린 리더 설정 화면입니다 화면 상단에는 이전 페이지로 가는 버튼이 있고 화면 중간부터 하단까지 왼쪽은 스크린 리더를 켜는 버튼 오른쪽은 끄는 버튼입니다";

                tts.setPitch(1.0f);
                tts.setSpeechRate(1.0f);

                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            } //onClick
        }); //settingbtn.setOnClickListner
    } //onCreate

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
        super.onDestroy();
    } //onDestroy
} //MainActivity.class