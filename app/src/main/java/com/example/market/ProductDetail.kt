package com.example.market

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDetail(
    val imageUrl: String,
    val title: String,
    val price: Int,
    val content: String,
    val seller: String,
    val isAvailable: Boolean
) : Parcelable {
    constructor() : this("", "", 0, "", "", true)
}

