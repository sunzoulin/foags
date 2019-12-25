package com.sbl.foags.cube.bean

import com.sbl.foags.bean.User
import com.sbl.foags.cube.CubeType

class CubeMomentBean(id: String,
                     cubeType: CubeType,
                     user: User,
                     isFree: Boolean,
                     price: Double,
                     buyCount: Long,
                     commentCount: Long,
                     likeCount: Long,
                     releaseTime: String)

    : BaseCubeContentBean(id, cubeType, user, isFree, price, buyCount, commentCount, likeCount, releaseTime){

    var contentType: MomentContentType? = null

    var photoList: ArrayList<String>? = null

    var video: String = ""

    var desc: String = ""



    var isForwardWork: Boolean = false

    var forwardWork: CubeWorkBean? = null
}