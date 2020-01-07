package com.sbl.foags.user

import kotlinx.serialization.Serializable

@Serializable
class User(var id: String, var headPic: String, var nickName: String) {

    var level: Int = 0
    var memberLevel: Int = 0
    var address: String = ""
    var tags: ArrayList<String>? = null
    var fansCount: Long = 0
    var followCount: Long = 0
    var followStatus: UserFollowStatus = UserFollowStatus.Not
    var signature: String = ""
}