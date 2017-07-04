package com.sample.shop.type;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sample.shop.base.BaseFragment;

/**
 * 作者：Administrator on 2017/6/19 13:57
 * 作用：
 */

public class TypeFragment extends BaseFragment {
    private static final String TAG = TypeFragment.class.getSimpleName();
    private TextView textView;

    @Override
    protected View initView() {
        Log.e(TAG, "twoFragment页面被初始化了...");
        textView = new TextView(context);
        return textView;
    }

    @Override
    protected void initData() {
        super.initData();
        Log.e(TAG, "twoFragment数据被初始化了...");
        textView.setText("two");

    }
}
