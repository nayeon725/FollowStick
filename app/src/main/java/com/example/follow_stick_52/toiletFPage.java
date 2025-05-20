package com.example.follow_stick_52;

import android.content.Intent;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class toiletFPage extends AppCompatActivity {
    TextToSpeech tts;
    Button homebtn, toiletbtn, backbtn, nextbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toilet_fpage);

        homebtn = findViewById(R.id.homebtn);
        toiletbtn = findViewById(R.id.toiletbtn);
        backbtn = findViewById(R.id.backbtn);
        nextbtn = findViewById(R.id.nextbtn);

        //tts 설정
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != android.speech.tts.TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                }
            } //onInit
        }); //TextToSpeech

        //secondPage로 가기 버튼
        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //홈 화면(secondPage)페이지로 이동
                Intent intent = new Intent(getApplicationContext(), secondFPage.class);
                startActivity(intent);

                //슬라이드 애니메이션 제거
                overridePendingTransition(0,0);
            }//onClick
        });//homebtn.setOnClickListener

        //공공 화장실 위치 찾기 버튼(toiletbtn)
        toiletbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "kakaomap://search?q=화장실";

                //화장실 위치 찾기페이지로 이동(카카오 지도 연동)
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }//onClick
        });//toiletbtn.setOnClickLinstener

        //이전 페이지로 넘어가기 버튼(backbtn)
        //이전 페이지 == 지하철 위치 찾기 페이지(toiletPage)
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이전(subPage)페이지로 이동
                Intent intent = new Intent(getApplicationContext(), subFPage.class);
                startActivity(intent);

                //슬라이드 애니메이션 제거
                overridePendingTransition(0,0);

                //subPage tts
                Toast.makeText(getApplicationContext(), "지하철 위치 찾기", Toast.LENGTH_SHORT).show();
                String text = "지하철 위치 찾기 화면입니다";

                tts.setPitch(1.0f);
                tts.setSpeechRate(1.0f);

                tts.speak(text, TextToSpeech.QUEUE_ADD, null);
            }//onClick
        });//backbtn.setOnCickListener

        //다음 페이지로 넘아가기 버튼(nextbtn)
        //다음 페이지 == 지하철 위치 찾기 페이지(toiletPage)
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //다음(toiletPage)페이지로 이동
                Intent intent = new Intent(getApplicationContext(), subFPage.class);
                startActivity(intent);

                //슬라이드 애니메이션 제거
                overridePendingTransition(0,0);

                //subPage tts
                Toast.makeText(getApplicationContext(), "지하철 위치 찾기", Toast.LENGTH_SHORT).show();
                String text = "지하철 위치 찾기 화면입니다";

                tts.setPitch(1.0f);
                tts.setSpeechRate(1.0f);

                tts.speak(text, TextToSpeech.QUEUE_ADD, null);
                return;

            }//onClick
        });//nextbtn.setOnClickListner

    } //onCreate
} //toiletPage.class