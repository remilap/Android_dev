package com.remilapointe.minipaint.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class BrushSizeDialog extends Dialog {
    public interface OnBrushSizeChangedListener {
        void brushSizeChanged(int size);
    }

    private OnBrushSizeChangedListener mListener;
    private int mInitialSize;

    private static class BrushSizeView extends View {
        private Paint mPaint;
        private Paint mCenterPaint;
        private final int[] mSizes = new int[] {2, 5, 10, 20, 30};
        private int[] posLeft = new int[mSizes.length];
        private int[] posTop = new int[mSizes.length];
        private int[] posRight = new int[mSizes.length];
        private int[] posBottom = new int[mSizes.length];
        private OnBrushSizeChangedListener mListener;

        private static final int CENTER_X = 250;
        private static final int CENTER_Y = 150;
        private int viewWidth;
        private int viewHeight;

        BrushSizeView(Context c, OnBrushSizeChangedListener l, int color) {
            super(c);
            mListener = l;
//            mSizes = new int[] {2, 5, 10, 20, 30};
//            Shader s = new SweepGradient(0, 0, mSizes, null);

            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//            mPaint.setShader(s);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(5);
            mPaint.setTextAlign(Paint.Align.CENTER);

            mCenterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mCenterPaint.setColor(color);
            mCenterPaint.setStrokeWidth(5);
            mCenterPaint.setStyle(Paint.Style.STROKE);
        }

        private int mTrackingSize;
        private int mHighlightSize;
        private float coef = 2.0f;
        private long maxElemWidth = 5;
        private long spaceBetweenElem = 0;

        @Override
        protected void onDraw(Canvas canvas) {
            mPaint.setStyle(Paint.Style.FILL);
            for (int n = 0; n < mSizes.length; n++) {
                canvas.drawRect(posLeft[n], posTop[n], posRight[n], posBottom[n], mPaint);
                canvas.drawText(String.valueOf(mSizes[n]), posLeft[n]+maxElemWidth/2, posBottom[n]+5, mPaint);
            }
            if (mTrackingSize >= 0) {
                mCenterPaint.setStyle(Paint.Style.STROKE);

                if (mHighlightSize >= 0) {
                    mCenterPaint.setAlpha(0xFF);
                } else {
                    mCenterPaint.setAlpha(0x80);
                }
                mCenterPaint.setColor(Color.red(1));
//                x = (int) (spaceBetweenElem + (spaceBetweenElem + maxElemWidth) * mTrackingSize);
                int larg = (int) maxElemWidth/2 + 3;
                canvas.drawRect(posLeft[mTrackingSize]-3, posTop[mTrackingSize]-3,posRight[mTrackingSize]+3, posBottom[mTrackingSize]+3, mCenterPaint);

//                mCenterPaint.setStyle(Paint.Style.FILL);
//                mCenterPaint.setColor(c);
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int desiredWidth = CENTER_X*2;
            int desiredHeight = CENTER_Y*2;

            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);

            //Measure Width
            if (widthMode == MeasureSpec.EXACTLY) {
                //Must be this size
                viewWidth = widthSize;
            } else if (widthMode == MeasureSpec.AT_MOST) {
                //Can't be bigger than...
                viewWidth = Math.min(desiredWidth, widthSize);
            } else {
                //Be whatever you want
                viewWidth = desiredWidth;
            }

            //Measure Height
            if (heightMode == MeasureSpec.EXACTLY) {
                //Must be this size
                viewHeight = heightSize;
            } else if (heightMode == MeasureSpec.AT_MOST) {
                //Can't be bigger than...
                viewHeight = Math.min(desiredHeight, heightSize);
            } else {
                //Be whatever you want
                viewHeight = desiredHeight;
            }

            //MUST CALL THIS
            setMeasuredDimension(viewWidth, viewHeight);

            long availWidth = -1;
            while (availWidth < 0) {
                coef /= 1.5;
                maxElemWidth = Math.round((float)mSizes[mSizes.length - 1] * coef);
                availWidth = viewWidth - maxElemWidth * mSizes.length;
            }
            spaceBetweenElem = Math.round(availWidth / (mSizes.length+1));
            int x = (int) spaceBetweenElem;
            for (int n = 0; n < mSizes.length; n++) {
                posLeft[n] = (int) (x + maxElemWidth - Math.round(mSizes[n] * coef/2));
                posTop[n] = 5;
                posRight[n] = (int) (x + maxElemWidth + Math.round(mSizes[n] * coef/2));
                posBottom[n] = (int) Math.round(viewHeight*0.8);
                x += maxElemWidth + spaceBetweenElem;
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            int inBrush = -1;
            for (int n = 0; n < mSizes.length; n++) {
                if ((x > posLeft[n]) && (x < posRight[n])) {
                    inBrush = n;
                    break;
                }
            }

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mTrackingSize = inBrush;
                    if (inBrush >= 0) {
                        mHighlightSize = inBrush;
                        invalidate();
                        break;
                    }
                case MotionEvent.ACTION_MOVE:
                    if (mTrackingSize >= 0) {
                        if (mHighlightSize != inBrush) {
                            mHighlightSize = inBrush;
                            invalidate();
                        }
                    } else {
                        invalidate();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (mTrackingSize >= 0) {
                        if (inBrush >= 0) {
                            mListener.brushSizeChanged(mSizes[inBrush]);
                        }
                        mTrackingSize = -1;    // so we draw w/o halo
                        invalidate();
                    }
                    break;
            }
            return true;
        }
    }

    public BrushSizeDialog(Context context,
                           OnBrushSizeChangedListener listener,
                           int initialSize) {
        super(context);

        mListener = listener;
        mInitialSize = initialSize;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBrushSizeChangedListener l = new OnBrushSizeChangedListener() {
            public void brushSizeChanged(int size) {
                mListener.brushSizeChanged(size);
                dismiss();
            }
        };

        setContentView(new BrushSizeView(getContext(), l, mInitialSize));
        setTitle("Choose a brush size");
    }

}
