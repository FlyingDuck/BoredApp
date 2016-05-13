package com.cookbeans.boredapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
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
import com.cookbeans.boredapp.data.daniu.DaNiuGank;
import com.cookbeans.boredapp.ui.func.OnDaNiuMeizhiTouchListener;
import com.cookbeans.boredapp.utils.DateTimeUtils;
import com.cookbeans.boredapp.utils.Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongsj on 16/5/13.
 */
public class DaNiuListTableRecyclerViewAdapter
        extends AnimRecyclerViewAdapter<DaNiuListTableRecyclerViewAdapter.DaNiuItemViewHolder> {

    private static final String TAG = DaNiuListTableRecyclerViewAdapter.class.getSimpleName();

    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    private static final int ANIMATED_ITEMS_COUNT = 3;
    private Context context;
    private int lastAnimatedPosition = -1;
    private boolean animateItems = false;

    private OnDaNiuMeizhiTouchListener mOnDaNiuMeizhiTouchListener;

    private List<DaNiuGank> mDaNiuGanList;


    public DaNiuListTableRecyclerViewAdapter(Context context) {
        this.context = context;
        mDaNiuGanList = new ArrayList<>();
    }



    @Override
    public DaNiuItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.rv_daniu_meizhi_item, parent, false);
        final DaNiuItemViewHolder daNiuItemViewHolder = new DaNiuItemViewHolder(view);
        return daNiuItemViewHolder;
    }

    @Override
    public void onBindViewHolder(DaNiuItemViewHolder viewHolder, int position) {
        runEnterAnimation(viewHolder.itemView, position);
        bindDaNiuGankItem(position, viewHolder);
    }

    private void bindDaNiuGankItem(int position, DaNiuItemViewHolder holder) {
        DaNiuGank daNiuGank = mDaNiuGanList.get(position);

        holder.gankTitle.setText(daNiuGank.desc);
        holder.gankTime.setText(DateTimeUtils.dateToDefaultDateStr(daNiuGank.publishedAt));
        holder.gankAuthor.setText("recommend by " + daNiuGank.who);

        Glide.with(context)
                .load(daNiuGank.meizhiUrl)
//                .placeholder(R.drawable.meizhi_default)
//                .error(R.drawable.error_image)
                .centerCrop()
                .into(holder.meizhiImg);

        holder.card.setTag(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDaNiuGanList.size();
    }




    private View.OnClickListener mFavGankOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: 16/5/14
        }
    };

    private View.OnClickListener mItemOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer position = (Integer)view.getTag();
            DaNiuGank daNiuGank = mDaNiuGanList.get(position);
            Intent intent= new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(daNiuGank.url);
            intent.setData(content_url);
            view.getContext().startActivity(intent);
        }
    };

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

    public void updateItems(List<DaNiuGank> daNiuGanks, boolean animated) {
        mDaNiuGanList.clear();
        mDaNiuGanList.addAll(daNiuGanks);
        animateItems = animated;
        notifyDataSetChanged();
    }


    class DaNiuItemViewHolder extends RecyclerView.ViewHolder {
        View card;

        ImageView meizhiImg;

        TextView gankTitle;
        TextView gankAuthor;
        TextView gankTime;



        public DaNiuItemViewHolder(View itemView) {
            super(itemView);
            card = itemView;

            meizhiImg = (ImageView) itemView.findViewById(R.id.img_meizhi_img);
            gankTitle = (TextView) itemView.findViewById(R.id.tv_gank_title);
            gankAuthor = (TextView) itemView.findViewById(R.id.tv_gank_author);
            gankTime = (TextView) itemView.findViewById(R.id.tv_gank_time);

            card.setOnClickListener(mItemOnClickListener);
        }
    }
}
