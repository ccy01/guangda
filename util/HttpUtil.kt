package com.example.ccy.tes.util

import android.content.Context
import android.os.Build
import android.widget.Toast
import com.example.ccy.tes.service.LoginHelper
import okhttp3.*
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern


/**
 * Http请求工具类
 *
 * @author lizhangqu
 * @date 2015-2-1
 */

/**
 * @author Administrator
 */
object HttpUtil {
    /**
     * 返回请求客户端
     *
     * @return
     */
    var client: OkHttpClient? = null
        private set // 实例话对象
    // Host地址

    var HOST = "202.192.18.182"

    // 登陆地址
    val URL_LOGIN = "https://cas.gzhu.edu.cn/cas_server/login?service=http%3a%2f%2f$HOST%2fLogin_gzdx.aspx"//登陆地址
    // 登录成功的首页
    var URL_MAIN:String = "http://$HOST/xs_main.aspx?xh=XH"//主页地址
    // 请求地址
    var URL_QUERY = "http://$HOST/QUERY"

    /**
     * 请求参数
     */

    var captcha = ""
    var warn = "true"
    var lt = ""
    var _eventId = "submit"
    var submit = "登陆"
    var username: String? = null
    var password: String? = null
    var execution = ""


  fun setClient(cookieJar: CookieJar) {
        client = OkHttpClient.Builder()
                .cookieJar(cookieJar).connectTimeout(10, TimeUnit.SECONDS)//设置链接超时
                .build()

    }





/*
    *//**//**
     * get,用一个完整url获取一个string对象
     *
     * @param urlString
     * @param callBack
     *//**//*
    operator fun get(urlString: String, callBack: Callback) {

        client!!.newCall(getRequest(urlString)).enqueue(callBack)
    }

    operator fun get(urlString: String, referer: String, callBack: Callback) {

        client!!.newCall(getRequest(urlString, referer)).enqueue(callBack)
    }


    fun post(urlString: String, referer: String, formBody: RequestBody, callBack: Callback) {
        client!!.newCall(getRequest(urlString, referer, formBody)).enqueue(callBack)
    }

    fun post(urlString: String, formBody: RequestBody, callBack: Callback) {
        client!!.newCall(getRequest(urlString, formBody)).enqueue(callBack)
    }

    fun getParams(formParams: Map<String, String>): String {

        val sb = StringBuffer()
        //设置表单参数
        for (key in formParams.keys) {
            sb.append(key + "=" + formParams[key] + "&")
        }
        return sb.toString()
    }*/

    /**
     * 获得登录时所需的请求参数
     *
     * @return
     */


    interface QueryCallback {
        fun handleResult(result: ByteArray): String //过一段时间调用这个东西，将代码作为参数
    }

    fun getQuery(context: Context, linkService: LoginHelper, urlName: String, callback: QueryCallback?) {
       /* val dialog = CommonUtil.getProcessDialog(context, "正在获取" + urlName)
        dialog.show()
        val link = linkService.getLinkByName(urlName)
        if (link != null) {
            HttpUtil.URL_QUERY = HttpUtil.URL_QUERY.replace("QUERY", link)
        } else {
            Toast.makeText(context, "链接出现错误", Toast.LENGTH_SHORT).show()
            return
        }*/

/*
        HttpUtil[HttpUtil.URL_QUERY, "http://202.192.18.182/xs_main.aspx?xh=1619300046", object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                dialog.dismiss()
                Toast.makeText(context, urlName + "获取失败！！！", Toast.LENGTH_SHORT).show()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                callback?.handleResult(response.body().bytes())
                Toast.makeText(context, urlName + "获取成功！！！", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
        }]*/
    }

    fun findCn(s: String): String? {

        val pattern = "[\u4e00-\u9fa5]+"
        val p = Pattern.compile(pattern)
        val result = p.matcher(s)
        return if (result.find()) {
            result.group()
        } else null
    }

}
