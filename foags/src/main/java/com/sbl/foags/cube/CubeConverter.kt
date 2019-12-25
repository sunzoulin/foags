//package com.sbl.foags.cube
//
//import android.graphics.Color
//import java.util.*
//
//
//object CubeConverter {
//
//    @JvmStatic
//    fun parseCube(item: CubeFragment): BaseBean? {
//        // 图片专辑专辑
//        if (item.cubeType() == CubeType.ALBUM) {
//            return convertAlbum(item)
//        }
//        // 视频
//        else if (item.cubeType() == CubeType.MINISHOW
//                || item.cubeType() == CubeType.VIDEO
//                || item.cubeType() == CubeType.VR180
//                || item.cubeType() == CubeType.VR360) {
//            return convertVideo(item)
//        }
//        // 动态
//        else if (item.cubeType() == CubeType.MOMENT) {
//            return convertMoment(item)
//        }
//        // 圈子
//        else if (item.cubeType() == CubeType.CYCLE) {
//            return convertCircle(item)
//        }
//        // 圈子中的帖子
//        else if (item.cubeType() == CubeType.CYCLEPOST) {
//            return convertCirclePost(item)
//        }
//        return null
//    }
//
//    @JvmStatic
//    fun convertAlbum(item: CubeFragment): UGAlbum {
//        val fragment = item.ALBUM()!!.fragments().albumFragment()
//        val user = User()
//        fragment.authorOfAlbum()?.let {
//            user.userID = it.id()
//            user.nickName = it.basic().nickName()
//            user.cover = it.basic().headPortrait_w34()
//            user.level = it.level()
//            user.gender = it.basic().gender()
//            user.icons = it.basic().icons()
//            user.appointmentAuthorized = it.modelInfo()?.appointmentSetting()?.appointmentAuthorized()?: false
//            user.appointmentEnable = it.modelInfo()?.appointmentSetting()?.appointmentEnable()?: false
//            user.setAgeByYearBirth(it.basic().yearBirth())
//            user.lastOnlineStr = it.onlineStatus().lastOnlineStr()
//            user.lastOnline = it.onlineStatus().lastOnline().toLong()
//            user.identity = it.basic().identities()
//            user.onlineStatus = it.onlineStatus().onlineStatus()
//            user.onlineStatusDescrpition = it.onlineStatus().descrpition()
//            user.onlineColor = Color.parseColor(it.onlineStatus().color())
//            user.isFollowedByViewer = it.isFollowedByViewer
//        }
//
//        val photoList = arrayListOf<String>()
//        val photos = fragment.photos()
//        if (photos != null && photos.isNotEmpty()) {
//            photos.forEach {
//                photoList.add(it.url())
//            }
//        }
//
//        // 相关商品
//        var relatedCommodities: List<RelatedCommodity>? = null
//        if (fragment.relatedCommodities() != null) {
//            relatedCommodities = arrayListOf()
//            fragment.relatedCommodities()!!.forEach {
//                relatedCommodities.add(
//                        RelatedCommodity(
//                                it.id(),
//                                it.title(),
//                                it.taobaoUrl(),
//                                it.price().toString())
//                )
//            }
//        }
//
//        return UGAlbum(
//                item.id(),
//                item.cubeType()!!,
//                user,
//                fragment.modelIdList()!!,
//                item.liked(),
//                item.likedCountStr(),
//                item.commentedCountStr(),
//                item.readCountStr(),
//                item.releasedAtStr(),
//                item.isViewerCollectedThis,
//                fragment.title() ?: "",
//                Image(
//                        fragment.cover()?.url() ?: "",
//                        fragment.cover()?.width() ?: 1,
//                        fragment.cover()?.height() ?: 1),
//                photoList,
//                fragment.price(),
//                item.isViewerBoughtThis,
//                fragment.feeType() ?: FeeType.FREE,
//                fragment.totalPhotoCount(),
//                fragment.introduction() ?: "",
//                fragment.isShowRelatedCommodities,
//                relatedCommodities)
//                .apply {
//                    // 标签
//                    cubeLabel = item.cubeLabels()
//                }
//    }
//
//    @JvmStatic
//    fun convertMoment(item: CubeFragment): MomentOnCube {
//        // 解析用户信息
//        val momentFragment = item.moment()!!.fragments().momentFragment()
//        val user = User()
//        momentFragment.author().let {
//            user.userID = it.id()
//            user.nickName = it.basic().nickName()
//            user.cover = it.basic().headPortrait_w34()
//            user.level = it.level()
//            user.appointmentAuthorized = it.modelInfo()?.appointmentSetting()?.appointmentAuthorized()?: false
//            user.appointmentEnable = it.modelInfo()?.appointmentSetting()?.appointmentEnable()?: false
//            user.gender = it.basic().gender()
//            user.icons = it.basic().icons()
//            user.setAgeByYearBirth(it.basic().yearBirth())
//            user.lastOnlineStr = it.onlineStatus().lastOnlineStr()
//            user.lastOnline = it.onlineStatus().lastOnline().toLong()
//            user.identity = it.basic().identities()
//            user.onlineStatus = it.onlineStatus().onlineStatus()
//            user.onlineStatusDescrpition = it.onlineStatus().descrpition()
//            user.onlineColor = Color.parseColor(it.onlineStatus().color())
//            user.isFollowedByViewer = it.isFollowedByViewer
//        }
//
//        val momentOnCube = MomentOnCube(
//                item.id(),
//                item.cubeType()!!,
//                user,
//                null,
//                item.liked(),
//                item.likedCountStr(),
//                item.commentedCountStr(),
//                item.readCountStr(),
//                item.releasedAtStr(),
//                item.isViewerCollectedThis)
//
//        momentOnCube.cover = Image(
//                momentFragment.cover()?.url() ?: "",
//                momentFragment.cover()?.width() ?: 1,
//                momentFragment.cover()?.height() ?: 1)
//        momentOnCube.text = momentFragment.text()
//        momentOnCube.photosNum = momentFragment.photosNum()
//        momentOnCube.contentType = momentFragment.contentType()
//        // 标签
//        momentOnCube.cubeLabel = item.cubeLabels()
//
//        // 图片缩略图列表
//        val photosThumb = ArrayList<String>()
//        val photos = ArrayList<String>()
//        if (momentFragment.contentType() == ContentType.PICTURE) {
//            // 解析动态缩略图
//            if (momentFragment.miniPhotos() != null) {
//                for (photo in momentFragment.miniPhotos()!!) {
//                    photosThumb.add(photo.url())
//                }
//                momentOnCube.photosThumb = photosThumb
//            }
//
//            // 解析动态图片
//            if (momentFragment.photos() != null) {
//                for (photo in momentFragment.photos()!!) {
//                    photos.add(photo.url())
//                }
//                momentOnCube.photos = photos
//            }
//
//        } else {
//            // 解析视频的多张封面图
//            val coverList = arrayListOf<String>()
//            val covers = momentFragment.coverList()
//            if (covers != null && covers.isNotEmpty()) {
//                covers.forEach {
//                    coverList.add(it.url())
//                }
//            }
//            momentOnCube.VIDEO = MomentOnCube.Video(
//                    coverList,
//                    momentFragment.VIDEO()!!.url(),
//                    momentFragment.VIDEO()!!.duration().toLong(),
//                    momentFragment.VIDEO()!!.width(),
//                    momentFragment.VIDEO()!!.height())
//        }
//        return momentOnCube
//    }
//
//    @JvmStatic
//    fun convertVideo(item: CubeFragment): UGVideo? {
//
//        // 解析用户信息
//        val videoFragment = item.videoWorks()!!.fragments().videoWorksFragment()
//        val user = User()
//        videoFragment.authorOfVideoWorks()?.let {
//            user.userID = it.id()
//            user.nickName = it.basic().nickName()
//            user.cover = it.basic().headPortrait_w34()
//            user.level = it.level()
//            user.gender = it.basic().gender()
//            user.icons = it.basic().icons()
//            user.appointmentAuthorized = it.modelInfo()?.appointmentSetting()?.appointmentAuthorized()?: false
//            user.appointmentEnable = it.modelInfo()?.appointmentSetting()?.appointmentEnable()?: false
//            user.setAgeByYearBirth(it.basic().yearBirth())
//            user.lastOnlineStr = it.onlineStatus().lastOnlineStr()
//            user.lastOnline = it.onlineStatus().lastOnline().toLong()
//            user.identity = it.basic().identities()
//            user.onlineStatus = it.onlineStatus().onlineStatus()
//            user.onlineStatusDescrpition = it.onlineStatus().descrpition()
//            user.onlineColor = Color.parseColor(it.onlineStatus().color())
//            user.isFollowedByViewer = it.isFollowedByViewer
//        }
//
//        // 相关商品
//        var relatedCommodities: List<RelatedCommodity>? = null
//        if (videoFragment.relatedCommodities() != null) {
//            relatedCommodities = arrayListOf()
//            videoFragment.relatedCommodities()!!.forEach {
//                relatedCommodities.add(
//                        RelatedCommodity(
//                                it.id(),
//                                it.title(),
//                                it.taobaoUrl(),
//                                it.price().toString()
//                        )
//                )
//            }
//        }
//
//        return UGVideo(
//                item.id(),
//                item.cubeType()!!,
//                user,
//                videoFragment.modelIdList()!!,
//                item.liked(),
//                item.likedCountStr(),
//                item.commentedCountStr(),
//                item.readCountStr(),
//                item.releasedAtStr(),
//                item.isViewerCollectedThis,
//                videoFragment.title() ?: "",
//                Image(
//                        videoFragment.cover()?.url() ?: "",
//                        videoFragment.cover()?.width() ?: 1,
//                        videoFragment.cover()?.height() ?: 1),
//                videoFragment.videoForApp()?.url() ?: "",
//                videoFragment.duration(),
//                videoFragment.width(),
//                videoFragment.height(),
//                item.isViewerBoughtThis,
//                videoFragment.feeType() ?: FeeType.FREE,
//                videoFragment.price(),
//                videoFragment.introduction() ?: "",
//                videoFragment.isShowRelatedCommodities,
//                relatedCommodities)
//                .apply {
//                    cubeLabel = item.cubeLabels()
//                }
//    }
//
//    @JvmStatic
//    fun convertCircle(item: CubeFragment): Circle {
//
//        val circleFragment = item.cycle()!!.fragments().cycleFragment()
//        return Circle(
//                item.id(),
//                circleFragment.logoString(),
//                circleFragment.nameString(),
//                circleFragment.descTexString(),
//                circleFragment.todayNewPostNum())
//    }
//
//    @JvmStatic
//    fun convertCirclePost(item: CubeFragment): CirclePost {
//
//        val circlePostFragment = item.cyclePost()!!.fragments().cyclePostFragment()
//
//        val user = User()
//        circlePostFragment.author().let {
//            user.userID = it.id()
//            user.nickName = it.basic().nickName()
//            user.cover = it.basic().headPortrait_w34()
//            user.level = it.level()
//            user.gender = it.basic().gender()
//            user.icons = it.basic().icons()
//            user.setAgeByYearBirth(it.basic().yearBirth())
//            user.lastOnlineStr = it.onlineStatus().lastOnlineStr()
//            user.lastOnline = it.onlineStatus().lastOnline().toLong()
//            user.identity = it.basic().identities()
//            user.onlineStatus = it.onlineStatus().onlineStatus()
//            user.onlineStatusDescrpition = it.onlineStatus().descrpition()
//            user.onlineColor = Color.parseColor(it.onlineStatus().color())
//            user.isFollowedByViewer = it.isFollowedByViewer
//            user.appointmentAuthorized = it.modelInfo()?.appointmentSetting()?.appointmentAuthorized()?: false
//            user.appointmentEnable = it.modelInfo()?.appointmentSetting()?.appointmentEnable()?: false
//        }
//
//        return CirclePost(
//                item.id(), item.cubeType()!!, user,
//                null,
//                item.liked(),
//                item.likedCountStr(),
//                item.commentedCountStr(),
//                item.readCountStr(),
//                item.releasedAtStr(),
//                item.isViewerCollectedThis,
//                circlePostFragment.titleString() ?: "",
//                circlePostFragment.textString() ?: "",
//                circlePostFragment.contentType())
//                .apply {
//                    // 标签
//                    cubeLabel = item.cubeLabels()
//                    if (contentType == ContentType.PICTURE) {
//
//                        val photosThumb = arrayListOf<String>()
//                        val photos = circlePostFragment.miniPhotoList()
//                        if (photos != null && photos.isNotEmpty()) {
//                            photos.forEach {
//                                photosThumb.add(it.url())
//                            }
//                        }
//                        this.photosThumb = photosThumb
//
//                        val photoList = arrayListOf<String>()
//                        val photos1 = circlePostFragment.photoList()
//                        if (photos1 != null && photos1.isNotEmpty()) {
//                            photos1.forEach {
//                                photoList.add(it.url())
//                            }
//                        }
//                        this.photoList = photoList
//                    } else if (contentType == ContentType.VIDEO) {
//                        this.videoCover = Image(
//                                circlePostFragment.cover()?.url() ?: "",
//                                circlePostFragment.cover()?.width() ?: 1,
//                                circlePostFragment.cover()?.height() ?: 1)
//                    }
//                }
//    }
//}