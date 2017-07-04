package com.sample.shop.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sample.shop.R;
import com.sample.shop.home.bean.HomeBean;
import com.sample.shop.utils.Constants;

import java.util.List;

/**
 * 作者：Administrator on 2017/6/26 23:04
 * 作用：
 */
public class SeckillRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<HomeBean.ResultBean.SeckillInfoBean.ListBean> listBean;

    public SeckillRecyclerViewAdapter(Context context, List<HomeBean.ResultBean.SeckillInfoBean.ListBean> listBean) {
        this.context = context;
        this.listBean = listBean;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_seckill, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(position);

    }

    @Override
    public int getItemCount() {
        return listBean.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_item_seckill;
        private TextView tv_cover_seckill;
        private TextView tv_origin_seckill;
        private LinearLayout ll_root;


        public ViewHolder(View itemView) {
            super(itemView);
            img_item_seckill = (ImageView) itemView.findViewById(R.id.img_item_seckill);
            tv_cover_seckill = (TextView) itemView.findViewById(R.id.tv_cover_seckill);
            tv_origin_seckill = (TextView) itemView.findViewById(R.id.tv_origin_seckill);
            ll_root = (LinearLayout) itemView.findViewById(R.id.ll_root);
        }

        public void setData(final int position) {
            tv_cover_seckill.setText("$" + listBean.get(position).getCover_price());
            tv_origin_seckill.setText("$" + listBean.get(position).getOrigin_price());
            Glide.with(context).load(Constants.BASE_IMAGE_URL + listBean.get(position).getFigure()).into(img_item_seckill);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSeckillReclclerView != null) {
                        onSeckillReclclerView.onItemClick(position);
                    }
                }
            });
        }
    }

    /**
     * 监听器
     */
    public interface OnSeckillReclclerView {
        /**
         * item被点击回调
         *
         * @param position
         */
        public void onItemClick(int position);
    }

    private OnSeckillReclclerView onSeckillReclclerView;

    public void setOnSeckillReclclerView(OnSeckillReclclerView onSeckillReclclerView) {
        this.onSeckillReclclerView = onSeckillReclclerView;
    }
}
