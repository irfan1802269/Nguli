package com.ti2.nguli.buttonHome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.ti2.nguli.MyData
import com.ti2.nguli.R
import com.ti2.nguli.adapter.GridMyDataAdapter
import com.ti2.nguli.adapter.VoucherDataAdapter
import kotlinx.android.synthetic.main.activity_voucher.*
import kotlinx.android.synthetic.main.fragment_home.*

class VoucherActivity : AppCompatActivity() {
    private val list = ArrayList<MyData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voucher)
        rv_item_voucher.setHasFixedSize(true)
        list.addAll(getListMyDatas())
        showRecyclerGrid()
        supportActionBar?.title = "Voucher"
    }

    fun getListMyDatas(): ArrayList<MyData> {
        val dataName = resources.getStringArray(R.array.data_name_voucher)
        val dataDescription = resources.getStringArray(R.array.data_description_voucher)
        val dataPhoto = resources.getStringArray(R.array.data_photo_voucher)
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
        rv_item_voucher.layoutManager = GridLayoutManager(this, 1)
        val gridMyDataAdapter = VoucherDataAdapter(list, this)
        rv_item_voucher.adapter = gridMyDataAdapter
    }
}