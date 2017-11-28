package com.example.ccy.tes.retrofit

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


/**
 * Created by ccy on 2017/11/9.
 */
class RetrofitClient private constructor(context: Context, baseUrl: String) {
    var context: Context? = null
    var retrofit: Retrofit? = null
     val DEFAULT_TIMEOUT: Long = 10
    private var  cookieJar: PersistentCookieJar? = null
    init {
        this.context = context
        if (okHttpClient == null) {
            cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context.applicationContext))

            okHttpClient = OkHttpClient.Builder()
                    .cookieJar(cookieJar!!)
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build()
        }
        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build()
    }

    companion object {
         var okHttpClient: OkHttpClient? = null
        private var instance: ApiService? = null
        private var url: String = ""

        fun getInstance(context: Context, baseUrl: String): ApiService {
            if (url != baseUrl) {
                instance = null
            }
            if (instance == null) {
                synchronized(RetrofitClient::class) {
                    if (instance == null) {
                        url = baseUrl
                        val retrofitClient = RetrofitClient(context, baseUrl)
                        instance = retrofitClient.retrofit?.create(ApiService::class.java)
                    }
                }
            }
            return instance!!
        }
       /* fun setCookie(context: Context,isClear:Boolean){
           // if(cookieJar == null){
                cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context.applicationContext))

            //}
        }*/
    }
}