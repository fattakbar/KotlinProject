package com.example.kotlinproject.viewModel.home

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinproject.global.common.AppApplication
import com.example.kotlinproject.model.repository.news.NewsRepository
import com.example.kotlinproject.model.respo.newsChannel.NewsChanelRespo
import com.prodege.shopathome.model.networkCall.ApiResponse
import org.koin.core.KoinComponent

/**
 * Created by Deepak Sharma on 01/07/19.
 */
class HomeViewModel(application: AppApplication, private val projectRepository: NewsRepository) :
    AndroidViewModel(application), KoinComponent {
    private var channelResponse = MutableLiveData<ApiResponse<NewsChanelRespo>>()

    fun getNewsChannelLiveData(): MutableLiveData<ApiResponse<NewsChanelRespo>> {
        return channelResponse
    }

    fun newsChannelApi(strUrl: String) {
        projectRepository.getNewsChannel(strUrl, channelResponse)
    }

}