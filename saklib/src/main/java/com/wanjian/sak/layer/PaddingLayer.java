package com.wanjian.sak.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.wanjian.sak.R;
import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.layer.adapter.LayerAdapter;

import java.text.DecimalFormat;


/**
 * Created by wanjian on 2016/10/23.
 */

public class PaddingLayer extends LayerAdapter {
    private final Paint mTextPaint;
    private final Paint mBgPaint;
    private final Paint mPaddingPaint;
    private final int mTxtColor = 0xFF000000;
    private final int mBgColor = 0x88FFFFFF;
    private final int mPaddingColor = 0x3300FF0D;
    private Rect mRect = new Rect();
    private DecimalFormat mFormat = new DecimalFormat("#.###");

    public PaddingLayer(Context context) {
        super(context);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(dp2px(10));
        mTextPaint.setColor(mTxtColor);

        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(mBgColor);

        mPaddingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaddingPaint.setColor(mPaddingColor);
    }

    @Override
    protected void onDrawLayer(Canvas canvas, View view) {
        if (view.getRootView() == view) {
            return;
        }
        int l = view.getPaddingLeft();
        int t = view.getPaddingTop();
        int r = view.getPaddingRight();
        int b = view.getPaddingBottom();
        int w = view.getWidth();
        int h = view.getHeight();

        canvas.drawRect(0, 0, l, h, mPaddingPaint);
        canvas.drawRect(0, 0, w, t, mPaddingPaint);
        canvas.drawRect(w - r, 0, w, h, mPaddingPaint);
        canvas.drawRect(0, h - b, w, h, mPaddingPaint);

        ISizeConverter converter = getSizeConverter();
        Context context = getContext();
        if (l != 0) {
            String txt = "L" + mFormat.format(converter.convert(context, l).getLength());
            mTextPaint.getTextBounds(txt, 0, txt.length(), mRect);
            canvas.drawRect(0, h / 2, mRect.width(), h / 2 + mRect.height(), mBgPaint);
            canvas.drawText(txt, 0, h / 2 + mRect.height(), mTextPaint);
        }
        if (t != 0) {
            String txt = "T" + mFormat.format(converter.convert(context, t).getLength());
            mTextPaint.getTextBounds(txt, 0, txt.length(), mRect);
            canvas.drawRect(w / 2, 0, w / 2 + mRect.width(), mRect.height(), mBgPaint);
            canvas.drawText(txt, w / 2, mRect.height(), mTextPaint);
        }
        if (r != 0) {
            String txt = "R" + mFormat.format(converter.convert(context, r).getLength());
            mTextPaint.getTextBounds(txt, 0, txt.length(), mRect);
            canvas.drawRect(w - mRect.width(), h / 2, w, h / 2 + mRect.height(), mBgPaint);
            canvas.drawText(txt, w - mRect.width(), h / 2 + mRect.height(), mTextPaint);
        }
        if (b != 0) {
            String txt = "B" + mFormat.format(converter.convert(context, b).getLength());
            mTextPaint.getTextBounds(txt, 0, txt.length(), mRect);
            canvas.drawRect(w / 2, h - mRect.height(), w / 2 + mRect.width(), h, mBgPaint);
            canvas.drawText(txt, w / 2, h, mTextPaint);
        }
    }

    @Override
    public Drawable icon() {
        return getContext().getResources().getDrawable(R.drawable.sak_padding_icon);
    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_padding);
    }
}
