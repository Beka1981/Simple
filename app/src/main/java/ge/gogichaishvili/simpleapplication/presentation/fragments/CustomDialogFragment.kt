package ge.gogichaishvili.simpleapplication.presentation.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.DialogFragment
import ge.gogichaishvili.simpleapplication.R
import ge.gogichaishvili.simpleapplication.databinding.FragmentCustomDialogBinding
import ge.gogichaishvili.simpleapplication.tools.vibratePhone


class CustomDialogFragment : DialogFragment() {

    private var _binding: FragmentCustomDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.myDialogTheme)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        _binding = FragmentCustomDialogBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.updateLayoutParams<FrameLayout.LayoutParams> {
            val w = (resources.displayMetrics.widthPixels * 0.90).toInt()
            width = w
        }

        vibratePhone()



        val text = arguments?.getSerializable("DATA") as String
        if (text == getString(R.string.lose)) {
            binding.ivLogo.setImageResource(R.drawable.lose)
        }
        binding.tvTitle.text = text

        //close button click
        binding.btnClose.setOnClickListener {
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    /*override fun onStart() {
        if (dialog == null) {
            return
        }
        dialog!!.window!!.setWindowAnimations(
            R.style.myDialogTheme
        )
        super.onStart()
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}