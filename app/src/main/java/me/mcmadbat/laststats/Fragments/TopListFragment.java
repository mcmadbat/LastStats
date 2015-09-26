package me.mcmadbat.laststats.Fragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.mcmadbat.laststats.Adaptors.CardListAdaptor;
import me.mcmadbat.laststats.Helpers.CardInfo;
import me.mcmadbat.laststats.Helpers.LfmApiHelper;
import me.mcmadbat.laststats.R;

/**
 * Created by David on 2015-09-19.
 *
 * This fragment displays the top artist, album or tracks of a user
 */
public class TopListFragment extends Fragment {

    private static String _type = "";
    private static String _span = "";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static TopListFragment newInstance(String type, String span){
        TopListFragment mFragment = new TopListFragment();
        Bundle mBundle = new Bundle();

        _type = type;
        mBundle.putString("type", type);
        mBundle.putString("span", span);

        mFragment.setArguments(mBundle);

        return mFragment;
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

        for (int i = 0; i < 15 ; i ++) {
            CardInfo temp = new CardInfo();
            temp.count = getArguments().getString("span");
            temp.title = getArguments().getString("type");
            foo.add(temp);
        }

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
                mRecyclerView.setLayoutParams(new RelativeLayout.LayoutParams((x-150), ViewGroup.LayoutParams.MATCH_PARENT));
            }
        });

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
