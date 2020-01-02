package com.sbl.foags.activity.main.video

import androidx.recyclerview.widget.LinearLayoutManager
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.sbl.foags.R
import com.sbl.foags.activity.main.video.data.VideoType
import com.sbl.foags.base.BaseFragment
import com.sbl.foags.bean.User
import com.sbl.foags.cube.CubeListAdapter
import com.sbl.foags.cube.CubeType
import com.sbl.foags.cube.bean.BaseCubeContentBean
import com.sbl.foags.cube.bean.CubeWorkBean
import com.sbl.foags.cube.bean.WorkContentType
import com.sbl.foags.view.recycler.FloatHeaderAndFooterRecyclerView


class TypeVideoFragment: BaseFragment(), OnRefreshListener {

    var videoType: VideoType? = null

    private lateinit var swipeToLoadLayout: SwipeToLoadLayout
    private lateinit var videoRecyclerView: FloatHeaderAndFooterRecyclerView

    override fun initLayout(): Int = R.layout.fragment_type_video

    override fun initView() {
        rootView.tag = myTag

        swipeToLoadLayout = getViewById(R.id.swipeToLoadLayout)
        videoRecyclerView = getViewById(R.id.videoRecyclerView)

        swipeToLoadLayout.isLoadMoreEnabled = false
        swipeToLoadLayout.setOnRefreshListener(this)



        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        videoRecyclerView.layoutManager = layoutManager


        val cubes = arrayListOf<BaseCubeContentBean>()

        val cube = CubeWorkBean("222",
            CubeType.WORK,
            User("2", "http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg", "李四", 30, 0),
            true,
            190.0,
            9,
            88,
            2,
            "3天前")

        cube.video = "http://g.hiphotos.baidu.com/zhidao/pic/item/ac4bd11373f08202b4a9a53a4bfbfbedab641bff.jpg"
        cube.duration = 1000
        cube.contentType = WorkContentType.VIDEO
        cubes.add(cube)


        val cube2 = CubeWorkBean("222",
            CubeType.WORK,
            User("2", "http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg", "李四", 30, 0),
            true,
            190.0,
            9,
            88,
            2,
            "3天前")

        cube2.video = "http://g.hiphotos.baidu.com/zhidao/pic/item/ac4bd11373f08202b4a9a53a4bfbfbedab641bff.jpg"
        cube2.duration = 1000
        cube2.contentType = WorkContentType.VIDEO

        cubes.add(cube2)

        setCubes(cubes)

    }

    override fun onRefresh() {
        onFinishRefresh()
    }

    fun onFinishRefresh() {
        swipeToLoadLayout.isRefreshing = false
    }


    fun setCubes(cubes: ArrayList<BaseCubeContentBean>){
        val cubesAdapter = CubeListAdapter(requireContext(), cubes)
        videoRecyclerView.adapter = cubesAdapter
    }

}