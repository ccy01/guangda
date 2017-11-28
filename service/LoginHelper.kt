package com.example.ccy.tes.service


import com.example.ccy.tes.util.LinkUtil
import com.example.ccy.tes.model.LinkNode
import org.jsoup.Jsoup
import org.litepal.crud.DataSupport


class LoginHelper private constructor() {

    private val filter = arrayOf(LinkUtil.QXXXXK, LinkUtil.TYXK, LinkUtil.GRXX, LinkUtil.CJCX, LinkUtil.XSXKQKCX, LinkUtil.ZYTJKBCX)

    fun getLinkByName(name: String): String? {
        val find = DataSupport.where("title=?", name).limit(1).find(LinkNode::class.java)
        return if (find.size != 0) {
            find[0].link
        } else {
            null
        }
    }

    fun save(linknode: LinkNode): Boolean {
        return linknode.save()
    }


    fun findAll(): List<LinkNode> {
        return DataSupport.findAll(LinkNode::class.java)

    }

    fun parseMenu(content: String): String {
        var linkNode: LinkNode? = null
        val result = StringBuilder()
        val doc = Jsoup.parse(content)
        val elements = doc.select("ul.nav a[target=zhuti]")
        for (element in elements) {
            for (s in filter) {
                if (s == element.text()) {
                    result.append(element.html() + "\n" + element.attr("href") + "\n\n")
                    linkNode = LinkNode()
                    linkNode.title = element.text()
                    linkNode.link = element.attr("href")
                    save(linkNode)
                }
            }

        }
        return result.toString()

    }

    fun isLogin(content: String): String? {//是否登录成功
        val doc = Jsoup.parse(content, "Gb2312")
        val elements = doc.select("span#xhxm")
        try {
            val element = elements[0]
            return element.text()
        } catch (e: IndexOutOfBoundsException) {

        }

        return null
    }

    companion object {
        @Volatile private var linkService: LoginHelper? = null

        fun getInstance(): LoginHelper? {//单例模式
            if (linkService == null) {
                synchronized(LoginHelper::class.java) {
                    if (linkService == null)
                        linkService = LoginHelper()
                }
            }

            return linkService
        }
    }


}
