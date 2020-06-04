package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.myapplication.entity.land;
import com.example.myapplication.entity.landData;
import com.example.myapplication.entity.landInfo;
import com.example.myapplication.entity.landInfoData;
import com.example.myapplication.service.LandService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LandDataActivity extends AppCompatActivity {
    private int landId;
    private TextView Land_Name;
    private TextView Land_Locate;
    private ImageView Land_Pic;
    private int temperature;
    private int humidity;
    private int Light;
    private int weather;

    private TextView textTemp1, textHumi1, textLight1, textWater;
    static TextView textTips;
    private Button btnNetwork, btnWater;

    //消息定义
    static final int RX_DATA_UPDATE_UI = 1;
    final int TX_DATA_UPDATE_UI = 2;
    static final int TIPS_UPDATE_UI = 3;
    final int READ_ALL_INFO = 4;
    final int WRITE_LAMP = 5;
    final int WRITE_LAMP_ALL = 6;
    public static Handler mainHandler;
    private ClientThread clientThread = null;
    private Timer mainTimer;

    byte SendBuf[] = { 0x3A, 0x00, 0x01, 0x0A, 0x00, 0x00, 0x23, 0x00 };
    private String strTemp, strHumi, strRfid, strLight;
    private Message MainMsg;
    final byte LAMP_ON = 1;
    final byte LAMP_OFF = 0;
    private byte LampAllState = LAMP_ON;

    final static int MAX_DEVICE=2;//共定义2路终端
    static landInfo  LandInfo[];

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LandInfo = new landInfo[MAX_DEVICE];

        for(int i=0; i<MAX_DEVICE; i++){
            LandInfo[i]=new landInfo();
            LandInfo[i].id=(byte)(i+1);
            LandInfo[i].addr=0;
            LandInfo[i].LampState=false;
        }

        //initControl();
        initMainHandler();

        Intent i = getIntent();
        landId = i.getIntExtra("landId",0);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_land_data);
        TextView back =findViewById(R.id.button_backward);
        TextView setting =findViewById(R.id.button_setting);

        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        setting.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LandDataActivity.this.finish();
            }
        });
        Land_Name =findViewById(R.id.Land_Name);
        Land_Locate =findViewById(R.id.Land_Detail);
        Land_Pic = findViewById(R.id.Land_Pic);
        textTemp1 = findViewById(R.id.temperature1);
        textHumi1 = findViewById(R.id.humidity1);
        textLight1 = findViewById(R.id.light1);
        textTips = findViewById(R.id.Tips);
        btnNetwork = findViewById(R.id.btn_network);
        btnNetwork.setOnClickListener(new ButtonClick());
