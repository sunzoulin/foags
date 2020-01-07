package com.sbl.foags.activity.addressbook

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.flyco.tablayout.SlidingScaleTabLayout
import com.sbl.foags.R
import com.sbl.foags.activity.addressbook.data.AddressBookType
import com.sbl.foags.adapter.OnFragmentLoadListener
import com.sbl.foags.adapter.PagerViewFragmentAdapter
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.utils.statusbar.StatusBarUtil


class AddressBookActivity: BaseActivity(), View.OnClickListener, OnFragmentLoadListener {

    private val titles = arrayOf(
        UIUtils.getString(R.string.friend),
        UIUtils.getString(R.string.follow),
        UIUtils.getString(R.string.fans)
    )

    private val type = arrayOf(
        AddressBookType.Friend,
        AddressBookType.Follow,
        AddressBookType.Fans
    )


    private lateinit var backView: ImageView
    private lateinit var titleView: TextView
    private lateinit var tabLayout: SlidingScaleTabLayout
    private lateinit var viewPager: ViewPager

    companion object {

        @JvmStatic
        fun open(context: Context, position: Int) {
            val intent = Intent(context, AddressBookActivity::class.java)
            intent.putExtra("position", position)
            context.startActivity(intent)
        }
    }


    override fun initStatusBarMode() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white)
    }

    override fun initLayout(): Int = R.layout.activity_address_book

    override fun initView() {
        bindViews()
    }

    override fun loadData() {
        titleView.text = UIUtils.getString(R.string.address_book)
    }

    private fun bindViews(){
        backView = findViewById(R.id.backView)
        titleView = findViewById(R.id.titleView)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        backView.setOnClickListener(this)
        viewPager.offscreenPageLimit = pagerAdapterCount
        viewPager.adapter = PagerViewFragmentAdapter(supportFragmentManager, this)
        tabLayout.setViewPager(viewPager)
        viewPager.currentItem = intent.getIntExtra("position", 0)
    }

    override fun onClick(v: View?) {
        when(v){
            backView -> {
                finish()
            }
        }
    }

    override fun getFragmentPositionTitle(position: Int): String = titles[position]

    override fun getFragmentPosition(position: Int): Fragment {
        val fragment = AddressBookFragment()
        fragment.myTag = position
        fragment.type = type[position]
        return fragment
    }

    override fun getPagerAdapterCount(): Int = titles.size
}