package com.sample.shop.home.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
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
 * 作者：Administrator on 2017/6/26 15:49
 * 作用：
 */
public class GridAdapter extends BaseAdapter {
    private Context context;
    private List<HomeBean.ResultBean.ChannelInfoBean> channelInfo;

    public GridAdapter(Context context, List<HomeBean.ResultBean.ChannelInfoBean> channelInfo) {
        this.context = context;
        this.channelInfo = channelInfo;
    }

    @Override
    public int getCount() {
        return channelInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return channelInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.home_item_gridview, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.holder_tv.setText(channelInfo.get(position).getChannel_name());
        Glide.with(context).load(Constants.BASE_IMAGE_URL + channelInfo.get(position).getImage()).into(viewHolder.holder_iv);

        return convertView;
    }

    class ViewHolder {
        private TextView holder_tv;
        private ImageView holder_iv;


        public ViewHolder(View convertView) {
            holder_iv = (ImageView) convertView.findViewById(R.id.iv_gv);
            holder_tv = (TextView) convertView.findViewById(R.id.tv_gv);
        }
    }
}


