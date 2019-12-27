package com.sbl.foags.activity.main.selected.moment

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.sbl.foags.R
import com.sbl.foags.base.BaseFragment
import com.sbl.foags.bean.User
import com.sbl.foags.cube.CubeListAdapter
import com.sbl.foags.cube.CubeType
import com.sbl.foags.cube.bean.*
import com.sbl.foags.view.MomentFollowPersonTipView
import com.sbl.foags.view.recycler.FloatHeaderAndFooterRecyclerView


class SelectedMomentFragment : BaseFragment(), OnRefreshListener, View.OnClickListener {


    private lateinit var swipeToLoadLayout: SwipeToLoadLayout
    private lateinit var momentRecyclerView: FloatHeaderAndFooterRecyclerView
    private lateinit var followMomentLayout: LinearLayout
    private lateinit var momentFollowPersonTipView: MomentFollowPersonTipView
    private lateinit var rightMoreView: ImageView


    override fun initLayout(): Int = R.layout.fragment_selected_moment

    override fun initView() {
        rootView.tag = 2

        bindViews()

        val photos0 = arrayListOf<String>()
        photos0.add("http://g.hiphotos.baidu.com/zhidao/pic/item/ac4bd11373f08202b4a9a53a4bfbfbedab641bff.jpg")
        photos0.add("http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg")
        photos0.add("http://a4.att.hudong.com/50/32/01300000836651126875327576537.jpg")
        photos0.add("http://a4.att.hudong.com/50/32/01300000836651126875327576537.jpg")
        setMomentFollowPersonTip(photos0)


        val cubes = arrayListOf<BaseCubeContentBean>()


        val cube1 = CubeMomentBean("111",
            CubeType.MOMENT,
            User("1", "http://g.hiphotos.baidu.com/zhidao/pic/item/ac4bd11373f08202b4a9a53a4bfbfbedab641bff.jpg", "张三", 12, 0),
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


        val cube111 = CubeWorkBean("111",
            CubeType.WORK,
            User("1", "http://g.hiphotos.baidu.com/zhidao/pic/item/ac4bd11373f08202b4a9a53a4bfbfbedab641bff.jpg", "张三", 12, 0),
            false,
            99.0,
            23,
            10,
            1000,
            "1天前")
        val photos222 = arrayListOf<String>()
        photos222.add("http://g.hiphotos.baidu.com/zhidao/pic/item/ac4bd11373f08202b4a9a53a4bfbfbedab641bff.jpg")
        photos222.add("http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg")
        photos222.add("http://a4.att.hudong.com/50/32/01300000836651126875327576537.jpg")
        cube111.photoList = photos222
        cube111.totalPhotoCount = 10
        cube111.contentType = WorkContentType.PHOTO
        cube111.photographer = User("32", "http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg", "摄影师1", 30, 0)
        cube111.shotTime = "2019.11.11"

        cube1.forwardWork = cube111
        cube1.isForwardWork = true
        cubes.add(cube1)



        val cube2 = CubeMomentBean("222",
            CubeType.MOMENT,
            User("2", "http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg", "李四", 30, 0),
            true,
            190.0,
            9,
            88,
            2,
            "3天前")


        val cube2222 = CubeWorkBean("222",
            CubeType.WORK,
            User("2", "http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg", "李四", 30, 0),
            false,
            190.0,
            9,
            88,
            2,
            "3天前")
        cube2222.video = "http://g.hiphotos.baidu.com/zhidao/pic/item/ac4bd11373f08202b4a9a53a4bfbfbedab641bff.jpg"
        cube2222.duration = 1000
        cube2222.contentType = WorkContentType.VIDEO
        cube2222.photographer = User("12", "http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg", "摄影师2", 30, 0)
        cube2222.shotTime = "2019.11.12"

        cube2.forwardWork = cube2222
        cube2.isForwardWork = true
        cubes.add(cube2)

        val cube3 = CubeMomentBean("333",
            CubeType.MOMENT,
            User("3", "http://a4.att.hudong.com/50/32/01300000836651126875327576537.jpg", "王五", 5, 0),
            false,
            190.0,
            9,
            88,
            2,
            "3天前")

        cube3.desc = "但是发送到发送到发送到发送到发送到发"
        cube3.video = "http://g.hiphotos.baidu.com/zhidao/pic/item/ac4bd11373f08202b4a9a53a4bfbfbedab641bff.jpg"
        cube3.contentType = MomentContentType.VIDEO
        cubes.add(cube3)

        val cube4 = CubeMomentBean("444",
            CubeType.MOMENT,
            User("2", "http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg", "李四", 30, 0),
            false,
            190.0,
            9,
            88,
            2,
            "3天前")

        val photos1 = arrayListOf<String>()
        photos1.add("http://g.hiphotos.baidu.com/zhidao/pic/item/ac4bd11373f08202b4a9a53a4bfbfbedab641bff.jpg")
        photos1.add("http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg")
        photos1.add("http://a4.att.hudong.com/50/32/01300000836651126875327576537.jpg")
        cube4.photoList = photos1
        cube4.contentType = MomentContentType.PHOTO
        cube4.desc = "但是发送到发送到发送到发送到发送到发但是发送到发送到发送到发送到发送到发"
        cubes.add(cube4)



        setCubes(cubes)

    }

    fun getFragmentTitle(): String = "动态"


    override fun onRefresh() {
        onFinishRefresh()
    }

    fun onFinishRefresh() {
        swipeToLoadLayout.isRefreshing = false
    }


    private fun bindViews(){
        swipeToLoadLayout = getViewById(R.id.swipeToLoadLayout)
        momentRecyclerView =  getViewById(R.id.momentRecyclerView)
        followMomentLayout = getViewById(R.id.followMomentLayout)
        momentFollowPersonTipView = getViewById(R.id.momentFollowPersonTipView)
        rightMoreView = getViewById(R.id.rightMoreView)

        swipeToLoadLayout = getViewById(R.id.swipeToLoadLayout)
        swipeToLoadLayout.isLoadMoreEnabled = false
        swipeToLoadLayout.setOnRefreshListener(this)
        rightMoreView.setOnClickListener(this)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        momentRecyclerView.layoutManager = layoutManager
    }

    override fun onClick(v: View?) {

    }


    fun setMomentFollowPersonTip(headPic: ArrayList<String>){
        if (headPic.isEmpty()) {
            followMomentLayout.visibility = View.GONE
            return
        }
        followMomentLayout.visibility = View.VISIBLE
        momentFollowPersonTipView.show(headPic)
    }


    fun setCubes(cubes: ArrayList<BaseCubeContentBean>){
        if (cubes.isEmpty()) {
            momentRecyclerView.visibility = View.GONE
            return
        }
        momentRecyclerView.visibility = View.VISIBLE
        val cubesAdapter = CubeListAdapter(requireContext(), cubes)
        momentRecyclerView.adapter = cubesAdapter
    }
}