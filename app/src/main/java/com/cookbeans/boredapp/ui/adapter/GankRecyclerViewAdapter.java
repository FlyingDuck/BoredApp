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
public class GankRecyclerViewAdapter extends RecyclerView.Adapter<GankRecyclerViewAdapter.GankItemViewHolder>{
    private static final String TAG = GankRecyclerViewAdapter.class.getSimpleName();

    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    private static final int ANIMATED_ITEMS_COUNT = 3;

    private Context mContext;
    private List<Gank> mGankList;

    private boolean animateItems = false;
    private int lastAnimatedPosition = -1;

    // TODO: 16/5/3 gank card 点击事件


    public GankRecyclerViewAdapter(Context context) {
        this.mContext = context;
        this.mGankList = new ArrayList<>();
    }

    public GankRecyclerViewAdapter(Context context, List<Gank> gankList) {
        this.mContext = context;
        this.mGankList = gankList;
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


    public static class GankItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Gank gank;

        View card;
        TextView titleView;
        ImageView meizhiView;

        public GankItemViewHolder(View itemView) {
            super(itemView);
            card = itemView;
            titleView = (TextView) itemView.findViewById(R.id.tv_gank_title);
            meizhiView = (ImageView) itemView.findViewById(R.id.iv_meizhi);
            card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Element " + getPosition() + " clicked." + gank.desc);
            // TODO: 16/5/4 点击事件处理
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
//        Log.d(TAG, "onBindViewHolder: position = " + position + " meizi = " + gank.url);
        int limit = 48;
        String text = gank.desc.length() > limit ? gank.desc.substring(0, limit) + "..." : gank.desc;
        viewHolder.gank = gank;
        viewHolder.titleView.setText(text);
        viewHolder.card.setTag(gank.desc);

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
