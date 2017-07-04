package com.sample.shop.shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sample.shop.R;
import com.sample.shop.home.bean.GoodsBean;
import com.sample.shop.shoppingcart.utils.CartStorage;
import com.sample.shop.shoppingcart.view.NumberAddSubView;
import com.sample.shop.utils.CacheUtils;
import com.sample.shop.utils.Constants;

import java.util.List;

/**
 * 作者：Administrator on 2017/7/2 22:18
 * 作用：
 */
public class CartFragmentRecyclerAdapter extends RecyclerView.Adapter<CartFragmentRecyclerAdapter.ViewHolder> {

    private TextView tvShopcartotal;
    //完成状态
    private CheckBox cbAll;
    //编辑状态
    private CheckBox checkboxAll;
    private Context context;
    private List<GoodsBean> goodsBeans;


    public CartFragmentRecyclerAdapter(Context context, List<GoodsBean> goodsBeans, TextView tvShopcartotal, CheckBox checkboxAll, CheckBox cbAll) {
        this.context = context;
        this.goodsBeans = goodsBeans;
        this.tvShopcartotal = tvShopcartotal;
        this.checkboxAll = checkboxAll;
        this.cbAll = cbAll;
        showTotalPrice();
        checkboxAll.setChecked(true);
        for (int i = 0; i < goodsBeans.size(); i++) {
            goodsBeans.get(i).setSelected(true);
        }
        //点击事件监听
        listener();
        //校验是否全选
        checkAll();
    }

    private void listener() {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //获取位置
                GoodsBean goodsBean = goodsBeans.get(position);
                //设置反转状态
                goodsBean.setSelected(!goodsBean.isSelected());
                //刷新状态
                notifyItemChanged(position);
                //校验是否全选
                checkAll();
                //重新计算价格
                showTotalPrice();
            }
        });
        //checkboxAll点击事件
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到状态
                boolean isCheck = checkboxAll.isChecked();
                //2.根据状态设置全选和非全选
                checkAll_none(isCheck);
                //3.计算总价格
                showTotalPrice();
            }
        });
        //checkAll点击事件
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到状态
                boolean isCheck = cbAll.isChecked();
                //2.根据状态设置全选和非全选
                checkAll_none(isCheck);

            }
        });
    }

    public void checkAll_none(boolean isCheck) {
        if (goodsBeans != null && goodsBeans.size() > 0) {
            for (int i = 0; i < goodsBeans.size(); i++) {
                GoodsBean goodsBean = goodsBeans.get(i);
                goodsBean.setSelected(isCheck);
                notifyItemChanged(i);
            }
        }
    }

    //显示合计
    public void showTotalPrice() {
        tvShopcartotal.setText("合计:" + getTotalPrice());
    }

    //计算总价格
    private double getTotalPrice() {
        double totalPrice = 0.0;
        if (goodsBeans != null && goodsBeans.size() > 0) {
            for (int i = 0; i < goodsBeans.size(); i++) {
                GoodsBean goodsBean = goodsBeans.get(i);
                if (goodsBean.isSelected()) {
                    totalPrice = totalPrice + Double.valueOf(goodsBean.getNumber()) * Double.valueOf(goodsBean.getCover_price());
                }
            }
        }
        return totalPrice;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //实例化布局
        View view = View.inflate(context, R.layout.shoppingcart_item_recycler, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //根据位置获取bean对象
        final GoodsBean goodsBean = goodsBeans.get(position);

        holder.cb_gov.setChecked(goodsBean.isSelected());
        holder.tv_desc_gov.setText(goodsBean.getName());
        holder.tv_price_gov.setText("$" + goodsBean.getCover_price());
        Glide.with(context).load(Constants.BASE_IMAGE_URL + goodsBean.getFigure()).into(holder.iv_gov);
        holder.numberAddSubView.saveValue(goodsBean.getNumber());
        holder.numberAddSubView.setMaxValue(10);
        holder.numberAddSubView.setMinValue(1);
        //设置商品数量变化监听
        holder.numberAddSubView.setOnNumberChangeListener(new NumberAddSubView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int value) {
                //1.当前列表内存中
                goodsBean.setNumber(value);
                //2本地要更新
                CartStorage.getInstance().UpdateData(goodsBean);
                //3.刷新适配器
                notifyItemChanged(position);
                //4.再次计算总价格
                showTotalPrice();
            }
        });
    }

    public void checkAll() {
        if (goodsBeans != null && goodsBeans.size() > 0) {
            int number = 0;
            for (int i = 0; i < goodsBeans.size(); i++) {
                GoodsBean goodsBean = goodsBeans.get(i);
                if (!goodsBean.isSelected()) {
                    //非全选
                    checkboxAll.setChecked(false);
                    cbAll.setChecked(false);
                } else {
                    number++;
                }

            }
            if (number == goodsBeans.size()) {
                //全选
                checkboxAll.setChecked(true);
                cbAll.setChecked(true);
            } else {
                checkboxAll.setChecked(false);
                cbAll.setChecked(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return goodsBeans.size();
    }

    public void deleteData() {
        if (goodsBeans != null && goodsBeans.size() > 0) {
            for (int i = 0; i < goodsBeans.size(); i++) {
                GoodsBean goodsBean = goodsBeans.get(i);
                if (goodsBean.isSelected()) {
                    //内存移除
                    goodsBeans.remove(goodsBean);
                    //本地移除
                    CartStorage.getInstance().deleteData(goodsBean);
                    //刷新
                    notifyItemRemoved(i);
                    i--;
                }
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cb_gov;
        private ImageView iv_gov;
        private TextView tv_desc_gov;
        private TextView tv_price_gov;
        private NumberAddSubView numberAddSubView;

        public ViewHolder(View itemView) {
            super(itemView);
            cb_gov = (CheckBox) itemView.findViewById(R.id.cb_gov);
            iv_gov = (ImageView) itemView.findViewById(R.id.iv_gov);
            tv_desc_gov = (TextView) itemView.findViewById(R.id.tv_desc_gov);
            tv_price_gov = (TextView) itemView.findViewById(R.id.tv_price_gov);
            numberAddSubView = (NumberAddSubView) itemView.findViewById(R.id.numberAddSubView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(getLayoutPosition());
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        public void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
