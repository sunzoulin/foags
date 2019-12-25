package com.sbl.foags.cube.bean

import com.sbl.foags.bean.User
import com.sbl.foags.cube.CubeType

class CubeWorkBean(id: String,
                   cubeType: CubeType,
                   user: User,
                   isFree: Boolean,
                   price: Double,
                   buyCount: Long,
                   commentCount: Long,
                   likeCount: Long,
                   releaseTime: String)

    : BaseCubeContentBean(id, cubeType, user, isFree, price, buyCount, commentCount, likeCount, releaseTime){

    var photographer: User? = null

    var shotTime: String = ""

    var contentType: WorkContentType? = null

    var video: String = ""

    var duration: Long = 0

    var photoList: ArrayList<String>? = null

    var totalPhotoCount: Int = 0
}