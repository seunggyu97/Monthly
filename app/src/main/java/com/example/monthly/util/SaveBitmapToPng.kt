package com.example.monthly.util

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("Recycle")
fun SaveBitmapToPng(bitmap: Bitmap, context : Context) {
    val fileName: String
    @SuppressLint("SimpleDateFormat") val sdf =
        SimpleDateFormat("yyyyMMddHHmmss") //년,월,일,시간 포멧 설정
    val time = Date() //파일명 중복 방지를 위해 사용될 현재시간
    val current_time = sdf.format(time) //String형 변수에 저장

    // 파일명 지정
    fileName = "Monthly_$current_time"

    val values = ContentValues()
    values.put(MediaStore.Images.Media.DISPLAY_NAME, "$fileName.PNG")
    values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        // 현재 is_pending 상태임을 만들어준다.
        // 다른 곳에서 이 데이터를 요구하면 무시하라는 의미로, 해당 저장소를 독점할 수 있다.
        values.put(MediaStore.Images.Media.IS_PENDING, 1)
    }
    val contentResolver = context.contentResolver
    val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

    // 이미지를 저장할 uri를 미리 설정함
    val item = contentResolver.insert(collection, values)

    var completed: Boolean = true
    try {
        val pdf = contentResolver.openFileDescriptor(item!!, "w", null)
        if (pdf == null) {
            Log.e("pdf", "null")
        } else {
            val fos = FileOutputStream(pdf.fileDescriptor)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.close()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.clear()

                // IS_PENDING 값을 0으로 설정해 주고 다른 곳에서 사용 가능하도록 복구
                values.put(MediaStore.Images.Media.IS_PENDING, 0)
                contentResolver.update(item, values, null, null)
            }
        }
    } catch (e: FileNotFoundException) {
        completed = false
        e.printStackTrace()
        Toast.makeText(context,"이미지 저장에 실패했습니다.", Toast.LENGTH_SHORT).show()
    } catch (e: IOException) {
        completed = false
        e.printStackTrace()
        Toast.makeText(context,"이미지 저장에 실패했습니다.", Toast.LENGTH_SHORT).show()
    } finally {
        if(completed) Toast.makeText(context, "이미지를 저장했습니다.", Toast.LENGTH_SHORT).show()
    }
}