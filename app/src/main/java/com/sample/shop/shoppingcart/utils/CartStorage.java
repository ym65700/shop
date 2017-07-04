package com.sample.shop.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sample.shop.app.MyApplication;
import com.sample.shop.home.bean.GoodsBean;
import com.sample.shop.utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Administrator on 2017/6/28 13:28
 * 作用：
 */

public class CartStorage {
    private static final String JSON_CART = "json_cart";
    private static CartStorage instance;
    private Context context;
    private SparseArray<GoodsBean> sparseArray;

    //得到购物车实例
    public static CartStorage getInstance() {
        if (instance == null) {
            instance = new CartStorage(MyApplication.getContext());
        }
        return instance;
    }

    public CartStorage(Context context) {
        this.context = context;
        //把之前存储的数据读取出来
        sparseArray = new SparseArray<>(100);
        listToSparseArray();
    }

    //从本地读取的数据放入SparseArray
    private void listToSparseArray() {
        List<GoodsBean> carts = getAllData();
        //放入SparseArray
        if (carts != null && carts.size() > 0) {
            for (int i = 0; i < carts.size(); i++) {
                GoodsBean goodsBean = carts.get(i);
                sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
            }
        }

    }

    public List<GoodsBean> getAllData() {
        return getDataFromLocale();
    }

    //本地获取数据，并且通过 Gson 解析成 list 列表数据
    private List<GoodsBean> getDataFromLocale() {
        List<GoodsBean> carts = new ArrayList<>();
        //从本地获取缓存数据
        String saveJson = CacheUtils.getString(context, JSON_CART);
        if (!TextUtils.isEmpty(saveJson)) {
            //把数据转换成列表
            carts = new Gson().fromJson(saveJson, new TypeToken<List<GoodsBean>>() {
            }.getType());
        }
        return carts;
    }


    //1.SparseArray转换成List
    private List<GoodsBean> parsesToList() {
        List<GoodsBean> carts = new ArrayList<>();
        if (sparseArray != null && sparseArray.size() > 0) {
            for (int i = 0; i < sparseArray.size(); i++) {
                GoodsBean shopCart = sparseArray.valueAt(i);
                carts.add(shopCart);
            }
        }
        return carts;
    }
    //1.添加到内存中SparseArray
    //如果当前数据已经存在，就修改成number递增
    public void addData(GoodsBean goodBean) {
        GoodsBean tempData = sparseArray.get(Integer.parseInt(goodBean.getProduct_id()));
        if (tempData != null) {
            tempData.setNumber(tempData.getNumber() + 1);
        } else {
            tempData = goodBean;
            tempData.setNumber(1);
        }
        //同步到内存中
        sparseArray.put(Integer.parseInt(tempData.getProduct_id()), tempData);
        //2.同步到本地
        saveLocal();
    }
    public void deleteData(GoodsBean goodBean) {
        //内存中删除
        sparseArray.delete(Integer.parseInt(goodBean.getProduct_id()));
        //把内存存储到本地
        saveLocal();

    }
    public void UpdateData(GoodsBean goodBean) {
        //内存更新
        sparseArray.put(Integer.parseInt(goodBean.getProduct_id()),goodBean);
        //把内存存储到本地
        saveLocal();
    }



    /**
     * 保持数据到本地
     */
    private void saveLocal() {
        //1.SparseArray转换成List
        List<GoodsBean> goodsBeanList = parsesToList();
        //2.使用Gson把列表转换成String类型
        String json = new Gson().toJson(goodsBeanList);
        //3.把String数据保存
        CacheUtils.putString(context, JSON_CART, json);
    }



}
