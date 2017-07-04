package com.sample.shop.shoppingcart.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sample.shop.R;

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
        int value = getValue();
        saveValue(value);

        //设置点击事件
        iv_sub.setOnClickListener(this);
        iv_add.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //jia
            case R.id.iv_add:
                addNumber();
                break;
            //jian
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
        if (onNumberChangeListener != null) {
            onNumberChangeListener.onNumberChange(value);

        }

    }


    private void subNumber() {
        if (value > minValue) {
            value--;
        }
        saveValue(value);
        if (onNumberChangeListener != null) {
            onNumberChangeListener.onNumberChange(value);

        }
    }

    public void saveValue(int value) {
        this.value = value;
        tv_value.setText(value + "");
    }

    public int getValue() {
        String valueStr = tv_value.getText().toString().trim();
        if (!TextUtils.isEmpty(valueStr)) {
            value = Integer.parseInt(valueStr);

        }
        return value;

    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * 当数量发生变化的时候回调
     */
    public interface OnNumberChangeListener {
        /**
         * 当数据发生变化的时候回调
         *
         * @param value
         */
        public void onNumberChange(int value);
    }

    private OnNumberChangeListener onNumberChangeListener;

    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }
}
