package com.ti2.nguli

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ti2.nguli.data.HistoryData
import com.ti2.nguli.data.VerifikasiPesananData
import com.ti2.nguli.databinding.ActivityHistoryAddUpdateBinding
import com.ti2.nguli.databinding.ActivityVerifikasiPesananBinding
import kotlinx.android.synthetic.main.activity_history_add_update.*

class VerifikasiPesananActivity : AppCompatActivity(), View.OnClickListener {
    private var isEdit = false
    private var provinsiSpinnerArray = ArrayList<String>()
    private var kotaSpinnerArray = ArrayList<String>()
    private var kecamatanSpinnerArray = ArrayList<String>()
    private var jumlahPekerjaSpinnerArray = ArrayList<String>()
    private var durasiSpinnerArray = ArrayList<String>()
    private var identitas: VerifikasiPesananData? = null
    private var position: Int = 0
    private var provinsiSelection: Int = 0
    private var kotaSelection: Int = 0
    private var kecamatanSelection: Int = 0
    private var jumlahPekerjaSelection: Int = 0
    private var durasiSelection: Int = 0
    private var provinsiName: String = "0"
    private var kotaName: String = "0"
    private var kecamatanName: String = "0"
    private var jumlahPekerjaName: String = "0"
    private var durasiName: String = "0"

