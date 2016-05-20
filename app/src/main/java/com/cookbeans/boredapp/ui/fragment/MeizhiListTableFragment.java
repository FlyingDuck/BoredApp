package com.cookbeans.boredapp.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cookbeans.boredapp.BoredApplication;
import com.cookbeans.boredapp.R;
import com.cookbeans.boredapp.data.MeizhiOnly;
import com.cookbeans.boredapp.data.daniu.DaNiuGank;
import com.cookbeans.boredapp.data.daniu.entity.DaNiu;
import com.cookbeans.boredapp.data.daniu.net.DaNiuResult;
import com.cookbeans.boredapp.data.gank.entity.Gank;
import com.cookbeans.boredapp.data.gank.net.GankResult;
import com.cookbeans.boredapp.service.DaNiuApi;
import com.cookbeans.boredapp.service.GankApi;
import com.cookbeans.boredapp.ui.BaseActivity;
import com.cookbeans.boredapp.ui.adapter.MeizhiListTableRecyclerViewAdapter;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.malinskiy.materialicons.IconDrawable;
import com.malinskiy.materialicons.Iconify;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by dongshujin on 16/5/14.
 */
public class MeizhiListTableFragment extends BaseLoadingFragment
        implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = MeizhiListTableFragment.class.getSimpleName();

    private static final String KEY_POSITION = "position";


    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<MeizhiOnly> mAllMeizhi;
    private MeizhiListTableRecyclerViewAdapter mMeizhiListTableRecyclerViewAdapter;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    private boolean isALlLoad = false;
    private int hasLoadPage = 0;
    private int pageSize = 20;
    private boolean isLoadMore = false;


    public static MeizhiListTableFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_POSITION, position);

        MeizhiListTableFragment fragment = new MeizhiListTableFragment();
        fragment.setArguments(bundle);

        return fragment;
    }


    private View.OnClickListener mErrorRetryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            reloadData();
        }
    };

    @Override
    public void onRefresh() {
        reloadData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAllMeizhi = new ArrayList<>();

        mMeizhiListTableRecyclerViewAdapter = new MeizhiListTableRecyclerViewAdapter(getContext());
    }


    @Override
    View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meizhi_page, null);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_meizhi);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.pinkSecondary, R.color.pinkPrimary);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_only_meizhi);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mRecyclerView.setAdapter(mMeizhiListTableRecyclerViewAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    MeizhiListTableFragment.this.onScrollStateChanged();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        reloadData();
    }


    private void onScrollStateChanged(){
        int[] positions = new int[mStaggeredGridLayoutManager.getSpanCount()];
        mStaggeredGridLayoutManager.findLastVisibleItemPositions(positions);
        for (int position : positions) {
            if (position == mStaggeredGridLayoutManager.getItemCount() - 1) {
                loadMore();
                break;
            }
        }
    }

    private void loadMore(){
        if(isALlLoad){
            Toast.makeText(getActivity(), "全部加载完毕", Toast.LENGTH_SHORT).show();
            return;
        }
        if(isLoadMore)return;
        isLoadMore = true;
        loadData(hasLoadPage + 1);
    }

    private void reloadData(){
        mSwipeRefreshLayout.setRefreshing(true);
        mAllMeizhi.clear();
        isALlLoad = false;
        hasLoadPage = 0;
        loadData(1);
    }

    private void loadData(int startPage){
        QueryBuilder query = new QueryBuilder(MeizhiOnly.class);
        query.appendOrderAscBy("publishedAt");
        query.limit(startPage, pageSize);
        List<MeizhiOnly> meizhis = BoredApplication.dbInstance.query(query);
        disposeResults(meizhis);
    }


    private void disposeResults(final List<MeizhiOnly> meizhiOnlys) {
        Log.d(TAG, "disposeResults. " + meizhiOnlys.size());
        if(mAllMeizhi.isEmpty() && meizhiOnlys.isEmpty()){
            showNoDataView();
            return;
        }
        showContent();

        mAllMeizhi.addAll(meizhiOnlys);
        if(meizhiOnlys.size() == pageSize){
            hasLoadPage++;
        } else {
            isALlLoad = true;
        }
        isLoadMore = false;
        mMeizhiListTableRecyclerViewAdapter.updateItems(mAllMeizhi, hasLoadPage == 1);
    }


    private void showNoDataView(){
        Drawable emptyDrawable = new IconDrawable(getContext(), Iconify.IconValue.zmdi_shopping_cart)
                .colorRes(android.R.color.white);
        List<Integer> skipIds = new ArrayList<>();
        showEmpty(emptyDrawable, "数据列表为空", "没有拿到数据哎，请等一下再来玩妹子吧", skipIds);
    }


}
