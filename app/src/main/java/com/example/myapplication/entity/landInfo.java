package com.example.myapplication.entity;

public class landInfo {

    public byte id;       //编号
    public int addr;		//终端短地址,
    public boolean LampState;  //灯的状态  0：关闭
    public byte Light;      //光照值
    public byte Temperature;//温度
    public byte Humidity;   //湿度

    public landInfo() {
    }
}
