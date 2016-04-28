package com.cookbeans.boredapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cookbeans.boredapp.R;

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
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getPosition() + " clicked.");
                }
            });
            textView = (TextView) v.findViewById(R.id.show_test);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    private List<String> testDatas;

    public RecyclerViewAdapter(List<String> testDatas) {
        this.testDatas = testDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_content_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getTextView().setText(testDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return testDatas.size();
    }
}
