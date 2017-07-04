package com.sample.shop.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sample.shop.R;
import com.sample.shop.app.GoodsInfoActivity;
import com.sample.shop.home.bean.GoodsBean;
import com.sample.shop.home.bean.HomeBean;
import com.sample.shop.home.loader.GlideImageLoader;
import com.sample.shop.utils.Constants;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.magicviewpager.transformer.RotateDownPageTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作者：Administrator on 2017/6/22 23:01
 * 作用：HomeRecyclerAdapter 主页面适配器
 */
public class HomeRecyclerAdapter extends RecyclerView.Adapter {
    /**
     * 五中适配类型
     */
    //横幅广告
    private static final int BANNER = 0;
    //引导
    private static final int CHANNEL = 1;
    //活动
    private static final int ACTION = 2;
    //秒杀
    private static final int SECKILL = 3;
    //推荐
    private static final int RECOMMEND = 4;
    //推荐
    private static final int HOT = 5;
    /**
     * 初始化布局
     */
    private LayoutInflater mLayoutInflater;
    //当前类型
    public int currentType = BANNER;

    private Context context;
    private HomeBean.ResultBean resultBean;
    private static final String GOODS_BEAN = "goodsBean";

    public HomeRecyclerAdapter(Context context, HomeBean.ResultBean resultBean) {
        this.context = context;
        this.resultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACTION:
                currentType = ACTION;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            View view = mLayoutInflater.inflate(R.layout.home_banner, null);
            BannerViewHolder bannerViewHolder = new BannerViewHolder(context, view);
            return bannerViewHolder;
        } else if (viewType == CHANNEL) {
            View view = mLayoutInflater.inflate(R.layout.home_channel, null);
            ChannelViewHolder channelViewHolder = new ChannelViewHolder(context, view);
            return channelViewHolder;
        } else if (viewType == ACTION) {
            View view = mLayoutInflater.inflate(R.layout.home_action, null);
            ActionViewHolder actionViewHolder = new ActionViewHolder(context, view);
            return actionViewHolder;
        } else if (viewType == SECKILL) {
            View view = mLayoutInflater.inflate(R.layout.home_seckill, null);
            SeckillViewHolder seckillViewHolder = new SeckillViewHolder(context, view);
            return seckillViewHolder;
        } else if (viewType == RECOMMEND) {
            View view = mLayoutInflater.inflate(R.layout.home_recommend, null);
            RecommendViewholder recommendViewholder = new RecommendViewholder(context, view);
            return recommendViewholder;
        } else if (viewType == HOT) {
            View view = mLayoutInflater.inflate(R.layout.home_hot, null);
            HotViewholder hotViewholder = new HotViewholder(context, view);
            return hotViewholder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(resultBean.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info());
        } else if (getItemViewType(position) == ACTION) {
            ActionViewHolder actionViewHolder = (ActionViewHolder) holder;
            actionViewHolder.setData(resultBean.getAct_info());
        } else if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(resultBean.getSeckill_info());
        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewholder recommendViewholder = (RecommendViewholder) holder;
            recommendViewholder.setData(resultBean.getRecommend_info());
        } else if (getItemViewType(position) == HOT) {
            HotViewholder hotViewholder = (HotViewholder) holder;
            hotViewholder.setData(resultBean.getHot_info());
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    /**
     * 启动商品信息列表页面
     *
     * @param goodsBean
     */
    private void startGoodsInfoActivity(GoodsBean goodsBean) {
        Intent intent = new Intent(context, GoodsInfoActivity.class);
        intent.putExtra(GOODS_BEAN, goodsBean);
        context.startActivity(intent);
    }

    class HotViewholder extends RecyclerView.ViewHolder {
        private Context context;
        private TextView tv_more_hot;
        private GridView gv_hot;

        public HotViewholder(Context context, View view) {
            super(view);
            this.context = context;
            tv_more_hot = (TextView) view.findViewById(R.id.tv_more_hot);
            gv_hot = (GridView) view.findViewById(R.id.gv_hot);

        }

        public void setData(final List<HomeBean.ResultBean.HotInfoBean> hotInfo) {
            HotGridAdapter hotGridAdapter = new HotGridAdapter(context, hotInfo);
            gv_hot.setAdapter(hotGridAdapter);

            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(context, "position==" + position, Toast.LENGTH_SHORT).show();
                    HomeBean.ResultBean.HotInfoBean hotInfoBean = hotInfo.get(position);
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(hotInfoBean.getCover_price());
                    goodsBean.setFigure(hotInfoBean.getFigure());
                    goodsBean.setName(hotInfoBean.getName());
                    goodsBean.setProduct_id(hotInfoBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }


    class RecommendViewholder extends RecyclerView.ViewHolder {
        private Context context;
        private TextView tv_more_recommend;
        private GridView gv_recommend;

        public RecommendViewholder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            tv_more_recommend = (TextView) itemView.findViewById(R.id.tv_more_recommend);
            gv_recommend = (GridView) itemView.findViewById(R.id.gv_recommend);
        }

        public void setData(final List<HomeBean.ResultBean.RecommendInfoBean> recommendInfo) {
            RecommendGridAdapter recommendGridAdapter = new RecommendGridAdapter(context, recommendInfo);
            gv_recommend.setAdapter(recommendGridAdapter);
            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(context, "position==" + position, Toast.LENGTH_SHORT).show();
                    HomeBean.ResultBean.RecommendInfoBean recommendInfoBean = recommendInfo.get(position);
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(recommendInfoBean.getCover_price());
                    goodsBean.setFigure(recommendInfoBean.getFigure());
                    goodsBean.setName(recommendInfoBean.getName());
                    goodsBean.setProduct_id(recommendInfoBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });

        }
    }

    class SeckillViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private TextView tv_time_seckill;
        private TextView tv_more_seckill;
        private RecyclerView rl_seckill;
        /**
         * 相差多少时间-毫秒
         */
        private long dt = 0;
        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                dt = dt - 1000;
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                String time = format.format(new Date(dt));
                tv_time_seckill.setText(time);

                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, 1000);
                if (dt <= 0) {
                    //把消息移除
                    handler.removeCallbacksAndMessages(null);
                }


            }
        };

        public SeckillViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            tv_time_seckill = (TextView) itemView.findViewById(R.id.tv_time_seckill);
            tv_more_seckill = (TextView) itemView.findViewById(R.id.tv_more_seckill);
            rl_seckill = (RecyclerView) itemView.findViewById(R.id.rl_seckill);
        }

        public void setData(final HomeBean.ResultBean.SeckillInfoBean seckillInfo) {
            List<HomeBean.ResultBean.SeckillInfoBean.ListBean> listBean = seckillInfo.getList();
            SeckillRecyclerViewAdapter seckillRecyclerViewAdapter = new SeckillRecyclerViewAdapter(context, listBean);
            rl_seckill.setAdapter(seckillRecyclerViewAdapter);
            //设置布局管理
            rl_seckill.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            //设置点击事件
            seckillRecyclerViewAdapter.setOnSeckillReclclerView(new SeckillRecyclerViewAdapter.OnSeckillReclclerView() {
                @Override
                public void onItemClick(int position) {
                    HomeBean.ResultBean.SeckillInfoBean.ListBean seckillInfoBean = seckillInfo.getList().get(position);
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(seckillInfoBean.getCover_price());
                    goodsBean.setFigure(seckillInfoBean.getFigure());
                    goodsBean.setName(seckillInfoBean.getName());
                    goodsBean.setProduct_id(seckillInfoBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });
            ////秒杀倒计时 -毫秒
            dt = (int) ((Integer.parseInt(seckillInfo.getEnd_time())) - System.currentTimeMillis());
            handler.sendEmptyMessageDelayed(0, 1000);
        }
    }

    class ActionViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private ViewPager viewpager;

        public ActionViewHolder(Context context, View view) {
            super(view);
            this.context = context;
            viewpager = (ViewPager) view.findViewById(R.id.viewpager);

        }

        public void setData(List<HomeBean.ResultBean.ActInfoBean> actInfo) {
            viewpager.setPageMargin(20);//设置page间间距，自行根据需求设置
            viewpager.setOffscreenPageLimit(3);//>=3
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(context, actInfo);
            viewpager.setAdapter(viewPagerAdapter);
            //setPageTransformer 决定动画效果
            viewpager.setPageTransformer(true, new ScaleInTransformer());

            //iamgeview 点击事件


        }
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private GridView grid_view;

        public ChannelViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            grid_view = (GridView) itemView.findViewById(R.id.grid_view);
        }

        public void setData(final List<HomeBean.ResultBean.ChannelInfoBean> channelInfo) {
            GridAdapter gridAdapter = new GridAdapter(context, channelInfo);
            grid_view.setAdapter(gridAdapter);
            grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("gride", "onItemClick: " + position);
                    Toast.makeText(context, "position=="+channelInfo.get(position).getChannel_name(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        private Banner banner;
        private Context context;


        public BannerViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            banner = (Banner) itemView.findViewById(R.id.banner);


        }

        public void setData(final List<HomeBean.ResultBean.BannerInfoBean> banner_info) {
            List<String> imagesUrl = new ArrayList<String>();
            for (int i = 0; i < banner_info.size(); i++) {
                String image = banner_info.get(i).getImage();
                Log.e("home_iamge", Constants.BASE_IMAGE_URL + image);
                imagesUrl.add(Constants.BASE_IMAGE_URL + image);
            }

            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            banner.setImages(imagesUrl);
            //设置banner动画效果
            banner.setBannerAnimation(Transformer.Accordion);
            //设置标题集合（当banner样式有显示title时）
            //banner.setBannerTitles(titles);
            //设置自动轮播，默认为true
            banner.isAutoPlay(true);
            //设置轮播时间
            banner.setDelayTime(2500);
            //设置指示器位置（当banner模式中有指示器时）
            banner.setIndicatorGravity(BannerConfig.CENTER);

            //banner设置方法全部调用完毕时最后调用
            banner.start();
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Toast.makeText(context, position + "=position", Toast.LENGTH_SHORT).show();

                }
            });
        }

    }


}






