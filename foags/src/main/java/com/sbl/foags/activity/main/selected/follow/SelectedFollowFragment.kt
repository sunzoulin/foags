package com.sbl.foags.activity.main.selected.follow

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.sbl.foags.R
import com.sbl.foags.base.BaseFragment
import com.sbl.foags.bean.User
import com.sbl.foags.cube.CubeListAdapter
import com.sbl.foags.cube.CubeType
import com.sbl.foags.cube.bean.BaseCubeContentBean
import com.sbl.foags.cube.bean.CubeWorkBean
import com.sbl.foags.cube.bean.WorkContentType
import com.sbl.foags.view.recycler.FloatHeaderAndFooterRecyclerView

class SelectedFollowFragment : BaseFragment(), OnRefreshListener {


    private lateinit var swipeToLoadLayout: SwipeToLoadLayout
    private lateinit var followRecyclerView: FloatHeaderAndFooterRecyclerView


    override fun initLayout(): Int = R.layout.fragment_selected_follow

    override fun initView() {
        rootView.tag = 0

        bindViews()


        val cubes = arrayListOf<BaseCubeContentBean>()

        val cube1 = CubeWorkBean("111",
            CubeType.WORK,
            User("1", "http://g.hiphotos.baidu.com/zhidao/pic/item/ac4bd11373f08202b4a9a53a4bfbfbedab641bff.jpg", "张三", 12),
            true,
            99.0,
            23,
            10,
            1000,
            "1天前")
        val photos = arrayListOf<String>()
        photos.add("http://g.hiphotos.baidu.com/zhidao/pic/item/ac4bd11373f08202b4a9a53a4bfbfbedab641bff.jpg")
        photos.add("http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg")
        photos.add("http://a4.att.hudong.com/50/32/01300000836651126875327576537.jpg")
        cube1.photoList = photos
        cube1.totalPhotoCount = 10
        cube1.contentType = WorkContentType.PHOTO

        cubes.add(cube1)

        val cube2 = CubeWorkBean("222",
            CubeType.WORK,
            User("2", "http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg", "李四", 30),
            false,
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

    fun getFragmentTitle(): String = "关注"


    override fun onRefresh() {
        onFinishRefresh()
    }

    fun onFinishRefresh() {
        swipeToLoadLayout.isRefreshing = false
    }


    fun setCubes(cubes: ArrayList<BaseCubeContentBean>){
        if (cubes.isEmpty()) {
            followRecyclerView.visibility = View.GONE
            return
        }
        followRecyclerView.visibility = View.VISIBLE
        val cubesAdapter = CubeListAdapter(requireContext(), cubes)
        followRecyclerView.adapter = cubesAdapter
    }


    private fun bindViews(){
        swipeToLoadLayout = getViewById(R.id.swipeToLoadLayout)
        followRecyclerView =  getViewById(R.id.followRecyclerView)

        swipeToLoadLayout = getViewById(R.id.swipeToLoadLayout)
        swipeToLoadLayout.isLoadMoreEnabled = false
        swipeToLoadLayout.setOnRefreshListener(this)


        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        followRecyclerView.layoutManager = layoutManager
    }

}