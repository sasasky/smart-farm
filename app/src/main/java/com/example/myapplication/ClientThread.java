package com.example.myapplication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import android.util.Log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

@SuppressLint("HandlerLeak")
public class ClientThread extends Thread{
    private String TAG = "zigbee_demo";

    private OutputStream outputStream = null;
    private InputStream inputStream = null;
    private Socket socket;
    private SocketAddress socketAddress;
    public static Handler childHandler;
    private boolean RxFlag = true;
    public boolean bClientThreadStart = false;
    private RxThread rxThread;
    final int TEXT_INFO = 12;
    static final int RX_EXIT = 11;
    static final int TX_DATA = 10;
    Context mainContext;
    Message msg;
    private String strIP;
    final int SERVER_PORT = 20001;

    public ClientThread(String ip) {
        strIP = ip;
    }

    //连接网络
    void connect() {
        RxFlag = true;
        socketAddress = new InetSocketAddress(strIP, SERVER_PORT);
        socket = new Socket();
        bClientThreadStart = false;

        try {
            socket.connect(socketAddress, SERVER_PORT);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            msg = LandDataActivity.mainHandler.obtainMessage(LandDataActivity.TIPS_UPDATE_UI, "连接成功");
            LandDataActivity.mainHandler.sendMessage(msg);

            rxThread = new RxThread();
            rxThread.start();
            bClientThreadStart=true;
        } catch (IOException e) {
            try {
                sleep(10);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            msg = LandDataActivity.mainHandler.obtainMessage(LandDataActivity.TIPS_UPDATE_UI, "无法连接到服务器");
            LandDataActivity.mainHandler.sendMessage(msg);
            e.printStackTrace();
            bClientThreadStart = false;
        } catch (NumberFormatException e) {

        }
    }

    void initChildHandler() {

        Looper.prepare();  //在子线程中创建Handler必须初始化Looper

        childHandler = new Handler() {
            //子线程消息处理中心
            public void handleMessage(Message msg) {

                //接收主线程及其他线程的消息并处理...
                switch (msg.what) {
                    case TX_DATA:
                        int len = msg.arg1;

                        try {
                            outputStream.write((byte [])msg.obj, 0, len);
                            outputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    case RX_EXIT:
                        RxFlag = false;
                        try {
                            if (socket.isConnected()) {
                                inputStream.close();
                                outputStream.close();
                                socket.close();
                            }

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        childHandler.getLooper().quit();// 结束消息队列

                        break;

                    default:
                        break;
                }

            }
        };

        // 启动该线程的消息队列
        Looper.loop();

    }

    public void run() {
        connect();
        initChildHandler();
        msg = LandDataActivity.mainHandler.obtainMessage(LandDataActivity.TIPS_UPDATE_UI, "与服务器断开连接");
        LandDataActivity.mainHandler.sendMessage(msg);
    }

    //socket 接收线程
    public class RxThread extends Thread {
        public void run() {
            try {
                while (socket.isConnected() && RxFlag) {
                    byte RxBuf[] = new byte[256];
                    int len = inputStream.read(RxBuf);

                    if (len > 2 && RxBuf[0] == 0x3A) {
                        int index = 4, i;

                        // 解析数据
                        switch (RxBuf[1]) {
                            case 0x01:
                                Log.i(TAG, "RxThread len="+len);
                                index = 2;
                                for (i = 0; i <  LandDataActivity.MAX_DEVICE; i++) {
                                    LandDataActivity.LandInfo[i].id=RxBuf[index++];
                                    LandDataActivity.LandInfo[i].addr=RxBuf[index++]+RxBuf[index++]*256;
                                    LandDataActivity.LandInfo[i].LampState=RxBuf[index++]>0?true:false;
                                    LandDataActivity.LandInfo[i].Light=(byte)RxBuf[index++];
                                    LandDataActivity.LandInfo[i].Temperature=(byte)RxBuf[index++];
                                    LandDataActivity.LandInfo[i].Humidity=(byte)RxBuf[index++];
                                }

                                msg = LandDataActivity.mainHandler.obtainMessage(LandDataActivity.RX_DATA_UPDATE_UI,"Connect");
                                LandDataActivity.mainHandler.sendMessage(msg);
                                break;
                            case 0x0A:  //循环在刷新画面，写操作时这里可以不用刷新

                                break;

                            default:
                                break;
                        }
                    }else if (len < 0){
                        msg = LandDataActivity.mainHandler.obtainMessage(LandDataActivity.TIPS_UPDATE_UI,
                                "与服务器断开连接");
                        LandDataActivity.mainHandler.sendMessage(msg);

                        //退出接收线程
                        msg = childHandler.obtainMessage(RX_EXIT);
                        childHandler.sendMessage(msg);
                        break;

                    }

                    //sleep(100);
                }

                if (socket.isConnected())
                    socket.close();

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
