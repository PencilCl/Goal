package cn.goal.goal.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * Created by 97617 on 2017/2/25.
 */

public class GoalCommentListView extends ListView {
    //为了适应Scrollview而重写的listview的类
    public GoalCommentListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public GoalCommentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public GoalCommentListView(Context context) {
        super(context);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}