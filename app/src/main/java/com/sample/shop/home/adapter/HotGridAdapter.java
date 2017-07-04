package com.sample.shop.home.adapter;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sample.shop.R;
import com.sample.shop.home.bean.HomeBean;
import com.sample.shop.utils.Constants;

import java.util.List;

/**
 * 作者：Administrator on 2017/6/27 15:44
 * 作用：
 */
public class HotGridAdapter extends BaseAdapter {
    private Context context;
    private List<HomeBean.ResultBean.HotInfoBean> hotInfo;

    public HotGridAdapter(Context context, List<HomeBean.ResultBean.HotInfoBean> hotInfo) {
        this.context = context;
        this.hotInfo = hotInfo;
    }

    @Override
    public int getCount() {
        return hotInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.home_hot_gride, null);
            viewHolder = new ViewHolder();
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv_hot);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name_hot);
            viewHolder.price = (TextView) convertView.findViewById(R.id.tv_price_hot);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HomeBean.ResultBean.HotInfoBean hotInfoBean= hotInfo.get(position);
        viewHolder.name.setText(hotInfoBean.getName());
        viewHolder.price.setText(hotInfoBean.getCover_price());
        Glide.with(context).load(Constants.BASE_IMAGE_URL+hotInfoBean.getFigure()).into(viewHolder.iv);
        return convertView;
    }

    static class ViewHolder {
        private ImageView iv;
        private TextView name;
        private TextView price;

    }
}
