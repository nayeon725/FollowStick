package com.example.follow_stick_52;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ledFPage extends AppCompatActivity {

    ConnectedBluetoothThread mThreadConnectedBluetooth;
    Handler mBluetoothHandler;

    final static int BT_MESSAGE_READ = 2;

    Button homebtn, ledbtn, backbtn, nextbtn;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led_fpage);

        homebtn = findViewById(R.id.homebtn);
        ledbtn = findViewById(R.id.ledbtn);
        backbtn = findViewById(R.id.backbtn);
        nextbtn = findViewById(R.id.nextbtn);

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), secondFPage.class);
                startActivity(intent);

                //슬라이드 애니메이션 제거
                overridePendingTransition(0,0);
            }//onClick
        });//homebtn.setOnClickListener

        ledbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mThreadConnectedBluetooth.write("0");
            }//onClick
        });//ledbtn.setOnClickListener

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //화장실 페이지로 이동
                Intent intent = new Intent(getApplicationContext(), toiletFPage.class);
                startActivity(intent);

                //슬라이드 애니메이션 제거
                overridePendingTransition(0,0);
            }//onClick
        });//back.setOnClickListener

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //지하철 페이지로 이동(지하철 위치찾기, 공공화장실 위치찾기, led 제어하기 화면 무한 루프, 무한 루프 빠져나가려면 홈화면 클릭)
                Intent intent = new Intent(getApplicationContext(), subFPage.class);
                startActivity(intent);

                //슬라이드 애니메이션 제거
                overridePendingTransition(0,0);
            }//onClick
        });//nextbtn.setOnClickListener
    }//onCreate

    private class ConnectedBluetoothThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private byte[] buffer;

        public ConnectedBluetoothThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        public void run() {
            buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = mmInStream.available();
                    if (bytes != 0) {
                        SystemClock.sleep(100);
                        bytes = mmInStream.available();
                        bytes = mmInStream.read(buffer, 0, bytes);
                        mBluetoothHandler.obtainMessage(BT_MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                    }
                } catch (IOException e) {
                    break;
                }
            }
        }

        public void write(String str) {
            Log.v("ble", str);
            byte[] bytes = str.getBytes();
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "데이터 전송 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 해제 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }
}//AppCompatActivity
