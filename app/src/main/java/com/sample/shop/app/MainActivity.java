package com.sample.shop.app;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.sample.shop.R;
import com.sample.shop.base.BaseFragment;
import com.sample.shop.shoppingcart.fragment.CartFragment;
import com.sample.shop.comunity.ComunityFragment;
import com.sample.shop.home.fragment.HomeFragment;
import com.sample.shop.type.TypeFragment;
import com.sample.shop.user.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Administrator on 2017/6/19 01:39
 * 作用：主页面
 */

public class MainActivity extends FragmentActivity {
    private RadioGroup rg_main;
    private List<BaseFragment> mBaseFragments;
    private int position;
    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化View
        initView();
        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        setListener();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        rg_main = (RadioGroup) findViewById(R.id.rg_main);
    }

    private void initFragment() {
        mBaseFragments = new ArrayList<>();
        mBaseFragments.add(new HomeFragment());
        mBaseFragments.add(new TypeFragment());
        mBaseFragments.add(new ComunityFragment());
        mBaseFragments.add(new CartFragment());
        mBaseFragments.add(new UserFragment());

    }

    private void setListener() {
        ////设置RadioGroup的监听
        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //设置默认选中第一个
        rg_main.check(R.id.rb_home);
    }

    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_home:
                    position = 0;
                    break;
                case R.id.rb_type:
                    position = 1;
                    break;
                case R.id.rb_community:
                    position = 2;
                    break;
                case R.id.rb_cart:
                    position = 3;
                    break;
                case R.id.rb_user:
                    position = 4;
                    break;
                default:
                    position = 0;
                    break;
            }
            // //根据位置得到对应的Fragment
            BaseFragment to = getFragment();
            //替换
            switchFragment(tempFragment, to);

        }
    }

    /**
     * @param from 刚显示的Fragment,马上就要被隐藏了
     * @param to   马上要切换到的Fragment，一会要显示
     */
    private void switchFragment(Fragment from, Fragment to) {
        if (from != to) {
            tempFragment = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //判断有没有被添加
            if (!to.isAdded()) {
                //to没有被添加
                //from隐藏
                if (from != null) {
                    ft.hide(from);
                }
                if (to != null) {
                    //添加to
                    ft.add(R.id.fl_content, to).commit();
                }
            } else {
                //to已经被添加
                // from隐藏
                if (from != null) {
                    ft.hide(from);
                }
                if (to != null) {
                    //显示to
                    ft.show(to).commit();
                }
            }
        }

    }

    /**
     * 根据位置得到对应的Fragment
     *
     * @return
     */
    private BaseFragment getFragment() {
        if (mBaseFragments != null && mBaseFragments.size() > 0) {
            BaseFragment baseFragment = mBaseFragments.get(position);
            return baseFragment;
        }
        return null;

    }
}
