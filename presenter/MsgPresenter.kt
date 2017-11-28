package com.example.ccy.tes.presenter

import android.content.Context
import android.widget.Toast
import com.example.ccy.tes.retrofit.ApiService
import com.example.ccy.tes.retrofit.Constant
import com.example.ccy.tes.retrofit.RetrofitClient
import com.example.ccy.tes.ui.activity.MainActivity
import com.example.ccy.tes.ui.view.MessageView
import com.example.ccy.tes.util.FileUtils
import com.example.ccy.tes.util.HttpUtil
import com.example.ccy.tes.util.SharedPreferenceUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import java.io.UnsupportedEncodingException

/**
 * Created by ccy on 2017/11/10.
 */
class MsgPresenter(context: Context, messageView: MessageView) {
    val mView = messageView
    val context = context
    var api :ApiService? = null
    var imgUrl:String? = null

    fun getMsg() {

        val util = SharedPreferenceUtil(context.applicationContext, "accountInfo")
        val home = HttpUtil.URL_MAIN.replace("XH", util.getKeyData("username"))
        val name = util.getKeyData("name")
        if (name == "") {
            Toast.makeText(context, "数据出错", Toast.LENGTH_SHORT).show()
            return
        }
        mView.setRefreshing(true)
        api = RetrofitClient.getInstance(context, "http://202.192.18.182/")
        println("${Constant.username!!} $name   ")
        api!!.getMessage(home,Constant.username!!, name, "N121501")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ body ->
                    val s: String
                    try {
                        s = String(body.bytes(), charset("GB2312"))
                        mView.setText(getMessage(s))
                        getImg(imgUrl!!)
                    } catch (e: UnsupportedEncodingException) {
                        Toast.makeText(context, "数据出错", Toast.LENGTH_SHORT).show()
                    } finally {
                        mView.setRefreshing(false)
                    }
                }, { error ->
                    mView.setRefreshing(false)
                    Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
                })


    }

    fun getMessage(content: String): String {
        val builder = StringBuilder()
        val doc = Jsoup.parse(content, "GB2312")
        val rowRegex = "div.main_box div.mid_box span.formbox table.formlist tbody tr"//挑选条件
        val rowElements = doc.select(rowRegex)
        val ss = StringBuilder()
        ss.append(rowElements[0].select("TD span")[0].text() + rowElements[0].select("TD span")[1].text() + "\n")
        ss.append(rowElements[1].select("TD span")[0].text() + rowElements[1].select("TD span")[1].text() + "\n")
        ss.append(rowElements[12].select("TD span")[0].text() + rowElements[12].select("TD span")[1].text() + "\n")
        ss.append(rowElements[16].select("TD span")[0].text() + rowElements[16].select("TD span")[1].text() + "\n...")
        imgUrl = rowElements[0].select("td img#xszp").attr("src")

        val util = SharedPreferenceUtil(context.applicationContext, "message")
        util.setKeyData("message", ss.toString().trim { it <= ' ' })
        MainActivity.main!!.getmAdapter()!!.notifyDataSetChanged()
        for (i in rowElements.indices) {
            builder.append("\n")
            val elements = rowElements[i].select("TD span")
            for (j in elements.indices) {

                if (i == 0 && j == elements.size - 1)
                    break
                if (j % 2 != 0) {
                    builder.append(elements[j].text() + "\n")
                } else
                    builder.append(elements[j].text())
                println(elements[j].text() + " " + elements.size)
            }
        }

        return builder.toString()
    }

    fun getImg(path: String){
        api!!.getImg("http://202.192.18.182/xsgrxx.aspx?xh=1619300046&xm=%B2%DC%B4%CA%D3%EE&gnmkdm=N121501",path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ body ->
                    FileUtils.writeResponseBodyToDisk(context,body)
                },{error ->
                    Toast.makeText(context,error.message,Toast.LENGTH_SHORT).show()
                })
    }

}