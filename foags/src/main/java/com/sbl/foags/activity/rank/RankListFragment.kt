package com.sbl.foags.activity.rank

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.sbl.foags.R
import com.sbl.foags.activity.rank.data.RankCycle
import com.sbl.foags.activity.rank.data.RankType
import com.sbl.foags.adapter.OnFragmentLoadListener
import com.sbl.foags.adapter.PagerViewFragmentAdapter
import com.sbl.foags.base.BaseFragment
import com.sbl.foags.utils.UIUtils


class RankListFragment : BaseFragment(), OnFragmentLoadListener {

    var type: RankType? = null

    private val titles = arrayOf(
            UIUtils.getString(R.string.day_rank),
            UIUtils.getString(R.string.week_rank),
            UIUtils.getString(R.string.month_rank)
    )

    private val cycle = arrayOf(
        RankCycle.DAY,
        RankCycle.WEEK,
        RankCycle.MONTH
    )

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: SmartTabLayout

    override fun initLayout(): Int = R.layout.fragment_rank_list

    override fun initView() {
        rootView.tag = myTag

        viewPager = getViewById(R.id.viewPager)
        tabLayout = getViewById(R.id.tabLayout)

        viewPager.offscreenPageLimit = pagerAdapterCount
        viewPager.adapter = PagerViewFragmentAdapter(childFragmentManager, this)
        tabLayout.setViewPager(viewPager)
    }

    override fun getPagerAdapterCount(): Int = titles.size

    override fun getFragmentPosition(position: Int): Fragment {
        val fragment = TypeCycleRankListFragment()
        fragment.cycle = cycle[position]
        fragment.type = type
        return fragment
    }

    override fun getFragmentPositionTitle(position: Int): String = titles[position]
}
