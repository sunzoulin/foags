package com.sbl.foags.activity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.sbl.foags.R
import com.sbl.foags.activity.main.my.MyCenterFragment
import com.sbl.foags.activity.main.oto.OTOVideoFragment
import com.sbl.foags.activity.main.photo.PrivatePhotoFragment
import com.sbl.foags.activity.main.selected.SelectedFragment
import com.sbl.foags.activity.main.video.VideoFragment
import com.sbl.foags.adapter.OnFragmentLoadListener
import com.sbl.foags.adapter.PagerViewFragmentAdapter
import com.sbl.foags.base.BaseActivity
import com.sbl.foags.base.BaseFragment
import com.sbl.foags.manager.ActivityManager
import com.sbl.foags.utils.SystemUtil
import com.sbl.foags.view.CanNotScrollViewPager
import com.sbl.foags.view.TableView

class MainActivity : BaseActivity(),  View.OnClickListener {


    companion object {

        /**
         * 此方法相当于创建新的MainActivity
         * */
        fun open(context: Context) {
            open(context, 0)
        }

        /**
         * 此方法相当于创建新的MainActivity
         * */
        fun open(context: Context, position: Int) {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("position", position)
            // 判断是否已经打开过MainActivity
            if (ActivityManager.getInstance().findActivityByClass(MainActivity::class.java) != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
            context.startActivity(intent)
        }
    }


    private lateinit var viewPager: CanNotScrollViewPager
    private var adapter: PagerViewFragmentAdapter? = null
    private var fragments: ArrayList<BaseFragment> = arrayListOf()

    private lateinit var tabOne: TableView
    private lateinit var tabTwo: TableView
    private lateinit var tabThree: TableView
    private lateinit var tabFour: TableView
    private lateinit var tabFive: TableView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.showBackTip = true

        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.mainViewPager)
        tabOne = findViewById(R.id.tab_one)
        tabTwo = findViewById(R.id.tab_two)
        tabThree = findViewById(R.id.tab_three)
        tabFour = findViewById(R.id.tab_four)
        tabFive = findViewById(R.id.tab_five)
        tabOne.setOnClickListener(this)
        tabTwo.setOnClickListener(this)
        tabThree.setOnClickListener(this)
        tabFour.setOnClickListener(this)
        tabFive.setOnClickListener(this)


        fragments.clear()
        fragments.add(SelectedFragment())
        fragments.add(VideoFragment())
        fragments.add(OTOVideoFragment())
        fragments.add(PrivatePhotoFragment())
        fragments.add(MyCenterFragment())

        viewPager.setCanScrollHorizontal(false)
        adapter = PagerViewFragmentAdapter(supportFragmentManager, object: OnFragmentLoadListener {

            override fun getPagerAdapterCount(): Int {
                return fragments.size
            }

            override fun getFragmentPosition(position: Int): Fragment {
                return fragments[position]
            }

            override fun getFragmentPositionTitle(position: Int): String {
                return ""
            }

        })
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = fragments.size


        selectTab(0)
    }



    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tab_one -> selectTab(0)
            R.id.tab_two -> selectTab(1)
            R.id.tab_three -> selectTab(2)
            R.id.tab_four -> selectTab(3)
            R.id.tab_five -> selectTab(4)
        }
    }

    private fun selectTab(position: Int) {

        tabOne.isChecked = position == 0
        tabTwo.isChecked = position == 1
        tabThree.isChecked = position == 2
        tabFour.isChecked = position == 3
        tabFive.isChecked = position == 4

        viewPager.currentItem = position
    }

    override fun showBackTip() {
        super.showBackTip()
        showErrorMsg(getString(R.string.double_click_exit))
    }

    override fun confirmBack() {
        super.confirmBack()
        SystemUtil.backHome(this)
    }
}