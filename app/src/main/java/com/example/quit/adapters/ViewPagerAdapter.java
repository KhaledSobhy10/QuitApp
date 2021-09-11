package com.example.quit.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quit.ui.fragments.SelectDateFragment;
import com.example.quit.ui.fragments.SelectTypeFragment;
import com.example.quit.ui.fragments.SetNameAndIconFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private final int NUM_FRAGMENT = 3;
    private final SetNameAndIconFragment setNameAndIconFragment = new SetNameAndIconFragment();
    private final SelectTypeFragment selectTypeFragment = new SelectTypeFragment();
    private final SelectDateFragment selectDateFragment = SelectDateFragment.newInstance(setNameAndIconFragment);

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return setNameAndIconFragment;
            case 1:
                return selectTypeFragment;
            default:
                return selectDateFragment;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_FRAGMENT;
    }


}
