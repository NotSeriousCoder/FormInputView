package com.bingor.forminput;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bingor.forminputview.FormInputView;
import com.bingor.poptipwindow.builder.CustomTipWindowBuilder;
import com.bingor.poptipwindow.builder.TipWindowBuilder;
import com.bingor.poptipwindow.impl.OnWindowStateChangedListener;

public class MainActivity extends AppCompatActivity {
//    FormInputView formInputView1;
//    FormInputView formInputView4;
//    FormInputView formInputView7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        formInputView1 = findViewById(R.id.fip_test1);
//        formInputView4 = findViewById(R.id.fip_test4);
//        formInputView7 = findViewById(R.id.fip_test7);
//        formInputView7.setOnItemClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(getBaseContext(), "啦啦啦啦啦", Toast.LENGTH_SHORT).show();
//                Log.d("HXB", "啦啦啦啦啦");
//            }
//        });
        findViewById(R.id.bt_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                formInputView7.setTitleOffset(32);
//                formInputView7.setTextSizeTitle(32);
//                formInputView1.setTextSize(160);
//                formInputView1.setTitle("啦啦啦啦啦啦啦啦啦啦");
//                formInputView1.setBorderWidth(46);
//                formInputView1.setRadius(0);
//                formInputView1.setShowLeftIcon(!formInputView1.isShowLeftIcon());
//                formInputView7.setShowRightIcon(!formInputView7.isShowRightIcon());
//                formInputView4.setShowPswSwitch(!formInputView4.isShowPswSwitch());
//                formInputView7.setLeftIconRes(R.drawable.ic_test);
//                formInputView4.setPswSwitchIconRes(R.drawable.selector_psw22);
//                formInputView7.setRightIconRes(R.drawable.ic_test);
//                formInputView7.setBorderColor(Color.parseColor("#88000000"));
//                formInputView7.setInputType(InputType.INPUTTYPE_NONE);

                LinearLayout mainLayout = new LinearLayout(MainActivity.this);
                mainLayout.setOrientation(LinearLayout.VERTICAL);
                FormInputView fivBaseUrl = new FormInputView(MainActivity.this);
                fivBaseUrl.setTextSize(20);
                fivBaseUrl.setTitle("baseUrl");
                fivBaseUrl.setBackgroundColor(Color.WHITE);
                fivBaseUrl.setBorderColor(Color.RED);
                fivBaseUrl.setBorderWidth(4);

                mainLayout.addView(fivBaseUrl);

                new CustomTipWindowBuilder(MainActivity.this, TipWindowBuilder.TIP_TYPE_DIALOG)
                        //不设置就没有确定按钮
                        .setOK("好的")
                        //不设置就没有取消按钮
                        .setCancel("不要")
                        //窗口是否包裹内容
                        .setWrapContent(false)
                        //自定义控件
                        .setContentView(mainLayout)
                        //设置了自定义控件，就不要再设置文字了哦，会被覆盖的
                        //.setTextContent("确定要删除这个文件吗~~")
                        //空白处不透明度1=全黑 0=透明
                        .setAlpha(0.2f)
                        //是否能点击空白处/返回键关闭窗口（WINDOW模式下，无法拦截返回键QAQ）
                        .setCancelable(false)
                        //回调
                        .setOnWindowStateChangedListener(new OnWindowStateChangedListener() {
                            @Override
                            public void onOKClicked() {
                                Toast.makeText(getBaseContext(), "好吧", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelClicked() {
                                Toast.makeText(getBaseContext(), "取消", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onOutsideClicked() {
                                Toast.makeText(getBaseContext(), "窗户消失", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create()
                        //DIALOG模式下，传null就行，WINDOW模式下，传界面里面的任意一个View就好
                        .show(null);
            }
        });


    }
}
