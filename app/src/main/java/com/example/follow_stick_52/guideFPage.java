package com.example.follow_stick_52;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class guideFPage extends AppCompatActivity {
    Button uibtn, functionbtn, settingGuidebtn, homebtn;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_fpage);

        uibtn = findViewById(R.id.uibtn);
        functionbtn = findViewById(R.id.functionbtn);
        settingGuidebtn = findViewById(R.id.settingGuidebtn);
        homebtn = findViewById(R.id.homebtn);

        //tts 설정
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != android.speech.tts.TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                }
            } //onInit
        }); //TextToSpeech

        //그래픽 설명 버튼(uibtn)
        uibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 그래픽 설명(uiPage) 페이지로 이동
                Intent intent = new Intent(getApplicationContext(), uiFPage.class);
                startActivity(intent);

                // 슬라이드 애니메이션 제거
                overridePendingTransition(0,0);

                // 애플리케이션 그래픽 설명 화면(uiPage)
                Toast.makeText(getApplicationContext(), "애플리케이션 그래픽 설명", Toast.LENGTH_SHORT).show();
                String text = "애플리케이션 그래픽 설명 화면입니다 팔로우 스틱 어플리케이션 그래픽에 대한 설명은 버튼을 눌러 다른 화면으로 이동할 때마다 무조건 화면에 대한 그래픽을 설명해줍니다" +
                        "화면 하단에는 이전 페이지로 가는 버튼이 있습니다";

                tts.setPitch(1.0f);
                tts.setSpeechRate(1.0f);

                tts.speak(text, TextToSpeech.QUEUE_ADD, null);

            } //onClick
        }); //uibtn.setOnClickListner

        //애플리케이션 기능 설명 버튼(functionbtn)
        functionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 애플리케이션 기능 설명(functionPage) 페이지로 이동
                Intent intent = new Intent(getApplicationContext(), functionFPage.class);
                startActivity(intent);

                // 슬라이드 애니메이션 제거
                overridePendingTransition(0,0);

                //functionPage 그래픽 설명
                Toast.makeText(getApplicationContext(), "애플리케이션 기능 설명", Toast.LENGTH_SHORT).show();
                String text = "애플리케이션 기능 설명 화면입니다 팔로우 스틱 어플리케이션의 주요 기능은 공공 지하철 위치 찾기 기능과 공공 화장실 위치 찾기 기능입니다" +
                        "해당 기능 페이지에서 중간 부분의 버튼을 누르면 해당 기능에 대한 페이지로 연동됩니다" +
                        "화면 하단에는 이전 페이지로 가는 버튼이 있습니다";

                tts.setPitch(1.0f);
                tts.setSpeechRate(1.0f);

                tts.speak(text, TextToSpeech.QUEUE_ADD, null);

            } //onClick
        }); //functionbtn.setOnClickListner

        //애플리케이션 환경 설정 안내 버튼(settingGuicebtn)
        settingGuidebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //환경설정 안내(settingGuidePage)페이지로 이동
                Intent intent = new Intent(getApplicationContext(), settingGuideFPage.class);
                startActivity(intent);

                //슬라이드 애니메이션 제거
                overridePendingTransition(0,0);

                //settingGuidePage 그래픽 설명
                Toast.makeText(getApplicationContext(), "애플리케이션 환경 설정 안내", Toast.LENGTH_SHORT).show();
                String text = "애플리케이션 환경 설정 안내 화면입니다 팔로우 스틱 애플리케이션을 사용하시려면 무조건 와이파이 및 블루투스,GPS를 연결해주셔야 합니다" +
                        "블루투스를 켰다면 블루투스 연결 버튼을 눌러 지팡이와 액션캠을 연결해주세요" +
                        "모든 환경설정을 마치셨다면 LED ON/OFF버튼을 사용할 수 있습니다 필요에 따라 LED ON/OFF 버튼을 눌러 지팡이의" +
                        "LED 사용을 제어해주세요 화면 제일 하단에는 이전 페이지로 가는 버튼이 있습니다";

                tts.setPitch(1.0f);
                tts.setSpeechRate(1.0f);

                tts.speak(text, TextToSpeech.QUEUE_ADD, null);

            } //onClick
        }); //settingGuidebtn.setOnClickListner

        // 홈 화면 바로가기 버튼(homebtn(secondPage))
        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 홈 (secondPage) 페이지로 이동
                Intent intent = new Intent(getApplicationContext(), secondFPage.class);
                startActivity(intent);

                // 슬라이드 애니메이션 제거
                overridePendingTransition(0,0);

                //guidePage 그래픽 설명
                Toast.makeText(getApplicationContext(), "홈 화면", Toast.LENGTH_SHORT).show();
                String text = "어플에 대한 설명 버튼과 그 아래에는 앱 사용 바로가기 버튼이 있는 화면입니다";

                tts.setPitch(1.0f);
                tts.setSpeechRate(1.0f);

                tts.speak(text, TextToSpeech.QUEUE_ADD, null);
                return;

            } //onClick
        }); //homebtn.setOnClickListner

    } //onCreate
}