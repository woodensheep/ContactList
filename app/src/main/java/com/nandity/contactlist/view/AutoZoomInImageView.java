package com.nandity.contactlist.view;

/**
 * Created by lemon on 2016/12/13.
 */

import android.annotation.SuppressLint;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

@SuppressLint("NewApi")
public class AutoZoomInImageView extends ImageView {

    private Drawable mDrawable;
    private int mDrawableW;
    private int mDrawableH;

    private int mImageViewW;
    private int mImageViewH;

    private Matrix mMatrix;
    private float[] mValues = new float[9];

    private float mScaleDelta = 0.2f;
    private long mDurationMillis = 700;

    public AutoZoomInImageView(Context context) {
        super(context);
        this.setScaleType(ScaleType.MATRIX);
    }

    public AutoZoomInImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setScaleType(ScaleType.MATRIX);
    }

    public AutoZoomInImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setScaleType(ScaleType.MATRIX);
    }

    public AutoZoomInImageView init() {
        initInternalValues();
        initPicturePosition();
        return this;
    }

    public void init(Drawable drawable) {
        initInternalValues(drawable);
        initPicturePosition();
    }

    private void initInternalValues() {
        mDrawable = getDrawable();

        if (mDrawable == null) {
            throw new IllegalArgumentException("please set the source of AutoZoomInImageView");
        }

        mDrawableW = mDrawable.getIntrinsicWidth();
        mDrawableH = mDrawable.getIntrinsicHeight();

        mImageViewW = getMeasuredWidth();
        mImageViewH = getMeasuredHeight();

        mMatrix = getImageMatrix();
        mMatrix.getValues(mValues);
    }

    private void initInternalValues(Drawable drawable) {
        mDrawable = drawable;

        if (mDrawable == null) {
            throw new IllegalArgumentException("please set the source of AutoZoomInImageView");
        }

        mDrawableW = mDrawable.getIntrinsicWidth();
        mDrawableH = mDrawable.getIntrinsicHeight();

        mImageViewW = getMeasuredWidth();
        mImageViewH = getMeasuredHeight();

        mMatrix = getImageMatrix();
        mMatrix.getValues(mValues);
    }

    private void initPicturePosition() {
        updateMatrixValuesOrigin(mMatrix, mValues, mDrawableW, mDrawableH, mImageViewW, mImageViewH);
        setImageMatrix(mMatrix);
    }

    private void startZoomInByScaleDelta(final float scaleDelta, long duration) {

        final float oriScaleX = mValues[0];
        final float oriScaleY = mValues[4];

        ValueAnimator va = ValueAnimator.ofFloat(0, scaleDelta);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                if (mOnZoomListener != null)
                    mOnZoomListener.onUpdate(AutoZoomInImageView.this, value / scaleDelta);
                updateMatrixValuesSpan(mValues, mDrawableW, mDrawableH, mImageViewW, mImageViewH,
                        oriScaleX, oriScaleY, value);
                mMatrix.setValues(mValues);
                setImageMatrix(mMatrix);
            }
        });
        va.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (mOnZoomListener != null) mOnZoomListener.onStart(AutoZoomInImageView.this);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mOnZoomListener != null) mOnZoomListener.onEnd(AutoZoomInImageView.this);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        va.setDuration(duration);
        va.start();
    }

    /**
     * 开始放大动画
     * start zooming in
     *
     * @param scaleDelta     放大的增大倍数，如果是0.2，那么最后大小放大至1.2倍。
     *                       the scale that the image will add to original scale
     * @param durationMillis 放大效果的持续时间，单位毫秒。
     *                       the duration of zoomin animation, in millisecond.
     * @param delayMillis    开始放大效果的延迟时间，单位毫秒。delayed毫秒后开始放大动画效果。
     *                       the delayed time of starting zoomin animation, in millisecond.
     */
    public void startZoomInByScaleDeltaAndDuration(final float scaleDelta, final long durationMillis, long delayMillis) {
        if (scaleDelta < 0) {
            throw new IllegalArgumentException("scaleDelta should be larger than 0, now scaleDelta is " + scaleDelta);
        }
        if (durationMillis < 0) {
            throw new IllegalArgumentException("durationMillis should not be less than 0, now durationMillis is " + durationMillis);
        }
        if (delayMillis < 0) {
            throw new IllegalArgumentException("delayMillis should not be less than 0, now delayMillis is " + delayMillis);
        }

        postDelayed(new Runnable() {
            @Override
            public void run() {
                startZoomInByScaleDelta(scaleDelta, durationMillis);
            }
        }, delayMillis);
    }

    /**
     * 放大的增大倍数，如果是0.2，那么最后大小放大至1.2倍。
     * the scale that the image will add to original scale
     *
     * @param scaleDelta
     * @return
     */
    public AutoZoomInImageView setScaleDelta(float scaleDelta) {
        mScaleDelta = scaleDelta;
        return this;
    }

    /**
     * 放大效果的持续时间，单位毫秒。
     * the duration of zoomin animation, in millisecond.
     *
     * @param durationMillis
     * @return
     */
    public AutoZoomInImageView setDurationMillis(long durationMillis) {
        mDurationMillis = durationMillis;
        return this;
    }

    /**
     * 动画结束的回调
     * callback when zoomin animation finished
     *
     * @param onZoomListener
     * @return
     */
    public AutoZoomInImageView setOnZoomListener(OnZoomListener onZoomListener) {
        mOnZoomListener = onZoomListener;
        return this;
    }

    /**
     * 开始放大效果
     * start animation of zoomin
     *
     * @param delayMillis 开始放大效果的延迟时间，单位毫秒。delayed毫秒后开始放大动画效果
     *                    the delayed time of starting zoomin animation, in millisecond.
     */
    public void start(long delayMillis) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                startZoomInByScaleDelta(mScaleDelta, mDurationMillis);
            }
        }, delayMillis);
    }

    private void updateMatrixValuesOrigin(Matrix outMatrix, float[] outValues, float drawW, float drawH, float imageW, float imageH) {

        if (outMatrix == null || outValues == null) {
            throw new IllegalArgumentException("please set the source of AutoZoomInImageView's matrix and values");
        }

        outMatrix.reset();

        if ((imageH * drawW > drawH * imageW)) {
            float scale1 = (imageH) / (drawH);
            float offset1 = (drawW * scale1 - imageW) / 2;

            outMatrix.postScale(scale1, scale1);
            outMatrix.postTranslate(-offset1, 0);

        } else {
            float scale2 = (imageW) / (drawW);
            float offset2 = (drawH * scale2 - imageH) / 2;

            outMatrix.postScale(scale2, scale2);
            outMatrix.postTranslate(0, -offset2);
        }
        outMatrix.getValues(outValues);
    }

    private void updateMatrixValuesSpan(float[] outValues,
                                        float drawW, float drawH,
                                        float imageW, float imageH,
                                        float oriScaleX, float oriScaleY,
                                        float scaleDelta) {
        //根据四个参数：图片的宽高、控件的宽高，动态的计算出输出的矩阵（float数组）的值
        outValues[0] = oriScaleX * (1 + scaleDelta);
        outValues[4] = oriScaleY * (1 + scaleDelta);
        float offsetwidth = (drawW * outValues[0] - imageW) / 2;
        outValues[2] = -offsetwidth;
        float offsetHeight = (drawH * outValues[0] - imageH) / 2;
        outValues[5] = -offsetHeight;
    }

    private OnZoomListener mOnZoomListener;

    public interface OnZoomListener {
        /**
         * 动画更新时执行的回调
         *
         * @param view     返回此AutoZoomInImageView
         * @param progress 返回动画进行过程，范围是[0,1]
         */
        void onUpdate(View view, float progress);

        void onEnd(View view);

        void onStart(View view);
    }


    //function for log
    public String printMyMatrix(Matrix m) {
        float[] valueFloat = new float[9];
        m.getValues(valueFloat);

        String s = "";
        for (int i = 0; i < 9; i++) {
            s = s + " [ " + valueFloat[i] + " ] ";
        }
        return s;
    }

    //function for log
    public String printMyValue(float[] valueFloat) {
        String s = "";
        for (int i = 0; i < 9; i++) {
            s = s + " [ " + valueFloat[i] + " ] ";
        }
        return s;
    }

}
