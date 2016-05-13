package com.cookbeans.boredapp.ui.fragment;

import android.content.Intent;
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
import com.cookbeans.boredapp.data.gank.entity.Gank;
import com.cookbeans.boredapp.data.gank.net.GankResult;
import com.cookbeans.boredapp.service.GankApi;
import com.cookbeans.boredapp.ui.BaseActivity;
import com.cookbeans.boredapp.ui.GankDetailActivity;
import com.cookbeans.boredapp.ui.adapter.GankListTableRecyclerViewAdapter;
import com.cookbeans.boredapp.ui.func.OnGankMeizhiTouchListener;
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
 *
 */
public class GankListTableFragment extends BaseLoadingFragment {
    public static final String TAG = GankListTableFragment.class.getSimpleName();

    public static final String KEY_POSITION = "position";


    private List<Gank> mGankList;

    private RecyclerView mRecyclerView;
    private GankListTableRecyclerViewAdapter mGankListTableRecyclerViewAdapter;
    private SwipeRefreshLayout  mSwipeRefreshLayout;

    private int hasLoadPage = 0;
    private int mLastVisibleItem;
    private boolean isLoadMore = false;
    private boolean isALlLoad = false;

    private boolean isPullDown = true;
        /**
         * @return a new instance of {@link GankListTableFragment}, adding the parameters into a bundle and
         * setting them as arguments.
         */
    public static GankListTableFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_POSITION, position);

        GankListTableFragment fragment = new GankListTableFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGankListTableRecyclerViewAdapter = new GankListTableRecyclerViewAdapter(getActivity());
        mGankListTableRecyclerViewAdapter.setOnGankMeizhiTouchListener(getOnGankMeizhiTouchListener());
        mGankList = new ArrayList<>();

//        setRetainInstance(true);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater,
//                             ViewGroup container,
//                             Bundle savedInstanceState) {
    // return inflater.inflate(R.layout.view_pager_gank_item, container, false);
