package com.example.ccy.tes.util

import android.content.Context
import android.content.SharedPreferences

/**
 * SharedPreference工具类
 *
 * @author lizhangqu
 * @date 2015-2-1
 */
class SharedPreferenceUtil(private val mContext: Context, private val mFileName: String) {

    /**
     * 保存键值对
     *
     * @param key
     * @param value
     */
    fun setKeyData(key: String, value: String) {
        val sharedPreferences = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun setKeyData(key: String, value: Boolean) {
        val sharedPreferences = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE)
        val e = sharedPreferences.edit()
        e.putBoolean(key, value)
        e.commit()
    }
    fun setKeyData(key: String, value: Int) {
        val sharedPreferences = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE)
        val e = sharedPreferences.edit()
        e.putInt(key, value)
        e.commit()
    }

    /**
     * 根据键得到值，如果为空返回""
     *
     * @param key
     * @return
     */
    fun getKeyData(key: String): String {
        val sharedPreferences = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "")
    }

    fun getKeyBooleanData(key: String): Boolean {
        val sharedPreferences = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, false)
    }
    fun getKeyIntData(key: String): Int {
        val sharedPreferences = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(key, 0)
    }

}
