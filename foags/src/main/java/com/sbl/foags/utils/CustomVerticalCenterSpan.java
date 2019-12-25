package com.sbl.foags.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.style.ReplacementSpan;

/**
 * 使TextView中不同大小字体垂直居中
 */
public class CustomVerticalCenterSpan extends ReplacementSpan {

    /**
     * 字体大小dp
     */
    private int fontSizePx;

    private int color;

    public CustomVerticalCenterSpan(int fontSizePx, int color) {
        this.fontSizePx = fontSizePx;
        this.color = color;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        text = text.subSequence(start, end);
        Paint p = getCustomTextPaint(paint);
        return (int) p.measureText(text.toString());
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        text = text.subSequence(start, end);
        Paint p = getCustomTextPaint(paint);
        Paint.FontMetricsInt fm = p.getFontMetricsInt();
        //此处重新计算y坐标，使字体居中
        canvas.drawText(text.toString(), x, y - ((y + fm.descent + y + fm.ascent) / 2 - (bottom + top) / 2), p);
    }

    private TextPaint getCustomTextPaint(Paint srcPaint) {
        TextPaint paint = new TextPaint(srcPaint);
        //设定字体大小, sp转换为px
        paint.setColor(color);
        paint.setTextSize(fontSizePx);
        return paint;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(color);
    }
}