//    }

    @Override // from BaseLoadingFragment
    View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gank_page, null);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_meizhi);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setAdapter(mGankListTableRecyclerViewAdapter);
        mRecyclerView.addOnScrollListener(getOnBottomListener());

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.colorSecondary, R.color.colorPrimary);
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

    private OnGankMeizhiTouchListener getOnGankMeizhiTouchListener() {
        return new OnGankMeizhiTouchListener() {
            @Override
            public void onTouch(View view, View meizhiView, View titleView, View card, Gank meizhi) {
                if (null == meizhi) {
                    return;
                }
                if (view == meizhiView) {
                    Snackbar.make(mRecyclerView, "你点了这个妹纸", Snackbar.LENGTH_SHORT)
                            .setAction("好的", null)
                            .show();
                } else if (view == titleView) {
                    startGankDetailActivity(meizhi);
                } else if (view == card) {

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
                        && mLastVisibleItem + 1 == mGankListTableRecyclerViewAdapter.getItemCount()) {
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

    private void loadData(int startPage){
        Log.d(TAG, "loadData startPage : " + startPage);
        mSwipeRefreshLayout.setRefreshing(true);

        Observable
                .zip( // zip 两个结果集合并
                        BaseActivity.gankApi.getMeizhiData(startPage),
                        BaseActivity.gankApi.getVideoData(startPage),
                        new Func2<GankResult, GankResult, GankResult>() { // 将video中的描述
                            @Override
                            public GankResult call(GankResult meizhiResult, GankResult videoResult) {
                                // todo 有时候妹纸图片一天有多张 后期需要将同一天的meizhi图片做合并
                                for (int index = 0; index < meizhiResult.results.size(); index++) {
                                    Gank meizhi = meizhiResult.results.get(index);
                                    if (index < videoResult.results.size()) {
                                        Gank video = videoResult.results.get(index);
                                        meizhi.desc = video.desc;
                                    }
                                }
                                return meizhiResult;
                            }})
                .map( // map 一个Result对象 转化为一个List<Gank>对象
                        new Func1<GankResult, List<Gank>>() {
                            @Override
                            public List<Gank> call(GankResult meizhiResult) {
                                return meizhiResult.results;
                            }
                        }
                )
                .flatMap( // flatMap 将一个List<Gank>对象 转化为多个Observable<Gank>对象
                        new Func1<List<Gank>, Observable<Gank>>() {
                            @Override
                            public Observable<Gank> call(List<Gank> meizhiList) {
                                return Observable.from(meizhiList);
                            }
                        }
                )
                .toSortedList( // sort 排序
                        new Func2<Gank, Gank, Integer>() {
                            @Override
                            public Integer call(Gank meizhi1, Gank meizhi2) {
                                return meizhi2.publishedAt.compareTo(meizhi1.publishedAt);
                            }
                        }
                )
                .cache()
                .subscribeOn(Schedulers.computation())
                .doOnNext(  //
                        new Action1<List<Gank>>() {
                            @Override
                            public void call(List<Gank> meizhiList) {
                                Log.d(TAG, "Observable doOnNext : 保存数据到数据库");
                                // TODO: 16/5/8 保存妹纸数据到数据库
                            }
                        }
                )
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(
                        new Action1<List<Gank>>() {
                            @Override
                            public void call(List<Gank> meizhiList) {
                                Log.d(TAG, "Observable doOnNext : 更新页面 size : " + meizhiList.size());
                                disposeResults(meizhiList);
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
                        new Action1<List<Gank>>() {
                            @Override
                            public void call(List<Gank> ganks) {
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

    private void disposeResults(final List<Gank> meizhiList){
        if(mGankList.isEmpty() && meizhiList.isEmpty()){
            showNoDataView();
            return;
        }
        showContent();

        if (isPullDown) {
            if (hasNew(meizhiList)) {
                mGankList.addAll(0, meizhiList);
            } else {
                Snackbar.make(mRecyclerView,"已经是最新的了",Snackbar.LENGTH_SHORT)
                        .setAction("知道了",null)
                        .show();
            }
        } else {
            Log.d(TAG, "is not pulldown");
            if (hasMore(meizhiList)) {
                Log.d(TAG, "has more");
                mGankList.addAll(meizhiList);
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

        mGankListTableRecyclerViewAdapter.updateItems(mGankList, hasLoadPage == 1);
    }

    private void showNoDataView(){
        Drawable emptyDrawable = new IconDrawable(getContext(), Iconify.IconValue.zmdi_shopping_cart)
                .colorRes(android.R.color.white);
        List<Integer> skipIds = new ArrayList<>();
        showEmpty(emptyDrawable, "数据列表为空", "没有拿到数据哎，请等一下再来玩干货吧", skipIds);
    }

    private boolean hasNew(List<Gank> newMeizhiList) {
        if (null == newMeizhiList || 0 == newMeizhiList.size()) {
            return false;
        }
        if (0 == mGankList.size()) {
            return true;
        }

        Gank currentFirstMeizhi = mGankList.get(0);
        Gank newFirstMeizhi = newMeizhiList.get(0);
        return -1 == currentFirstMeizhi.publishedAt.compareTo(newFirstMeizhi.publishedAt);
    }

    private boolean hasMore(List<Gank> historyMeizhiList) {
        if (0 == historyMeizhiList.size()) {
            return false;
        }

        Gank currentLastMeizhi = mGankList.get(mGankList.size()-1);
        Gank historyLastMeizhi = historyMeizhiList.get(historyMeizhiList.size()-1);

        return 1 == currentLastMeizhi.publishedAt.compareTo(historyLastMeizhi.publishedAt);
    }


    private void startGankDetailActivity(Gank meizhi) {
        Intent intent = new Intent(getContext(), GankDetailActivity.class);
        intent.putExtra(GankDetailActivity.EXTRA_GANK_DATE, meizhi.publishedAt);
        intent.putExtra(GankDetailActivity.EXTRA_GANK_MEIZHI_URL, meizhi.url);
        startActivity(intent);
    }

}
