package com.example.monthly.ui

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.monthly.MainActivity
import com.example.monthly.R
import com.example.monthly.databinding.ActivityInitfinalBinding
import com.example.monthly.viewModel.InitViewModel
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class InitFinalActivity : AppCompatActivity(){
    private lateinit var binding: ActivityInitfinalBinding
    private lateinit var initViewModel: InitViewModel

    private lateinit var bitmap: Bitmap
    private lateinit var mCanvas: CanvasView
    private lateinit var name: String
    private lateinit var referenceDate: String
    private lateinit var limitValue: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_initfinal)

        getFromIntent()

        init()

        drawSign()
    }

    fun init() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_initfinal)
        initViewModel = ViewModelProvider(this).get(InitViewModel::class.java)

        val guideText = StringBuilder()
        guideText.append(name).append(getString(R.string.initfinal_guide))

        val innerTitle = StringBuilder()
        innerTitle.append(name).append(getString(R.string.initfinal_inner_title))

        val innerMain = StringBuilder()
        innerMain.append("나 ").append(name).append(" ")
            .append(getString(R.string.initfinal_inner_main)).append(" ")
            .append(referenceDate)
            .append(getString(R.string.initfinal_inner_main2)).append(" ")
            .append(limitValue)
            .append(getString(R.string.initfinal_inner_main3))

        binding.apply {
            tvGuide.text = guideText.toString()
            tvInnerTitle.text = innerTitle.toString()
            tvInnerMain.text = innerMain.toString()
            tvSign.alpha = 0.5f

            llDownloadButton.setOnClickListener {
                val bitmap = convertBitmap(binding.clInnerlayout)
                saveBitmaptoPng(bitmap)
            }

            btnComplete.setOnClickListener {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun getFromIntent() {
        val byteArray = intent.getByteArrayExtra("signBitmap")
        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
        name = intent.getStringExtra("name").toString()
        referenceDate = intent.getStringExtra("referenceDate").toString()
        limitValue = intent.getStringExtra("limitValue").toString()
    }

    fun drawSign(){
        binding.ivSign.setImageBitmap(bitmap)
    }

    @SuppressLint("Recycle")
    @RequiresApi(Build.VERSION_CODES.Q)
    fun saveBitmaptoPng(bitmap: Bitmap) {
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
        val contentResolver = this.contentResolver
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
            Toast.makeText(this,"이미지 저장에 실패했습니다.",Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            completed = false
            e.printStackTrace()
            Toast.makeText(this,"이미지 저장에 실패했습니다.",Toast.LENGTH_SHORT).show()
        } finally {
            if(completed) Toast.makeText(this, "이미지를 저장했습니다.",Toast.LENGTH_SHORT).show()
        }
    }

    private fun convertBitmap(view: View): Bitmap {
        // 1. 캐쉬(Cache)를 허용시킨다.
        // 2. 그림을 Bitmap 으로 저장.
        // 3. 캐쉬를 막는다.
        view.setDrawingCacheEnabled(true) // 캐쉬허용
        // 캐쉬에서 가져온 비트맵을 복사해서 새로운 비트맵(스크린샷) 생성
        val bitmap: Bitmap = Bitmap.createBitmap(view.getDrawingCache())
        view.setDrawingCacheEnabled(false) // 캐쉬닫기

        return bitmap
    }
}