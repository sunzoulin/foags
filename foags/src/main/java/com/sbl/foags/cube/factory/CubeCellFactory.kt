package com.sbl.foags.cube.factory

import android.content.Context
import com.sbl.foags.cube.bean.*
import com.sbl.foags.cube.factory.cell.BaseCell
import com.sbl.foags.cube.factory.cell.album.CubeAlbumBigCell
import com.sbl.foags.cube.factory.cell.album.CubeAlbumSmallCell
import com.sbl.foags.cube.factory.cell.home.CubeWorkCell
import com.sbl.foags.cube.factory.cell.moment.CubeMomentCell
import com.sbl.foags.cube.factory.cell.moment.CubeMomentForwardWorkCell
import com.sbl.foags.cube.factory.cell.moment.CubeMomentNormalCell
import com.sbl.foags.view.recycler.adapter.BaseRecycleViewAdapter


object CubeCellFactory {

    private const val itemWorkType = 0
    private const val itemMomentType = 1
    private const val itemAlbumBigType = 2
    private const val itemAlbumSmallType = 3

    private const val unknow = 9


    fun getLayoutIdByViewType(viewType: Int): Int {
        return when (viewType) {
            itemWorkType -> CubeWorkCell.getLayoutId()
            itemMomentType -> CubeMomentCell.getLayoutId()
            itemAlbumBigType -> CubeAlbumBigCell.getLayoutId()
            itemAlbumSmallType -> CubeAlbumSmallCell.getLayoutId()
            else -> 0
        }
    }

    fun getTypeByBeanBean(baseCubeBean: BaseCubeContentBean, isLand: Boolean): Int {
        return when (baseCubeBean) {
            is CubeWorkBean -> itemWorkType
            is CubeMomentBean -> itemMomentType
            is CubeAlbumBean -> {
                if(isLand){
                    itemAlbumSmallType
                } else {
                    itemAlbumBigType
                }
            }
            else -> unknow
        }
    }


    fun showData(context: Context, holder: BaseRecycleViewAdapter.ViewHolder, item: BaseCubeContentBean, isLand: Boolean) {
        when (item) {
            is CubeWorkBean -> {
                val cell = CubeWorkCell(context, holder)
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
            is CubeAlbumBean -> {
                val cell: BaseCell<CubeAlbumBean> = if(isLand){
                     CubeAlbumSmallCell(context, holder)
                } else {
                    CubeAlbumBigCell(context, holder)
                }
                cell.showData(item)
            }
        }
    }

}
