package com.ti2.nguli.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SettingModel (
    var name: String? = null,
    var email: String? = null,
    var age: String? = null,
    var phoneNumber: String? = null,
    var isLaki: Boolean = false,
    var isDarkTheme: Boolean = false
): Parcelable