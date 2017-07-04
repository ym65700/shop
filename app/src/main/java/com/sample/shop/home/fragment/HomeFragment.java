package com.sample.shop.home.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.sample.shop.R;
import com.sample.shop.base.BaseFragment;
import com.sample.shop.home.adapter.HomeRecyclerAdapter;
import com.sample.shop.home.bean.HomeBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

import static com.sample.shop.utils.Constants.HOME_URL;

/**
 * 作者：Administrator on 2017/6/19 13:57
 * 作用：首页页面
 */

public class HomeFragment extends BaseFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();

    private TextView tvSearchHome;
    private TextView tvMessageHome;
    private ImageButton ib_top;
    private RecyclerView rvHome;


    @Override
    protected View initView() {
        Log.e(TAG, "首页页面被初始化了...");
        View view = View.inflate(context, R.layout.fragment_home, null);
        tvSearchHome = (TextView) view.findViewById(R.id.tv_search_home);
        tvMessageHome = (TextView) view.findViewById(R.id.tv_message_home);
        ib_top = (ImageButton) view.findViewById(R.id.ib_top);
        rvHome = (RecyclerView) view.findViewById(R.id.rv_home);
        //初始化监听
        initListener();
        return view;
    }

    private void initListener() {
        tvSearchHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "搜索", Toast.LENGTH_SHORT).show();
            }
        });
        tvMessageHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "进入消息中心", Toast.LENGTH_SHORT).show();
            }
        });
        ib_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        Log.e(TAG, "首页Fragment数据被初始化了...");
        //网络请求数据
        getDataFromNet();
    }

    /**
     * OkHttpUtils联网请求
     */

    private void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(HOME_URL)
                .id(100)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "home网络请求失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        switch (id) {
                            //100 http
                            case 100:
                                Log.e(TAG, "home网络请求成功");
                                if (response != null) {
                                    //fastJson解析数据
                                    HomeBean homeBean = JSON.parseObject(response, HomeBean.class);
                                    HomeBean.ResultBean resultBean = homeBean.getResult();
                                    //创建recycleradapter适配器
                                    HomeRecyclerAdapter homeRecyclerAdapter = new HomeRecyclerAdapter(context, resultBean);
                                    //设置适配器
                                    rvHome.setAdapter(homeRecyclerAdapter);
                                    GridLayoutManager manager = new GridLayoutManager(context, 1);
                                    //设置滑动到那个位置监听
                                    manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                                        @Override
                                        public int getSpanSize(int position) {
                                            if (position <= 3) {
                                                ib_top.setVisibility(View.GONE);
                                            } else {
                                                ib_top.setVisibility(View.VISIBLE);
                                            }
                                            //只能返回1
                                            return 1;
                                        }
                                    });
                                    //设置网格布局
                                    rvHome.setLayoutManager(manager);
                                    ib_top.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            rvHome.scrollToPosition(0);
                                        }
                                    });

                                }

                                break;
                            //101 https
                            case 101:
                                Toast.makeText(context, "https", Toast.LENGTH_SHORT).show();
                                break;
                        }

                    }

                });
    }


}
