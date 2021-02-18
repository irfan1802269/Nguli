package com.ti2.nguli.data

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryContentData(
    var id: String? = null,
    var title: String? = null,
    var alamat: String? = null,
    var jumlahPekerja: Int? = null,
    var durasi: Int? = null,
    var harga: Int? = null,
    var date: Timestamp? = null
) : Parcelable
