package com.cookbeans.boredapp.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cookbeans.boredapp.R;
import com.cookbeans.boredapp.data.daniu.entity.DaNiu;
import com.cookbeans.boredapp.data.gank.entity.Gank;
import com.cookbeans.boredapp.service.GankApi;
import com.cookbeans.boredapp.ui.adapter.DaNiuListTableRecyclerViewAdapter;
import com.cookbeans.boredapp.ui.func.OnDaNiuMeizhiTouchListener;
import com.malinskiy.materialicons.IconDrawable;
import com.malinskiy.materialicons.Iconify;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongsj on 16/5/13.
 */
public class DaNiuListTableFragment extends BaseLoadingFragment{
    private static final String TAG = DaNiuListTableFragment.class.getSimpleName();

    private static final String KEY_POSITION = "position";

    private List<DaNiu> mDaNiuList;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DaNiuListTableRecyclerViewAdapter mDaNiuListTableRecyclerViewAdapter;

    private int mLastVisibleItem;
    private boolean isPullDown = true;
    private boolean isLoadMore = false;
    private boolean isALlLoad = false;

    private int hasLoadPage = 0;


    public static DaNiuListTableFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_POSITION, position);

        DaNiuListTableFragment fragment = new DaNiuListTableFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private OnDaNiuMeizhiTouchListener getOnDaNiuMeizhiTouchListener() {
        return new OnDaNiuMeizhiTouchListener() {
            @Override
            public void onTouch(View view, View card, Gank meizhi) {
                if (null == meizhi) {
                    return;
                }
                if (view == card) {
                    Snackbar.make(mRecyclerView, "你点了这个妹纸", Snackbar.LENGTH_SHORT)
                            .setAction("好的", null)
                            .show();
                }

            }
        };
    }

    private RecyclerView.OnScrollListener getOnBottomListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItem + 1 == mDaNiuListTableRecyclerViewAdapter.getItemCount()) {
                    isPullDown = false;
                    loadMore();
                }
            }
        };
    }

    private View.OnClickListener mErrorRetryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            reloadData();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDaNiuList = new ArrayList<>();
        mDaNiuListTableRecyclerViewAdapter = new DaNiuListTableRecyclerViewAdapter();
        mDaNiuListTableRecyclerViewAdapter.setOnDaNiuMeizhiTouchListener(getOnDaNiuMeizhiTouchListener());
    }

    //@Override
    //public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    //    return super.onCreateView(inflater, container, savedInstanceState);
    //}

    @Override // from BaseLoadingFragment 在 onCreateView中回调
    View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_daniu_page, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_daniu_meizhi);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setAdapter(mDaNiuListTableRecyclerViewAdapter);
        mRecyclerView.addOnScrollListener(getOnBottomListener());

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_daniu);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.deepPurpleSecondary, R.color.deepPurplePrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isPullDown = true;
                reloadData();
            }
        });

        Bundle args = getArguments();
        if (args != null) {
            int position = args.getInt(KEY_POSITION);
            Log.d(TAG, "onViewCreate postion : " + position);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLoading();
        reloadData();
    }


    private void loadMore(){
        Log.d(TAG, "loadMore");
        if(isLoadMore) {
            return;
        }
        if(isALlLoad){
            Snackbar.make(mRecyclerView,"全部加载完毕",Snackbar.LENGTH_SHORT)
                    .setAction("返回顶部", null)
                    .show();
            return;
        }
        isLoadMore = true;
        loadData(hasLoadPage + 1);
    }

    private void reloadData(){
//        mGankList.clear();
        isALlLoad = false;
        hasLoadPage = 0;
        loadData(1);
    }

    private void loadData(int startPage) {
        Log.d(TAG, "loadData startPage : " + startPage);
        // TODO: 16/5/13  api 已干货为主 配妹纸图

    }

    private void disposeResults(final List<DaNiu> meizhiList) {
        if(mDaNiuList.isEmpty() && meizhiList.isEmpty()){
            showNoDataView();
            return;
        }
        showContent();

        if (isPullDown) {
            if (hasNew(meizhiList)) {
                mDaNiuList.addAll(0, meizhiList);
            } else {
                Snackbar.make(mRecyclerView,"已经是最新的了",Snackbar.LENGTH_SHORT)
                        .setAction("知道了",null)
                        .show();
            }
        } else {
            Log.d(TAG, "is not pulldown");
            if (hasMore(meizhiList)) {
                Log.d(TAG, "has more");
                mDaNiuList.addAll(meizhiList);
            } else {
                Log.d(TAG, "no more");
                isALlLoad = true;
            }
            isLoadMore = false;
        }


        if(meizhiList.size() == GankApi.LOAD_LIMIT){
            hasLoadPage++;
        } else {
//            isALlLoad = true;
        }

//       todo  mDaNiuListTableRecyclerViewAdapter.updateItems(mDaNiuList, hasLoadPage == 1);
    }


    private void showNoDataView(){
        Drawable emptyDrawable = new IconDrawable(getContext(), Iconify.IconValue.zmdi_shopping_cart)
                .colorRes(android.R.color.white);
        List<Integer> skipIds = new ArrayList<>();
        showEmpty(emptyDrawable, "数据列表为空", "没有拿到数据哎，请等一下再来玩干货吧", skipIds);
    }

    private boolean hasNew(List<DaNiu> newMeizhiList) {
        if (null == newMeizhiList || 0 == newMeizhiList.size()) {
            return false;
        }
        if (0 == mDaNiuList.size()) {
            return true;
        }

        DaNiu currentFirstMeizhi = mDaNiuList.get(0);
        DaNiu newFirstMeizhi = newMeizhiList.get(0);
        return -1 == currentFirstMeizhi.publishedAt.compareTo(newFirstMeizhi.publishedAt);
    }

    private boolean hasMore(List<DaNiu> historyMeizhiList) {
        if (0 == historyMeizhiList.size()) {
            return false;
        }

        DaNiu currentLastMeizhi = mDaNiuList.get(mDaNiuList.size()-1);
        DaNiu historyLastMeizhi = historyMeizhiList.get(historyMeizhiList.size()-1);

        return 1 == currentLastMeizhi.publishedAt.compareTo(historyLastMeizhi.publishedAt);
    }





}
