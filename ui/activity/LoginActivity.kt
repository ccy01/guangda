package com.example.ccy.tes.ui.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.example.ccy.tes.R
import com.example.ccy.tes.presenter.LoginPresenter
import com.example.ccy.tes.service.LoginHelper
import com.example.ccy.tes.ui.view.LoginView
import com.example.ccy.tes.util.HttpUtil
import com.example.ccy.tes.util.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_login.*

/*
登陆界面
 */
class LoginActivity : AppCompatActivity(), OnClickListener, LoginView {


    private var linkService: LoginHelper? = null
    private var dialog: ProgressDialog? = null
    var loginPresenter:LoginPresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initValue()
        initView()
        initEvent()

    }

    private fun initValue() {
        setCookie()
        linkService = LoginHelper.getInstance()
        loginPresenter = LoginPresenter(this,this)
        loginPresenter?.getLoginArg()


    }


    private fun initView() {
        val util = SharedPreferenceUtil(applicationContext, "accountInfo")
        swiperefreshlayout.isEnabled = false
        swiperefreshlayout.isRefreshing = true
        swiperefreshlayout.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        toolbar.title = "登陆"
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        cb_save.isChecked = util.getKeyBooleanData("isChecked")
        if (util.getKeyBooleanData("isChecked")) {
            et_username.setText(util.getKeyData("username"))
            et_password.setText(util.getKeyData("password"))
        }
    }

    private fun initEvent() {
        bt_login.setOnClickListener(this)
        service_one.setOnClickListener(this)
        service_two.setOnClickListener(this)
        service_three.setOnClickListener(this)

    }

    override fun joinToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun showToast(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun isChecked(): Boolean = cb_save.isChecked



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.login_menu, menu)
        return true
    }

    override fun getPassword(): String = et_password.text.toString().trim { it <= ' ' }

    override fun getUserName(): String = et_username.text.toString().trim { it <= ' ' }

    override fun setSwipeRefreshLayout(isRefresh: Boolean) {
        swiperefreshlayout.isRefreshing = isRefresh
    }

    override fun showDialog(isShow: Boolean) {
        if(isShow){
            if(dialog ==null){
                dialog = ProgressDialog(this)
                dialog?.setMessage("正在拼命加载中...")
                dialog?.show()


            }
        }else{
            dialog?.cancel()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_refresh) {

        }
        return true
    }

    fun setCookie() {
        //RetrofitClient.setCookie(applicationContext,true)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_login -> loginPresenter?.Login()
            R.id.service_one-> HttpUtil.HOST = "202.192.18.182"
            R.id.service_two -> HttpUtil.HOST = "202.192.18.183"
            R.id.service_three -> HttpUtil.HOST = "202.192.18.184"
        }
    }
}

