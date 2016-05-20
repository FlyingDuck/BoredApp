package com.cookbeans.boredapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.bumptech.glide.Glide;
import com.cookbeans.boredapp.R;
import com.cookbeans.boredapp.data.MeizhiOnly;
import com.cookbeans.boredapp.ui.view.RadioImageView;
import com.cookbeans.boredapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongshujin on 16/5/15.
 */
public class MeizhiListTableRecyclerViewAdapter
        extends AnimRecyclerViewAdapter<MeizhiListTableRecyclerViewAdapter.MeizhiViewHolder>{

    private static final String TAG = DaNiuListTableRecyclerViewAdapter.class.getSimpleName();

    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    private static final int ANIMATED_ITEMS_COUNT = 3;
    private Context context;
    private int lastAnimatedPosition = -1;
    private boolean animateItems = false;


    private List<MeizhiOnly> meizhiOnlyList;


    public MeizhiListTableRecyclerViewAdapter(Context context) {
        this.context = context;
        meizhiOnlyList = new ArrayList<>();
    }

    @Override
    public MeizhiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.rv_only_meizhi_item, parent, false);
        return new MeizhiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MeizhiViewHolder viewHolder, int position) {
        runEnterAnimation(viewHolder.itemView, position);
        bindMeizhiItem(position, viewHolder);
    }


    private void bindMeizhiItem(int position, MeizhiViewHolder holder) {
        MeizhiOnly meizhiOnly = meizhiOnlyList.get(position);

        holder.meizhiImg.setOriginalSize(meizhiOnly.width, meizhiOnly.height);

        Glide.with(context)
                .load(meizhiOnly.url)
                .placeholder(R.drawable.meizhi_default)
                .error(R.drawable.error_image)
//                .centerCrop()
                .into(holder.meizhiImg);

        holder.card.setTag(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return meizhiOnlyList.size();
    }


    private void runEnterAnimation(View view, int position) {
        if (!animateItems || position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }
        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(Utils.getScreenHeight(context));
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }

    public void updateItems(List<MeizhiOnly> meizhiOnlyList, boolean animated) {
        this.meizhiOnlyList.clear();
        this.meizhiOnlyList.addAll(meizhiOnlyList);
        animateItems = animated;
        notifyDataSetChanged();
    }






    class MeizhiViewHolder extends RecyclerView.ViewHolder {
        View card;

        RadioImageView meizhiImg;


        public MeizhiViewHolder(View itemView) {
            super(itemView);
            card = itemView;

            meizhiImg = (RadioImageView) itemView.findViewById(R.id.image);

        }
    }
}
