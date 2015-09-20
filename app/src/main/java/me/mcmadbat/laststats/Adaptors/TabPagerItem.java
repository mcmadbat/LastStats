package me.mcmadbat.laststats.Adaptors;

import android.support.v4.app.Fragment;

/**
 * Created by David on 2015-09-19.
 * The individual tab item on the lil toolbar thingy
 */
public class TabPagerItem {

    private final CharSequence mTitle;
    private final Fragment mFragment;

    public TabPagerItem(CharSequence title, Fragment fragment) {
        this.mTitle = title;
        this.mFragment = fragment;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public CharSequence getTitle() {
        return mTitle;
    }
}