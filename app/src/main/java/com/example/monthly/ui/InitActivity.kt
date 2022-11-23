package com.example.monthly.ui

//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.R
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AccountCircle
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Surface
//import com.example.monthly.ui.theme.MonthlyTheme
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.monthly.R
import com.example.monthly.databinding.ActivityInitBinding
import com.example.monthly.viewModel.InitViewModel

class InitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInitBinding
    private lateinit var viewModel: InitViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        addTextChangedListener()
    }

    fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_init)
        viewModel = ViewModelProvider(this).get(InitViewModel::class.java)

        // viewModel의 inputName 값에 변동이 생길때마다 실행
        viewModel.inputName.observe(this, Observer {
            if (it.isNotEmpty()) binding.btnConfirm.visibility = View.VISIBLE
            else binding.btnConfirm.visibility = View.GONE
        })

        binding.tvReferenceDate.text = viewModel.getReferenceDate().toString()

        binding.apply {
            btnConfirm.setOnClickListener {
                if (etName.isFocused) {
                    // 이름에 포커스 되어있는 상태라면 기준일 레이아웃을 보이게 한다.
                    binding.llReferenceDate.visibility = View.VISIBLE
                    binding.tvGuide.text = getString(R.string.init_guide2)
                    Log.e("MyTag", "이름 포커스")
                }
                // 기준일 입력칸에 포커스 되어있는 상태라면 한도금액 레이아웃을 보이게 한 후 한도 입력칸에 포커스 이동
                else if (tvReferenceDate.isFocused) {
                    binding.llLimitValue.visibility = View.VISIBLE
                    binding.etLimitValue.requestFocus()
                    Log.e("MyTag", "기준일 포커스")

                } else if (etLimitValue.isFocused) {
                    if (viewModel?.getLimitValue() != null && viewModel?.getLimitValue()!! >= 10) {
                        Log.d("MyTag", "입력 완료")
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "월 사용한도금액은 10원 이상이어야 합니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    fun addTextChangedListener() {
        // 이름 입력칸의 내용이 바뀔 때 마다 viewModel에 저장
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //텍스트를 입력 후
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 전
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 중
                viewModel.setName(binding.etName.text.toString())
            }
        })

        // 기준일 입력칸의 내용이 바뀔 때 마다 viewModel에 저장
        binding.tvReferenceDate.addTextChangedListener(object : TextWatcher {
            // Todo : 이 부분은 다이얼로그에서 선택할 때 리스너로 변경해야 함.
            override fun afterTextChanged(p0: Editable?) {
                //텍스트를 입력 후
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 전
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 중
                viewModel.setDay(binding.tvReferenceDate.text.toString())
            }
        })

        //  한도금액의 내용이 바뀔 때 마다 viewModel에 저장
        binding.etLimitValue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //텍스트를 입력 후
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 전
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 중
                viewModel.setLimitValue(binding.etLimitValue.text.toString())
            }
        })
    }
}

//@Composable
//fun InitUser() {
//    Scaffold(
//        bottomBar = { SootheBottomNavigation()}
//    ){ padding ->
//        InnerScreen(Modifier.padding(padding))
//    }
//}
//
//@Composable
//fun InnerScreen(modifier: Modifier = Modifier) {
//    Column(
//        modifier
//            .verticalScroll(rememberScrollState())
//            .padding(vertical = 16.dp)
//    ) {
//        SearchBar(Modifier.padding(horizontal = 16.dp))
//        HomeSection(title = R.string.align_your_body) {
//            AlignYourBodyRow()
//        }
//        HomeSection(title = R.string.favorite_collections) {
//            FavoriteCollectionsGrid()
//        }
//    }
//}
//
//@Preview(widthDp = 360, heightDp = 640)
//@Composable
//fun MyInitPreview() {
//    InitUser()
//}