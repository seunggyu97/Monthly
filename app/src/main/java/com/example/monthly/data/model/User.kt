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
    val pushSetting: Boolean,

    @SerializedName("push_time")
    val pushTime: Int,

    @SerializedName("security_setting")
    val securitySetting: Boolean,

    @SerializedName("security_password")
    val securityPassword: Long,

    @SerializedName("security_bio_setting")
    val securityBioSetting: Boolean,
) : Parcelable