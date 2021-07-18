package dev.mobile.project.navigations;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import dev.mobile.project.fragments.ProfileFragment;
import dev.mobile.project.fragments.SubjectFragment;
import dev.mobile.project.fragments.TaskFragment;

public class NavigationAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    Activity activity;
    Fragment fragmentCommon;

    public NavigationAdapter(FragmentManager fm, Context context, Activity activity, int totalTabs) {
        super(fm);
        this.context = context;
        this.activity = activity;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragmentCommon = new SubjectFragment();
                return fragmentCommon;
            case 1:
                fragmentCommon = new TaskFragment();
                return fragmentCommon;
            case 2:
                fragmentCommon = new ProfileFragment();
                return fragmentCommon;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
