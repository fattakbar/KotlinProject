package com.webaddicted.kotlinproject.model.repository.news

import androidx.lifecycle.MutableLiveData
import com.webaddicted.kotlinproject.apiUtils.ApiResponse
import com.webaddicted.kotlinproject.apiUtils.ApiServices
import com.webaddicted.kotlinproject.apiUtils.DataFetchCall
import com.webaddicted.kotlinproject.model.bean.newsChannel.NewsChanelRespo
import kotlinx.coroutines.Deferred
import org.koin.core.KoinComponent
import retrofit2.Response
/**
 * Created by Deepak Sharma on 01/07/19.
 */
class NewsRepository constructor(private val apiServices: ApiServices) : KoinComponent {
    fun getNewsChannel(strUrl: String, loginResponse: MutableLiveData<ApiResponse<NewsChanelRespo>>) {
        object : DataFetchCall<NewsChanelRespo>(loginResponse) {
            override fun createCallAsync(): Deferred<Response<NewsChanelRespo>> {
                return apiServices.getChannelListName(strUrl)
            }

            override fun shouldFetchFromDB(): Boolean {
                return false
            }
        }.execute()
    }
}