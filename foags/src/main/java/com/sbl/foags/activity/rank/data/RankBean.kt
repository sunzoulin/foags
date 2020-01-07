package com.sbl.foags.activity.rank.data

import com.sbl.foags.user.User

class RankBean(val id: String,
               val user: User){
    var liveTime: Long = 0

    var lollipopCount: Long = 0

    var buyCount: Long = 0
}

