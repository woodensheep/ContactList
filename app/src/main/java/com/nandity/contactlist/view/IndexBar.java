package com.nandity.contactlist.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nandity.contactlist.R;
import com.nandity.contactlist.utils.Utils;

public class IndexBar extends LinearLayout implements View.OnTouchListener {
    private static final String[] INDEXES = new String[]{"#", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private static final int TOUCHED_BACKGROUND_COLOR = 0x40000000;
    private OnIndexChangedListener mListener;

    public void setOnIndexChangedListener(OnIndexChangedListener listener) {
        mListener = listener;
    }

    public IndexBar(Context context) {
        this(context, null);
    }

    public IndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.IndexBar);
        float indexTextSize = ta.getDimension(R.styleable.IndexBar_indexTextSize, Utils.sp2px(getContext(), 12));
        int indexTextColor = ta.getColor(R.styleable.IndexBar_indexTextColor, 0xFF616161);
        ta.recycle();

        setOrientation(VERTICAL);
        setOnTouchListener(this);
        for (String index : INDEXES) {
            TextView text = new TextView(getContext());
            text.setText(index);
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX, indexTextSize);
            text.setTextColor(indexTextColor);
            text.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            text.setLayoutParams(params);
            addView(text);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(TOUCHED_BACKGROUND_COLOR);
                handle(v, event);
                return true;
            case MotionEvent.ACTION_MOVE:
                handle(v, event);
                return true;
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.TRANSPARENT);
                handle(v, event);
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void handle(View v, MotionEvent event) {
        int y = (int) event.getY();
        int height = v.getHeight();
        int position = INDEXES.length * y / height;
        if (position < 0) {
            position = 0;
        } else if (position >= INDEXES.length) {
            position = INDEXES.length - 1;
        }

        String index = INDEXES[position];
        boolean showIndicator = event.getAction() != MotionEvent.ACTION_UP;
        if (mListener != null) {
            mListener.onIndexChanged(index, showIndicator);
        }
    }

    public interface OnIndexChangedListener {
        void onIndexChanged(String index, boolean showIndicator);
    }
}