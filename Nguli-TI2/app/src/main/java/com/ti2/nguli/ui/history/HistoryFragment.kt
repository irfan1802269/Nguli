package com.ti2.nguli.ui.history

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ti2.nguli.CategoryActivity
import com.ti2.nguli.HistoryAddUpdateActivity
import com.ti2.nguli.MyData
import com.ti2.nguli.adapter.HistoryAdapter
import com.ti2.nguli.data.HistoryData
import com.ti2.nguli.databinding.FragmentHistoryBinding
import com.ti2.nguli.helper.REQUEST_ADD
import com.ti2.nguli.helper.REQUEST_UPDATE
import com.ti2.nguli.helper.RESULT_ADD
import com.ti2.nguli.helper.RESULT_DELETE
import com.ti2.nguli.helper.RESULT_UPDATE
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {


    private val list = ArrayList<MyData>()
    private lateinit var adapter: HistoryAdapter
    private lateinit var binding: FragmentHistoryBinding
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
        adapter = HistoryAdapter(this)

        binding.fabAdd.setOnClickListener {
            val intent = Intent(activity, HistoryAddUpdateActivity::class.java)
            startActivityForResult(intent, REQUEST_ADD)
           // startActivity(intent)
        }
        loadQuotes()
    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvQuotes, message, Snackbar.LENGTH_SHORT).show()
    }
    private fun loadQuotes() {
        GlobalScope.launch(Dispatchers.Main) {
            progressbar.visibility = View.VISIBLE
            val quotesList = ArrayList<HistoryData>()
            val currentUser = auth.currentUser
            firestore.collection("quotes")
                .whereEqualTo("uid", currentUser?.uid)
                .get()
                .addOnSuccessListener { result ->
                    progressbar.visibility = View.INVISIBLE
                    for (document in result) {
                        val id = document.id
                        val title = document.get("title").toString()
                        val description = document.get("description").toString()
                        val category = document.get("category").toString()
                        val date = document.get("date") as com.google.firebase.Timestamp
                        quotesList.add(HistoryData(id, title, description, category, date))
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
                REQUEST_ADD -> if (resultCode == RESULT_ADD) {
                    loadQuotes()
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                REQUEST_UPDATE ->
                    when (resultCode) {
                        RESULT_UPDATE -> {
                            loadQuotes()
                            showSnackbarMessage("Satu item berhasil diubah")
                        }
                        RESULT_DELETE -> {
                            loadQuotes()
                            showSnackbarMessage("Satu item berhasil dihapus")
                        }
                    }
            }
        }
    }


}