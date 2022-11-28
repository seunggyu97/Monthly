import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.monthly.databinding.DialogBottomsheetBinding
import com.example.monthly.ui.dialogs.UpdateReferenceDateInterface
import com.example.monthly.viewModel.InitViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialog(updateReferenceDateInterface: UpdateReferenceDateInterface) : BottomSheetDialogFragment() {
    private lateinit var binding: DialogBottomsheetBinding
    // 액티비티에서 인터페이스를 받아오기
    private var updateReferenceDateInterface: UpdateReferenceDateInterface = updateReferenceDateInterface

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
                val referenceDateValue = npReferenceDay.value.toString()
                updateReferenceDateInterface.onCompleteButtonClicked(referenceDateValue)
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