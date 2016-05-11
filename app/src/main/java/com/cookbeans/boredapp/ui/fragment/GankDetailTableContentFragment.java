package com.cookbeans.boredapp.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.cookbeans.boredapp.R;
import com.cookbeans.boredapp.data.gank.entity.Gank;
import com.cookbeans.boredapp.data.gank.net.GankDayResult;
import com.cookbeans.boredapp.ui.BaseActivity;
import com.cookbeans.boredapp.ui.adapter.GankDetailTableRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GankDetailTableContentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GankDetailTableContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * 干货详情页面
 *
 */
public class GankDetailTableContentFragment extends Fragment {
    private static final String TAG = GankDetailTableContentFragment.class.getSimpleName();

    private static final String ARG_YEAR = "year";
    private static final String ARG_MONTH = "month";
    private static final String ARG_DAY = "day";

    private int year;
    private int month;
    private int day;

    private List<Gank> mGankList;

    private RecyclerView mRecyclerView;
    private GankDetailTableRecyclerViewAdapter mRecyclerAdapter;

    private ViewStub mEmptyViewStub;

    private Subscription mSubscription;

    private OnFragmentInteractionListener mListener;

    public GankDetailTableContentFragment() {
        // Required empty public constructor
    }

    public static GankDetailTableContentFragment newInstance(int year, int month, int day) {
        GankDetailTableContentFragment fragment = new GankDetailTableContentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_YEAR, year);
        args.putInt(ARG_MONTH, month);
        args.putInt(ARG_DAY, day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        mGankList = new ArrayList<>();
        mRecyclerAdapter = new GankDetailTableRecyclerViewAdapter(mGankList);
        Bundle argBundle = getArguments();
        year = argBundle.getInt(ARG_YEAR);
        month = argBundle.getInt(ARG_MONTH);
        day = argBundle.getInt(ARG_DAY);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gank_detail_content, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEmptyViewStub = (ViewStub) view.findViewById(R.id.stub_empty_view);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_gank_detail);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        if (0 == mGankList.size()) {
            loadData();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            // throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
            mListener = null;
        }
    }

    private void loadData() {
        mSubscription = BaseActivity.gankApi.getGankDayData(year, month, day)
                .map(
                        new Func1<GankDayResult, GankDayResult.Result>() {
                            @Override
                            public GankDayResult.Result call(GankDayResult gankDayResult) {
                                return gankDayResult.results;
                            }
                        }
                )
                .map(
                        new Func1<GankDayResult.Result, List<Gank>>() {
                            @Override
                            public List<Gank> call(GankDayResult.Result result) {
                                if (null != result.androidList) {
                                    mGankList.addAll(result.androidList);
                                }
                                if (null != result.iOSList) {
                                    mGankList.addAll(result.iOSList);
                                }
                                if (null != result.appList) {
                                    mGankList.addAll(result.appList);
                                }
                                if (null != result.expandList) {
                                    mGankList.addAll(result.expandList);
                                }
                                if (null != result.recommendList) {
                                    mGankList.addAll(result.recommendList);
                                }
                                return mGankList;
                            }
                        }
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<Gank>>() {
                            @Override
                            public void call(List<Gank> ganks) {
                                if (0 == ganks.size()) {
                                    showNoDataView();
                                } else {
                                    mRecyclerAdapter.notifyDataSetChanged();
                                }
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        }
                );
    }

    private void showNoDataView() {
        mEmptyViewStub.inflate();
    }



    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (null != mSubscription) {
            mSubscription.unsubscribe();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
