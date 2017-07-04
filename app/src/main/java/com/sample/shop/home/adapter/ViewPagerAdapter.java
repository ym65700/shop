package com.sample.shop.home.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sample.shop.home.bean.HomeBean;
import com.sample.shop.utils.Constants;

import java.util.List;

/**
 * 作者：Administrator on 2017/6/26 17:56
 * 作用：
 */
public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<HomeBean.ResultBean.ActInfoBean> actInfo;

    public ViewPagerAdapter(Context context, List<HomeBean.ResultBean.ActInfoBean> actInfo) {
        this.context = context;
        this.actInfo = actInfo;
    }

    @Override
    public int getCount() {
        return actInfo.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final ImageView imageView = new ImageView(context);
        //iamgeview 填充比例
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Log.e("viewpage", Constants.BASE_IMAGE_URL + actInfo.get(position).getIcon_url());
        Glide.with(context).load(Constants.BASE_IMAGE_URL + actInfo.get(position).getIcon_url()).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "position=="+position, Toast.LENGTH_SHORT).show();
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
