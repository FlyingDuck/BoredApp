package com.cookbeans.boredapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cookbeans.boredapp.R;
import com.cookbeans.boredapp.data.gank.entity.Gank;
import com.cookbeans.boredapp.ui.func.OnGankMeizhiTouchListener;
import com.cookbeans.boredapp.utils.DateTimeUtils;
import com.cookbeans.boredapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * *****************************
 * ***    BoredApp   ****
 * *****************************
 * Package : com.cookbeans.boredapp.ui.adapter
 * Data   : 16/2/26
 * Auth   : dongsj
 * Desc   :
 */
public class GankListTableRecyclerViewAdapter extends RecyclerView.Adapter<GankListTableRecyclerViewAdapter.GankItemViewHolder>{
    private static final String TAG = GankListTableRecyclerViewAdapter.class.getSimpleName();

    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    private static final int ANIMATED_ITEMS_COUNT = 3;

    private Context mContext;
    private List<Gank> mGankList;

    private boolean animateItems = false;
    private int lastAnimatedPosition = -1;

    private OnGankMeizhiTouchListener mOnGankMeizhiTouchListener;

    public GankListTableRecyclerViewAdapter(Context context) {
        this.mContext = context;
        this.mGankList = new ArrayList<>();
    }

    @Override
    public GankItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_gank_meizhi_item, parent, false);
        return new GankItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GankItemViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);
        bindGankItem(holder, position);
    }

    @Override
    public void onViewRecycled(GankItemViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return mGankList.size();
    }

    public void setOnGankMeizhiTouchListener(OnGankMeizhiTouchListener listener) {
        this.mOnGankMeizhiTouchListener = listener;
    }


    public class GankItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Gank meizhi;

        View card;
        TextView titleView;
        ImageView meizhiView;
        TextView timeView;

        public GankItemViewHolder(View itemView) {
            super(itemView);
            card = itemView;
            titleView = (TextView) itemView.findViewById(R.id.tv_gank_title);
            meizhiView = (ImageView) itemView.findViewById(R.id.iv_meizhi);
            timeView = (TextView) itemView.findViewById(R.id.tv_gank_time);
            meizhiView.setOnClickListener(this);
            titleView.setOnClickListener(this);
            card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Element " + getLayoutPosition() + " clicked." + meizhi.desc);
            mOnGankMeizhiTouchListener.onTouch(v, meizhiView, titleView, card, meizhi);
        }
    }

    private void runEnterAnimation(View view, int position) {
        if (!animateItems || position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }
        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(Utils.getScreenHeight(mContext));
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }

    private void bindGankItem(GankItemViewHolder viewHolder, int position) {
        Gank gank = mGankList.get(position);
//        Log.d(TAG, "onBindViewHolder: position = " + position + " meizi = " + meizhi.url);
        int limit = 60;
        String title = gank.desc.length() > limit ? gank.desc.substring(0, limit) + "..." : gank.desc;
        String timeText = DateTimeUtils.dateToDefaultDateStr(gank.publishedAt);

        viewHolder.meizhi = gank;
        viewHolder.titleView.setText(title);
        viewHolder.card.setTag(gank.desc);
        viewHolder.timeView.setText(timeText);

        Glide.with(mContext)
                .load(gank.url)
//                .fitCenter()
                .placeholder(R.drawable.meizhi_default)
                .error(R.drawable.error_image)
                .centerCrop()
                .into(viewHolder.meizhiView);
//                .getSize(new SizeReadyCallback() {
//                    @Override
//                    public void onSizeReady(int width, int height) {
//                        if (!holder.card.isShown()) {
//                            holder.card.setVisibility(View.VISIBLE);
//                        }
//                    }
//                });
    }

    public void updateItems(List<Gank> ganks, boolean animated) {
        mGankList.clear();
        mGankList.addAll(ganks);
        animateItems = animated;
        notifyDataSetChanged();
    }


}
