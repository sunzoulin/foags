package com.sbl.foags.cube.bean

import com.sbl.foags.user.User
import com.sbl.foags.cube.CubeType

class CubeAlbumBean(id: String,
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

    var photoList: ArrayList<String>? = null

    var totalPhotoCount: Int = 0

    var albumCover: String = ""
}