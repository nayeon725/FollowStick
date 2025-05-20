package com.example.follow_stick_52;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class subFPage extends AppCompatActivity {
    TextToSpeech tts;
    Button homebtn, subbtn, backbtn, nextbtn;

    Location location;
    double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_fpage);

        homebtn = findViewById(R.id.homebtn);
        subbtn = findViewById(R.id.subbtn);
        backbtn = findViewById(R.id.backbtn);
        nextbtn = findViewById(R.id.nextbtn);

        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //tts 설정
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != android.speech.tts.TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                } //if
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
                overridePendingTransition(0, 0);
            }//onClick
        });//homebtn.setOnClickListner

        //지하철 위치 찾기 버튼(subbtn)
        subbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions( subFPage.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                            0 );
                }
                else{
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    lat = location.getLatitude();
                    lng = location.getLongitude();

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            1000,
                            1,
                            gpsLocationListener);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            1000,
                            1,
                            gpsLocationListener);
                    Log.v("locate", String.valueOf(lat));
                    Log.v("locate", String.valueOf(lng));

                    String url = "kakaomap://search?q=지하철&x="+lat+"&y="+lng+"&sort=distance";

                    //지하철 위치 찾기페이지로 이동(카카오 지도 연동)
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            }//onClick
        });//subbtn.setOnClickListener


        //이전 페이지로 넘어가기 버튼(backbtn)
        //이전 페이지 == 화장실 위치 찾기 페이지(toiletPage)
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이전(toiletPage)페이지로 이동
                Intent intent = new Intent(getApplicationContext(), toiletFPage.class);
                startActivity(intent);

                //슬라이드 애니메이션 제거
                overridePendingTransition(0,0);

                //toiletPage tts
                Toast.makeText(getApplicationContext(), "공공 화장실 위치 찾기", Toast.LENGTH_SHORT).show();
                String text = "공공 화장실 위치 찾기 화면입니다.";

                tts.setPitch(1.0f);
                tts.setSpeechRate(1.0f);

                tts.speak(text, TextToSpeech.QUEUE_ADD, null);
                return;
            }//onClick
        });//backbtn.setOnClickListener

        //다음 페이지로 넘아가기 버튼(nextbtn)
        //다음 페이지 == 화장실 위치 찾기 페이지(toiletPage)
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //다음(toiletPage)페이지로 이동
                Intent intent = new Intent(getApplicationContext(), toiletFPage.class);
                startActivity(intent);

                //슬라이드 애니메이션 제거
                overridePendingTransition(0,0);

                //toiletPage 그래픽 설명
                Toast.makeText(getApplicationContext(), "공공 화장실 위치 찾기", Toast.LENGTH_SHORT).show();
                String text = "공공 화장실 위치 찾기 화면입니다.";

                tts.setPitch(1.0f);
                tts.setSpeechRate(1.0f);

                tts.speak(text, TextToSpeech.QUEUE_ADD, null);
                return;
            }//onClick
        });//nextbtn.setOnClickListener

    }//onCreate

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };
}//AppCompatActivity