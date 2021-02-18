package com.ti2.nguli.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BundleData(
    var name: String,
    var description: String,
    var photo: String,
    val lat: Double,
    val lang: Double

) : Parcelable
