package com.ti2.nguli.buttonHome

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ti2.nguli.R
import com.ti2.nguli.data.CategoryContentData
import com.ti2.nguli.data.CategoryData
import com.ti2.nguli.data.VerifikasiPesananData
import com.ti2.nguli.databinding.ActivityContentCategoryBinding
import com.ti2.nguli.databinding.ActivityMainBinding
import com.ti2.nguli.databinding.ActivityVerifikasiPesananBinding
import com.ti2.nguli.helper
import com.ti2.nguli.helper.REQUEST_ADD
import com.ti2.nguli.helper.REQUEST_UPDATE
import com.ti2.nguli.helper.RESULT_ADD
import com.ti2.nguli.helper.RESULT_DELETE
import com.ti2.nguli.helper.RESULT_UPDATE
import kotlinx.android.synthetic.main.activity_content_category.*
import kotlinx.android.synthetic.main.activity_content_voucher.*

class ContentCategoryActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMapClickListener, View.OnClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityContentCategoryBinding
    private var isEdit = false
    private var identitas: CategoryContentData? = null
    private var position: Int = 0
    private var hargaTotal = 0;
    private var alamat = "0";


    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firestore = Firebase.firestore
        auth = Firebase.auth

        val hargaDurasi = 40000
        val hargaJumlahpkrj = 50000
        val myData by getParcelableExtra<CategoryData>(ContentCategoryActivity.EXTRA_MYDATA)
        supportActionBar?.title = myData?.name.toString()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnSubmit.setOnClickListener {

        }
        binding.btnDecreaseJmlhpekerja.setOnClickListener {
            val t: Int = edt_jumlahPekerja.getText().toString().toInt()
            edt_jumlahPekerja.setText((t - 1).toString())
            val harga = hargaJumlahpkrj
            hargaTotal = hargaTotal - harga
            tv_harga.setText(hargaTotal.toString())
        }



        binding.btnIncreaseJmlhpekerja.setOnClickListener {
            val t: Int = edt_jumlahPekerja.getText().toString().toInt()
            edt_jumlahPekerja.setText((t + 1).toString())
            val harga = hargaJumlahpkrj
            hargaTotal = hargaTotal + harga
            tv_harga.setText(hargaTotal.toString())
        }



        binding.btnDecreaseDurasi.setOnClickListener {
            val t: Int = edt_durasi.getText().toString().toInt()
            edt_durasi.setText((t - 1).toString())
            val harga = hargaDurasi
            hargaTotal = hargaTotal - harga
            tv_harga.setText(hargaTotal.toString())
        }


        binding.btnIncreaseDurasi.setOnClickListener {
            val t: Int = edt_durasi.getText().toString().toInt()
            edt_durasi.setText((t + 1).toString())
            val harga = hargaDurasi
            hargaTotal = hargaTotal + harga
            tv_harga.setText(hargaTotal.toString())
        }

        identitas = intent.getParcelableExtra(helper.EXTRA_QUOTE)
        if (identitas != null) {
            position = intent.getIntExtra(helper.EXTRA_POSITION, 0)
            isEdit = true
        } else {
            identitas = CategoryContentData()
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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMapClickListener(this)
    }

    override fun onMapClick(p0: LatLng?) {
        val intent = Intent(this, CCMapsActivity::class.java)
        startActivityForResult(intent, helper.REQUEST_ADD)
    }

    companion object {
        const val EXTRA_MYDATA = "extra_mydata"
        const val EXTRA_LOCATION = ""
        const val EXTRA_LATLNG = ""
    }

    inline fun <reified T : Parcelable> Activity.getParcelableExtra(key: String) = lazy {
        intent.getParcelableExtra<T>(key)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_submit) {
            //val title = binding.edtTitle.text.toString().trim()
            //   val alamat = binding.edtAlamat.text.toString().trim()

            alamat = intent.getStringExtra(EXTRA_LOCATION).toString()

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
                    "jumlahPekerja" to edt_jumlahPekerja.getText().toString().toInt(),
                    "durasi" to edt_durasi.getText().toString().toInt(),
                    "harga" to hargaTotal,
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
                            this,
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
                    "jumlahPekerja" to edt_jumlahPekerja.getText().toString().toInt(),
                    "durasi" to edt_durasi.getText().toString().toInt(),
                    "harga" to hargaTotal,
                    "date" to FieldValue.serverTimestamp()
                )
                firestore.collection("identitas")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(
                            this,
                            "DocumentSnapshot added with ID: ${documentReference.id}",
                            Toast.LENGTH_SHORT
                        ).show()
                        setResult(helper.RESULT_ADD, intent)
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this,
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
                                this,
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       
            when (requestCode) {
                REQUEST_ADD -> if (resultCode == RESULT_ADD) {
                     alamat = intent.getStringExtra(EXTRA_LOCATION).toString()

                }

            }

    }


}


