package com.cookbeans.boredapp.ui.fragment;

import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cookbeans.boredapp.R;
import com.cookbeans.boredapp.data.MeizhiOnly;
import com.cookbeans.boredapp.data.daniu.entity.DaNiu;
import com.cookbeans.boredapp.data.daniu.net.DaNiuResult;
import com.cookbeans.boredapp.data.gank.entity.Gank;
import com.cookbeans.boredapp.data.gank.net.GankResult;
import com.cookbeans.boredapp.service.GankApi;
import com.cookbeans.boredapp.ui.BaseActivity;
import com.cookbeans.boredapp.ui.adapter.MeizhiListTableRecyclerViewAdapter;
import com.malinskiy.materialicons.IconDrawable;
import com.malinskiy.materialicons.Iconify;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by dongshujin on 16/5/14.
 */
public class MeizhiListTableFragment extends BaseLoadingFragment
        implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = MeizhiListTableFragment.class.getSimpleName();

    private final OkHttpClient client = new OkHttpClient();

    private static final String KEY_POSITION = "position";


    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<MeizhiOnly> mAllMeizhi;
    private MeizhiListTableRecyclerViewAdapter mMeizhiListTableRecyclerViewAdapter;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    private boolean isALlLoad = false;
    private int hasLoadPage = 0;
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
        Observable
                .zip(
                BaseActivity.gankApi.getMeizhiData(startPage),
                BaseActivity.daNiuApi.getEnjoyData(startPage),
                new Func2<GankResult, DaNiuResult, List<MeizhiOnly>>() {
                    @Override
                    public List<MeizhiOnly> call(GankResult gankResult, DaNiuResult daNiuResult) {
                        List<MeizhiOnly> meizhiOnlyList = new ArrayList<MeizhiOnly>();
                        for (Gank gank: gankResult.results) {
                            MeizhiOnly meizhiOnly = new MeizhiOnly();
                            meizhiOnly.desc = gank.desc;
                            meizhiOnly.url = gank.url;
                            meizhiOnly.publishedAt = gank.publishedAt;

                            meizhiOnlyList.add(meizhiOnly);
                        }

                        for (DaNiu daNiu: daNiuResult.results) {
                            MeizhiOnly meizhiOnly = new MeizhiOnly();
                            meizhiOnly.desc = daNiu.desc;
                            meizhiOnly.url = daNiu.url;
                            meizhiOnly.publishedAt = daNiu.publishedAt;

                            meizhiOnlyList.add(meizhiOnly);
                        }

                        return meizhiOnlyList;
                    }
                }
                )
//                .map(
//                        new Func1<List<MeizhiOnly>, List<MeizhiOnly>>() {
//                            @Override
//                            public List<MeizhiOnly> call(List<MeizhiOnly> meizhiOnlies) {
//
//                            }
//                        }
//                )
//                .flatMap(
//                        new Func1<List<MeizhiOnly>, Observable<MeizhiOnly>>() {
//                            @Override
//                            public Observable<MeizhiOnly> call(List<MeizhiOnly> meizhiOnlyList) {
//                                return Observable.from(meizhiOnlyList);
//                            }
//                        }
//                )
                .cache()
                .subscribeOn(Schedulers.computation())
                .doOnNext(
                        new Action1<List<MeizhiOnly>>() {
                            @Override
                            public void call(List<MeizhiOnly> meizhiOnlies) {
                                for (MeizhiOnly meizhiOnly : meizhiOnlies) {
                                    try {
                                        Point size = new Point();
                                        loadImageForSize(meizhiOnly.url, size);
                                        meizhiOnly.height = size.y;
                                        meizhiOnly.width = size.x;
                                        Log.d(TAG, "h = "+meizhiOnly.height +" w = " + meizhiOnly.width);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        Log.e(TAG, e.getMessage());
                                    }
                                }
                            }
                        }
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getMeizhiObserver);
    }

    private Observer<List<MeizhiOnly>> getMeizhiObserver = new Observer<List<MeizhiOnly>>() {
        @Override
        public void onNext(final List<MeizhiOnly> meizhiOnlyList) {
            if(mAllMeizhi.isEmpty() && meizhiOnlyList.isEmpty()){
                showNoDataView();
                return;
            }
            showContent();
            if(meizhiOnlyList.size() == GankApi.LOAD_LIMIT){
                hasLoadPage++;
            }else {
                isALlLoad = true;
            }

            mAllMeizhi.addAll(meizhiOnlyList);
            isLoadMore = false;
            mMeizhiListTableRecyclerViewAdapter.updateItems(mAllMeizhi, hasLoadPage == 1);
        }

        @Override
        public void onCompleted() {
            isLoadMore = false;
            mSwipeRefreshLayout.setRefreshing(false);
        }

        @Override
        public void onError(final Throwable error) {
            if (error instanceof RetrofitError) {
                Drawable errorDrawable = new IconDrawable(getContext(), Iconify.IconValue.zmdi_network_off)
                        .colorRes(android.R.color.white);
                RetrofitError e = (RetrofitError) error;
                if (e.getKind() == RetrofitError.Kind.NETWORK) {
                    showError(errorDrawable,"网络异常","好像您的网络出了点问题","重试",mErrorRetryListener);
                } else if (e.getKind() == RetrofitError.Kind.HTTP) {
                    showError(errorDrawable,"服务异常","好像服务器出了点问题","再试一次",mErrorRetryListener);
                } else {
                    showError(errorDrawable,"莫名异常","外星人进攻地球了？","反击",mErrorRetryListener);
                }
            }
        }
    };



    private void showNoDataView(){
        Drawable emptyDrawable = new IconDrawable(getContext(), Iconify.IconValue.zmdi_shopping_cart)
                .colorRes(android.R.color.white);
        List<Integer> skipIds = new ArrayList<>();
        showEmpty(emptyDrawable, "数据列表为空", "没有拿到数据哎，请等一下再来玩妹子吧", skipIds);
    }

    public void loadImageForSize(String url, Point measured) throws IOException {
        Response response = client.newCall(new Request.Builder().url(url).build()).execute();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(response.body().byteStream(), null, options);
        measured.x = options.outWidth;
        measured.y = options.outHeight;
    }


}
