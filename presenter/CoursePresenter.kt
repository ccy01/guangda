package com.example.ccy.tes.presenter

import android.content.Context
import android.widget.Toast
import com.example.ccy.tes.retrofit.Constant
import com.example.ccy.tes.retrofit.RetrofitClient
import com.example.ccy.tes.service.CourseService
import com.example.ccy.tes.util.HttpUtil
import com.example.ccy.tes.util.SharedPreferenceUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.URLEncoder

/**
 * Created by ccy on 2017/11/26.
 */
class CoursePresenter(val context: Context) {

    var util: SharedPreferenceUtil? = null
    val api = RetrofitClient.getInstance(context, "http://202.192.18.182/")
    val courseService = CourseService.getInstance()
    private var home: String = ""
    private var name: String = ""
    private var link: String = ""


    fun getCourse() {

        util = SharedPreferenceUtil(context.applicationContext, "accountInfo")
        home = HttpUtil.URL_MAIN.replace("XH", util!!.getKeyData("username"))
        name = util!!.getKeyData("name")
        link = "http://202.192.18.182/tjkbcx.aspx?xh=${Constant.username}&xm=${URLEncoder.encode(name, "gb2312")}&gnmkdm=N121601"
        if (name == "") {
            Toast.makeText(context, "数据出错", Toast.LENGTH_SHORT).show()
            return
        }

        /*val dialog = CommonUtil.getProcessDialog(context, "正在获取" + urlName)
         dialog.show()
         val link = linkService.getLinkByName(urlName)
         if (link != null) {
             HttpUtil.URL_QUERY = HttpUtil.URL_QUERY.replace("QUERY", link)
         } else {
             Toast.makeText(context, "链接出现错误", Toast.LENGTH_SHORT).show()
             return
         }*/

        api.getCourse(home, Constant.username!!, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ body ->
                    val s = String(body.bytes(), charset("gb2312"))
                    println(courseService!!.parseCourse(s))
                }, {
                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                })


    }
}