/*        final TextView water =findViewById(R.id.water);
        final TextView warm =findViewById(R.id.warm);
        final TextView light =findViewById(R.id.light);*/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        LandService request = retrofit.create(LandService.class);
        Call<landData> call = request.getItem(landId);
        call.enqueue(new Callback<landData>() {
            @Override
            public void onResponse(Call<landData> call, Response<landData> response) {
                land lands=response.body().getData();
                Land_Name.setText(lands.getlocation());
                Land_Locate.setText(lands.getbrief());
                Land_Pic.setImageBitmap(lands.getphotoUrl());
            }
            @Override
            public void onFailure(Call<landData> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
/*
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(LandDataActivity.this, PlanActivity.class);//启动MainActivity
                it.putExtra("landId",landId);
                it.putExtra("humidity",humidity);
                it.putExtra("temperature",temperature);
                it.putExtra("Light",Light);
                it.putExtra("weather",weather);
                startActivity(it);
            }
        });*/
    }

    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (clientThread == null
                    && (v.getId() != R.id.btn_network)) {
                textTips.setText("提示信息：请先连接网络");
                return;
            }

            switch (v.getId()) {
                case R.id.btn_network: //连接网络
                    connect();
                    break;
                /*case R.id.watering: //开关终端1的灯
                    MainMsg = mainHandler.obtainMessage(TX_DATA_UPDATE_UI,
                            WRITE_LAMP, 1);
                    mainHandler.sendMessage(MainMsg);
                    break;*/
                default:
                    break;
            }
        }
    }

    void initMainHandler() {
        mainHandler = new Handler() {

            //主线程消息处理中心
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case RX_DATA_UPDATE_UI:
                        System.out.println("RX_DATA_UPDATE_UI");
                        //终端1
                        strTemp = LandInfo[0].Temperature + "℃";
                        textTemp1.setText(strTemp);
                        strHumi = LandInfo[0].Humidity + "%";
                        textHumi1.setText(strHumi);
                        strLight = LandInfo[0].Light + "%";
                        textLight1.setText(strLight);
/*
                        if (LandInfo[0].LampState)                           //低电平亮，高电平灭
                        {
                            textWater.setText("灌溉状态：开启中"); //灯亮
                            btnWater.setText("关闭灌溉");
                        }
                        else{
                            textWater.setText("灌溉状态：关闭中"); //灯灭
                            btnWater.setText("开启灌溉");
                        }*/
                        break;

                    case TX_DATA_UPDATE_UI: //msg.arg1保存功能码 arg2保存终端地址
                        switch (msg.arg1) {
                            case READ_ALL_INFO://读取所有终端的数据
                                SendBuf[0]=0x3A;
                                SendBuf[1] = 0x01;           //FC
                                SendBuf[2] = (byte) msg.arg2;//0xFF;
                                SendBuf[3] = 0x0;
                                SendData(SendBuf, 4); //查询所有终端
                                break;

                            case WRITE_LAMP:
                                SendBuf[0]=0x3A;
                                SendBuf[1] = 0x02;  //FC
                                SendBuf[2] = (byte) msg.arg2; //终端编号
                                if (LandInfo[SendBuf[2]-1].LampState) { //当前灯处于开灯状态，发命令关灯
                                    SendBuf[3] = 0x00; //data
                                } else {
                                    SendBuf[3] = 0x01;
                                }

                                SendData(SendBuf, 4); //发命令控制灯命令
                                break;
                            case WRITE_LAMP_ALL:
                                SendBuf[0]=0x3A;
                                SendBuf[1] = 0x02;  //FC
                                SendBuf[2] = (byte) 0xFF; //终端编号
                                SendBuf[3] = LampAllState;

                                if (LampAllState == LAMP_ON) {
                                    LampAllState = LAMP_OFF;
                                } else {
                                    LampAllState = LAMP_ON;
                                }
                                SendData(SendBuf, 4); //发命令控制灯
                                break;

                            default:
                                break;
                        }
                        break;
                    case TIPS_UPDATE_UI:
                        String str = (String) msg.obj;
                        textTips.setText(str);
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    //连接
    private void connect() {
        clientThread = new ClientThread("101.37.25.139");//建立客户端线程
        clientThread.start();

        try{
            Thread.sleep(1000);
        }catch(InterruptedException e)
        {
            ;
        }

        if(clientThread.bClientThreadStart){
            mainTimer = new Timer();//定时查询所有终端信息
            setTimerTask();
        }
    }

    private void setTimerTask() {
        mainTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (clientThread != null) {
                    MainMsg = mainHandler.obtainMessage(TX_DATA_UPDATE_UI,
                            READ_ALL_INFO, 0xFF);
                    mainHandler.sendMessage(MainMsg);
                }
            }
        }, 1000, 1500);//表示1000毫秒之后，每隔1500毫秒执行一次
    }

    //通知客户端线程 发送消息
    void SendData(byte buffer[], int len) {
        MainMsg = ClientThread.childHandler.obtainMessage(ClientThread.TX_DATA,
                len, 0, (Object) buffer);
        ClientThread.childHandler.sendMessage(MainMsg);
    }


/*
    void initControl() {
        textTemp1 = (TextView) findViewById(R.id.temperature1);
        textTemp1.setText("0");
        textHumi1 = (TextView) findViewById(R.id.humidity1);
        textHumi1.setText("0");
        //textLight1 = (TextView) findViewById(R.id.light1);
        //textLight1.setText("0");


        textTips = (TextView) findViewById(R.id.Tips);
        textTips.setText("000");
        btnNetwork = (Button) findViewById(R.id.btn_network);
        btnNetwork.setOnClickListener(new ButtonClick());

        textWater = (TextView) findViewById(R.id.Water);
        textWater.setText("灌溉状态：关闭中");
        btnWater = (Button) findViewById(R.id.watering);
        btnWater.setOnClickListener(new ButtonClick());
    }*/



}
