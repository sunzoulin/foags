package com.sbl.foags.activity.main.photo

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.flyco.tablayout.SlidingScaleTabLayout
import com.sbl.foags.R
import com.sbl.foags.activity.main.photo.data.AlbumType
import com.sbl.foags.adapter.OnFragmentLoadListener
import com.sbl.foags.adapter.PagerViewFragmentAdapter
import com.sbl.foags.base.BaseFragment
import com.sbl.foags.utils.UIUtils


class PrivatePhotoFragment : BaseFragment(), OnFragmentLoadListener {

    private val titles = arrayOf(
        UIUtils.getString(R.string.new_album),
        UIUtils.getString(R.string.hot_album),
        UIUtils.getString(R.string.popularity_album),
        UIUtils.getString(R.string.portrait_album),
        UIUtils.getString(R.string.hat_album),
        UIUtils.getString(R.string.yoga_album)
    )

    private val types = arrayOf(
        AlbumType.New,
        AlbumType.Hot,
        AlbumType.Popularity,
        AlbumType.Portrait,
        AlbumType.Hat,
        AlbumType.Yoga
    )


    private lateinit var tabsView: SlidingScaleTabLayout
    private lateinit var viewPager: ViewPager


    override fun initLayout(): Int = R.layout.fragment_private_photo

    override fun initView() {

        tabsView = getViewById(R.id.tabLayout)
        viewPager = getViewById(R.id.viewPager)


        viewPager.offscreenPageLimit = pagerAdapterCount
        viewPager.adapter = PagerViewFragmentAdapter(childFragmentManager, this)
        tabsView.setViewPager(viewPager)
    }

    override fun getFragmentPositionTitle(position: Int): String = titles[position]

    override fun getFragmentPosition(position: Int): Fragment {
        val fragment = PrivatePhotoTypeFragment()
        fragment.myTag = position
        fragment.videoType = types[position]
        return fragment
    }

    override fun getPagerAdapterCount(): Int = titles.size
}