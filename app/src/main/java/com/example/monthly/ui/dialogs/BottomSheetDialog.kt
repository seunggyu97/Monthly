import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.monthly.databinding.DialogBottomsheetBinding
import com.example.monthly.viewModel.InitViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialog() : BottomSheetDialogFragment() {
    private lateinit var binding: DialogBottomsheetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogBottomsheetBinding.inflate(inflater, container, false)

        dialogInit()

        return binding.root
    }

    private fun dialogInit() {

        binding.apply {
            tvClose.setOnClickListener {
                dismiss()
            }
            tvComplete.setOnClickListener {
                viewModel?.setDay(npReferenceDay.value.toString())
                Log.e("MyTag", "clicked")
                dismiss()
            }

            npReferenceDay.apply {
                maxValue = 31 // 최대값
                minValue = 1 // 최소값
                value = 1 // 초기값
                wrapSelectorWheel = false // 스크롤 무한반복 사용 여부
            }
        }


    }

}