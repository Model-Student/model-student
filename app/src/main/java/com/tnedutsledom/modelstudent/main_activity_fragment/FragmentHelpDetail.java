package com.tnedutsledom.modelstudent.main_activity_fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.tnedutsledom.modelstudent.MainActivity;
import com.tnedutsledom.modelstudent.R;
import com.tnedutsledom.modelstudent.ThemeColorAdaptor;

public class FragmentHelpDetail extends Fragment {

    View v;
    ImageView iv_back;
    ImageView iv_ui;
    TextView tv_detail;
    int category;
    ThemeColorAdaptor colorAdaptor;
    ConstraintLayout cl_back;
    String[] detail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_help_detail, container, false);
        init();
        setiv_back();
        setDataByCategory();
        setThemeColor();

        return v;
    }

    public static FragmentHelpDetail newInstance() {
        return new FragmentHelpDetail();
    }

    void setDataByCategory() {
        tv_detail.setText(detail[category]);
        switch (category) {
            case 0:
                iv_ui.setImageResource(R.drawable.help_detail_hw);
                break;
            case 1:
                iv_ui.setImageResource(R.drawable.help_detail_chat_log);
                break;
            case 2:
                iv_ui.setImageResource(R.drawable.help_detail_last_time);
                break;
            case 3:
                iv_ui.setImageResource(R.drawable.help_detail_setting);
                break;
        }
    }

    void setiv_back() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).replaceFragment(FragmentHelp.newInstance());
            }
        });
    }

    void setThemeColor() {
        colorAdaptor.setViewColorBack(cl_back);
        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.help_btn_shape);
        colorAdaptor.setViewColorText(drawable, iv_back);
    }

    void init() {
        iv_back = v.findViewById(R.id.btn_help_detail_back);
        iv_ui = v.findViewById(R.id.iv_help_detail_ui);
        category = FragmentHelp.categoryHelp;
        cl_back = v.findViewById(R.id.cl_help_detail_back);
        tv_detail = v.findViewById(R.id.tv_help_detail);
        colorAdaptor = ThemeColorAdaptor.getInstance(getActivity().getApplicationContext());
        detail = new String[]{
                "\n아이가 할일을 앱에서 등록해두면 아이가 궁금할때 nugu가 알려줍니다.\n\n" +
                        "모델스튜던트 시작 = \"원숭아 시작\" / \"원숭아 오픈\"\n\n" +
                        "하우스워크 = \"나 오늘 뭐 해야해?\" / \"원숭아 나 오늘 뭐해야해?\"" +
                        "\nex)\n아이 : 원숭아 나 오늘 뭐해야해?\nnugu : 오늘 할일은 OO,OO,OO이 있어 무엇을 할래?\n" +
                        "아이 : OO 할래\nnugu : 알겠어 다 하고 나를 다시 불러줘",

                "\n아이가 nugu와 대화하며 스스로 무엇을 할지 정하고 실천합니다.\n\n" +
                        "모델스튜던트 시작 = \"원숭아 시작\" / \"원숭아 오픈\"\n\n" +
                        "\nex1)\n아이 : 원숭아 안녕\n" +
                        "누구 : OO아 뭐할거야\n" +
                        "아이 : 15분동안 유튜브 볼거야\n" +
                        "누구 : 알겠어 \n" +
                        "누구 : (15분 뒤) 얘기한 시간이 다 됬어" +
                        "\n\nex2)\n" +
                        "아이 : 원숭아 안녕\n" +
                        "누구 : OO아 뭐할거야\n" +
                        "아이 : 유튜브 볼거야\n" +
                        "누구 : 얼마나 할거야?\n" +
                        "아이 : 15분 볼거야\n" +
                        "누구 : 알겠어\n" +
                        "누구 : (15분 뒤) 얘기한 시간이 다 됬어" +
                        "\n\nex3)\n" +
                        "아이 : 원숭아 나 15분동안 유튜브 볼거야 \n" +
                        "누구 : 알겠어\n" +
                        "누구 : (15분 뒤) 얘기한 시간이 다 됬어",

                "\n아이가 그날을 어떻게 느꼈는지 nugu에게 말하고 앱에서 날짜별로 확인 할 수 있습니다." +
                        "모델스튜던트 시작 = \"원숭아 시작\" / \"원숭아 오픈\"\n\n" +
                        "라스트타임 = \"라스트타임\" / \"원숭아 라스트타임\"\n\n" +
                        "아이 : 원숭아 라스트타임\n" +
                        "누구 : OO아 오늘 하루는 어땠어?\n" +
                        "아이 : 재밌었어\n" +
                        "누구 : 그렇구나 좋았겠다. 내일도 모레도 계속 좋은일만 있으면 좋겠다",
                "\n테마색 변경 : 앱의 테마색을 변경합니다.\n\n" +
                        "아이 이름 재설정 : 아이 이름을 수정 할 수 있습니다.\n\n" +
                        "NUGU 목소리 변경 : nugu의 목소리를 변경 할 수 있습니다.\n\n" +
                        "자주 묻는 질문 : 사용자분들이 궁금해 할만한 질문과 답변입니다.\n\n" +
                        "계정 탈퇴 : model student 계정을 삭제합니다."
        };
    }
}
