package com.ti2.nguli.buttonHome

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ti2.nguli.MyData
import com.ti2.nguli.R
import com.ti2.nguli.data.BundleData
import kotlinx.android.synthetic.main.activity_content_bundle.*
import kotlinx.android.synthetic.main.activity_content_voucher.*
import kotlinx.android.synthetic.main.activity_content_voucher.iv_image_category
import kotlinx.android.synthetic.main.item_grid.*

class ContentBundleActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MYDATA = "extra_mydata"
    }

    inline fun <reified T : Parcelable> Activity.getParcelableExtra(key: String) = lazy {
        intent.getParcelableExtra<T>(key)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_bundle)
        val myData by getParcelableExtra<BundleData>(ContentBundleActivity.EXTRA_MYDATA)
        tv_content_bundle_desc.text = myData?.name.toString()
        Glide.with(this)
            .load(myData?.photo.toString())
            .apply(RequestOptions().override(700, 700))
            .into(iv_image_category)


    }
}