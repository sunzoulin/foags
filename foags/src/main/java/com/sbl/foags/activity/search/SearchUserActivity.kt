package com.sbl.foags.activity.search

import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.sbl.foags.R
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.bean.User
import com.sbl.foags.utils.statusbar.StatusBarUtil
import com.sbl.foags.view.recycler.FloatHeaderAndFooterRecyclerView
import com.sbl.foags.view.recycler.other.HeaderAndFooterRecyclerViewAdapter


class SearchUserActivity: BaseActivity(), TextWatcher {

    private lateinit var searchView: EditText
    private lateinit var searchClearView: TextView
    private lateinit var closeView: TextView
    private lateinit var searchRecyclerView: FloatHeaderAndFooterRecyclerView


    private lateinit var searchUserAdapter: SearchUserAdapter
    private lateinit var baseAdapter: HeaderAndFooterRecyclerViewAdapter
    private val modelList = arrayListOf<User>()


    override fun initStatusBarMode() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white)
    }

    override fun initLayout(): Int = R.layout.activity_search_user


    override fun initView() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        searchView = findViewById(R.id.searchView)
        searchClearView = findViewById(R.id.searchClearView)
        closeView = findViewById(R.id.closeView)
        searchRecyclerView = findViewById(R.id.searchRecyclerView)

        closeView.setOnClickListener{ finish() }


        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        searchRecyclerView.layoutManager = layoutManager

        searchUserAdapter = SearchUserAdapter(this, modelList)
        baseAdapter = HeaderAndFooterRecyclerViewAdapter(searchUserAdapter)
        searchRecyclerView.adapter = baseAdapter

        searchView.isFocusable = true
        searchView.isFocusableInTouchMode = true
        searchView.requestFocus()
        searchView.addTextChangedListener(this)

        searchClearView.setOnClickListener { searchView.text.clear() }
    }

    override fun loadData() {
    }

    override fun afterTextChanged(s: Editable?) {
        if(s != null && s.isNotEmpty()){
            modelList.clear()

            val bean = User("12", "http://b-ssl.duitang.com/uploads/item/201704/10/20170410073535_HXVfJ.thumb.700_0.jpeg", "阿斯${s}顿说${s}", 11, 20)
            bean.tags = arrayListOf("平面模特", "网红大V")

            modelList.add(bean)
            modelList.add(bean)
            modelList.add(bean)
            modelList.add(bean)
            modelList.add(bean)
            modelList.add(bean)
            modelList.add(bean)


            searchUserAdapter.setSearchResult(s.toString())
            baseAdapter.notifyDataSetChanged()

        }else{
            modelList.clear()

            searchUserAdapter.setSearchResult("")
            baseAdapter.notifyDataSetChanged()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}