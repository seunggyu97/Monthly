package com.example.monthly.ui.dialogs

import android.graphics.Bitmap

interface InitDialogInterface {
    fun onCompleteButtonClicked(content : String)

    fun onFinishButtonClicked(bitmap : Bitmap)

}