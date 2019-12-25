package com.sbl.foags.cube.bean

import com.sbl.foags.bean.User
import com.sbl.foags.cube.CubeType


abstract class BaseCubeContentBean(var id: String,
                                   var cubeType: CubeType,
                                   var user: User,
                                   var isFree: Boolean,
                                   var price: Double,
                                   var buyCount: Long,
                                   var commentCount: Long,
                                   var likeCount: Long,
                                   var releaseTimeStr: String){


    fun haveSeePermission() = isFree
}