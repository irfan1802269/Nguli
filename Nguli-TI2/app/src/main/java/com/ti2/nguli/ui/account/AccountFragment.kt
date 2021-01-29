package com.ti2.nguli.ui.account

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.ti2.nguli.CategoryActivity
import com.ti2.nguli.MainActivity
import com.ti2.nguli.R
import com.ti2.nguli.databinding.FragmentAccountBinding
import com.ti2.nguli.databinding.FragmentHomeBinding
import com.ti2.nguli.ui.cart.CartFragment
import com.ti2.nguli.ui.history.HistoryFragment

class AccountFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentAccountBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //(requireActivity() as MainActivity).supportActionBar?.title = "Akun"

        binding.stgPesanan.setOnClickListener(this)
        binding.stgVoucher.setOnClickListener(this)
        binding.stgPengaturanProfil.setOnClickListener(this)
        binding.stgKebijakanPrivacy.setOnClickListener(this)
        binding.stgLogout.setOnClickListener(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.stgPesanan -> {
                val intent = Intent(activity, CategoryActivity::class.java)
                startActivity(intent)
            }
            R.id.stgVoucher -> {
                val intent = Intent(activity, CategoryActivity::class.java)
                startActivity(intent)
            }
            R.id.stgPengaturanProfil -> {
                val intent = Intent(activity, CategoryActivity::class.java)
                startActivity(intent)
            }
            R.id.stgKebijakanPrivacy -> {
                val intent = Intent(activity, CategoryActivity::class.java)
                startActivity(intent)
            }
            R.id.stgLogout -> {
                val mCartFragment = CartFragment()
                val mFragmentManager = fragmentManager as FragmentManager
                mFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, mCartFragment)
                    .commit()
            }
        }
    }


}