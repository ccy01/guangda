package com.example.ccy.tes.presenter

import android.content.Context
import android.widget.Toast
import com.example.ccy.tes.retrofit.Constant
import com.example.ccy.tes.retrofit.RetrofitClient
import com.example.ccy.tes.service.ScoreService
import com.example.ccy.tes.ui.view.ScoreView
import com.example.ccy.tes.util.HttpUtil
import com.example.ccy.tes.util.SharedPreferenceUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.URLEncoder


/**
 * Created by ccy on 2017/11/25.
 */
class ScorePresenter(val context: Context,val mView:ScoreView) {

    private var home: String = ""
    private var name: String = ""
    private var link: String = ""
    var util: SharedPreferenceUtil? = null
    val api = RetrofitClient.getInstance(context, "http://202.192.18.182/")
    val scoreService = ScoreService.getInstance()
    var temp:Array<String>? = null

     fun getScore() {
        util = SharedPreferenceUtil(context.applicationContext, "accountInfo")
        home = HttpUtil.URL_MAIN.replace("XH", util!!.getKeyData("username"))
        name = util!!.getKeyData("name")
        link = "http://202.192.18.182/xscj_gc.aspx?xh=${Constant.username}&xm=${URLEncoder.encode(name, "gb2312")}&gnmkdm=N121605"
        if (name == "") {
            Toast.makeText(context, "数据出错", Toast.LENGTH_SHORT).show()
            return
        }
        api!!.getScore(home, Constant.username!!, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ body ->
                    val s =  String(body.bytes(), charset("GB2312"))
                    println(s)
                    temp = scoreService?.getArg(s)
                    getData()

                }, {
                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                })

    }

     fun getData() {

         val params = HashMap<String, String>()
         params.put("__VIEWSTATE", URLEncoder.encode(temp!![0],"gb2312"))
         params.put("__VIEWSTATEGENERATOR",  URLEncoder.encode(temp!![1],"gb2312"))
         params.put("ddlXN", URLEncoder.encode("2016-2017","gb2312"))
         params.put("ddlXQ",  URLEncoder.encode("2","gb2312"))
         params.put("Button1",  "%B0%B4%D1%A7%C6%DA%B2%E9%D1%AF")

         api!!.getScore(link, params, Constant.username!!, name)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe({ body ->
                     val s = String(body.bytes(), charset("GB2312"))
                     val list = scoreService!!.getScore(s)
                     mView.setList(list)
                     mView.setAdapter()
                     mView.save()

                 }, {
                     Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                 })

     }

}