package com.tnedutsledom.modelstudent.main_activity_fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.tnedutsledom.modelstudent.R;
import com.tnedutsledom.modelstudent.ThemeColorAdaptor;
import com.tnedutsledom.modelstudent.carouselitem.CarouselAdapter;

import java.util.Random;

public class FragmentMain extends Fragment {

    private ViewPager2 vp_carousel; // 캐러셀 뷰페이저
    private CarouselAdapter pager_adapter; // 캐러셀 어뎁터
    private int carousel_size = 3; // 페이지(버튼) 개수
    ThemeColorAdaptor colorAdaptor;
    TextView tvSubTitle;
    ConstraintLayout cl_today_sentence;
    ImageView iv_sentence_moon, iv_sentence_back;
    ShimmerTextView tv_sentence_title, tv_sentence_said;
    View v, v_back;
    SharedPreferences preferences;
    int sentenceCategory;
    boolean showedSentence = false;
    Animation anim_text_get_back, anim_none, anim_background_get_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main, container, false);
        init();
        initCarousel();
        setThemeColor();
        setTodaySentence();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setThemeColor();
    }

    void showTodaySentence() {
        cl_today_sentence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                tv_sentence_title.setPadding(0, 0, 0, 0);
                startSentenceAnim();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeSentenceValue();
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    }
                }, 700);
            }
        });
    }

    void changeSentenceValue() {
        iv_sentence_back.setImageResource(R.drawable.sentence_back_clicked);

        String[] array, arraySaid;
        Random random = new Random();
        int tmp = random.nextInt(20);
        switch (sentenceCategory) {
            case 0:
                array = getActivity().getResources().getStringArray(R.array.sentence_list_1);
                tv_sentence_said.setVisibility(View.INVISIBLE);
                tv_sentence_title.setText("\"" +array[tmp] + "\"");
                break;
            case 1:
                array = getSentenceList(true);
                arraySaid = getSentenceSaidList(true);
                setTv_sentence_title(array[tmp]);
                setTv_sentence_said(arraySaid[tmp]);
                break;
            default:
                array = getSentenceList(false);
                arraySaid = getSentenceSaidList(false);
                setTv_sentence_title(array[tmp]);
                setTv_sentence_said(arraySaid[tmp]);
                break;
        }
    }

    public void startSentenceAnim() {
        tv_sentence_title.startAnimation(anim_text_get_back);
        if (sentenceCategory != 0)
            tv_sentence_said.startAnimation(anim_text_get_back);
        if (!showedSentence) {
            iv_sentence_back.startAnimation(anim_background_get_back);
            showedSentence = true;
        }
    }

    public String[] getSentenceList(boolean a) {
        if (a) {
            return getResources().getStringArray(R.array.sentence_list_2);
        } else {
            return getResources().getStringArray(R.array.sentence_list_3);
        }
    }

    public String[] getSentenceSaidList(boolean a) {
        if (a) {
            return getResources().getStringArray(R.array.sentence_list_2_said);
        } else {
            return getResources().getStringArray(R.array.sentence_list_3_said);
        }
    }

    void setTv_sentence_title(String a) {
        tv_sentence_title.setText(a);
    }

    void setTv_sentence_said(String a) {
        tv_sentence_said.setVisibility(View.VISIBLE);
        tv_sentence_said.setText("- " + a + " -");
        tv_sentence_said.setTextSize(Dimension.DP, tv_sentence_title.getTextSize()-10);
    }


    void setTodaySentenceTitle() {
        switch (sentenceCategory) {
            case 0:
                tv_sentence_title.setText("오늘은 아이에게\n이런 얘기 어떠세요?");
                break;
            case 1:
                tv_sentence_title.setText("지치고 힘드실 떄\n한번 읽어보실래요?");
                break;
            default:
                tv_sentence_title.setText("부모 역할에 관한\n명언 한번 보실래요?");
                break;
        }
    }

    void setTodaySentence() {
        setShimmerTv();
        setTodaySentenceTitle();
        showTodaySentence();
    }

    void setThemeColor() {
        colorAdaptor.setViewColorTheme(tvSubTitle);
        colorAdaptor.setViewColorTheme(v_back);
        colorAdaptor.setSentenceMoon(iv_sentence_moon);
        colorAdaptor.setViewColorText(iv_sentence_back);
    }

    //    캐러셀 초기설정
    void initCarousel() {
        //캐러셀
        vp_carousel = v.findViewById(R.id.vp_main_carousel);

        //어뎁터 세팅
        pager_adapter = new CarouselAdapter(getActivity(), carousel_size);
        vp_carousel.setAdapter(pager_adapter);

        //뷰페이저 세팅
        vp_carousel.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        vp_carousel.setOffscreenPageLimit(3);
        vp_carousel.postDelayed(new Runnable() {
            @Override
            public void run() {
                vp_carousel.setCurrentItem(1);

            }
        }, 10);

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
    }

    void setShimmerTv() {
        Shimmer shimmer = new Shimmer();
        shimmer.setDuration(2500).setStartDelay(300).start(tv_sentence_title);
    }

    void init() {
        colorAdaptor = ThemeColorAdaptor.getInstance(getActivity().getApplicationContext());
        tvSubTitle = v.findViewById(R.id.tv_main_sub_title);
        v_back = v.findViewById(R.id.v_main_back);
        tv_sentence_title = v.findViewById(R.id.tv_sentence_title);
        tv_sentence_said = v.findViewById(R.id.tv_sentence_said);
        iv_sentence_moon = v.findViewById(R.id.iv_sentence_moon);
        iv_sentence_back = v.findViewById(R.id.iv_sentence_back);
        cl_today_sentence = v.findViewById(R.id.cl_btn_today_sentence);
        preferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        sentenceCategory = preferences.getInt("sentence_category", 0);
        SharedPreferences.Editor editor = preferences.edit();
        if (sentenceCategory == 2) {
            editor.putInt("sentence_category", 0);
        } else {
            editor.putInt("sentence_category", sentenceCategory + 1);
        }
        editor.commit();
        anim_text_get_back = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.text_get_back);
        anim_none = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.none_anim);
        anim_background_get_back = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.background_get_back);
    }
}
