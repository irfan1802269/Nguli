package com.ti2.nguli.ui.cart

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ti2.nguli.MainActivity
import com.ti2.nguli.MyData
import com.ti2.nguli.R
import com.ti2.nguli.adapter.CardViewMyDataAdapter
import com.ti2.nguli.adapter.GridMyDataAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class CartFragment : Fragment() {

    private val list = ArrayList<MyData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //(requireActivity() as MainActivity).supportActionBar?.title = "Cart"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }


}