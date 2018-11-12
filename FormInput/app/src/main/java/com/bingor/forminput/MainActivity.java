package com.bingor.forminput;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bingor.forminputview.FormInputView;
import com.bingor.forminputview.InputType;

public class MainActivity extends AppCompatActivity {
    FormInputView formInputView1;
    FormInputView formInputView4;
    FormInputView formInputView7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        formInputView1 = findViewById(R.id.fip_test1);
        formInputView4 = findViewById(R.id.fip_test4);
        formInputView7 = findViewById(R.id.fip_test7);
        formInputView7.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getBaseContext(), "啦啦啦啦啦", Toast.LENGTH_SHORT).show();
                Log.d("HXB", "啦啦啦啦啦");
            }
        });
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

                String aaa = formInputView1.getText().toString();
                Log.d("HXB", formInputView1.getText().toString());
            }
        });
    }
}
