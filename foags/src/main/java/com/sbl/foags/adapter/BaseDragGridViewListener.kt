package com.sbl.foags.adapter;

import java.util.*


interface BaseDragGridViewListener<T> {

    /**
     * 点击删除按键
     */
    fun onItemDeleteClick(position: Int)

    /**
     * 点击添加按键
     */
    fun onItemAddClick()

    /**
     * 修改顺序
     */
    fun moveChange(arrayList: ArrayList<T>)
}