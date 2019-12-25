package com.sbl.foags.cube.factory.cell.moment.view;

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sbl.foags.R
import com.sbl.foags.base.MyBaseAdapter
import com.sbl.foags.utils.GlideRoundTransform
import com.sbl.foags.utils.UIUtils


class MomentMultiPhotoAdapter(context: Context) : MyBaseAdapter<String>(context) {

    override fun getLayoutId(): Int = R.layout.adapter_item_photo

    override fun getCount(): Int {
        return if (list != null && list.size > 0) {
            return if (list.size > 9) {
                9
            } else {
                list.size
            }
        } else 0
    }

    fun setData(coversUrl: List<String>){
        setInitList(coversUrl)
    }

    override fun bindView(convertView: View?, position: Int) {
        val photo = ViewHolder.getViewID<ImageView>(convertView, R.id.photo)

        Glide.with(context).load(list[position]).transform(GlideRoundTransform(UIUtils.dip2px(6f))).into(photo)
    }
}