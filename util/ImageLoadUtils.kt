package com.example.ccy.tes.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.ccy.tes.R

/**
 * Created by ccy on 2017/11/10.
 */
 class ImageLoadUtils {
    companion object {
        fun display(context: Context, imageView: ImageView?, url: String) {
            if (imageView == null)
                throw IllegalArgumentException("argument error")

            Glide.with(context).load(url)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_theme_graduating)
                    .crossFade().into(imageView)

        }
    }

}