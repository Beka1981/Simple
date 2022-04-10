package ge.gogichaishvili.simpleapplication.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ge.gogichaishvili.simpleapplication.R
import ge.gogichaishvili.simpleapplication.databinding.FragmentMainBinding
import ge.gogichaishvili.simpleapplication.tools.Tools

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPlay.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, GameFragment())
                .addToBackStack(GameFragment::class.java.name)
                .commit()
        }

        binding.btnSettings.setOnClickListener {
            Tools.playSound(requireContext(), R.raw.click)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, SettingsFragment())
                .addToBackStack(SettingsFragment::class.java.name)
                .commit()
        }

        binding.btnExit.setOnClickListener {
            activity?.finishAndRemoveTask()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}