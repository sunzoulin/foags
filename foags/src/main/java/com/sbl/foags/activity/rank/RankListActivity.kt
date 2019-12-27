package com.sbl.foags.activity.rank

import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.flyco.tablayout.SlidingScaleTabLayout
import com.sbl.foags.R
import com.sbl.foags.activity.rank.data.RankType
import com.sbl.foags.adapter.OnFragmentLoadListener
import com.sbl.foags.adapter.PagerViewFragmentAdapter
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.utils.UIUtils


class RankListActivity : BaseActivity(), OnFragmentLoadListener {

    private val titles = arrayOf(
            UIUtils.getString(R.string.near_relation_rank),
            UIUtils.getString(R.string.hot_live_rank),
            UIUtils.getString(R.string.hot_rich_rank)
    )

    private val type = arrayOf(
            RankType.NearRelation,
            RankType.HotLive,
            RankType.HotRich
    )


    private lateinit var backView: ImageView
    private lateinit var tabLayout: SlidingScaleTabLayout
    private lateinit var viewPager: ViewPager

    override fun initLayout(): Int = R.layout.activity_rank_list

    override fun initView() {

        backView = findViewById(R.id.backView)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        backView.setOnClickListener { finish() }

        viewPager.offscreenPageLimit = pagerAdapterCount
        viewPager.adapter = PagerViewFragmentAdapter(supportFragmentManager, this)
        tabLayout.setViewPager(viewPager)
    }

    override fun loadData() {

    }

    override fun getPagerAdapterCount(): Int = titles.size

    override fun getFragmentPosition(position: Int): Fragment {
        val fragment = RankListFragment()
        fragment.myTag = position
        fragment.type = type[position]
        return fragment
    }

    override fun getFragmentPositionTitle(position: Int): String = titles[position]
}
