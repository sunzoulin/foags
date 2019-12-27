package com.sbl.foags.activity.rank

import androidx.recyclerview.widget.LinearLayoutManager
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.sbl.foags.R
import com.sbl.foags.activity.rank.data.RankBean
import com.sbl.foags.activity.rank.data.RankCycle
import com.sbl.foags.activity.rank.data.RankType
import com.sbl.foags.base.BaseFragment
import com.sbl.foags.bean.User
import com.sbl.foags.view.recycler.FloatHeaderAndFooterRecyclerView
import com.sbl.foags.view.recycler.other.HeaderAndFooterRecyclerViewAdapter

class TypeCycleRankListFragment : BaseFragment(), OnRefreshListener {

    var type: RankType? = null
    var cycle: RankCycle? = null

    private lateinit var swipeToLoadLayout: SwipeToLoadLayout
    private lateinit var rankRecyclerView: FloatHeaderAndFooterRecyclerView
    private lateinit var header: TypeCycleRankListHeader

    private lateinit var typeCycleRankListAdapter: TypeCycleRankListAdapter
    private lateinit var baseAdapter: HeaderAndFooterRecyclerViewAdapter
    private val rankList = arrayListOf<RankBean>()




    override fun initLayout(): Int = R.layout.fragment_type_cycle_rank_list

    override fun initView() {

        swipeToLoadLayout = getViewById(R.id.swipeToLoadLayout)
        rankRecyclerView = getViewById(R.id.rankRecyclerView)

        swipeToLoadLayout.isLoadMoreEnabled = false
        swipeToLoadLayout.setOnRefreshListener(this)

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rankRecyclerView.layoutManager = layoutManager

        typeCycleRankListAdapter = TypeCycleRankListAdapter(requireContext(), type!!, rankList)
        baseAdapter = HeaderAndFooterRecyclerViewAdapter(typeCycleRankListAdapter)
        rankRecyclerView.adapter = baseAdapter

        header = TypeCycleRankListHeader(requireContext())
        baseAdapter.addHeaderView(header)

        setData()
    }

    override fun onRefresh() {
        onFinishRefresh()
    }

    fun onFinishRefresh() {
        swipeToLoadLayout.isRefreshing = false
    }


    fun setData(){

        val bean = RankBean("1", User("12", "http://b-ssl.duitang.com/uploads/item/201704/10/20170410073535_HXVfJ.thumb.700_0.jpeg", "阿斯顿说", 11, 20))
        bean.buyCount = 123
        bean.liveTime = 42
        bean.lollipopCount = 900
        rankList.add(bean)
        rankList.add(bean)
        rankList.add(bean)
        rankList.add(bean)
        rankList.add(bean)
        rankList.add(bean)
        rankList.add(bean)

        header.showData(type!!, bean, bean, bean)
        baseAdapter.notifyDataSetChanged()
    }
}