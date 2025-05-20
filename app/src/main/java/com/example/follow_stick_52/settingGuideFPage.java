package com.example.follow_stick_52;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
//import com.luckycatlabs.sunrisesunset.dto.Location;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.TimeZone;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

public class settingGuideFPage extends AppCompatActivity {

    Button btConnbtn, ledbtn, backbtn;

    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> mPairedDevices;
    List<String> mListPairedDevices;

    TextToSpeech tts;
    Handler mBluetoothHandler1, mBluetoothHandler2;
    ConnectedBluetoothThread mThreadConnectedBluetooth;
    BluetoothDevice mBluetoothDevice1, mBluetoothDevice2;
    BluetoothSocket mBluetoothSocket1, mBluetoothSocket2;

    private static int flag = 1;
    private StringBuilder sb = new StringBuilder();

//    long mNow;
//    Date mDate;
//    @SuppressLint("SimpleDateFormat")
//    SimpleDateFormat mFormat = new SimpleDateFormat("k:mm:ss");

    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;
    final static int BT_RECEIVE_MESSAGE = 1;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_guide_fpage);

        backbtn = findViewById(R.id.backbtn);
        ledbtn = findViewById(R.id.ledbtn);
        btConnbtn = findViewById(R.id.btConnbtn);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //tts 설정
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != android.speech.tts.TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                }
            } //onInit
        }); //TextToSpeech

        /*//블루투스 활성화(ON) 버튼 눌렀을 때
        btOnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //블루투스 활성화
                bluetoothOn();
            }//onClick
        });//btnOnbtn.setOnClick*/

        // 이전 페이지 버튼 눌렀을 때
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //설명서 화면(guidePage)페이지로 이동
                Intent intent = new Intent(getApplicationContext(), guideFPage.class);
                startActivity(intent);

                // 슬라이드 애니메이션 제거
                overridePendingTransition(0,0);
            } // onClick
        }); // backbtn.setOnclickListener

        // 블루투스 연결 버튼 눌렀을 때
        btConnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listPairedDevices();
            } // onClick
        }); // btConnbtn.setOnclickListener

//        Log.v("bletest", getTime());
//        if(getTime() == "23:53:00") {
//            mThreadConnectedBluetooth.write("0");
//            flag = 0;
//        }

        // LED ON/OFF 버튼 눌렀을 때
        ledbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 1) {
                    mThreadConnectedBluetooth.write("0");
                    flag = 0;

                    String text = "LED가 켜졌습니다.";
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                } else if (flag == 0){
                    mThreadConnectedBluetooth.write("1");
                    flag = 1;

                    String text = "LED가 꺼졌습니다.";
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
            } // onClick
        }); // ledbtn.setOnClickListener

        mBluetoothHandler1 = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case BT_MESSAGE_READ:
                        String readMessage = null;
                        try {
                            readMessage = new String((byte[]) msg.obj, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } //try-catch
                        break;

                    case BT_RECEIVE_MESSAGE:
                        byte[] readBuf = (byte[]) msg.obj;
                        String strIncom = new String(readBuf, 0, msg.arg1);
                        sb.append(strIncom);
                        int endOfLineIndex = sb.indexOf("\r\n");
                        if (endOfLineIndex > 0) {
                            sb.delete(0, sb.length());
                            if (flag == 0) {
                                mThreadConnectedBluetooth.write("1");
                            }
                            else if (flag == 1) {
                                mThreadConnectedBluetooth.write("0");
                            }
                            flag++;
                            ledbtn.setEnabled(true);
                        } //if-else
                        break;
                } //switch-case
            } //handlerMessage
        }; //Handler

        mBluetoothHandler2 = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case BT_MESSAGE_READ:
                        String readMessage = null;
                        try {
                            readMessage = new String((byte[]) msg.obj, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } //try-catch
                        break;

                    case BT_RECEIVE_MESSAGE:
                        byte[] readBuf = (byte[]) msg.obj;
                        String strIncom = new String(readBuf, 0, msg.arg1);
                        sb.append(strIncom);
                        int endOfLineIndex = sb.indexOf("\r\n");
                        if (endOfLineIndex > 0) {
                            sb.delete(0, sb.length());
                            if (flag == 0) {
                                mThreadConnectedBluetooth.write("1");
                            }
                            else if (flag == 1) {
                                mThreadConnectedBluetooth.write("0");
                            }
                            flag++;
                            ledbtn.setEnabled(true);
                        } //if-else
                        break;
                } //switch-case
            } //handlerMessage
        }; //Handler

    } //onCreate

