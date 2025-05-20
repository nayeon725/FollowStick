package com.example.follow_stick_52;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class secondFPage extends AppCompatActivity {
    Button manualbtn, shortcutbtn;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_fpage);

        manualbtn = findViewById(R.id.manualbtn);
        shortcutbtn = findViewById(R.id.shortcutbtn);

        //tts 설정
        tts = new TextToSpeech(secondFPage.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != android.speech.tts.TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                }//if
            }//onInit
        }); //TextToSpeech

        // 애플리케이션 설명서 버튼(manualbtn)
        manualbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 설명서(guidePage) 페이지로 이동
                Intent intent = new Intent(getApplicationContext(), guideFPage.class);
                startActivity(intent);

                // 슬라이드 애니메이션 제거
                overridePendingTransition(0,0);

                //guidePage 그래픽 설명
                Toast.makeText(getApplicationContext(), "애플리케이션 설명", Toast.LENGTH_SHORT).show();
                String text = "화면 상단에는 팔로우 스틱 로고가 있고 아래에는 1번 그래픽 설명 버튼 2번 기능 설명 버튼 3번 환경 설정 안내 버튼 4번 홈화면 가기 버튼이 있습니다";

                tts.setPitch(1.0f);
                tts.setSpeechRate(1.0f);

                tts.speak(text, TextToSpeech.QUEUE_ADD, null);
                return;

            } //onClick
        }); //manual.setOnClickListener

        // 앱 사용 바로가기 버튼(지하철, 화장실)
        shortcutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // shortCutPage로 넘어가기
                Intent intent = new Intent(getApplicationContext(), subFPage.class);
                startActivity(intent);

                // 슬라이드 애니메이션 제거
                overridePendingTransition(0,0);

                //shortCutPage 그래픽 설명
                Toast.makeText(getApplicationContext(), "앱사용 바로가기", Toast.LENGTH_SHORT).show();
                String text = "지하철 위치 찾기 화면입니다 상단에는 홈화면 바로가기 버튼이 있고 중간에는 지하철 위치 찾기 버튼이 있고 하단 왼쪽은 이전 버튼 오른쪽은 다음 버튼이 있습니다";

                tts.setPitch(1.0f);
                tts.setSpeechRate(1.0f);

                tts.speak(text, TextToSpeech.QUEUE_ADD, null);
                return;
            } //onClick
        }); //shortcutbtn.setOnClickListener

    } //onCreate
} //secondPage.class