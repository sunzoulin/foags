package com.sbl.foags.bean


class User(var id: String, var headPic: String, var nickName: String, var level: Int, val memberLevel: Int){

    var address: String = ""
    var tags: ArrayList<String>? = null

    var fansCount: Long = 0
    var followCount: Long = 0
    var viewerIsFollowThis: Boolean = false
}