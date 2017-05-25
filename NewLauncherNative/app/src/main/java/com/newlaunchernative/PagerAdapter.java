package com.newlaunchernative;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by leo on 06/05/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                NewApp newApp = new NewApp();
                return newApp;
            case 1:
                FreeApp freeApp = new FreeApp();
                return freeApp;
            case 2:
                UpdateApp tabFragmentGame = new UpdateApp();
                return tabFragmentGame;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
