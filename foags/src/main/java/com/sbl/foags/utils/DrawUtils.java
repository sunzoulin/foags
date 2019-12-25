package com.sbl.foags.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.sbl.foags.R;


public class DrawUtils {
    /**
     * 计算旋转角度
     *
     * @param centerX 中心X位置
     * @param centerY 中心Y位置
     * @param x       计算角度的x位置
     * @param y       计算角度的Y位置
     * @return
     */
    public static float calcDegree(float centerX, float centerY, float x, float y) {
        //第一象限
        if (x >= centerX && y <= centerY) {
            //对边的长度
            float opposite = x - centerX;
            //邻边的长度
            float adjacent = centerY - y;
            double atan = Math.atan(opposite / adjacent);
            //转角度
            double degree = atan * 180 / Math.PI;
            return (float) degree;
        }
        //第二象限
        else if (x >= centerX && y > centerY) {
            //对边的长度
            float opposite = y - centerY;
            //邻边的长度
            float adjacent = x - centerX;
            double atan = Math.atan(opposite / adjacent);
            //转角度
            double degree = atan * 180 / Math.PI + 90;
            return (float) degree;
        }
        //第三象限
        else if (x < centerX && y > centerY) {
            //对边的长度
            float opposite = centerX - x;
            //邻边的长度
            float adjacent = y - centerY;
            double atan = Math.atan(opposite / adjacent);
            //转角度
            double degree = atan * 180 / Math.PI + 180;
            return (float) degree;
        } else {
            //对边的长度
            float opposite = centerY - y;
            //邻边的长度
            float adjacent = centerX - x;
            double atan = Math.atan(opposite / adjacent);
            //转角度
            double degree = atan * 180 / Math.PI + 270;
            return (float) degree;
        }
    }

    /**
     * 计算两点之间的距离
     *
     * @return
     */
    public static float calcDistance(float point1X, float point1Y, float point2X, float point2Y) {
        double _x = Math.abs(point1X - point2X);
        double _y = Math.abs(point1Y - point2Y);
        return (float) Math.sqrt(_x * _x + _y * _y);
    }

    /***
     * 坐标变换的方法
     *
     * @param x      起点x
     * @param y      起点y
     * @param angle  角度
     * @param length 长度
     * @return
     */
    public static float[] transformation(float x, float y, float angle, float length) {
        float[] location = new float[2];
        //angle<180
        if (angle < 180) {
            if (angle == 0) {
                //计算坐标
                location[0] = x;
                location[1] = y - length;
            } else {
                //算出正玄
                float sin = (float) Math.sin(Math.PI * angle / 180);
                //算出对边的长度
                float oppositeLength = sin * length;
                //算出正切
                float tan = (float) Math.tan(Math.PI * angle / 180);
                //计算邻边的长度
                float adjacentLength = oppositeLength / tan;
                //计算坐标
                location[0] = x + oppositeLength;
                location[1] = y - adjacentLength;
            }
        } else {
            float newAngle = angle - 180;
            if (newAngle == 0) {
                location[0] = x;
                location[1] = y + length;
            } else {
                //算出正玄
                float sin = (float) Math.sin(Math.PI * newAngle / 180);
                //算出对边的长度
                float oppositeLength = sin * length;
                //算出正切
                float tan = (float) Math.tan(Math.PI * newAngle / 180);
                //计算邻边的长度
                float adjacentLength = oppositeLength / tan;

                location[0] = x - oppositeLength;
                location[1] = y + adjacentLength;
            }
        }
        return location;
    }

    /**
     * 绘制虚线不使用path方法,在使用path绘制虚线的时候给 画笔设置了一个path属性会更改全局的绘制样式
     *
     * @param canvas
     * @param paint
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param spacing 个数必须是偶数(此方法暂时只支持水平或垂直坐标)
     */
    public static void drawDashline(Canvas canvas, Paint paint, float startX, float startY, float endX, float endY, float... spacing) {
        if (spacing.length % 2 != 0) {
            return;
        }
        if (startX > endX) {
            float temp = startX;
            startX = endX;
            endX = temp;
        }
        if (startY > endY) {
            float temp = startY;
            startY = endX;
            endY = temp;
        }
        float currPoint = 0;
        if (endX != startX) {
            currPoint = startX;
            while (currPoint < endX) {
                for (int i = 0; i < spacing.length; i++) {
                    if (i % 2 == 0) {
                        if (currPoint + spacing[i] >= endX) {
                            canvas.drawLine(currPoint, startY, endX, endY, paint);
                            return;
                        }
                        canvas.drawLine(currPoint, startY, currPoint + spacing[i], endY, paint);
                    }
                    currPoint += spacing[i];
                    if (currPoint >= endX) {
                        return;
                    }
                }
            }
        }
        if (endY != startY) {
            currPoint = startY;
            while (currPoint < endY) {
                for (int i = 0; i < spacing.length; i++) {
                    if (i % 2 == 0) {
                        if (currPoint + spacing[i] >= endY) {
                            canvas.drawLine(startX, currPoint, endX, endY, paint);
                            return;
                        }
                        canvas.drawLine(startX, currPoint, endX, currPoint + spacing[i], paint);
                    }
                    currPoint += spacing[i];
                    if (currPoint >= endY) {
                        return;
                    }
                }
            }
        }
    }

