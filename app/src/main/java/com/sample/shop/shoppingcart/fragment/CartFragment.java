package com.sample.shop.shoppingcart.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sample.shop.R;
import com.sample.shop.base.BaseFragment;
import com.sample.shop.home.bean.GoodsBean;
import com.sample.shop.shoppingcart.adapter.CartFragmentRecyclerAdapter;
import com.sample.shop.shoppingcart.utils.CartStorage;

import java.util.List;

/**
 * 作者：Administrator on 2017/6/19 13:57
 * 作用：
 */

public class CartFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = CartFragment.class.getSimpleName();
    private TextView tvShopcartEdit;
    private RecyclerView recyclerview;
    private LinearLayout llCheckAll;
    private CheckBox checkboxAll;
    private Button btnCheckOut;
    private LinearLayout llDelete;
    private CheckBox cbAll;
    private Button btnDelete;
    private Button btnCollection;
    private TextView tvShopcartotal;
    private CartFragmentRecyclerAdapter adapter;

    //编辑状态
    private static final int ACTION_EDIT = 1;
    //完成状态
    private static final int ACTION_COMPLETE = 2;
    private LinearLayout ll_empty_shopcart;
    private ImageView ivEmpty;
    private TextView tvEmptyCartTobuy;


    @Override
    protected View initView() {
        Log.e(TAG, "fourFragment页面被初始化了...");
        View view = View.inflate(context, R.layout.fragment_cart, null);
        findViews(view);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        initListener();
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");
        llCheckAll.setVisibility(View.VISIBLE);
        showData();

    }

    @Override
    public void onResume() {
        super.onResume();
        showData();
    }

    private void findViews(View view) {
        tvShopcartEdit = (TextView) view.findViewById(R.id.tv_shopcart_edit);
        tvShopcartotal = (TextView) view.findViewById(R.id.tv_shopcart_total);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        checkboxAll = (CheckBox) view.findViewById(R.id.checkbox_all);
        btnCheckOut = (Button) view.findViewById(R.id.btn_check_out);
        llCheckAll = (LinearLayout) view.findViewById(R.id.ll_check_all);
        llDelete = (LinearLayout) view.findViewById(R.id.ll_delete);
        cbAll = (CheckBox) view.findViewById(R.id.cb_all);
        btnDelete = (Button) view.findViewById(R.id.btn_delete);
        btnCollection = (Button) view.findViewById(R.id.btn_collection);

        ll_empty_shopcart = (LinearLayout) view.findViewById(R.id.ll_empty_shopcart);
        ivEmpty = (ImageView) view.findViewById(R.id.iv_empty);
        tvEmptyCartTobuy = (TextView) view.findViewById(R.id.tv_empty_cart_tobuy);

        btnCheckOut.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnCollection.setOnClickListener(this);


    }

    private void initListener() {
        //设置默认的编辑状态
        //tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int action = (int) tvShopcartEdit.getTag();
                if (action == ACTION_EDIT) {
                    //切换为完成状态
                    showDelete();
                } else {
                    //切换成编辑状态
                    hideDelete();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.deleteData();
                adapter.showTotalPrice();
                //判断是否显示空界面
                checkData();
                adapter.checkAll();
            }
        });


    }

    private void hideDelete() {
        //1.设置状态和文本-编辑
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");
        //2.变成非勾选
        if (adapter != null) {
            adapter.checkAll_none(true);
            // adapter.checkAll();

        }
        //3.结算视图显示
        llCheckAll.setVisibility(View.VISIBLE);
        //4删除视图隐藏
        llDelete.setVisibility(View.GONE);
        adapter.showTotalPrice();
    }

    private void showDelete() {
        //1.设置状态和文本-完成 删除界面
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        tvShopcartEdit.setText("完成");
        if (adapter != null) {
            adapter.checkAll_none(false);
            // adapter.checkAll();
            cbAll.setChecked(false);
            checkboxAll.setChecked(false);


        }
        //结算视图隐藏
        llCheckAll.setVisibility(View.GONE);
        //4删除视图显示
        llDelete.setVisibility(View.VISIBLE);
        adapter.showTotalPrice();
    }


    private void showData() {
        List<GoodsBean> goodsBeans = CartStorage.getInstance().getAllData();
        if (goodsBeans != null && goodsBeans.size() > 0) {
            tvShopcartEdit.setVisibility(View.VISIBLE);

            //llCheckAll.setVisibility(View.VISIBLE);
            //有数据
            //把当没有数据显示的布局-隐藏
            ll_empty_shopcart.setVisibility(View.GONE);
            //设置适配器
            adapter = new CartFragmentRecyclerAdapter(context, goodsBeans, tvShopcartotal, checkboxAll, cbAll);
            recyclerview.setAdapter(adapter);
            //设置布局管理器
            recyclerview.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        } else {
            //没有数据
            //显示数据为空的布局
            emptyShoppingCart();
        }

    }

    private void emptyShoppingCart() {
        ll_empty_shopcart.setVisibility(View.VISIBLE);
        tvShopcartEdit.setVisibility(View.GONE);
        //llDelete.setVisibility(View.GONE);

    }

    //点击事件
    public void onClick(View v) {
        if (v == btnCheckOut) {
            // Handle clicks for btnCheckOut
        } else if (v == btnCollection) {
            // Handle clicks for btnCollection
        }
    }


    private void checkData() {
        if (adapter != null && adapter.getItemCount() > 0) {
            tvShopcartEdit.setVisibility(View.VISIBLE);
            ll_empty_shopcart.setVisibility(View.GONE);

        } else {
            ll_empty_shopcart.setVisibility(View.VISIBLE);
            tvShopcartEdit.setVisibility(View.GONE);

        }

    }
}
