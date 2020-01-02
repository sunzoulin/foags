package com.sbl.foags.activity.main.photo

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.sbl.foags.R
import com.sbl.foags.activity.main.photo.data.AlbumType
import com.sbl.foags.base.BaseFragment
import com.sbl.foags.bean.User
import com.sbl.foags.cube.CubeListAdapter
import com.sbl.foags.cube.CubeType
import com.sbl.foags.cube.bean.BaseCubeContentBean
import com.sbl.foags.cube.bean.CubeAlbumBean
import com.sbl.foags.view.recycler.FloatHeaderAndFooterRecyclerView


class PrivatePhotoTypeFragment: BaseFragment(), OnRefreshListener {

    var videoType: AlbumType? = null

    private lateinit var swipeToLoadLayout: SwipeToLoadLayout
    private lateinit var albumRecyclerView: FloatHeaderAndFooterRecyclerView


    override fun initLayout(): Int = R.layout.fragment_private_photo_type

    override fun initView() {
        rootView.tag = myTag

        swipeToLoadLayout = getViewById(R.id.swipeToLoadLayout)
        albumRecyclerView = getViewById(R.id.albumRecyclerView)


        swipeToLoadLayout.isLoadMoreEnabled = false
        swipeToLoadLayout.setOnRefreshListener(this)


        if(isLand()){
            val layoutManager = GridLayoutManager(context, 2)
            albumRecyclerView.layoutManager = layoutManager
        }else{

            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            albumRecyclerView.layoutManager = layoutManager
        }

        val cubes = arrayListOf<BaseCubeContentBean>()

        val cube = CubeAlbumBean("222",
            CubeType.ALBUM,
            User("2", "http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg", "李四", 30, 0),
            true,
            190.0,
            9,
            88,
            2,
            "3天前")
        cube.totalPhotoCount = 55
        cube.albumCover = "http://a4.att.hudong.com/50/32/01300000836651126875327576537.jpg"


        cubes.add(cube)


        val cube2 = CubeAlbumBean("222",
            CubeType.ALBUM,
            User("2", "http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg", "李四", 30, 0),
            true,
            190.0,
            9,
            88,
            2,
            "3天前")

        cube2.totalPhotoCount = 55
        cube2.albumCover = "http://a4.att.hudong.com/50/32/01300000836651126875327576537.jpg"

        cubes.add(cube2)



        val cube3 = CubeAlbumBean("222",
            CubeType.ALBUM,
            User("2", "http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg", "李四", 30, 0),
            true,
            190.0,
            9,
            88,
            2,
            "3天前")

        cube3.totalPhotoCount = 55
        cube3.albumCover = "http://a4.att.hudong.com/50/32/01300000836651126875327576537.jpg"

        cubes.add(cube3)



        val cube4 = CubeAlbumBean("222",
            CubeType.ALBUM,
            User("2", "http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg", "李四", 30, 0),
            true,
            190.0,
            9,
            88,
            2,
            "3天前")

        cube4.totalPhotoCount = 55
        cube4.albumCover = "http://a4.att.hudong.com/50/32/01300000836651126875327576537.jpg"


        cubes.add(cube4)



        val cube5 = CubeAlbumBean("222",
            CubeType.ALBUM,
            User("2", "http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg", "李四", 30, 0),
            true,
            190.0,
            9,
            88,
            2,
            "3天前")

        cube5.totalPhotoCount = 55
        cube5.albumCover = "http://a4.att.hudong.com/50/32/01300000836651126875327576537.jpg"


        cubes.add(cube5)

        setCubes(cubes)

    }


    override fun onRefresh() {
        onFinishRefresh()
    }

    fun onFinishRefresh() {
        swipeToLoadLayout.isRefreshing = false
    }


    private fun isLand(): Boolean = when(videoType){
        AlbumType.New, AlbumType.Hot -> false
        else -> true
    }



    fun setCubes(cubes: ArrayList<BaseCubeContentBean>){
        val cubesAdapter = CubeListAdapter(requireContext(), cubes, isLand())
        albumRecyclerView.adapter = cubesAdapter
    }
}