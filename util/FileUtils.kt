package com.example.ccy.tes.util

import android.content.Context

import java.io.File.separator
import okhttp3.ResponseBody
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/**
 * Created by ccy on 2017/11/11.
 */
class FileUtils {
    companion object {
        fun writeResponseBodyToDisk(context: Context,body: ResponseBody) {
            try {
                val `is` = body.byteStream()
                //            File file = new File(path, "download.jpg");
                val suffix = ".jpg"
                val path = context.filesDir.toString() + separator + "loadingImg"
                val fileDr = File(path)
                if (!fileDr.exists()) {
                    fileDr.mkdir()
                }
                var file = File(path, "loadingImg" + suffix)
                if (file.exists()) {
                    file.delete()
                    file = File(path, "loadingImg" + suffix)
                }
                println(file.absolutePath)
                val fos = FileOutputStream(file)
                val bis = BufferedInputStream(`is`)
                val buffer = ByteArray(1024)
                var len = -1
                while (bis.read(buffer).apply { len = this } != -1) {
                    fos.write(buffer, 0, len)
                }
                fos.flush()
                fos.close()
                bis.close()
                `is`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }


    }
}