package com.sample.numberaddsubview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 作者：Administrator on 2017/7/2 22:33
 * 作用：
 */

public class NumberAddSubView extends LinearLayout implements View.OnClickListener {
    private Context context;
    private ImageView iv_sub;
    private ImageView iv_add;
    private TextView tv_value;
    private int value = 1;
    private int maxValue = 5;
    private int minValue = 1;


    public NumberAddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //把布局文件实例化，并且加载到AddSubView类中
        View.inflate(context, R.layout.add_sub_view, this);
        iv_sub = (ImageView) findViewById(R.id.iv_sub);
        tv_value = (TextView) findViewById(R.id.tv_value);
        iv_add = (ImageView) findViewById(R.id.iv_add);

        //设置点击事件
        iv_sub.setOnClickListener(this);
        iv_add.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:
                addNumber();
                break;
            case R.id.iv_sub:
                subNumber();
                break;
        }
    }

    private void addNumber() {
        if (value < maxValue) {
            value++;
        }
        saveValue(value);
    }


    private void subNumber() {
        if (value > minValue) {
            value--;
        }
        saveValue(value);
    }

    private void saveValue(int value) {
        tv_value.setText(value + "");
    }

}
