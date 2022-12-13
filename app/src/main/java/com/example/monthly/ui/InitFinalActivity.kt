package com.example.monthly.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.monthly.MainActivity
import com.example.monthly.R
import com.example.monthly.databinding.ActivityInitfinalBinding
import com.example.monthly.util.AppendCommaToPriceValue
import com.example.monthly.util.ConvertToBitmap
import com.example.monthly.util.SaveBitmapToPng
import com.example.monthly.viewModel.InitViewModel


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
            .append(AppendCommaToPriceValue(limitValue.toInt()))
            .append(getString(R.string.initfinal_inner_main3))

        binding.apply {
            tvGuide.text = guideText.toString()
            tvInnerTitle.text = innerTitle.toString()
            tvInnerMain.text = innerMain.toString()
            tvSign.alpha = 0.5f

            llDownloadButton.setOnClickListener {
                val bitmap = ConvertToBitmap(binding.clInnerlayout)
                SaveBitmapToPng(bitmap, this@InitFinalActivity)
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
}