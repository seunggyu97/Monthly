package com.example.monthly.ui.dialogs

import android.graphics.Bitmap
import com.example.monthly.enumClass.ServiceType

interface DailyInsertDialogInterface {
    fun onFinishButtonClicked(date: String, price: Int, category: ServiceType, memo: String)

}