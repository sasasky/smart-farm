package com.example.myapplication.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreference {

    Context context;
    public SharePreference(Context context)
    {
        this.context = context;
    }

    /****设置状态   false为安装后第一次登录，true为已经登录过****/
    public void setState()
    {
        SharedPreferences sp = context.getSharedPreferences("save.himi", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogin", true);
        editor.commit();
    }

    /***获取状态***/
    public boolean getState()
    {
        SharedPreferences sp = context.getSharedPreferences("save.himi", Context.MODE_PRIVATE);
        boolean b = sp.getBoolean("isLogin", false);
        return b;
    }

}
