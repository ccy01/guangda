package com.example.ccy.tes.presenter

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import com.example.ccy.tes.model.bean.LoginBean
import com.example.ccy.tes.retrofit.ApiService
import com.example.ccy.tes.retrofit.Constant
import com.example.ccy.tes.retrofit.RetrofitClient
import com.example.ccy.tes.service.LoginHelper
import com.example.ccy.tes.ui.view.LoginView
import com.example.ccy.tes.util.SharedPreferenceUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import java.io.UnsupportedEncodingException

/**
 * Created by ccy on 2017/11/9.
 */
class LoginPresenter(context: Context, view: LoginView) {
    val mContext: Context? = context
    val mView: LoginView? = view
    var loginBean: LoginBean? = null
    val apiService: ApiService = RetrofitClient.getInstance(mContext!!, "https://cas.gzhu.edu.cn/")

    fun Login() {
        if (TextUtils.isEmpty(mView?.getUserName()) || TextUtils.isEmpty(mView?.getPassword())) {
            return
        }
        Constant.username = mView?.getUserName()
        Constant.password = mView?.getPassword()

        mView?.showDialog(true)

        val map = HashMap<String, String>()
        map.put("username", mView!!.getUserName())
        map.put("password", mView!!.getPassword())
        map.put("captcha", "")
        map.put("warn", "true")
        map.put("lt", loginBean!!.lt)
        map.put("execution", loginBean!!.execution)
        map.put("_eventId", "submit")
        map.put("submit", "登录")

        println(map.toString())

        val headers = HashMap<String, String>()
        headers.put("Content-Type", "application/x-www-form-urlencoded")
        headers.put("Referer", "https://cas.gzhu.edu.cn/cas_server/login?service=http%3a%2f%2f202.192.18.182%2fLogin_gzdx.aspx")
        apiService.login(headers, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ body ->
                    mView.showDialog(false)
                    getHome(body.string())
                },{ error ->
                    Toast.makeText(mContext,error.message,Toast.LENGTH_SHORT).show()
                    mView.showDialog(false)
                })
    }

    /*

     */
    fun getLoginArg() {
        mView?.setSwipeRefreshLayout(true)

        loginBean = LoginBean()
        apiService.getLoginArg()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ body ->
                    val s: String
                    try {
                        s = String(body.bytes(), Charsets.UTF_8)
                        loginBean?.lt = Jsoup.parse(s).select("input[name=lt]").`val`()
                        loginBean?.execution = Jsoup.parse(s).select("input[name=execution]").`val`()
                        println("${loginBean?.lt}    ${loginBean?.execution}")

                    } catch (e: UnsupportedEncodingException) {

                    } finally {
                        mView?.setSwipeRefreshLayout(false)

                    }
                },{
                    error ->
                    Toast.makeText(mContext,error.message,Toast.LENGTH_SHORT).show()
                    mView?.setSwipeRefreshLayout(false)

                })

    }

    fun getHome(resultContent: String) {
        val linkService = LoginHelper.getInstance()
        val name = with(linkService!!.isLogin(resultContent)!!.split(" ")[1]){ substring(0,length-2) }
        println(name)
        if (name != "") {
            val ret = linkService.parseMenu(resultContent)
            println(ret)
            jumpToMain(name)
        }
    }

    fun jumpToMain(name:String) {
        //保存信息
        val util = SharedPreferenceUtil(mContext!!.applicationContext, "accountInfo")
        util.setKeyData("username", mView!!.getUserName())
        util.setKeyData("name", name)
        util.setKeyData("password", mView!!.getPassword())
        util.setKeyData("isLogin", "TRUE")
        util.setKeyData("isChecked", mView!!.isChecked())
        mView.joinToMain()
    }


}