package me.mcmadbat.laststats.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import me.mcmadbat.laststats.Adaptors.CardListAdaptor;
import me.mcmadbat.laststats.Helpers.CardInfo;
import me.mcmadbat.laststats.Helpers.DownloadTopList;
import me.mcmadbat.laststats.MainActivity;
import me.mcmadbat.laststats.R;

/**
 * Created by David on 2015-09-19.
 *
 * This fragment displays the top artist, album or tracks of a user
 */
public class TopListFragment extends Fragment implements MainActivity.DownloadListListener {

    private static String TYPE = "type";
    private static String SPAN = "span";
    private static String USER = "user";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static TopListFragment newInstance(String type, String span, String user){
        TopListFragment mFragment = new TopListFragment();
        Bundle mBundle = new Bundle();

        mBundle.putString(TYPE, type);
        mBundle.putString(SPAN, span);
        mBundle.putString(USER, user);

        mFragment.setArguments(mBundle);

        return mFragment;
    }

    @Override
    public void onResult(List<CardInfo> result){
        mAdapter = new CardListAdaptor(result);
        mRecyclerView.setAdapter(mAdapter);
        Log.w("INFO", "Updated recyclerview!");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<CardInfo> foo = new ArrayList<CardInfo>();

        //do not use this method for now
//        if (_t == "artist"){
//            data = LfmApiHelper.getTopArtists(_u,getArguments().getString(SPAN),"20",null);
//        } else if (_t == "album") {
//            data = LfmApiHelper.getTopAlbums(_u,getArguments().getString(SPAN),"20",null);
//        } else {
//            data = LfmApiHelper.getTopTracks(_u, getArguments().getString(SPAN), "20", null);
//        }
//
//        try {
//            for (int i = 0; i < data.size(); i ++) {
//                CardInfo temp = new CardInfo();
//                String[] dataArr = data.get(i).split("~~"); //split according to the wildcard ~~
//                temp.count = dataArr[0];
//                temp.title = getArguments().getString(TYPE);
//
//                foo.add(temp);
//            }
//        } catch (Exception e){
//            //hopefully never goes here
//            Log.wtf("INFO", "Trying to create Card List failed");
//            Log.wtf("INFO", e.getMessage());
//        }


        mAdapter = new CardListAdaptor(foo);

        mRecyclerView.setAdapter(mAdapter);

        ViewGroup.LayoutParams n = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final ViewTreeObserver observer= mRecyclerView.getViewTreeObserver();

        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int x = mRecyclerView.getHeight();
                //here we set the recyclerview to be smaller so that it fits the screen
                //TODO find a way to directly change the offset for different devices?
                mRecyclerView.setLayoutParams(new RelativeLayout.LayoutParams((x - 150), ViewGroup.LayoutParams.MATCH_PARENT));
            }
        });

        DownloadTopList dwnTask = new DownloadTopList(getActivity(),getArguments().getString(TYPE),getArguments().getString(SPAN));
        dwnTask.addListener(this);
        String _u = getArguments().getString(USER);
        dwnTask.execute(_u);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return true;
    }

}
