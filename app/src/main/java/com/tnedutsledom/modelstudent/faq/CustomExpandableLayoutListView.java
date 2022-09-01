package com.tnedutsledom.modelstudent.faq;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.andexert.expandablelayout.library.ExpandableLayoutItem;
import com.andexert.expandablelayout.library.ExpandableLayoutListView;
import com.tnedutsledom.modelstudent.R;

public class CustomExpandableLayoutListView extends ExpandableLayoutListView {
    private Integer position = -1;
    String[] array_answer;
    public CustomExpandableLayoutListView(Context context) {
        super(context);
    }

    public CustomExpandableLayoutListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomExpandableLayoutListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean performItemClick(View view, int position, long id) {
        this.position = position;

        for (int index = 0; index < getChildCount(); ++index)
        {
            if (index != (position - getFirstVisiblePosition()))
            {
                ExpandableLayoutItem currentExpandableLayout = (ExpandableLayoutItem) getChildAt(index).findViewWithTag(ExpandableLayoutItem.class.getName());
                currentExpandableLayout.hide();
            }
        }

        ExpandableLayoutItem expandableLayout = (ExpandableLayoutItem) getChildAt(position - getFirstVisiblePosition()).findViewWithTag(ExpandableLayoutItem.class.getName());
        TextView tv_answer = expandableLayout.findViewById(R.id.tv_faq_answer);
        tv_answer.setText(array_answer[position]);
        if (expandableLayout.isOpened())
            expandableLayout.hide();
        else
            expandableLayout.show();


        return super.performItemClick(view, position, id);
    }

    public void setArray_answer(String[] array_answer) {
        this.array_answer = array_answer;
    }
}
