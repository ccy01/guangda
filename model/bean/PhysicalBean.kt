package com.example.ccy.tes.model.bean

/**
 * Created by ccy on 2017/6/28.
 */
/*
 */


data class PhysicalBean(
        var courseName: String = "",
        var teacherName: String = "",
        var courseTime: String = "",
        var coursePlace: String = "",
        var score: String = "",
        var studyTime: String = "",
        var capacity: String = "",
        var selected: String = "",
        var last: String = "",
        var more: String = "",
        var phone: String = "",
        var isCheck_c: Boolean = false,
        var isCheck_b: Boolean = false,
        var tv_capacity: String = "",
        var courseCode: String = "",
        var tv_last: String = "",
        var expand: Boolean = false,
        var type: Int = 0,
        var ID: Int = 0
) : BaseBean {
    companion object {
        val PARENT_ITEM = 0//父布局
        val CHILD_ITEM = 1//子布局
    }
}
