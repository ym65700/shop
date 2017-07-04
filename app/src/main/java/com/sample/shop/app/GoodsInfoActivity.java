package com.sample.shop.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sample.shop.R;
import com.sample.shop.home.adapter.HomeRecyclerAdapter;
import com.sample.shop.home.bean.GoodsBean;
import com.sample.shop.shoppingcart.activity.CartActivity;
import com.sample.shop.shoppingcart.utils.CartStorage;
import com.sample.shop.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.onClick;

public class GoodsInfoActivity extends Activity implements View.OnClickListener {
    private ImageButton ibBackGoods;
    private ImageButton ibMoreGoods;
    private ImageView imgGoodsInfo;
    private TextView tvGoodsInfoName;
    private TextView tvGoodsInfoDesc;
    private TextView tvGoodsInfoPrice;
    private TextView tvGoodsInfoStore;
    private TextView tvGoodsInfoStyle;
    private WebView wvGoods;
    private TextView tvCallcenter;
    private TextView tvCollectionGoods;
    private TextView tvCartGoods;
    private Button btnAddcartGoods;

    //接收道德数据
    private GoodsBean goodsBean;

    private void initView() {
        ibBackGoods = (ImageButton) findViewById(R.id.ib_back_goods);
        ibMoreGoods = (ImageButton) findViewById(R.id.ib_more_goods);
        imgGoodsInfo = (ImageView) findViewById(R.id.img_goods_info);
        tvGoodsInfoName = (TextView) findViewById(R.id.tv_goods_info_name);
        tvGoodsInfoDesc = (TextView) findViewById(R.id.tv_goods_info_desc);
        tvGoodsInfoPrice = (TextView) findViewById(R.id.tv_goods_info_price);
        tvGoodsInfoStore = (TextView) findViewById(R.id.tv_goods_info_store);
        tvGoodsInfoStyle = (TextView) findViewById(R.id.tv_goods_info_style);
        wvGoods = (WebView) findViewById(R.id.wv_goods);
        tvCallcenter = (TextView) findViewById(R.id.tv_callcenter_goods);
        tvCollectionGoods = (TextView) findViewById(R.id.tv_collection_goods);
        tvCartGoods = (TextView) findViewById(R.id.tv_cart_goods);
        btnAddcartGoods = (Button) findViewById(R.id.btn_addcart_goods);
        //客服中心
        tvCallcenter.setOnClickListener(this);
        //收藏
        tvCollectionGoods.setOnClickListener(this);
        //购物车
        tvCartGoods.setOnClickListener(this);
        //更多
        ibMoreGoods.setOnClickListener(this);
        //添加到购物车
        btnAddcartGoods.setOnClickListener(this);
        //返回
        ibBackGoods.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        initView();

        // 接收道德数据
        goodsBean = (GoodsBean) getIntent().getSerializableExtra("goodsBean");
        if (goodsBean != null) {
            setDataForView(goodsBean);
        }
    }

    public void onClick(View v) {

        if (v == ibMoreGoods) {
            Toast.makeText(this, "更多", Toast.LENGTH_SHORT).show();

        } else if (v == ibBackGoods) {
            finish();
        } else if (v == tvCollectionGoods) {
            Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
        } else if (v == tvCartGoods) {
            Toast.makeText(this, "购物车", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, CartActivity.class);
//            startActivity(intent);
        } else if (v == btnAddcartGoods) {
            Toast.makeText(this, "添加到购物车", Toast.LENGTH_SHORT).show();
            CartStorage.getInstance().addData(goodsBean);

        }
    }

    private void setDataForView(GoodsBean goodsBean) {
        //设置图片
        Glide.with(this).load(Constants.BASE_IMAGE_URL + goodsBean.getFigure()).into(imgGoodsInfo);
//        //设置文本
        tvGoodsInfoName.setText(goodsBean.getName());
//        //设置价格
        tvGoodsInfoPrice.setText("￥" + goodsBean.getCover_price());
        setWebViewData(goodsBean.getProduct_id());
    }

    private void setWebViewData(String product_id) {
        if (product_id != null) {
            wvGoods.loadUrl("http://www.baidu.com");
            //设置支持JavaScript
            WebSettings webSettings = wvGoods.getSettings();
            webSettings.setUseWideViewPort(true);//支持双击页面变大变小
            webSettings.setJavaScriptEnabled(true);//设置支持JavaScript
            //优先使用缓存
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            wvGoods.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }

            });
        }
    }


}
