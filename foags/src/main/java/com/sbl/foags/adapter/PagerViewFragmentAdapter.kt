package com.sbl.foags.adapter

import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class PagerViewFragmentAdapter(fm: FragmentManager, private val listener: OnFragmentLoadListener) : FragmentStatePagerAdapter(fm) {

    /**
     * 获取当前的Fragment
     */
    var currentFragment: Fragment? = null
        private set

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        currentFragment = `object` as Fragment
        super.setPrimaryItem(container, position, `object`)
    }

    override fun getItem(position: Int): Fragment {
        return listener.getFragmentPosition(position)
    }

    override fun getCount(): Int {
        return listener.getPagerAdapterCount()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return listener.getFragmentPositionTitle(position)
    }

    override fun getItemPosition(`object`: Any): Int {
        val view = `object` as View
        if (view.tag == null) {
            return -1
        }
        return view.tag as Int
    }

    override fun saveState(): Parcelable? {
        return null
    }
}
