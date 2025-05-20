package com.example.follow_stick_52;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class uiFPage extends AppCompatActivity {
    Button guidePagebtn;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_fpage);

        guidePagebtn = findViewById(R.id.guidePagebtn);

        //tts 설정
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != android.speech.tts.TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                }
            } //onInit
        }); //TextToSpeech

        guidePagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent
                Intent intent = new Intent(getApplicationContext(), guideFPage.class);
                startActivity(intent);

                // 슬라이드 애니메이션 제거
                overridePendingTransition(0,0);
            } //onClick
        }); //setOnClickListner
    } //onCreate
} //uiPage.class