package com.ti2.nguli.buttonHome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.ti2.nguli.MyData
import com.ti2.nguli.R
import com.ti2.nguli.adapter.CategoryDataAdapter
import com.ti2.nguli.adapter.VoucherDataAdapter
import com.ti2.nguli.data.CategoryData
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.fragment_home.*

class CategoryActivity : AppCompatActivity() {

    private val list = ArrayList<CategoryData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        supportActionBar?.title = "Kategori"
        rv_item_category.setHasFixedSize(true)
        list.addAll(getListMyDatas())
        showRecyclerGrid()
    }

    fun getListMyDatas(): ArrayList<CategoryData> {
        val dataName = resources.getStringArray(R.array.data_name_category)
        val dataPhoto = resources.getStringArray(R.array.data_photo_category)
        val dataLat = resources.getStringArray(R.array.data_lat)
        val dataLang = resources.getStringArray(R.array.data_lang)
        val listMyData = ArrayList<CategoryData>()
        for (position in dataName.indices) {
            val myData = CategoryData(
                dataName[position],
                dataPhoto[position],
                dataLat[position].toDouble(),
                dataLang[position].toDouble()
            )
            listMyData.add(myData)
        }
        return listMyData
    }

    private fun showRecyclerGrid() {
        //rv_item_category.layoutManager = GridLayoutManager(this, 2)
        val gridMyDataAdapter = CategoryDataAdapter(list, this)
        rv_item_category.adapter = gridMyDataAdapter
    }
}