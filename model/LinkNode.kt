package com.example.ccy.tes.model

import org.litepal.crud.DataSupport


class LinkNode : DataSupport() {
    var id: Int = 0
    var title: String? = null
    var link: String? = null

    override fun toString(): String {
        return ("LinkNode [id=" + id + ", title=" + title + ", link=" + link
                + "]")
    }


}
