package com.tnedutsledom.modelstudent.carouselitem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CarouselAdapter extends FragmentStateAdapter {
    public int mCount;

    public CarouselAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0) return new FragmentHouseWorkBanner();
        else if(position==1) return new FragmentChatLogBanner();
        else  return new FragmentLastTimeBanner();
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