//    private String getTime() {
//        mNow = System.currentTimeMillis();
//        mDate = new Date(mNow);
//        return mFormat.format(mDate);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BT_REQUEST_ENABLE:
                if (resultCode == RESULT_OK) { // 블루투스 활성화를 확인을 클릭하였다면
                    Toast.makeText(getApplicationContext(), "블루투스 활성화", Toast.LENGTH_LONG).show();
                } else if (resultCode == RESULT_CANCELED) { // 블루투스 활성화를 취소를 클릭하였다면
                    Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_LONG).show();
                } //else-if
                break;
        } //switch-case
        super.onActivityResult(requestCode, resultCode, data);
    } //onActivityResult

    void listPairedDevices() {
        if (mBluetoothAdapter.isEnabled()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding``
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            } //if
            mPairedDevices = mBluetoothAdapter.getBondedDevices();

            if (mPairedDevices.size() > 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("장치 선택");

                mListPairedDevices = new ArrayList<>();
                for (BluetoothDevice device : mPairedDevices) {
                    mListPairedDevices.add(device.getName());
                } //for
                final CharSequence[] items = mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);
                mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        connectSelectedDevice(items[item].toString());
                    } //onClick
                }); //setItems
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                Toast.makeText(getApplicationContext(), "페어링된 장치가 없습니다.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
        } //if-else
    } //listPairedDevice

    void connectSelectedDevice(String selectedDeviceName) {
        for (BluetoothDevice tempDevice : mPairedDevices) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            } //if
            if (selectedDeviceName.equals(tempDevice.getName())) {
                mBluetoothDevice1 = tempDevice;
                mBluetoothDevice2 = tempDevice;
                break;
            } //if
        } //for
        try {
            mBluetoothSocket1 = mBluetoothDevice1.createRfcommSocketToServiceRecord(BT_UUID);
            mBluetoothSocket1.connect();
            mThreadConnectedBluetooth = new ConnectedBluetoothThread(mBluetoothSocket1);

//            mBluetoothSocket2 = mBluetoothDevice2.createRfcommSocketToServiceRecord(BT_UUID);
//            mBluetoothSocket2.connect();
//            mThreadConnectedBluetooth = new ConnectedBluetoothThread(mBluetoothSocket2);

            mThreadConnectedBluetooth.start();
            mBluetoothHandler1.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
        } //try-catch

        try {
            mBluetoothSocket2 = mBluetoothDevice2.createRfcommSocketToServiceRecord(BT_UUID);
            mBluetoothSocket2.connect();
            mThreadConnectedBluetooth = new ConnectedBluetoothThread(mBluetoothSocket2);

            mThreadConnectedBluetooth.start();
            mBluetoothHandler1.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
        } //try-catch
    } //connectSelectedDevice

    private class ConnectedBluetoothThread extends Thread {
        private final BluetoothSocket mmSocket1, mmSocket2;
        private final InputStream mmInStream1, mmInStream2;
        private final OutputStream mmOutStream1, mmOutStream2;

        public ConnectedBluetoothThread(BluetoothSocket socket) {
            mmSocket1 = socket;
            mmSocket2 = socket;
            InputStream tmpIn1 = null, tmpIn2 = null;
            OutputStream tmpOut1 = null, tmpOut2 = null;

            try {
                tmpIn1 = socket.getInputStream();
                tmpOut1 = socket.getOutputStream();

                tmpIn2 = socket.getInputStream();
                tmpOut2 = socket.getOutputStream();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            } //try-catch

            mmInStream1 = tmpIn1;
            mmOutStream1 = tmpOut1;

            mmInStream2 = tmpIn2;
            mmOutStream2 = tmpOut2;
        } //connectedBluetoothThread

        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = mmInStream1.available();
                    if (bytes != 0) {
                        SystemClock.sleep(100);
                        bytes = mmInStream1.available();
                        bytes = mmInStream1.read(buffer, 0, bytes);
                        mBluetoothHandler1.obtainMessage(BT_MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                    } //if
                } catch (IOException e) {
                    break;
                } //try-catch
            } //while

            while (true) {
                try {
                    bytes = mmInStream2.available();
                    if (bytes != 0) {
                        SystemClock.sleep(100);
                        bytes = mmInStream2.available();
                        bytes = mmInStream2.read(buffer, 0, bytes);
                        mBluetoothHandler2.obtainMessage(BT_MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                    } //if
                } catch (IOException e) {
                    break;
                } //try-catch
            } //while
        } //run

        public void write(String str) {
            byte[] bytes = str.getBytes();
            try {
                mmOutStream1.write(bytes);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "데이터 전송 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            } //try-catch
        } //write

        public void cancel() {
            try {
                mmSocket1.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 해제 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            } //try-catch
        } //cancel
    } //ConnectedBluetoothThread extends Thread
} //settingGuidePage.class