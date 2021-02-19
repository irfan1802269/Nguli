package com.ti2.nguli.ui.cart

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ti2.nguli.*
import com.ti2.nguli.adapter.CardViewMyDataAdapter
import com.ti2.nguli.adapter.CartAdapter
import com.ti2.nguli.adapter.GridMyDataAdapter
import com.ti2.nguli.adapter.HistoryAdapter
import com.ti2.nguli.buttonHome.CategoryActivity
import com.ti2.nguli.data.HistoryData
import com.ti2.nguli.data.VerifikasiPesananData
import com.ti2.nguli.databinding.FragmentCartBinding
import com.ti2.nguli.databinding.FragmentHistoryBinding
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private val list = ArrayList<MyData>()
    private lateinit var adapter: CartAdapter
    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mFragmentManager = fragmentManager as FragmentManager
        //(requireActivity() as MainActivity).supportActionBar?.title = "History"
        firestore = Firebase.firestore
        auth = Firebase.auth
        binding.rvQuotes.layoutManager = LinearLayoutManager(activity)
        binding.rvQuotes.setHasFixedSize(true)
        adapter = CartAdapter(this)

        binding.fabAdd.setOnClickListener {
            val intent = Intent(activity, CategoryActivity::class.java)
            startActivityForResult(intent, helper.REQUEST_ADD)
            // startActivity(intent)
        }
        loadQuotes()
    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvQuotes, message, Snackbar.LENGTH_SHORT).show()
    }
    private fun loadQuotes() {
        GlobalScope.launch(Dispatchers.Main) {
            progressbar.visibility = View.VISIBLE
            val quotesList = ArrayList<VerifikasiPesananData>()
            val currentUser = auth.currentUser
            firestore.collection("identitas")
                .whereEqualTo("uid", currentUser?.uid)
                .get()
                .addOnSuccessListener { result ->
                    progressbar.visibility = View.INVISIBLE
                    for (document in result) {
                        val id = document.id
                        val title = document.get("title").toString()
                        val alamat = document.get("alamat").toString()
                        val provinsi = document.get("provinsi").toString()
                        val kecamatan = document.get("provinsi").toString()
                        val kota = document.get("provinsi").toString()
                        val jumlahPekerja = document.get("provinsi").toString()
                        val durasi = document.get("provinsi").toString()
                        val date = document.get("date") as com.google.firebase.Timestamp
                        quotesList.add(VerifikasiPesananData(id, title, alamat, provinsi, kecamatan, kota, jumlahPekerja, durasi,  date))
                    }
                    if (quotesList.size > 0) {
                        binding.rvQuotes.adapter = adapter
                        adapter.listQuotes = quotesList
                    } else {
                        adapter.listQuotes = ArrayList()
                        showSnackbarMessage("Tidak ada data saat ini")
                    }
                }
                .addOnFailureListener { exception ->
                    progressbar.visibility = View.INVISIBLE
                    Toast.makeText(
                        activity, "Error adding document", Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                helper.REQUEST_ADD -> if (resultCode == helper.RESULT_ADD) {
                    loadQuotes()
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                helper.REQUEST_UPDATE ->
                    when (resultCode) {
                        helper.RESULT_UPDATE -> {
                            loadQuotes()
                            showSnackbarMessage("Satu item berhasil diubah")
                        }
                        helper.RESULT_DELETE -> {
                            loadQuotes()
                            showSnackbarMessage("Satu item berhasil dihapus")
                        }
                    }
            }
        }
    }


}