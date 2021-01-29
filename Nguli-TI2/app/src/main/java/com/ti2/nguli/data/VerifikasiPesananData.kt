package com.ti2.nguli.data

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VerifikasiPesananData(
    var id: String? = null,
    var title: String? = null,
    var provinsi: String? = null,
    var kota: String? = null,
    var kecamatan: String? = null,
    var alamat: String? = null,
    var jumlahPekerja: String? = null,
    var durasi: String? = null,
    var date: Timestamp? = null
) : Parcelable
