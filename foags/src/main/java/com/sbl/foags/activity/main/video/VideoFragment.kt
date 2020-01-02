package com.sbl.foags.activity.main.video

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.flyco.tablayout.SlidingScaleTabLayout
import com.sbl.foags.R
import com.sbl.foags.activity.main.video.data.VideoType
import com.sbl.foags.adapter.OnFragmentLoadListener
import com.sbl.foags.adapter.PagerViewFragmentAdapter
import com.sbl.foags.base.BaseFragment
import com.sbl.foags.utils.UIUtils


class VideoFragment: BaseFragment(), OnFragmentLoadListener {


    private val titles = arrayOf(
        UIUtils.getString(R.string.new_video),
        UIUtils.getString(R.string.recommend_video),
        UIUtils.getString(R.string.hot_video),
        UIUtils.getString(R.string.vr_video),
        UIUtils.getString(R.string.mini_video)
    )

    private val types = arrayOf(
        VideoType.New,
        VideoType.Recommend,
        VideoType.Hot,
        VideoType.RV,
        VideoType.MINI
    )


    private lateinit var tabsView: SlidingScaleTabLayout
    private lateinit var viewPager: ViewPager


    override fun initLayout(): Int = R.layout.fragment_video

    override fun initView() {
        tabsView = getViewById(R.id.tabLayout)
        viewPager = getViewById(R.id.viewPager)


        viewPager.offscreenPageLimit = pagerAdapterCount
        viewPager.adapter = PagerViewFragmentAdapter(childFragmentManager, this)
        tabsView.setViewPager(viewPager)
    }

    override fun getFragmentPositionTitle(position: Int): String = titles[position]

    override fun getFragmentPosition(position: Int): Fragment {
        val fragment = TypeVideoFragment()
        fragment.myTag = position
        fragment.videoType = types[position]
        return fragment
    }

    override fun getPagerAdapterCount(): Int = titles.size
}