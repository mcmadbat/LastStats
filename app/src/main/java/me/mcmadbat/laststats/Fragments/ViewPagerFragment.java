package me.mcmadbat.laststats.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

import me.mcmadbat.laststats.Adaptors.TabPagerItem;
import me.mcmadbat.laststats.Adaptors.ViewPagerAdapter;
import me.mcmadbat.laststats.R;

/**
 * Created by David on 2015-09-19.
 *
 * This fragment is the lil toolbar on top that selects between artists, tracks and albums
 */
public class ViewPagerFragment extends Fragment {
    private List<TabPagerItem> mTabs = new ArrayList<>();

    public String _type; //artist album etc...

    private int offset = 0;

    public static ViewPagerFragment newInstance(String type){
        ViewPagerFragment m = new ViewPagerFragment();
        m._type = type;
        return m;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createTabPagerItem();
    }


    private void createTabPagerItem(){
        mTabs.add(new TabPagerItem("Overall", TopListFragment.newInstance(_type, "overall")));
        mTabs.add(new TabPagerItem("Year", TopListFragment.newInstance(_type, "12month" )));
        mTabs.add(new TabPagerItem("Month", TopListFragment.newInstance(_type , "1month")));
        mTabs.add(new TabPagerItem("Week", TopListFragment.newInstance(_type , "7day")));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_viewpager, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

        mViewPager.setOffscreenPageLimit(mTabs.size());
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), mTabs));
        final TabLayout mSlidingTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);


//        final ViewTreeObserver observer= mSlidingTabLayout.getViewTreeObserver();
//        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                offset = mSlidingTabLayout.getHeight();
//                observer.removeGlobalOnLayoutListener(this);
//            }
//        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSlidingTabLayout.setElevation(15);
        }
        mSlidingTabLayout.setupWithViewPager(mViewPager);
    }
}