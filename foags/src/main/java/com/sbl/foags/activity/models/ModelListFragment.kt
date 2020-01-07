package com.sbl.foags.activity.models

import androidx.recyclerview.widget.LinearLayoutManager
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.sbl.foags.R
import com.sbl.foags.activity.models.data.ModelListType
import com.sbl.foags.base.BaseFragment
import com.sbl.foags.user.User
import com.sbl.foags.user.UserFollowStatus
import com.sbl.foags.view.recycler.FloatHeaderAndFooterRecyclerView
import com.sbl.foags.view.recycler.other.HeaderAndFooterRecyclerViewAdapter


class ModelListFragment : BaseFragment(), OnRefreshListener {


    var type: ModelListType? = null


    private lateinit var swipeToLoadLayout: SwipeToLoadLayout
    private lateinit var modelsRecyclerView: FloatHeaderAndFooterRecyclerView

    private lateinit var modelListAdapter: ModelListAdapter
    private lateinit var baseAdapter: HeaderAndFooterRecyclerViewAdapter
    private val modelList = arrayListOf<User>()


    override fun initLayout(): Int = R.layout.fragment_model_list

    override fun initView() {

        rootView.tag = myTag

        swipeToLoadLayout = getViewById(R.id.swipeToLoadLayout)
        modelsRecyclerView = getViewById(R.id.modelsRecyclerView)

        swipeToLoadLayout.isLoadMoreEnabled = false
        swipeToLoadLayout.setOnRefreshListener(this)

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        modelsRecyclerView.layoutManager = layoutManager

        modelListAdapter = ModelListAdapter(requireContext(), type!!, modelList)
        baseAdapter = HeaderAndFooterRecyclerViewAdapter(modelListAdapter)
        modelsRecyclerView.adapter = baseAdapter

        setData()
    }

    override fun onRefresh() {
        onFinishRefresh()
    }

    fun onFinishRefresh() {
        swipeToLoadLayout.isRefreshing = false
    }

    fun setData(){

        val bean = User("12", "http://b-ssl.duitang.com/uploads/item/201704/10/20170410073535_HXVfJ.thumb.700_0.jpeg", "阿斯顿说")
        bean.level = 14
        bean.memberLevel = 65

        bean.fansCount = 300
        bean.address = "北京"
        bean.followStatus = UserFollowStatus.Already
        bean.followCount = 100
        bean.tags = arrayListOf("平面模特", "网红大V")

        modelList.add(bean)
        modelList.add(bean)
        modelList.add(bean)
        modelList.add(bean)
        modelList.add(bean)
        modelList.add(bean)
        modelList.add(bean)

        baseAdapter.notifyDataSetChanged()
    }
}
