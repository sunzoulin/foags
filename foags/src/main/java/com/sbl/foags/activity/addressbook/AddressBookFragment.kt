package com.sbl.foags.activity.addressbook

import androidx.recyclerview.widget.LinearLayoutManager
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.sbl.foags.R
import com.sbl.foags.activity.addressbook.data.AddressBookType
import com.sbl.foags.base.BaseFragment
import com.sbl.foags.user.User
import com.sbl.foags.user.UserFollowStatus
import com.sbl.foags.view.recycler.FloatHeaderAndFooterRecyclerView
import com.sbl.foags.view.recycler.other.HeaderAndFooterRecyclerViewAdapter


class AddressBookFragment : BaseFragment(), OnRefreshListener {


    var type: AddressBookType? = null

    private lateinit var swipeToLoadLayout: SwipeToLoadLayout
    private lateinit var usersRecyclerView: FloatHeaderAndFooterRecyclerView

    private lateinit var userListAdapter: AddressBookAdapter
    private lateinit var baseAdapter: HeaderAndFooterRecyclerViewAdapter
    private val userList = arrayListOf<User>()


    override fun initLayout(): Int = R.layout.fragment_address_book

    override fun initView() {

        rootView.tag = myTag

        swipeToLoadLayout = getViewById(R.id.swipeToLoadLayout)
        usersRecyclerView = getViewById(R.id.usersRecyclerView)

        swipeToLoadLayout.isLoadMoreEnabled = false
        swipeToLoadLayout.setOnRefreshListener(this)

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        usersRecyclerView.layoutManager = layoutManager

        userListAdapter = AddressBookAdapter(requireContext(), type!!, userList)
        baseAdapter = HeaderAndFooterRecyclerViewAdapter(userListAdapter)
        usersRecyclerView.adapter = baseAdapter

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
        bean.level = 11
        bean.memberLevel = 12
        bean.fansCount = 300
        bean.address = "北京"
        bean.followStatus = UserFollowStatus.Already
        bean.followCount = 100
        bean.tags = arrayListOf("平面模特", "网红大V")

        userList.add(bean)
        userList.add(bean)
        userList.add(bean)
        userList.add(bean)
        userList.add(bean)
        userList.add(bean)
        userList.add(bean)

        baseAdapter.notifyDataSetChanged()
    }
}
