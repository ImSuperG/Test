package com.rxy.test.views.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12345678 on 2015-09-17.
 */
public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /*java.lang.ClassCastException: android.view.ViewGroup$LayoutParams cannot be cast to android.view.ViewGroup$MarginLayoutParams*/

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        Log.e("mylog", "onMeasure.................................");

        //获得父容器的测量模式和宽高
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //wrap_content下记录宽和高
        int width = 0;
        int height = 0;

        //记录每一行的宽度，取最大值
        int lineWidth = 0;
        //记录每一行的高度，叠加到heiht中
        int lineHeight = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (lineWidth + childWidth > widthSize) {
                //此情况需要换行
                width = Math.max(lineWidth, childWidth);
                /**
                 * change line
                 * */
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                //不需要换行
                lineWidth += childWidth;
                height = Math.max(lineHeight, childHeight);
            }

            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize : width, (heightMode == MeasureSpec.EXACTLY) ? heightSize : height);
    }

    /**
     * 记录所有的view，按行记录
     */
    private List<List<View>> mAllViews = new ArrayList<>();
    /**
     * 记录每一行的最大高度
     */
    private List<Integer> mLineHeight = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        Log.e("mylog", "onLayout.................................");

        mAllViews.clear();
        mLineHeight.clear();

        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;

        /*记录每一行的所有view*/
        List<View> lineViews = new ArrayList<>();

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            /**
             * 如果需要换行
             * */
            int length = childWidth + lp.leftMargin + lp.rightMargin + lineWidth;
//            Log.d("mylog", "onLayout------------->length=" + length);
            if (length > width) {
                //记录这一行的高度
                mLineHeight.add(lineHeight);
                //保存当前行的view，开启新的一行记录
                mAllViews.add(lineViews);
                Log.d("mylog", "lineViews----------------->in loop--<" + lineViews.size());
                lineWidth = 0;
                lineViews = new ArrayList<>();
            }
            /**
             * 不需要换行的话则累加
             * */
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            lineViews.add(child);

        }
        //记录最后一行
        Log.d("mylog", "lineViews----------------->out loop--<" + lineViews.size());
        mAllViews.add(lineViews);
        mLineHeight.add(lineHeight);

        int left = 0;
        int top = 0;

        //得到总行数
        int lineCount = mAllViews.size();
        Log.e("mylog", "lineCount=" + lineCount);
        for (int i = 0; i < lineCount; i++) {
            lineViews = mAllViews.get(i);
            //当前行的最大高度
            lineHeight = mLineHeight.get(i);

//            Log.d("mylog", "第" + i + "行 ：" + lineViews.size() + " , " + lineViews);
//            Log.d("mylog", "第" + i + "行， ：" + lineHeight);

            //遍历当前行所有的view
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                TextView tv = (TextView) child;
//                Log.e("mylog","every line"+tv.getText().toString());
                if (child.getVisibility() == GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                //计算child的left top right bottom
                int lc = lp.leftMargin + left;
                int tc = lp.topMargin + top;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

//                Log.d("mylog", child + " , l = " + lc + " , t = " + t + " , r ="
//                        + rc + " , b = " + bc);
                child.layout(lc, tc, rc, bc);
                left += child.getMeasuredWidth()+lp.rightMargin+lp.rightMargin;
            }
            left = 0;
            top += lineHeight;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("mylog", "onDraw.................................");
    }
}
