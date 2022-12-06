package com.example.monthly.util

import android.graphics.Bitmap
import android.view.View

fun ConvertToBitmap(view: View): Bitmap {
    // 1. 캐쉬(Cache)를 허용시킨다.
    // 2. 그림을 Bitmap 으로 저장.
    // 3. 캐쉬를 막는다.
    view.setDrawingCacheEnabled(true) // 캐쉬허용
    // 캐쉬에서 가져온 비트맵을 복사해서 새로운 비트맵(스크린샷) 생성
    val bitmap: Bitmap = Bitmap.createBitmap(view.getDrawingCache())
    view.setDrawingCacheEnabled(false) // 캐쉬닫기

    return bitmap
}