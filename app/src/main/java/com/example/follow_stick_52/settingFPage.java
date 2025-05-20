package com.example.follow_stick_52;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class settingFPage extends AppCompatActivity {
    Button backbtn, srOnbtn, srOffbtn;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_fpage);

        backbtn = findViewById(R.id.backbtn);
        srOnbtn = findViewById(R.id.srOnbtn);
        srOffbtn = findViewById(R.id.srOffbtn);

        //tts 설정
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != android.speech.tts.TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                }
            } //onInit
        }); //TextToSpeech

        //스크린 리더 설정 화면(MainActivity)으로 가기 버튼
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //스크린 리더 설정 화면(MainActivity) 페이지로 이동
                Intent intent = new Intent(getApplicationContext(), secondFPage.class);
                startActivity(intent);

                //슬라이드 애니메이션 제거
                overridePendingTransition(0,0);

            }//onClick
        });//homebtn.setOnClickListener

        // 휴대폰 스크린 리더 기능 OFF인 사람 전용(==자체 스크린리더 기능 ON)
        srOnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //애플리케이션 사용 설명서 페이지로 이동(secondPage)
                Intent intent = new Intent(getApplicationContext(), secondFPage.class);
                startActivity(intent);

                //슬라이드 애니메이션 제거
                overridePendingTransition(0,0);

                //secondPage 그래픽 설명
                Toast.makeText(getApplicationContext(), "홈 화면", Toast.LENGTH_SHORT).show();
                String text = "화면 상단에는 팔로우 스틱 로고가 있고 아래에는 어플에 대한 설명 버튼, 그 아래에는 앱 사용 바로가기 버튼이 있는 화면입니다";

                tts.setPitch(1.0f);
                tts.setSpeechRate(1.0f);

                tts.speak(text, TextToSpeech.QUEUE_ADD, null);
                return;
            }//onClick
        });//srOnbtn.setOnClickListener

        //휴대폰 스크린 리더 기능 ON인 사람 전용(==자체 스크린리더 기능 OFF)
        srOffbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //애플리케이션 사용 설명서 페이지로 이동(secondPage)
                Intent intent = new Intent(getApplicationContext(), secondFPage.class);
                startActivity(intent);

                //슬라이드 애니메이션 제거
                overridePendingTransition(0,0);

                //secondPage 그래픽 설명
                Toast.makeText(getApplicationContext(), "홈 화면", Toast.LENGTH_SHORT).show();
                String text = "화면 상단에는 팔로우 스틱 로고가 있고 아래에는 어플에 대한 설명 버튼, 그 아래에는 앱 사용 바로가기 버튼이 있는 화면입니다";

                tts.setPitch(1.0f);
                tts.setSpeechRate(1.0f);

                tts.speak(text, TextToSpeech.QUEUE_ADD, null);
                return;
            }//onClick
        });//srOffbtn.setOnClickListener
    }//onCreate
}//AppCompatActivity