    /**
     * 播放属性动画,统一抽取
     *
     * @param view
     * @param propertyName 属性名称
     * @param start        开始值
     * @param total        目标值
     */
    public static void animator(View view, String propertyName, float start, float total, long time) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, propertyName, start, total);
        animator.setDuration(time);
        animator.start();
    }

    /**
     * 播放属性动画,统一抽取
     *
     * @param view
     * @param propertyName 属性名称
     * @param start        开始值
     * @param total        目标值
     */
    public static void animator(View view, String propertyName, float start, float total, long time, TimeInterpolator interpolator) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, propertyName, start, total);
        animator.setDuration(time);
        if (interpolator != null) {
            animator.setInterpolator(interpolator);
        }
        animator.start();
    }

    /**
     * 播放属性动画,统一抽取
     *
     * @param view
     * @param propertyName 属性名称
     * @param start        开始值
     * @param total        目标值
     */
    public static void animator(View view, String propertyName, float start, float total, long time, Animator.AnimatorListener listener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, propertyName, start, total);
        animator.setDuration(time);
        if (listener != null) {
            animator.addListener(listener);
        }
        animator.start();
    }

    /**
     * 播放属性动画,统一抽取
     *
     * @param view
     * @param propertyName 属性名称
     * @param start        开始值
     * @param total        目标值
     */
    public static void animator(View view, String propertyName, float start, float total, long time, TimeInterpolator interpolator, Animator.AnimatorListener listener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, propertyName, start, total);
        animator.setDuration(time);
        if (interpolator != null) {
            animator.setInterpolator(interpolator);
        }
        if (listener != null) {
            animator.addListener(listener);
        }
        animator.start();
    }

    /**
     * 绘制文本在一个矩形中间
     */
    public static void drawTextInRect(Canvas canvas, String text, RectF rect, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        //获取文字信息
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float height = rect.bottom - rect.top;
        float width = rect.right - rect.left;
        canvas.drawText(text, rect.left + rect.width() / 2 - paint.measureText(text) / 2,
                rect.top + rect.height() / 2 + bounds.height() / 2, paint);
    }

    /**
     * 绘制一段文本在一个点上
     */
    public static void drawTextInPoint(Canvas canvas, String text, float x, float y, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        //获取文字信息
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
//        canvas.drawText(text,x-bounds.width()/2f,y + bounds.height()/2 - fontMetrics.leading-fontMetrics.descent,paint);
        canvas.drawText(text, x - bounds.width() / 2, y + bounds.height() / 2, paint);
    }

    /**
     * 绘制一段文本在一个点上
     */
    public static Rect getTextBounds(Paint paint, String text) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds;
    }

    /***
     * 根据显示文本的最大长度,截取字符串
     *
     * @param paint
     * @param text
     * @param maxLenght
     * @return
     */
    public static String getMaxLenghtDisplayText(Paint paint, String text, float maxLenght) {
        Rect bounds = new Rect();
        for (int i = 1; i < text.length(); i++) {
            String newStr = text.substring(0, i);
            paint.getTextBounds(newStr, 0, newStr.length(), bounds);
            if (bounds.width() >= maxLenght) {
                return newStr + "...";
            }
        }
        return text;
    }

    /**
     * 绘制圆角矩形,控制四个角是否需要圆角
     */
    public static void drawRoundRect(boolean leftTop, boolean leftBottom, boolean rightTop, boolean rightBottom,
                                     float radius, RectF rectF, Canvas canvas, Paint paint) {
        RectF roundRect = new RectF();
        //左上边矩形
        if (leftTop) {
            roundRect.left = rectF.left;
            roundRect.top = rectF.top;
            roundRect.right = rectF.left + radius * 2;
            roundRect.bottom = rectF.top + radius * 2;
            canvas.drawArc(roundRect, 180, 90, true, paint);
        }
        //左下边矩形
        if (leftBottom) {
            roundRect.left = rectF.left;
            roundRect.top = rectF.bottom - radius * 2;
            roundRect.right = rectF.left + radius * 2;
            roundRect.bottom = rectF.bottom;
            canvas.drawArc(roundRect, 90, 90, true, paint);
        }
        //右上边矩形
        if (rightTop) {
            roundRect.left = rectF.right - radius * 2;
            roundRect.top = rectF.top;
            roundRect.right = rectF.right;
            roundRect.bottom = rectF.top + radius * 2;
            canvas.drawArc(roundRect, -90, 90, true, paint);
        }
        //右下边矩形
        if (rightBottom) {
            roundRect.left = rectF.right - radius * 2;
            roundRect.top = rectF.bottom - radius * 2;
            roundRect.right = rectF.right;
            roundRect.bottom = rectF.bottom;
            canvas.drawArc(roundRect, 0, 90, true, paint);
        }
        //填充
        RectF fillRectF = new RectF();
        //左边矩形
        fillRectF.left = rectF.left;
        fillRectF.top = !leftTop ? rectF.top : rectF.top + radius;
        fillRectF.right = rectF.left + radius;
        fillRectF.bottom = !leftBottom ? rectF.bottom : rectF.bottom - radius;
        canvas.drawRect(fillRectF, paint);
        //右边矩形
        fillRectF.left = rectF.right - radius;
        fillRectF.top = !rightTop ? rectF.top : rectF.top + radius;
        fillRectF.right = rectF.right;
        fillRectF.bottom = !rightBottom ? rectF.bottom : rectF.bottom - radius;
        canvas.drawRect(fillRectF, paint);
        //上边矩形
        fillRectF.left = rectF.left + radius;
        fillRectF.top = rectF.top;
        fillRectF.right = rectF.right - radius;
        fillRectF.bottom = rectF.top + radius;
        canvas.drawRect(fillRectF, paint);
        //下边矩形
        fillRectF.left = rectF.left + radius;
        fillRectF.top = rectF.bottom - radius;
        fillRectF.right = rectF.right - radius;
        fillRectF.bottom = rectF.bottom;
        canvas.drawRect(fillRectF, paint);

        //填充中间
        fillRectF.left = rectF.left + radius;
        fillRectF.top = rectF.top + radius;
        fillRectF.right = rectF.right - radius;
        fillRectF.bottom = rectF.bottom - radius;
        canvas.drawRect(fillRectF, paint);
    }

    /**
     * 惯性移动View,移动View的 translationX与translationY的值
     *
     * @param view 目标View
     * @param xv   x方向速度(可以根据控制这个值来控制移动的距离)
     * @param yv   y方向速度(可以根据控制这个值来控制移动的距离)
     * @param minX 限制x的最小值
     * @param maxX 限制x的最大值
     * @param minY 限制y的最小值
     * @param maxY 限制y的最大值
     */
    public static void inertiaMove(final View view,
                                   final float xv,
                                   final float yv,
                                   final float minX,
                                   final float maxX,
                                   final float minY,
                                   final float maxY) {
        if (view == null) {
            return;
        }

        Object tag = view.getTag(R.id.card_inertia_move_animation);
        if (tag != null && tag instanceof ValueAnimator) {
            ((ValueAnimator) tag).end();
        }

        final float ox = view.getTranslationX();
        final float oy = view.getTranslationY();

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(500);
        animator.setInterpolator(new DecelerateInterpolator(1));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float speed = (float) animation.getAnimatedValue();

//                Logger.d("s=%f, x=%f,y=%f",speed, xv * speed,yv * speed);

                float xValue = ox + xv * speed;
                float yValue = oy + yv * speed;

                if (xValue < minX) {
                    xValue = minX;
                }
                if (xValue > maxX) {
                    xValue = maxX;
                }

                if (yValue < minY) {
                    yValue = minY;
                }
                if (yValue > maxY) {
                    yValue = maxY;
                }
                view.setTranslationX(xValue);
                view.setTranslationY(yValue);
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animator) {
                view.setTag(R.id.card_inertia_move_animation, null);
            }
        });

        view.setTag(R.id.card_inertia_move_animation, animator);
        animator.start();
    }

    /**
     * 图像灰度化
     */
    public static Bitmap bitmap2Gray(Bitmap bmSrc) {
        // 得到图片的长和宽
        int width = bmSrc.getWidth();
        int height = bmSrc.getHeight();
        // 创建目标灰度图像
        Bitmap bmpGray = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 创建画布
        Canvas c = new Canvas(bmpGray);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmSrc, 0, 0, paint);
        return bmpGray;
    }

    /**
     * 高斯模糊图片，该方法返回后源图片将被回收 (recycle)
     *
     * @param context Context
     * @param bitmap  原 bitmap
     * @param config  Bitmap.Config
     * @return 高斯模糊后的图片（RGB_565）
     */
    public static Bitmap blurBitmap(Context context, Bitmap bitmap, float radius, Bitmap.Config config) {

        //Let's create an empty bitmap with the same size of the bitmap we want to blur
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);

        //Instantiate a new Renderscript
        RenderScript rs = RenderScript.create(context);

        //Create an Intrinsic Blur Script using the Renderscript
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        //Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        //Set the radius of the blur
        blurScript.setRadius(radius);

        //Perform the Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        //Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);

        //recycle the original bitmap
        bitmap.recycle();

        //After finishing everything, we destroy the Renderscript.
        rs.destroy();

        return outBitmap;
    }

    /***
     * 获取圆角的 bitmap
     *
     * @param bitmap       源 bitmap
     * @param matrix       变换矩阵
     * @param mRoundRadius 圆角大小
     * @return 经圆角处理的图片（ARGB_4444）
     */
    public static Bitmap getRoundBitmap(Bitmap bitmap, int width, int height, Matrix matrix, float mRoundRadius) {
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xffffffff);
        //清空像素
        canvas.drawARGB(0, 0, 0, 0);
        //画圆角矩形
        canvas.drawRoundRect((new RectF(0, 0, width, height)), mRoundRadius, mRoundRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        if (matrix != null) {
            canvas.drawBitmap(bitmap, matrix, paint);
        } else {
            Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            Rect dst = new Rect(0, 0, width, height);
            canvas.drawBitmap(bitmap, src, dst, paint);
        }
        return output;
    }

    /***
     * 获取圆角的 bitmap
     *
     * @param bitmap      源 bitmap
     * @param matrix      变换矩阵
     * @param roundRadius 圆角大小
     * @param radius      四个角是否圆角   leftTop, leftBottom, rightTop, rightButtom
     * @return 经圆角处理的图片（ARGB_4444）
     */
    public static Bitmap getRoundBitmap(Bitmap bitmap, int width, int height, Matrix matrix, float roundRadius, boolean[] radius) {
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xffffffff);
        //清空像素
        canvas.drawARGB(0, 0, 0, 0);
        //画圆角矩形
        drawRoundRect(radius[0], radius[1], radius[2], radius[3], roundRadius, new RectF(0, 0, width, height), canvas, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        if (matrix != null) {
            canvas.drawBitmap(bitmap, matrix, paint);
        } else {
            Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            Rect dst = new Rect(0, 0, width, height);
            canvas.drawBitmap(bitmap, src, dst, paint);
        }


        return output;
    }

    /**
     * drawable-xxhdpi 转 bitmap
     *
     * @param drawable Drawable
     * @return 图片（RGB_565）
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ?
                        Bitmap.Config.RGB_565 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static int dip2px(Context context, float dip) {
        if (context != null) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dip * scale + 0.5f);
        }
        return 0;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        try {
            final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (spValue * fontScale + 0.5f);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 根据padding算出宽度的中间
     */
    public static int getWidthCentre(View view) {
        //首先获取实际的宽度
        int width = view.getWidth() - view.getPaddingLeft() - view.getPaddingRight();
        return view.getPaddingLeft() + width / 2;
    }

    /**
     * 根据padding算出高度的中间
     */
    public static int getHeightCentre(View view) {
        int height = view.getHeight() - view.getPaddingTop() - view.getPaddingBottom();
        return view.getPaddingTop() + height / 2;
    }

    /**
     * 获取实际的宽度
     */
    public static int getRealWidth(View view) {
        return view.getWidth() - view.getPaddingLeft() - view.getPaddingRight();
    }

    /**
     * 获取实际的高度
     */
    public static int getRealHeight(View view) {
        return view.getHeight() - view.getPaddingTop() - view.getPaddingBottom();
    }


}
