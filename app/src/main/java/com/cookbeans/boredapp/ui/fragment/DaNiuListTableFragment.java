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
import com.cookbeans.boredapp.data.daniu.DaNiuGank;
import com.cookbeans.boredapp.data.daniu.entity.DaNiu;
import com.cookbeans.boredapp.data.daniu.net.DaNiuResult;
import com.cookbeans.boredapp.service.DaNiuApi;
import com.cookbeans.boredapp.ui.BaseActivity;
import com.cookbeans.boredapp.ui.adapter.DaNiuListTableRecyclerViewAdapter;
import com.malinskiy.materialicons.IconDrawable;
import com.malinskiy.materialicons.Iconify;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by dongsj on 16/5/13.
 */
public class DaNiuListTableFragment extends BaseLoadingFragment{
    private static final String TAG = DaNiuListTableFragment.class.getSimpleName();

    private static final String KEY_POSITION = "position";

    private List<DaNiuGank> mDaNiuList;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DaNiuListTableRecyclerViewAdapter mDaNiuListTableRecyclerViewAdapter;

    private int mLastVisibleItem;
    private boolean isLoadMore = false;
    private boolean isALlLoad = false;


    private int gankPage = 0;
    private int meizhiPage = 0;


    public static DaNiuListTableFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_POSITION, position);

        DaNiuListTableFragment fragment = new DaNiuListTableFragment();
        fragment.setArguments(bundle);

        return fragment;
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
        mDaNiuListTableRecyclerViewAdapter = new DaNiuListTableRecyclerViewAdapter(getContext());
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
        loadData(gankPage++, meizhiPage++);
    }

    private void reloadData(){
        isALlLoad = false;
        gankPage = 0;
        meizhiPage = 0;
        mDaNiuList.clear();
        loadData(1, 1);
    }

    private void loadData(int startGankPage, int startMeizhiPage) {
        Log.d(TAG, "loadData startPage : " + startGankPage);
        mSwipeRefreshLayout.setRefreshing(true);

        Observable
                .zip( // 干货为主 配妹纸图
                        BaseActivity.daNiuApi.getAllDaNiuData(startGankPage),
                        BaseActivity.daNiuApi.getEnjoyData(startMeizhiPage),
                        new Func2<DaNiuResult, DaNiuResult, List<DaNiuGank>>() {
                            @Override
                            public List<DaNiuGank> call(DaNiuResult daNiuResult, DaNiuResult meizhiResult) {
                                List<DaNiuGank> daNiuGankList = new ArrayList<>(daNiuResult.results.size());
                                for (int index=0; index<daNiuResult.results.size(); index++) {
                                    DaNiu daNiu = daNiuResult.results.get(index);
                                    DaNiuGank daNiuGank = new DaNiuGank();
                                    daNiuGank.url = daNiu.url;
                                    daNiuGank.desc = daNiu.desc;
                                    daNiuGank.who = daNiu.who;
                                    daNiuGank.publishedAt = daNiu.publishedAt;

                                    if (meizhiResult.results.size() > index) {
                                        daNiuGank.meizhiUrl = meizhiResult.results.get(index).url;
                                    } else {
                                        int meizhiIndex = index % meizhiResult.results.size();
                                        daNiuGank.meizhiUrl = meizhiResult.results.get(meizhiIndex).url;
                                        meizhiPage = 0; //
                                    }
                                    daNiuGankList.add(daNiuGank);
                                }

                                return daNiuGankList;
                            }
                        }
                )
                .flatMap(
                        new Func1<List<DaNiuGank>, Observable<DaNiuGank>>() {
                            @Override
                            public Observable<DaNiuGank> call(List<DaNiuGank> daNiuGanks) {
                                return Observable.from(daNiuGanks);
                            }
                        }
                )
                .toSortedList(
                        new Func2<DaNiuGank, DaNiuGank, Integer>() {
                            @Override
                            public Integer call(DaNiuGank daNiuGank, DaNiuGank daNiuGank2) {
                                return daNiuGank2.publishedAt.compareTo(daNiuGank.publishedAt);
                            }
                        }
                )
                .cache()
                .subscribeOn(Schedulers.computation())
                .doOnNext(
                        new Action1<List<DaNiuGank>>() {
                            @Override
                            public void call(List<DaNiuGank> daNiuGanks) {
                                // TODO: 16/5/14 保存妹纸数据到数据库
                            }
                        }
                )
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(
                        new Action1<List<DaNiuGank>>() {
                            @Override
                            public void call(List<DaNiuGank> daNiuGankList) {
                                Log.d(TAG, "Observable doOnNext : 更新页面 size : " + daNiuGankList.size());
                                disposeResults(daNiuGankList);
                            }
                        }
                )
                .doOnCompleted(
                        new Action0() {
                            @Override
                            public void call() {
                                Log.d(TAG, "Observable doOnCompleteted");
                            }
                        }
                )
                .doOnError(
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.d(TAG, "Observable doOnError");

                            }
                        }
                )
                .finallyDo(
                        new Action0() {
                            @Override
                            public void call() {
                                Log.d(TAG, "Observable finallyDo");
                            }
                        }
                )
                .subscribe(
                        new Action1<List<DaNiuGank>>() {
                            @Override
                            public void call(List<DaNiuGank> ganks) {
                                Log.d(TAG, "Observable subscribe() onNext");
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.d(TAG, "Observable subscribe() onError");
                                if (throwable instanceof RetrofitError) {
                                    Drawable errorDrawable = new IconDrawable(getContext(), Iconify.IconValue.zmdi_network_off)
                                            .colorRes(android.R.color.white);
                                    RetrofitError e = (RetrofitError) throwable;
                                    if (e.getKind() == RetrofitError.Kind.NETWORK) {
                                        showError(errorDrawable, "网络异常", "好像您的网络出了点问题", "重试", mErrorRetryListener);
                                    } else if (e.getKind() == RetrofitError.Kind.HTTP) {
                                        showError(errorDrawable, "服务异常", "好像服务器出了点问题", "再试一次", mErrorRetryListener);
                                    } else {
                                        showError(errorDrawable, "莫名异常", "外星人进攻地球了？", "反击", mErrorRetryListener);
                                    }
                                }
                                isLoadMore = false;
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        },
                        new Action0() {
                            @Override
                            public void call() {
                                Log.d(TAG, "Observable subscribe() onComplete");
                                mSwipeRefreshLayout.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (null != mSwipeRefreshLayout) {
                                            mSwipeRefreshLayout.setRefreshing(false);
                                        }
                                    }
                                }, 1000);
                            }
                        }
                );
    }

    private void disposeResults(final List<DaNiuGank> daNiuGankList) {
        Log.d(TAG, "disposeResults. " + daNiuGankList.size());
        if(mDaNiuList.isEmpty() && daNiuGankList.isEmpty()){
            showNoDataView();
            return;
        }
        showContent();

//        if (isPullDown) {
//            if (hasNew(daNiuGankList)) {
//                mDaNiuList.addAll(0, daNiuGankList);
//            } else {
//                Snackbar.make(mRecyclerView,"已经是最新的了",Snackbar.LENGTH_SHORT)
//                        .setAction("知道了",null)
//                        .show();
//            }
//        } else {
//            Log.d(TAG, "is not pulldown");
//            if (hasMore(daNiuGankList)) {
//                Log.d(TAG, "has more");
//                mDaNiuList.addAll(daNiuGankList);
//            } else {
//                Log.d(TAG, "no more");
//                isALlLoad = true;
//            }
//            isLoadMore = false;
//        }

        mDaNiuList.addAll(daNiuGankList);
        if(daNiuGankList.size() == DaNiuApi.LOAD_LIMIT){
            gankPage++;
            meizhiPage++;
        } else {
            isALlLoad = true;
        }
        isLoadMore = false;
       mDaNiuListTableRecyclerViewAdapter.updateItems(mDaNiuList, gankPage == 1);
    }


    private void showNoDataView(){
        Drawable emptyDrawable = new IconDrawable(getContext(), Iconify.IconValue.zmdi_shopping_cart)
                .colorRes(android.R.color.white);
        List<Integer> skipIds = new ArrayList<>();
        showEmpty(emptyDrawable, "数据列表为空", "没有拿到数据哎，请等一下再来玩干货吧", skipIds);
    }

    private boolean hasNew(List<DaNiuGank> newMeizhiList) {
        if (null == newMeizhiList || 0 == newMeizhiList.size()) {
            return false;
        }
        if (0 == mDaNiuList.size()) {
            return true;
        }

        DaNiuGank currentFirstMeizhi = mDaNiuList.get(0);
        DaNiuGank newFirstMeizhi = newMeizhiList.get(0);
        return -1 == currentFirstMeizhi.publishedAt.compareTo(newFirstMeizhi.publishedAt);
    }

    private boolean hasMore(List<DaNiuGank> historyMeizhiList) {
        if (0 == historyMeizhiList.size()) {
            return false;
        }

        DaNiuGank currentLastMeizhi = mDaNiuList.get(mDaNiuList.size()-1);
        DaNiuGank historyLastMeizhi = historyMeizhiList.get(historyMeizhiList.size()-1);

        return 1 == currentLastMeizhi.publishedAt.compareTo(historyLastMeizhi.publishedAt);
    }





}
