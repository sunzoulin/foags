package com.sbl.foags.activity.main.selected.recommend

import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator
import com.bigkoo.convenientbanner.holder.Holder
import com.sbl.foags.R
import com.sbl.foags.activity.rank.RankListActivity
import com.sbl.foags.base.BaseFragment
import com.sbl.foags.bean.Banner
import com.sbl.foags.bean.User
import com.sbl.foags.cube.CubeListAdapter
import com.sbl.foags.cube.CubeType
import com.sbl.foags.cube.bean.*
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.view.MyAspectRatioLinearLayout
import com.sbl.foags.view.MyConvenientBanner
import com.sbl.foags.view.recycler.FloatHeaderAndFooterRecyclerView


class SelectedRecommendFragment : BaseFragment(), View.OnClickListener,
    OnRefreshListener {

    private lateinit var swipeToLoadLayout: SwipeToLoadLayout
    private lateinit var bannerLayout: LinearLayout
    private lateinit var bannerView: MyConvenientBanner<Banner>
    private lateinit var bannerIndicatorView: LinearLayout
    private lateinit var shareLayout: MyAspectRatioLinearLayout
    private lateinit var rankLayout: MyAspectRatioLinearLayout
    private lateinit var modelsLayout: FrameLayout
    private lateinit var modelsRecyclerView: RecyclerView
    private lateinit var recommendRecyclerView: FloatHeaderAndFooterRecyclerView


    override fun initLayout(): Int = R.layout.fragment_selected_recommend

    override fun initView() {
        rootView.tag = 1

        bindViews()

        val banner = arrayListOf<Banner>()
        banner.add(Banner("", "http://g.hiphotos.baidu.com/zhidao/pic/item/ac4bd11373f08202b4a9a53a4bfbfbedab641bff.jpg"))
        banner.add(Banner("", "http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg"))
        banner.add(Banner("", "http://a4.att.hudong.com/50/32/01300000836651126875327576537.jpg"))

        setBanner(banner)


        val models = arrayListOf<User>()
        models.add(User("1", "http://g.hiphotos.baidu.com/zhidao/pic/item/ac4bd11373f08202b4a9a53a4bfbfbedab641bff.jpg", "张三", 12, 0))
        models.add(User("2", "http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg", "李四", 30, 0))
        models.add(User("3", "http://a4.att.hudong.com/50/32/01300000836651126875327576537.jpg", "王五", 5, 0))
        models.add(User(UIUtils.getString(R.string.more_models), "", "", 1, 0))

        setModels(models)



        val cubes = arrayListOf<BaseCubeContentBean>()

        val cube1 = CubeWorkBean("111",
            CubeType.WORK,
            User("1", "http://g.hiphotos.baidu.com/zhidao/pic/item/ac4bd11373f08202b4a9a53a4bfbfbedab641bff.jpg", "张三", 12, 0),
            false,
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

//
//        val cube3 = CubeMomentBean("333",
//            CubeType.MOMENT,
//            User("3", "http://a4.att.hudong.com/50/32/01300000836651126875327576537.jpg", "王五", 5),
//            false,
//            190.0,
//            9,
//            88,
//            2,
//            "3天前")
//
//        cube3.desc = "但是发送到发送到发送到发送到发送到发"
//        cube3.video = "http://g.hiphotos.baidu.com/zhidao/pic/item/ac4bd11373f08202b4a9a53a4bfbfbedab641bff.jpg"
//        cube3.contentType = MomentContentType.VIDEO
//        cubes.add(cube3)
//
//        val cube4 = CubeMomentBean("444",
//            CubeType.MOMENT,
//            User("2", "http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg", "李四", 30),
//            false,
//            190.0,
//            9,
//            88,
//            2,
//            "3天前")
//
//        val photos1 = arrayListOf<String>()
//        photos1.add("http://g.hiphotos.baidu.com/zhidao/pic/item/ac4bd11373f08202b4a9a53a4bfbfbedab641bff.jpg")
//        photos1.add("http://bbs-fd.zol-img.com.cn/t_s800x5000/g4/M04/09/00/Cg-4WVE4lvyIHszpAABsHDCstNAAAFjeQAVpiMAAGw0289.jpg")
//        photos1.add("http://a4.att.hudong.com/50/32/01300000836651126875327576537.jpg")
//        cube4.photoList = photos1
//        cube4.contentType = MomentContentType.PHOTO
//        cube4.desc = "但是发送到发送到发送到发送到发送到发但是发送到发送到发送到发送到发送到发"
//        cubes.add(cube4)

        setCubes(cubes)
    }


    private fun bindViews(){
        swipeToLoadLayout = getViewById(R.id.swipeToLoadLayout)
        bannerLayout = getViewById(R.id.bannerLayout)
        bannerView = getViewById(R.id.bannerView)
        bannerIndicatorView = getViewById(R.id.bannerIndicatorView)
        shareLayout = getViewById(R.id.shareLayout)
        rankLayout = getViewById(R.id.rankLayout)
        modelsLayout = getViewById(R.id.modelsLayout)
        modelsRecyclerView =  getViewById(R.id.modelsRecyclerView)
        recommendRecyclerView =  getViewById(R.id.recommendRecyclerView)

        swipeToLoadLayout.isLoadMoreEnabled = false
        swipeToLoadLayout.setOnRefreshListener(this)
        shareLayout.setOnClickListener(this)
        rankLayout.setOnClickListener(this)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        modelsRecyclerView.layoutManager = layoutManager


        val layoutManager1 = LinearLayoutManager(context)
        layoutManager1.orientation = LinearLayoutManager.VERTICAL
        recommendRecyclerView.layoutManager = layoutManager1
    }


    fun setBanner(banner: ArrayList<Banner>) {
        if (banner.isEmpty()) {
            bannerLayout.visibility = View.GONE
            return
        }
        bannerLayout.visibility = View.VISIBLE

        bannerView.setPages(object: CBViewHolderCreator {

            override fun createHolder(itemView: View?): Holder<Banner> {
                return SelectedRecommendBannerHolderView(
                    requireContext(),
                    itemView
                )
            }

            override fun getLayoutId(): Int = R.layout.adapter_item_selected_recommend_banner

        }, banner).setPointView(bannerIndicatorView).setPageIndicator(intArrayOf(R.drawable.ic_dian_n, R.drawable.ic_dian_p))
        bannerView.startTurning()
    }


    fun setModels(models: ArrayList<User>){
        if (models.isEmpty()) {
            modelsLayout.visibility = View.GONE
            return
        }
        modelsLayout.visibility = View.VISIBLE
        val modelsAdapter = SelectedRecommendModelsAdapter(requireContext(), models)
        modelsRecyclerView.adapter = modelsAdapter
    }


    fun setCubes(cubes: ArrayList<BaseCubeContentBean>){
        if (cubes.isEmpty()) {
            recommendRecyclerView.visibility = View.GONE
            return
        }
        recommendRecyclerView.visibility = View.VISIBLE
        val cubesAdapter = CubeListAdapter(requireContext(), cubes)
        recommendRecyclerView.adapter = cubesAdapter
    }



    fun getFragmentTitle(): String = "推荐"

    override fun onClick(v: View?) {
        when(v){
            shareLayout -> {

            }

            rankLayout -> {
                openActivity(RankListActivity::class.java)
            }
        }
    }

    override fun onRefresh() {
        onFinishRefresh()
    }

    fun onFinishRefresh() {
        swipeToLoadLayout.isRefreshing = false
    }
}