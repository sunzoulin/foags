package com.sbl.foags.cube

import android.content.Context
import com.sbl.foags.cube.bean.BaseCubeContentBean
import com.sbl.foags.cube.factory.CubeCellFactory
import com.sbl.foags.view.recycler.adapter.BaseRecycleViewAdapter
import java.util.*


class CubeListAdapter(context: Context, list: ArrayList<BaseCubeContentBean>) : BaseRecycleViewAdapter<BaseCubeContentBean>(context, list) {

    private var isLand = false

    constructor(context: Context, list: ArrayList<BaseCubeContentBean>, isLand: Boolean) : this(context, list) {
        this.isLand = isLand
    }

//    var onCubeClickListener: OnCubeClickListener? = null

    override fun getItemViewType(position: Int): Int {
        return CubeCellFactory.getTypeByBeanBean(mDatas[position], isLand)
    }

    override fun getLayoutId(viewType: Int): Int = CubeCellFactory.getLayoutIdByViewType(viewType)

    override fun convert(holder: ViewHolder, item: BaseCubeContentBean, position: Int) {
        CubeCellFactory.showData(mContext, holder, item, isLand)
//        if (baseCell is BaseCubeCell<*>){
//            baseCell.listener = onCubeClickListener
//        }
    }
}