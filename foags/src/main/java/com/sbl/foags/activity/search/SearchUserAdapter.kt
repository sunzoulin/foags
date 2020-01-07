package com.sbl.foags.activity.search

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sbl.foags.R
import com.sbl.foags.user.User
import com.sbl.foags.utils.UIUtils
import com.sbl.foags.view.recycler.adapter.BaseRecycleViewAdapter
import java.util.*
import java.util.regex.Pattern


class SearchUserAdapter(val context: Context, list: ArrayList<User>) : BaseRecycleViewAdapter<User>(context, list) {

    private var searchResult: String = ""

    fun setSearchResult(searchResult: String){
        this.searchResult = searchResult
    }

    override fun getLayoutId(viewType: Int): Int = R.layout.adapter_item_search_user

    override fun convert(holder: ViewHolder, item: User, position: Int) {
        val headPicView = holder.getView<ImageView>(R.id.headPicView)
        val nickNameView = holder.getView<TextView>(R.id.nickNameView)
        val tagsView = holder.getView<TextView>(R.id.tagsView)

        Glide.with(context).load(item.headPic).transform(CircleCrop()).into(headPicView)

        nickNameView.text = matcherSearchTitle(UIUtils.getColor(R.color.color_FF3865), item.nickName, searchResult)

        var tagsResult = ""

        if(!item.tags.isNullOrEmpty()){
            for(t in item.tags!!){
                tagsResult = "$tagsResult$t "
            }
        }

        if(tagsResult.isEmpty()){
            tagsView.visibility = View.INVISIBLE
        }else{
            tagsView.visibility = View.VISIBLE
            tagsView.text = tagsResult
        }
    }


    fun matcherSearchTitle(color: Int, text1: String, keyword1: String): SpannableString {
        var s = SpannableString(text1)
        var mykeyword = escapeExprSpecialWord(keyword1)
        var myText = escapeExprSpecialWord(text1)

        if (myText.contains(mykeyword) && !TextUtils.isEmpty(mykeyword)){
            try {
                val p = Pattern.compile(mykeyword)
                val m = p.matcher(s)
                while (m.find()) {
                    val start = m.start()
                    val end = m.end()
                    s.setSpan(ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }catch (e: Exception){
            }
        }
        return s
    }

    fun escapeExprSpecialWord(keyword: String): String {
        var myKeyword = keyword
        if (!TextUtils.isEmpty(myKeyword)) {
            val fbsArr = arrayListOf("\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" )
            for (key in fbsArr) {
                if (myKeyword.contains(key)) {
                    myKeyword = myKeyword.replace(key, "\\" + key)
                }
            }
        }
        return myKeyword
    }
}