package com.sbl.foags.activity.models

import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.flyco.tablayout.SlidingScaleTabLayout
import com.sbl.foags.R
import com.sbl.foags.activity.models.data.ModelListType
import com.sbl.foags.activity.search.SearchUserActivity
import com.sbl.foags.adapter.OnFragmentLoadListener
import com.sbl.foags.adapter.PagerViewFragmentAdapter
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.utils.UIUtils


class ModelListActivity : BaseActivity(), OnFragmentLoadListener {

    private val titles = arrayOf(
            UIUtils.getString(R.string.follow),
            UIUtils.getString(R.string.big_v),
            UIUtils.getString(R.string.new_show)
    )

    private val type = arrayOf(
        ModelListType.Follow,
        ModelListType.BigV,
        ModelListType.NewShow
    )


    private lateinit var backView: ImageView
    private lateinit var tabLayout: SlidingScaleTabLayout
    private lateinit var searchView: ImageView

    private lateinit var viewPager: ViewPager

    override fun initLayout(): Int = R.layout.activity_model_list

    override fun initView() {

        backView = findViewById(R.id.backView)
        tabLayout = findViewById(R.id.tabLayout)
        searchView = findViewById(R.id.searchView)

        viewPager = findViewById(R.id.viewPager)

        backView.setOnClickListener { finish() }

        viewPager.offscreenPageLimit = pagerAdapterCount
        viewPager.adapter = PagerViewFragmentAdapter(supportFragmentManager, this)
        tabLayout.setViewPager(viewPager)

        searchView.setOnClickListener { openActivity(SearchUserActivity::class.java) }
    }

    override fun loadData() {

    }

    override fun getPagerAdapterCount(): Int = titles.size

    override fun getFragmentPosition(position: Int): Fragment {
        val fragment = ModelListFragment()
        fragment.myTag = position
        fragment.type = type[position]
        return fragment
    }

    override fun getFragmentPositionTitle(position: Int): String = titles[position]
}
