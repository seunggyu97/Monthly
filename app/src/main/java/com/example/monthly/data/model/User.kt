package com.example.monthly.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("id")
    val userId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("push_setting")
    val pushSetting: Int
) : Parcelable