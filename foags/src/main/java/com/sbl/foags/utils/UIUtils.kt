package com.sbl.foags.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.TypedValue
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import com.sbl.foags.MyApplication
import com.sbl.foags.R


object UIUtils {

    @JvmStatic
    fun dip2px(dipValue: Float): Int {
        return dip2px(MyApplication.instance, dipValue)
    }

    @JvmStatic
    fun dip2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    @JvmStatic
    private fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    @JvmStatic
    fun px2dip(pxValue: Float): Int {
        return px2dip(MyApplication.instance, pxValue)
    }


    /**
     * 根据资源名称获取对应的资源ID
     */

    private fun getResourcesFromName(drawableName: String): Int {
        return MyApplication.instance.resources.getIdentifier(drawableName, "drawable",
            MyApplication.instance.packageName)
    }

    /**
     * 资源名称转Drawable
     */
    @JvmStatic
    fun getDrawableFromName(drawableName: String): Drawable {
        return UIUtils.getDrawable(UIUtils.getResourcesFromName(drawableName))
    }

    /**
     * 根据资源ID转Drawable
     */
    @JvmStatic
    fun getDrawable(id: Int): Drawable {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MyApplication.instance.resources.getDrawable(id,
                MyApplication.instance.theme)
        } else {
            MyApplication.instance.resources.getDrawable(id)
        }
    }

    @JvmStatic
    fun getDimensionPixelSize(@DimenRes id: Int): Int {
        return MyApplication.instance.resources.getDimensionPixelSize(id)
    }

    /**
     * 获取字符串
     */
    @JvmStatic
    fun getString(@StringRes id: Int): String {
        return MyApplication.instance.resources.getString(id)
    }

    /**
     * 获取字符串
     */
    @JvmStatic
    fun getString(id: Int, vararg arg: Any): String {
        return MyApplication.instance.resources.getString(id, *arg)
    }

    /**
     * 获取颜色
     */
    @JvmStatic
    fun getColor(id: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MyApplication.instance.resources.getColor(id,
                MyApplication.instance.theme)
        } else {
            MyApplication.instance.resources.getColor(id)
        }
    }

    /**
     * drawable-xxhdpi 转 bitmap
     * 注意！此方法转换的出的 Bitmap 为 565 格式，没有透明度。
     *
     * @param drawableId 资源 id
     * @return bitmap
     */
    @JvmStatic
    fun drawableToBitmap(drawableId: Int): Bitmap {
        val drawable = MyApplication.instance.resources.getDrawable(drawableId)
        return drawableToBitmap(drawable)
    }

    /**
     * drawable-xxhdpi 转 bitmap
     * 注意！此方法转换的出的 Bitmap 为 565 格式，没有透明度。
     *
     * @param drawable Drawable
     * @return bitmap
     */
    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                if (drawable.opacity != PixelFormat.OPAQUE)
                    Bitmap.Config.RGB_565
                else
                    Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * 删除父view
     *
     * @param view
     */
    @JvmStatic
    fun stripView(view: View) {
        val parent = view.parent
        if (parent != null && parent is ViewGroup) {
            parent.removeView(view)
        }
    }

    /**
     * 设置一个TextView多个超链接
     *
     * @param content  内容
     * @param start    可点击文本的开始角标
     * @param end      可点击文本的结束角标
     * @param listener 超链接点击监听
     */
    @JvmStatic
    fun setTextViewHyperlink(textView: TextView, content: String, textColor: Int, start: Int, end: Int, listener: OnTextViewHyperLinkOnClickListener?) {
        val builder = SpannableStringBuilder(content)
        builder.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                listener?.onClick(content.substring(start, end))
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = textColor
                ds.isUnderlineText = false // 设置下划线
            }
        }, start, end, 0)

        textView.text = builder
        textView.movementMethod = LinkMovementMethod.getInstance()
        //
        textView.highlightColor = UIUtils.getColor(R.color.transparent)
    }

    /**
     * 设置一个TextView多个超链接
     *
     * @param content  内容
     * @param start1   可点击文本1的开始角标
     * @param end1     可点击文本1的结束角标
     * @param start2   可点击文本2的开始角标
     * @param end2     可点击文本2的结束角标
     * @param listener 超链接点击监听
     */
    @JvmStatic
    fun setTextViewHyperlink(textView: TextView, content: String, textColor: Int, start1: Int, end1: Int, start2: Int, end2: Int, listener: OnTextViewHyperLinkOnClickListener?) {
        val builder = SpannableStringBuilder(content)
        builder.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                listener?.onClick(content.substring(start1, end1))
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = textColor
                ds.isUnderlineText = false // 设置下划线
            }
        }, start1, end1, 0)

        builder.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                listener?.onClick(content.substring(start2, end2))
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = textColor
                ds.isUnderlineText = false // 设置下划线
            }
        }, start2, end2, 0)


        textView.text = builder
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.highlightColor = UIUtils.getColor(R.color.transparent)
    }

    /**
     * 获得输入框的文字
     */
    @JvmStatic
    fun getEditText(editText: EditText): String {
        return editText.text.toString().trim { it <= ' ' }
    }


    /***
     * 超文本连接点击监听
     *
     * @author Administrator
     */
    interface OnTextViewHyperLinkOnClickListener {
        /***
         * 根据正则匹配的超文本点击监听
         *
         * @param text
         */
        fun onClick(text: String)
    }


    @JvmStatic
    fun cleanZero(a: Double): String {
        val b = a.toString()
        val c = b.split(".")
        return if (c[1].toInt() == 0) {
            c[0]
        } else {
            a.toString()
        }
    }

    /**
     * 增加视图指定宽高的点击范围
     */
    @JvmStatic
    fun setViewTouchRect(view: View, width: Int, height: Int) {
        val parent = view.parent as View
        parent.post {
            val delegateArea = Rect()
            view.getHitRect(delegateArea)
            delegateArea.left -= width
            delegateArea.bottom += height
            delegateArea.top -= height
            delegateArea.right += width

            val delegate = TouchDelegate(delegateArea, view)
            if (View::class.java.isInstance(view.parent)) {
                (view.parent as View).touchDelegate = delegate
            }
        }
    }

    /**
     * 获取dip值
     */
    @JvmStatic
    fun getDpVale(value:Float,context:Context?):Float{
        if(context==null){
            return value
        }
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.resources.displayMetrics)
    }
}
