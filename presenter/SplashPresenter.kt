package com.example.ccy.tes.presenter

import android.content.Context
import com.example.ccy.tes.model.LinkNode
import com.example.ccy.tes.retrofit.ApiService
import com.example.ccy.tes.retrofit.RetrofitClient
import com.example.ccy.tes.ui.view.SplashView
import com.example.ccy.tes.util.HttpUtil
import com.example.ccy.tes.util.SharedPreferenceUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.litepal.crud.DataSupport

/**
 * Created by ccy on 2017/11/11.
 */
class SplashPresenter(context: Context,splashView: SplashView) {

    val mView = splashView
    val context = context

     fun checkIsLogin() {
        val util = SharedPreferenceUtil(context.applicationContext, "accountInfo")
        val username = util.getKeyData("username")
        HttpUtil.URL_MAIN = HttpUtil.URL_MAIN.replace("XH", username)


        if (username != "") {
            println("登陆检查")
            val apiService: ApiService = RetrofitClient.getInstance(context, "https://cas.gzhu.edu.cn/")
            apiService.getLoginArg()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({ body ->
                        mView.goHome()
                    },{
                        DataSupport.deleteAll(LinkNode::class.java)
                        val util = SharedPreferenceUtil(context.applicationContext, "accountInfo")
                        util.setKeyData("isLogin", "FALSE")
                        mView.goLogin()
                    })
        }

    }
}