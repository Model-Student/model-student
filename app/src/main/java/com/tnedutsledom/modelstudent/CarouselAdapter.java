package com.tnedutsledom.modelstudent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tnedutsledom.modelstudent.carouselitem.FragmentHouseWork;
import com.tnedutsledom.modelstudent.carouselitem.FragmentChatLog;
import com.tnedutsledom.modelstudent.carouselitem.FragmentLastTime;

public class CarouselAdapter extends FragmentStateAdapter {
    public int mCount;

    public CarouselAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0) return new FragmentHouseWork();
        else if(position==1) return new FragmentChatLog();
        else  return new FragmentLastTime();
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
