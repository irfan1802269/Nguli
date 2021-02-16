package com.ti2.nguli.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import com.ti2.nguli.*
import com.ti2.nguli.adapter.GridMyDataAdapter
import com.ti2.nguli.buttonHome.*
import com.ti2.nguli.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), View.OnClickListener {
    private val list = ArrayList<MyData>()
    private lateinit var binding: FragmentHomeBinding


    // private lateinit var homeViewModel: HomeViewModel
    //private lateinit var viewPager2: ViewPager2
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mFragmentManager = fragmentManager as FragmentManager
        binding.btnCategory.setOnClickListener(this)
        binding.btnBundle.setOnClickListener(this)
        binding.btnVoucher.setOnClickListener(this)
        binding.btnPencarian.setOnClickListener(this)
        //(requireActivity() as MainActivity).supportActionBar?.title = "NGULI"
        val textView = TextView(activity)
        //textView.gravity


        rv_mydata.setHasFixedSize(true)
        list.addAll(getListMyDatas())
        showRecyclerGrid()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

        //return inflater.inflate(R.layout.fragment_home, container, false)

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
        rv_mydata.layoutManager = GridLayoutManager(requireActivity(), 1)
        val gridMyDataAdapter = GridMyDataAdapter(list)
        rv_mydata.adapter = gridMyDataAdapter
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_category -> {
                btnCategory()
            }
            R.id.btn_bundle -> {
                btnBundle()
            }
            R.id.btn_voucher -> {
                btnVoucher()
            }
            R.id.btn_pencarian -> {
                btnPencarian()
            }
        }
    }

    private fun btnPencarian() {
        val intent = Intent(activity, TBMapsActivity::class.java)
        startActivity(intent)
    }

    private fun btnVoucher() {
        val intent = Intent(activity, VoucherActivity::class.java)
        startActivity(intent)
    }

    private fun btnBundle() {
        val intent = Intent(activity, ContentCategoryActivity::class.java)
        startActivity(intent)
    }

    private fun btnCategory() {

        val intent = Intent(activity, CategoryActivity::class.java)

        /* startActivityForResult(intent, helper.REQUEST_ADD) */
        startActivity(intent)
    }
}


