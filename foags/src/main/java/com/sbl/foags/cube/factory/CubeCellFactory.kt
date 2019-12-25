package com.sbl.foags.cube.factory

import android.content.Context
import com.sbl.foags.R
import com.sbl.foags.cube.bean.*
import com.sbl.foags.cube.factory.cell.home.CubeWorkCell
import com.sbl.foags.cube.factory.cell.moment.CubeMomentForwardWorkCell
import com.sbl.foags.cube.factory.cell.moment.CubeMomentNormalCell
import com.sbl.foags.view.recycler.adapter.BaseRecycleViewAdapter


object CubeCellFactory {

    private const val itemWorkType = 0
    private const val itemMomentType = 1

    private const val unknow = 9


    fun getLayoutIdByViewType(viewType: Int): Int {
        return when (viewType) {
            itemWorkType -> R.layout.cell_cube_work
            itemMomentType -> R.layout.cell_cube_moment
            else -> 0
        }
    }

    fun getTypeByBeanBean(baseCubeBean: BaseCubeContentBean): Int {
        return when (baseCubeBean) {
            is CubeWorkBean -> itemWorkType
            is CubeMomentBean -> itemMomentType
            else -> unknow
        }
    }


    fun showData(context: Context, holder: BaseRecycleViewAdapter.ViewHolder, item: BaseCubeContentBean) {
        when (item) {
            is CubeWorkBean -> {
                val cell = CubeWorkCell(
                    context,
                    holder
                )
                cell.showData(item)
            }
            is CubeMomentBean -> {
                val cell = if (item.isForwardWork) {
                    CubeMomentForwardWorkCell(context, holder)
                } else {
                    CubeMomentNormalCell(context, holder)
                }
                cell.showData(item)
            }
        }
    }

}
