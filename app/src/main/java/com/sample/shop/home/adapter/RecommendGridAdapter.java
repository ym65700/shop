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
 * 作者：Administrator on 2017/6/27 14:37
 * 作用：
 */
public class RecommendGridAdapter extends BaseAdapter {
    private Context context;
    private List<HomeBean.ResultBean.RecommendInfoBean> recommendInfo;

    public RecommendGridAdapter(Context context, List<HomeBean.ResultBean.RecommendInfoBean> recommendInfo) {
        this.context = context;
        this.recommendInfo = recommendInfo;
    }

    @Override
    public int getCount() {
        return recommendInfo.size();
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
            convertView = View.inflate(context, R.layout.item_recommend, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_recommend = (ImageView) convertView.findViewById(R.id.iv_recommend);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //根据位置得到对应的数据
        HomeBean.ResultBean.RecommendInfoBean recommendInfoBean = recommendInfo.get(position);
        Glide.with(context).load(Constants.BASE_IMAGE_URL + recommendInfoBean.getFigure()).into(viewHolder.iv_recommend);
        viewHolder.tv_name.setText(recommendInfoBean.getName());
        viewHolder.tv_price.setText("￥" + recommendInfoBean.getCover_price());
        return convertView;
    }
    static class ViewHolder {
        private TextView tv_name;
        private TextView tv_price;
        private ImageView iv_recommend;

    }
}
