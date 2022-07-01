package com.tnedutsledom.modelstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class MainActivity extends FragmentActivity {


    private ViewPager2 vp_carousel; // 캐러셀 뷰페이저
    private CarouselAdapter pager_adapter; // 캐러셀 어뎁터
    private int carousel_size = 3; // 페이지(버튼) 개수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initCarousel();


    }

//    캐러셀 초기설정
    void initCarousel() {
        //캐러셀
        vp_carousel = findViewById(R.id.vp_main_carousel);

        //어뎁터 세팅
        pager_adapter = new CarouselAdapter(this, carousel_size);
        vp_carousel.setAdapter(pager_adapter);

        //뷰페이저 세팅
        vp_carousel.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        vp_carousel.setCurrentItem(2);
        vp_carousel.setOffscreenPageLimit(3);

//        전환 애니메이션 세팅
        final float page_margin = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        final float page_offset = getResources().getDimensionPixelOffset(R.dimen.offset);
        final float MIN_SCALE = 0.9f;   // 포커스 되지 않았을 떄의 크기
        final float MIN_ALPHA = 0.6f;   // 포커스 되지 않았을 떄의 투명도

        // 페이지 전환 세팅 메소드
        vp_carousel.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float my_offset = position * -(2 * page_offset + page_margin);
                float scale_factor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                page.setScaleX(scale_factor);
                page.setScaleY(scale_factor);

                page.setAlpha(MIN_ALPHA +
                        (scale_factor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

                if (vp_carousel.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(vp_carousel) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.setTranslationX(-my_offset);
                    } else {
                        page.setTranslationX(my_offset);
                    }
                } else {
                    page.setTranslationY(my_offset);
                }
            }
        });

        vp_carousel.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    vp_carousel.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

            }

        });
        vp_carousel.setCurrentItem(2);

    }

    void init() {

    }
}