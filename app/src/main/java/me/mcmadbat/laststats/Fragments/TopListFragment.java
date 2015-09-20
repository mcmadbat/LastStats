package me.mcmadbat.laststats.Fragments;

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
import android.widget.EditText;
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

    private boolean mSearchCheck;
    private static final String TEXT_FRAGMENT = "";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static TopListFragment newInstance(String text){
        TopListFragment mFragment = new TopListFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        String currentMode = (getArguments().getString(TEXT_FRAGMENT));

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<CardInfo> foo = new ArrayList<CardInfo>();

        List<String> answer;

        if (currentMode == "Artists"){
            answer = LfmApiHelper.getTopArtists("mcmadbat3",null,"20", null);
        } else if (currentMode == "Albums") {
            answer = LfmApiHelper.getTopAlbums("mcmadbat3",null,"20", null);
        } else {
            answer = LfmApiHelper.getTopTracks("mcmadbat3",null,"20", null);
        }

        for (int i = 0 ; i <answer.size();i++) {
            CardInfo temp = new CardInfo();

            String[] info = answer.get(i).split("~~");

            temp.count = info[1];
            temp.title = info[0];
            foo.add(temp);
        }

        mAdapter = new CardListAdaptor(foo);
        mRecyclerView.setAdapter(mAdapter);

        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));
        return rootView;
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

    private SearchView.OnQueryTextListener onQuerySearchView = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            if (mSearchCheck){
                // implement your search here
            }
            return false;
        }
    };
}
