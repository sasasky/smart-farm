package com.example.myapplication.fragment;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.AddLandActivity;
import com.example.myapplication.R;
import com.example.myapplication.User1MainActivity;
import com.example.myapplication.User2MainActivity;

public class AddFragment extends Fragment {
    private Spinner spinner;
    private String sort;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user2_add, container, false);
        final Context context=getActivity();
        spinner = (Spinner) view.findViewById(R.id.edt_sort);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(context, R.array.sort, android.R.layout.simple_spinner_item);
        //设置spinner中每个条目的样式，同样是引用android提供的布局文件
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPromptId(R.string.prompt_goods_sort);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //当item被选择后调用此方法
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取我们所选中的内容
                sort = parent.getItemAtPosition(position).toString();
                //弹一个吐司提示我们所选中的内容
                Toast.makeText(context.getApplicationContext(), sort, Toast.LENGTH_SHORT).show();
            }
            //只有当patent中的资源没有时，调用此方法
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("nothingSelect");
            }
        });
        EditText edt_title = view.findViewById(R.id.edt_title);
        EditText edt_detail = view.findViewById(R.id.edt_detail);
        EditText edt_price = view.findViewById(R.id.edt_price);
        final String title = edt_title.getText().toString();
        final String detail = edt_detail.getText().toString();
        final String price = edt_price.getText().toString();
        Button add = view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(title)){
                    Toast.makeText(context, "请输入土地名称", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(detail)){
                    Toast.makeText(context, "请输入电话号码土地详情", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(price)){
                    Toast.makeText(context, "请输入土地价格", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);//设置弹出框的第二种方法
                    builder.setTitle("发布");
                    builder.setMessage("确定发布吗?");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent it = new Intent(context, User2MainActivity.class);//启动MainActivity
                            startActivity(it);
                        }
                    });
                    builder.setNegativeButton("否", null);
                    builder.show();
                }
            }
        });
        return view;
    }
}
