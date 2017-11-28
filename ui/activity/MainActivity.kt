package com.example.ccy.tes.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.example.ccy.tes.R
import com.example.ccy.tes.model.LinkNode
import com.example.ccy.tes.presenter.CoursePresenter
import com.example.ccy.tes.service.LoginHelper
import com.example.ccy.tes.util.LinkUtil
import com.example.ccy.tes.util.SharedPreferenceUtil

/**
 * 主页
 * Created by ccy on 2017/8/19.
 */

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var linkService: LoginHelper? = null


    fun getmAdapter(): RecyclerView.Adapter<*>? {
        return mAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main = this
        initValue()
        initView()

    }

    private fun initValue() {
        linkService = LoginHelper.getInstance()
    }


    private fun initView() {
        mRecyclerView = findViewById(R.id.recycler_view) as RecyclerView
        mRecyclerView!!.setHasFixedSize(true)
        mLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val list = linkService!!.findAll()
        mRecyclerView!!.layoutManager = mLayoutManager
        mAdapter = MyAdapter(list, this)
        mRecyclerView!!.adapter = mAdapter
        mRecyclerView!!.addItemDecoration(SpacesItemDecoration(16))
        mRecyclerView!!.setRecyclerListener { }

        val util = SharedPreferenceUtil(applicationContext, "accountInfo")
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.subtitle = util.getKeyData("username")

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.app_name, R.string.app_name)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

    }

    fun jump(arg: String?) {
        val intent: Intent
        when (arg) {
            LinkUtil.TYXK -> {
                intent = Intent(this@MainActivity, PhysicalActivity::class.java)
                startActivity(intent)
            }
            LinkUtil.QXXXXK -> {
                intent = Intent(this@MainActivity, XuanXiuActivity::class.java)
                startActivity(intent)
            }
            LinkUtil.CJCX -> {
                intent = Intent(this@MainActivity, ScoreActivity::class.java)
                startActivity(intent)
            }
            LinkUtil.XSXKQKCX -> {
                intent = Intent(this@MainActivity, SelectedActivity::class.java)
                startActivity(intent)
            }
            LinkUtil.ZYTJKBCX -> jump2Kb(false)

            LinkUtil.GRXX -> {
                intent = Intent(this@MainActivity, MessageActivity::class.java)
                startActivity(intent)
            }
        }
    }


    private fun jump2Kb(flag: Boolean) {
        val util = SharedPreferenceUtil(applicationContext, "flag")
        if (flag) {
            util.setKeyData(LinkUtil.ZYTJKBCX, "TRUE")
            val intent = Intent(this@MainActivity, CourseActivity::class.java)
            startActivity(intent)
        } else {
            //flag为false，则先判断是否获取过课表
            //如果已经获取过课表，则跳转
            val keyData = util.getKeyData(LinkUtil.ZYTJKBCX)
            if (keyData == "TRUE") {
                val intent = Intent(this@MainActivity, CourseActivity::class.java)
                startActivity(intent)
            } else {
                CoursePresenter(this).getCourse()
                jump2Kb(true)
            }
        }

    }


    override fun onBackPressed() {//把它收进去
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_cancel) {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else if (id == R.id.nav_about) {
            val intent = Intent(this@MainActivity, AboutActivity::class.java)
            startActivity(intent)
        } else if (id == R.id.nav_quit) {
            finish()
        }
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    internal inner class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
            outRect.left = space
            outRect.right = space
            outRect.bottom = space
            outRect.top = space

        }
    }

    companion object {
        var main: MainActivity? = null
    }


}

internal class MyAdapter(private val list: List<LinkNode>, var context: Context) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.view_holder, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = list[position].title
        when (list[position].title) {
            LinkUtil.QXXXXK -> {
                holder.textMsg.text = "课程:\n" + SharedPreferenceUtil(context.applicationContext, "xuanxiu_selected").getKeyData("select")
                holder.imageView.setImageDrawable(context.resources.getDrawable(LinkUtil.background[5]))
            }
            LinkUtil.TYXK -> {
                holder.textMsg.text = "课程:\n" + SharedPreferenceUtil(context.applicationContext, "physical").getKeyData("select")
                holder.imageView.setImageDrawable(context.resources.getDrawable(LinkUtil.background[4]))
            }
            LinkUtil.CJCX -> {
                holder.textMsg.text = "成绩:\n" + SharedPreferenceUtil(context.applicationContext, "score").getKeyData("score")
                holder.imageView.setImageDrawable(context.resources.getDrawable(LinkUtil.background[3]))
            }
            LinkUtil.GRXX -> {
                holder.textMsg.text = SharedPreferenceUtil(context.applicationContext, "message").getKeyData("message")
                holder.imageView.setImageDrawable(context.resources.getDrawable(LinkUtil.background[2]))
            }
            LinkUtil.ZYTJKBCX -> {
                holder.textMsg.text = "课程表:\n" + SharedPreferenceUtil(context.applicationContext, "courses").getKeyData("courses")
                holder.imageView.setImageDrawable(context.resources.getDrawable(LinkUtil.background[1]))
            }
            LinkUtil.XSXKQKCX -> {
                holder.textMsg.text = "选课:\n" + SharedPreferenceUtil(context.applicationContext, "selected").getKeyData("selected")
                holder.imageView.setImageDrawable(context.resources.getDrawable(LinkUtil.background[0]))
            }
        }

        holder.cardView.setOnClickListener { MainActivity.main?.jump(list[position].title) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var textView: TextView
        var cardView: CardView
        var textMsg: TextView

        init {
            imageView = itemView.findViewById(R.id.iv_view) as ImageView
            textView = itemView.findViewById(R.id.tv_name) as TextView
            cardView = itemView.findViewById(R.id.cardview) as CardView
            textMsg = itemView.findViewById(R.id.tv_meg) as TextView

        }
    }

}
