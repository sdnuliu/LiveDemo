package com.example.liuteng.livedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.liuteng.livedemo.util.XlfLog;

public class MainActivity extends AppCompatActivity {
    private String info;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XlfLog.d("我要测试log");
        test();
        mButton = (Button) findViewById(R.id.btn_go_video_classroom);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this,VideoClassroom.class));
            }
        });
//        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
//        info+="stacktrace len :"+stacktrace.length;
//
//        for (int i = 0; i < stacktrace.length; i++) {
//            info+="\n\t"+"----  the " + i + " element  ----";
//            info+="\n\t"+"toString: " + stacktrace[i].toString();
//            info+="\n\t"+"ClassName: " + stacktrace[i].getClassName();
//            info+="\n\t"+"FileName: " + stacktrace[i].getFileName();
//            info+="\n\t"+"LineNumber: " + stacktrace[i].getLineNumber();
//            info+="\n\t"+"MethodName: " + stacktrace[i].getMethodName();
//        }
//        Log.d("TEST",info);
    }

    private void test() {
        XlfLog.e("error log 测试");
    }
}
