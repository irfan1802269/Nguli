package com.ti2.nguli.buttonHome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.ti2.nguli.MyData
import com.ti2.nguli.R
import com.ti2.nguli.adapter.BundleDataAdapter
import com.ti2.nguli.adapter.CategoryDataAdapter
import kotlinx.android.synthetic.main.activity_bundle.*
import kotlinx.android.synthetic.main.activity_category.*

class BundleActivity : AppCompatActivity() {
    private val list = ArrayList<MyData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bundle)
        supportActionBar?.title = "Bundle"
        rv_item_bundle.setHasFixedSize(true)
        list.addAll(getListMyDatas())
        showRecyclerGrid()
    }

    fun getListMyDatas(): ArrayList<MyData> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.getStringArray(R.array.data_photo)
        val dataLat = resources.getStringArray(R.array.data_lat)
        val dataLang = resources.getStringArray(R.array.data_lang)
        val listMyData = ArrayList<MyData>()
        for (position in dataName.indices) {
            val myData = MyData(
                dataName[position],
                dataDescription[position],
                dataPhoto[position],
                dataLat[position].toDouble(),
                dataLang[position].toDouble()
            )
            listMyData.add(myData)
        }
        return listMyData
    }

    private fun showRecyclerGrid() {
        rv_item_bundle.layoutManager = GridLayoutManager(this, 1)
        val gridMyDataAdapter = BundleDataAdapter(list)
        rv_item_bundle.adapter = gridMyDataAdapter
    }
}