package com.cookbeans.boredapp.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cookbeans.boredapp.R;
import com.cookbeans.boredapp.data.daniu.DaNiuGank;
import com.cookbeans.boredapp.data.gank.entity.Gank;
import com.cookbeans.boredapp.utils.StringStyleUtils;

import java.util.List;

/**
 * Created by dongsj on 16/5/12.
 */
public class GankDetailTableRecyclerViewAdapter
        extends AnimRecyclerViewAdapter<GankDetailTableRecyclerViewAdapter.GankItemViewHolder> {
    private static final String TAG = GankDetailTableRecyclerViewAdapter.class.getSimpleName();

    private List<Gank> mGankList;

    public GankDetailTableRecyclerViewAdapter(List<Gank> mGankList) {
        this.mGankList = mGankList;
    }

    @Override
    public GankItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gank_detail_item, parent, false);
        return new GankItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GankItemViewHolder holder, int position) {
        Gank gank = mGankList.get(position);
        if (0 == position) {
            showCategory(holder);
        } else {
            boolean theLastGankCategoryEqualsToThis = mGankList.get(
                    position - 1).type.equals(mGankList.get(position).type);
            if (!theLastGankCategoryEqualsToThis) {
                showCategory(holder);
            }
            else {
                hideCategory(holder);
            }
        }

        holder.category.setText(gank.type);
        SpannableStringBuilder builder = new SpannableStringBuilder(gank.desc).append(
                StringStyleUtils.format(holder.gank.getContext(), " (via. " +
                        gank.who +
                        ")", R.style.ViaTextAppearance));
        CharSequence gankText = builder.subSequence(0, builder.length());
        holder.gank.setText(gankText);
        showItemAnim(holder.gank, position);

    }

    @Override
    public int getItemCount() {
        return mGankList.size();
    }


    private void showCategory(GankItemViewHolder holder) {
        if (!isVisibleOf(holder.category)) holder.category.setVisibility(View.VISIBLE);
    }


    private void hideCategory(GankItemViewHolder holder) {
        if (isVisibleOf(holder.category)) holder.category.setVisibility(View.GONE);
    }

    private boolean isVisibleOf(View view) {
        return view.getVisibility() == View.VISIBLE;
    }


    class GankItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        View layoutView;
        TextView category;
        TextView gank;


        public GankItemViewHolder(View itemView) {
            super(itemView);
            layoutView = itemView.findViewById(R.id.ll_gank_parent);
            category = (TextView) itemView.findViewById(R.id.tv_category);
            gank = (TextView) itemView.findViewById(R.id.tv_title);

            layoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Gank gank = mGankList.get(getLayoutPosition());
            Log.d(TAG, "onClick : " + gank.desc);
            Snackbar.make(itemView.getRootView(), gank.desc, Snackbar.LENGTH_SHORT)
                    .setAction("OK", null)
                    .show();

            Intent intent= new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(gank.url);
            intent.setData(content_url);
            v.getContext().startActivity(intent);

            // TODO: 16/5/20 使用内置浏览
//            Intent intent = WebActivity.newIntent(v.getContext(), gank.url, gank.desc);
//            v.getContext().startActivity(intent);
        }


//        @OnClick(R.id.ll_gank_parent) void onGank(View v) {
//            Gank gank = mGankList.get(getLayoutPosition());
//            Intent intent = WebActivity.newIntent(v.getContext(), gank.url, gank.desc);
//            v.getContext().startActivity(intent);
//        }
    }
}
