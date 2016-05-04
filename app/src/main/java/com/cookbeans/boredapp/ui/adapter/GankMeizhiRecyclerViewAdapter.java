package com.cookbeans.boredapp.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.cookbeans.boredapp.R;
import com.cookbeans.boredapp.data.gank.entity.Meizhi;
import com.cookbeans.boredapp.ui.widget.RatioImageView;

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
public class GankMeizhiRecyclerViewAdapter extends RecyclerView.Adapter<GankMeizhiRecyclerViewAdapter.ViewHolder>{
    private static final String TAG = GankMeizhiRecyclerViewAdapter.class.getSimpleName();


    private Context mContext;
    private List<Meizhi> mMeizhiList;
    private Fragment mFragment;

//    private OnMeizhiTouchListener mOnMeizhiTouchListener; // TODO: 16/5/3 gank card 点击事件

    public GankMeizhiRecyclerViewAdapter(Context context, List<Meizhi> meizhiList) {
        this.mContext = context;
        this.mMeizhiList = meizhiList;
    }

    public GankMeizhiRecyclerViewAdapter(Context context, Fragment fragment, List<Meizhi> meizhiList) {
        this.mContext = context;
        this.mFragment = fragment;
        this.mMeizhiList = meizhiList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_gank_meizhi_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Meizhi meizhi = mMeizhiList.get(position);
        Log.d(TAG, "onBindViewHolder: position = " + position + " meizi = " + meizhi.url);
        int limit = 48;
        String text = meizhi.desc.length() > limit ? meizhi.desc.substring(0, limit) + "..." : meizhi.desc;
        holder.meizhi = meizhi;
        holder.titleView.setText(text);
        holder.card.setTag(meizhi.desc);

        Glide.with(mContext)
                .load(meizhi.url)
//                .fitCenter()
                .placeholder(R.drawable.meizhi_default)
                .error(R.drawable.error_image)
                .centerCrop()
                .into(holder.meizhiView);
//                .getSize(new SizeReadyCallback() {
//                    @Override
//                    public void onSizeReady(int width, int height) {
//                        if (!holder.card.isShown()) {
//                            holder.card.setVisibility(View.VISIBLE);
//                        }
//                    }
//                });
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return mMeizhiList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View card;

        TextView titleView;
//        RatioImageView meizhiView;
        ImageView meizhiView;

        Meizhi meizhi;

        public ViewHolder(View itemView) {
            super(itemView);
            card = itemView;
            titleView = (TextView) itemView.findViewById(R.id.tv_gank_title);
            meizhiView = (ImageView) itemView.findViewById(R.id.iv_meizhi);
            // Define click listener for the ViewHolder's View.
            card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Element " + getPosition() + " clicked." + meizhi.desc);
            // TODO: 16/5/4 点击事件处理
        }
    }
}
