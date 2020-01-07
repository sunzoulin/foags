package com.sbl.foags.activity.cube.comment

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.sbl.foags.R
import com.sbl.foags.activity.cube.comment.data.CommentBean
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.common.cache.RxACache
import com.sbl.foags.user.User
import com.sbl.foags.user.UserFollowStatus
import com.sbl.foags.utils.KeyBoardUtil
import com.sbl.foags.utils.ScreenUtils
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.utils.statusbar.StatusBarUtil
import com.sbl.foags.view.recycler.FloatHeaderAndFooterRecyclerView
import com.sbl.foags.view.recycler.other.HeaderAndFooterRecyclerViewAdapter


class AllCommentActivity: BaseActivity(), View.OnClickListener, OnRefreshListener,
    TextView.OnEditorActionListener {


    private lateinit var backView: ImageView
    private lateinit var titleView: TextView
    private lateinit var commentEditView: EditText
    private lateinit var swipeToLoadLayout: SwipeToLoadLayout
    private lateinit var commentsRecyclerView: FloatHeaderAndFooterRecyclerView

    private lateinit var commentsAdapter: AllCommentAdapter
    private lateinit var baseAdapter: HeaderAndFooterRecyclerViewAdapter
    private val comments = arrayListOf<CommentBean>()

    override fun initStatusBarMode() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white)
    }

    override fun initLayout(): Int = R.layout.activity_all_comment


    override fun initView() {
        bindViews()

        setData()
    }

    private fun bindViews(){
        backView = findViewById(R.id.backView)
        titleView = findViewById(R.id.titleView)
        commentEditView = findViewById(R.id.commentEditView)
        swipeToLoadLayout = findViewById(R.id.swipeToLoadLayout)
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView)

        backView.setOnClickListener(this)
        commentEditView.setOnEditorActionListener(this)
        swipeToLoadLayout.isLoadMoreEnabled = false
        swipeToLoadLayout.setOnRefreshListener(this)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        commentsRecyclerView.layoutManager = layoutManager

        commentsAdapter = AllCommentAdapter(this, comments)
        baseAdapter = HeaderAndFooterRecyclerViewAdapter(commentsAdapter)
        commentsRecyclerView.adapter = baseAdapter
    }

    override fun loadData() {
        titleView.text = UIUtils.getString(R.string.all_comment)
    }

    override fun onClick(v: View?) {
        when(v){
            backView -> {
                finish()
            }
        }
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

        val commentBean = CommentBean("11", bean, "21分钟前", "我好想和你一起去旅行")

        comments.add(commentBean)
        comments.add(commentBean)
        comments.add(commentBean)
        comments.add(commentBean)
        comments.add(commentBean)
        comments.add(commentBean)
        comments.add(commentBean)

        baseAdapter.notifyDataSetChanged()
    }


    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if(actionId == EditorInfo.IME_ACTION_SEND && commentEditView.text.isNotEmpty()){
            val commentBean = CommentBean("999", RxACache.getInstance().user, "刚刚", commentEditView.text.toString())
            comments.add(0, commentBean)
            baseAdapter.notifyDataSetChanged()

            commentEditView.text.clear()

            KeyBoardUtil.closeKeybord(commentEditView, this)
            commentEditView.clearFocus()
            return true
        }
        return false
    }
}