    private lateinit var binding: ActivityVerifikasiPesananBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifikasiPesananBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firestore = Firebase.firestore
        auth = Firebase.auth
        kotaSpinnerArray = getKota()
        provinsiSpinnerArray = getProvinsi()
        kecamatanSpinnerArray = getKecamatan()
        jumlahPekerjaSpinnerArray = getJumlahPekerja()
        durasiSpinnerArray = getDurasi()
        identitas = intent.getParcelableExtra(helper.EXTRA_QUOTE)
        if (identitas != null) {
            position = intent.getIntExtra(helper.EXTRA_POSITION, 0)
            isEdit = true
        } else {
            identitas = VerifikasiPesananData()
        }
        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = "Ubah"
            btnTitle = "Update"
            identitas?.let {
                //binding.edtTitle.setText(it.title)
                binding.edtAlamat.setText(it.alamat)
            }!!
        } else {
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.btnSubmit.text = btnTitle
        binding.btnSubmit.setOnClickListener(this)
    }

    private fun getProvinsi(): ArrayList<String> {
        progressbar.visibility = View.VISIBLE
        firestore.collection("provinsi")
            .whereEqualTo("is_active", true)
            .get()
            .addOnSuccessListener { documents ->
                var selection = 0;
                for (document in documents) {
                    val name = document.get("name").toString()
                    identitas?.let {
                        if (name == it.provinsi) {
                            provinsiSelection = selection
                        }
                    }
                    provinsiSpinnerArray.add(name)
                    selection++
                }
                setProvinsi(provinsiSpinnerArray)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    this@VerifikasiPesananActivity,
                    "Categories cannot be retrieved ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        return provinsiSpinnerArray
    }

    private fun getKota(): ArrayList<String> {
        progressbar.visibility = View.VISIBLE
        firestore.collection("kota")
            .whereEqualTo("is_active", true)
            .get()
            .addOnSuccessListener { documents ->
                var selection = 0;
                for (document in documents) {
                    val name = document.get("name").toString()
                    identitas?.let {
                        if (name == it.kota) {
                            kotaSelection = selection
                        }
                    }
                    kotaSpinnerArray.add(name)
                    selection++
                }
                setKota(kotaSpinnerArray)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    this@VerifikasiPesananActivity,
                    "Categories cannot be retrieved ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        return kotaSpinnerArray
    }
    private fun getKecamatan(): ArrayList<String> {
        progressbar.visibility = View.VISIBLE
        firestore.collection("kecamatan")
            .whereEqualTo("is_active", true)
            .get()
            .addOnSuccessListener { documents ->
                var selection = 0;
                for (document in documents) {
                    val name = document.get("name").toString()
                    identitas?.let {
                        if(name==it.kecamatan){
                            kecamatanSelection = selection
                        }
                    }
                    kecamatanSpinnerArray.add(name)
                    selection++
                }
                setKecamatan(kecamatanSpinnerArray)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this@VerifikasiPesananActivity, "Categories cannot be retrieved ", Toast.LENGTH_SHORT).show()
            }
        return kecamatanSpinnerArray
    }
    private fun getJumlahPekerja(): ArrayList<String> {
        progressbar.visibility = View.VISIBLE
        firestore.collection("jumlahPekerjas")
            .whereEqualTo("is_active", true)
            .get()
            .addOnSuccessListener { documents ->
                var selection = 0;
                for (document in documents) {
                    val name = document.get("name").toString()
                    identitas?.let {
                        if(name==it.jumlahPekerja){
                            jumlahPekerjaSelection = selection
                        }
                    }
                    jumlahPekerjaSpinnerArray.add(name)
                    selection++
                }
                setJumlahPekerja(jumlahPekerjaSpinnerArray)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this@VerifikasiPesananActivity, "Categories cannot be retrieved ", Toast.LENGTH_SHORT).show()
            }
        return jumlahPekerjaSpinnerArray
    }
    private fun getDurasi(): ArrayList<String> {
        progressbar.visibility = View.VISIBLE
        firestore.collection("durasi")
            .whereEqualTo("is_active", true)
            .get()
            .addOnSuccessListener { documents ->
                var selection = 0;
                for (document in documents) {
                    val name = document.get("name").toString()
                    identitas?.let {
                        if(name==it.durasi){
                            durasiSelection = selection
                        }
                    }
                    durasiSpinnerArray.add(name)
                    selection++
                }
                setDurasi(durasiSpinnerArray)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this@VerifikasiPesananActivity, "Categories cannot be retrieved ", Toast.LENGTH_SHORT).show()
            }
        return durasiSpinnerArray
    }

    private fun setProvinsi(paymentMethodSpinnerAarray: ArrayList<String>) {
        var spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, paymentMethodSpinnerAarray)
        binding.edtProvinsi.adapter = spinnerAdapter
        binding.edtProvinsi.setSelection(provinsiSelection)
        binding.edtProvinsi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                provinsiName = binding.edtProvinsi.selectedItem.toString()
                progressbar.visibility = View.INVISIBLE
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setKota(paymentMethodSpinnerAarray: ArrayList<String>) {
        var spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, paymentMethodSpinnerAarray)
        binding.edtKota.adapter = spinnerAdapter
        binding.edtKota.setSelection(kotaSelection)
        binding.edtKota.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                kotaName = binding.edtKota.selectedItem.toString()
                progressbar.visibility = View.INVISIBLE
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
    private fun setKecamatan(paymentMethodSpinnerAarray: ArrayList<String>) {
        var spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, paymentMethodSpinnerAarray)
        binding.edtKecamatan.adapter = spinnerAdapter
        binding.edtKecamatan.setSelection(kecamatanSelection)
        binding.edtKecamatan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                kecamatanName = binding.edtKecamatan.selectedItem.toString()
                progressbar.visibility = View.INVISIBLE
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
    private fun setJumlahPekerja(paymentMethodSpinnerAarray: ArrayList<String>) {
        var spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, paymentMethodSpinnerAarray)
        binding.edtJumlahPekerja.adapter = spinnerAdapter
        binding.edtJumlahPekerja.setSelection(jumlahPekerjaSelection)
        binding.edtJumlahPekerja.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                jumlahPekerjaName = binding.edtJumlahPekerja.selectedItem.toString()
                progressbar.visibility = View.INVISIBLE
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
    private fun setDurasi(paymentMethodSpinnerAarray: ArrayList<String>) {
        var spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, paymentMethodSpinnerAarray)
        binding.edtDurasi.adapter = spinnerAdapter
        binding.edtDurasi.setSelection(durasiSelection)
        binding.edtDurasi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                durasiName = binding.edtDurasi.selectedItem.toString()
                progressbar.visibility = View.INVISIBLE
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_submit) {
            //val title = binding.edtTitle.text.toString().trim()
            val alamat = binding.edtAlamat.text.toString().trim()
            if (alamat.isEmpty()) {
                binding.edtAlamat.error = "Field can not be blank"
                return
            }
            if (isEdit) {
                val currentUser = auth.currentUser
                val user = hashMapOf(
                    "uid" to currentUser?.uid,
                    "title" to title,
                    "alamat" to alamat,
                    "provinsi" to provinsiName,
                    "kota" to kotaName,
                    "kecamatan" to kecamatanName,
                    "jumlahPekerja" to jumlahPekerjaName,
                    "durasi" to durasiName,
                    "date" to FieldValue.serverTimestamp()
                )
                firestore.collection("identitas").document(identitas?.id.toString())
                    .set(user)
                    .addOnSuccessListener {
                        setResult(helper.RESULT_UPDATE, intent)
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this@VerifikasiPesananActivity,
                            "Gagal mengupdate data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

            } else {
                val currentUser = auth.currentUser
                val user = hashMapOf(
                    "uid" to currentUser?.uid,
                    "title" to title,
                    "alamat" to alamat,
                    "provinsi" to provinsiName,
                    "kota" to kotaName,
                    "kecamatan" to kecamatanName,
                    "jumlahPekerja" to jumlahPekerjaName,
                    "durasi" to durasiName,
                    "date" to FieldValue.serverTimestamp()
                )
                firestore.collection("identitas")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(
                            this@VerifikasiPesananActivity,
                            "DocumentSnapshot added with ID: ${documentReference.id}",
                            Toast.LENGTH_SHORT
                        ).show()
                        setResult(helper.RESULT_ADD, intent)
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this@VerifikasiPesananActivity,
                            "Error adding document",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(helper.ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(helper.ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
        return true
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == helper.ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = "Batal"
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?"
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
            dialogTitle = "Hapus Quote"
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton("Ya") { _, _ ->
                if (isDialogClose) {
                    finish()
                } else {
                    firestore.collection("identitas").document(identitas?.id.toString())
                        .delete()
                        .addOnSuccessListener {
                            Log.d(
                                "delete",
                                "DocumentSnapshot successfully deleted!" + identitas?.id.toString()
                            )
                            val intent = Intent()
                            intent.putExtra(helper.EXTRA_POSITION, position)
                            setResult(helper.RESULT_DELETE, intent)
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Log.w("a", "Error deleting document", e)
                            Toast.makeText(
                                this@VerifikasiPesananActivity,
                                "Gagal menghapus data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                }
            }
            .setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}