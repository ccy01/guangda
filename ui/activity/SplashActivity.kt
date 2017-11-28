package com.example.ccy.tes.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.example.ccy.tes.util.HttpUtil
import com.example.ccy.tes.R
import com.example.ccy.tes.util.SharedPreferenceUtil
import com.example.ccy.tes.model.LinkNode
import com.example.ccy.tes.presenter.SplashPresenter
import com.example.ccy.tes.retrofit.RetrofitClient
import com.example.ccy.tes.ui.view.SplashView
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import org.litepal.crud.DataSupport
import java.io.IOException
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response

/*
    开始界面，最开始进入程序是的启动界面。
 */
class SplashActivity : AppCompatActivity(),SplashView{
    private val presenter = SplashPresenter(this,this)
    override fun goLogin() {
        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun goHome() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun setCookie() {
       // RetrofitClient.setCookie(applicationContext,false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        val util = SharedPreferenceUtil(applicationContext, "accountInfo")
        val isLogin = util.getKeyData("isLogin")
        println(isLogin)
        if (isLogin == "TRUE") {
            println("这有没有")
            setCookie()
            presenter.checkIsLogin()

        } else {
            Handler().postDelayed({
                goLogin()
            }, 2000)
        }

    }


}
