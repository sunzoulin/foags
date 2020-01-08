package com.sbl.foags.activity.main.selected

import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.sbl.foags.R
import com.sbl.foags.activity.main.selected.follow.SelectedFollowFragment
import com.sbl.foags.activity.main.selected.moment.SelectedMomentFragment
import com.sbl.foags.activity.main.selected.recommend.SelectedRecommendFragment
import com.sbl.foags.adapter.OnFragmentLoadListener
import com.sbl.foags.adapter.PagerViewFragmentAdapter
import com.sbl.foags.base.BaseFragment
import com.flyco.tablayout.SlidingScaleTabLayout
import com.sbl.foags.activity.cube.comment.AllCommentActivity
import com.sbl.foags.activity.cube.photo.CubePhotoDetailActivity
import com.sbl.foags.activity.cube.video.CubeVideoDetailActivity


class SelectedFragment : BaseFragment(), View.OnClickListener {


    private lateinit var tabsView: SlidingScaleTabLayout
    private lateinit var searchView: ImageView
    private lateinit var messageView: ImageView
    private lateinit var viewPager: ViewPager

    private var fragments: ArrayList<BaseFragment> = arrayListOf()


    override fun initLayout(): Int = R.layout.fragment_selected

    override fun initView() {

        tabsView = getViewById(R.id.tabLayout)
        searchView = getViewById(R.id.searchView)
        messageView = getViewById(R.id.messageView)
        viewPager = getViewById(R.id.viewPager)

        searchView.setOnClickListener(this)
        messageView.setOnClickListener(this)

        fragments.clear()
        fragments.add(SelectedFollowFragment())
        fragments.add(SelectedRecommendFragment())
        fragments.add(SelectedMomentFragment())


        val adapter = PagerViewFragmentAdapter(childFragmentManager, object: OnFragmentLoadListener {

            override fun getPagerAdapterCount(): Int {
                return fragments.size
            }

            override fun getFragmentPosition(position: Int): Fragment {
                return fragments[position]
            }

            override fun getFragmentPositionTitle(position: Int): String {
                return when (val fragment = fragments[position]) {
                    is SelectedFollowFragment -> {
                        fragment.getFragmentTitle()
                    }
                    is SelectedMomentFragment -> {
                        fragment.getFragmentTitle()
                    }
                    is SelectedRecommendFragment -> {
                        fragment.getFragmentTitle()
                    }
                    else -> {
                        ""
                    }
                }
            }
        })
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = fragments.size
        tabsView.setViewPager(viewPager)
        viewPager.currentItem = 1
    }

    override fun onClick(v: View?) {
        when(v){
            searchView -> {
                openActivity(CubePhotoDetailActivity::class.java)
            }

            messageView -> {
                openActivity(CubeVideoDetailActivity::class.java)
            }
        }
    }
}