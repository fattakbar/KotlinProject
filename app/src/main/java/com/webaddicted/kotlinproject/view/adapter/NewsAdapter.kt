package com.webaddicted.kotlinproject.view.adapter

import androidx.databinding.ViewDataBinding
import com.webaddicted.kotlinproject.R
import com.webaddicted.kotlinproject.databinding.RowChannelListBinding
import com.webaddicted.kotlinproject.global.common.showImage
import com.webaddicted.kotlinproject.model.bean.newschannel.NewsChanelRespo
import com.webaddicted.kotlinproject.view.base.BaseAdapter

/**
 * Created by Deepak Sharma on 01/07/19.
 */
class NewsAdapter(private var newsList: ArrayList<NewsChanelRespo.Source>?) : BaseAdapter() {
    override fun getListSize(): Int {
        if (newsList == null) return 0
        return newsList?.size!!
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.row_channel_list
    }

    override fun onBindTo(mRowBinding: ViewDataBinding, position: Int) {
        if (mRowBinding is RowChannelListBinding) {
            val source = newsList?.get(position)
            mRowBinding.txtChannelName.text = source?.name
            mRowBinding.txtChannelDesc.text = source?.description
            val stringBuilder = "https://besticon-demo.herokuapp.com/icon?url=" + source?.url + "&size=64..64..120"
            mRowBinding.imgChannelImg.showImage(stringBuilder, getPlaceHolder(0))
        }
    }

    fun notifyAdapter(newsBeanList: ArrayList<NewsChanelRespo.Source>) {
        newsList = newsBeanList
        notifyDataSetChanged()
    }
}
