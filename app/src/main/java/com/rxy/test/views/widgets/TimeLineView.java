package com.rxy.test.views.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.rxy.test.R;

/**
 * Created by 12345678 on 2015-09-17.
 */
public class TimeLineView extends View {

    private Drawable headerLine;
    private Drawable endLine;
    private Drawable middleLine;
    private int viewSize;
    private int lineSize;

    public TimeLineView(Context context) {
        this(context, null);
    }

    public TimeLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.TimeLineView, 0, 0);
        int size = ta.getIndexCount();
        for (int i = 0; i < size; i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.TimeLineView_begin_line:
                    headerLine = ta.getDrawable(attr);
                    break;
                case R.styleable.TimeLineView_end_line:
                    endLine = ta.getDrawable(attr);
                    break;
                case R.styleable.TimeLineView_middle_line:
                    middleLine = ta.getDrawable(attr);
                    break;
                case R.styleable.TimeLineView_size_line:
                    lineSize = ta.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10
                            , getResources().getDisplayMetrics()));
                    break;
                case R.styleable.TimeLineView_view_size:
                    viewSize = ta.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20
                            , getResources().getDisplayMetrics()));
                    break;
            }
        }
        ta.recycle();
        if (headerLine != null)
            headerLine.setCallback(this);
        if (endLine != null)
            endLine.setCallback(this);
        if (middleLine != null)
            middleLine.setCallback(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initDrawableSize();
    }

    private void initDrawableSize() {

    }

    @Override
    protected void onDraw(Canvas canvas) {

    }
}
