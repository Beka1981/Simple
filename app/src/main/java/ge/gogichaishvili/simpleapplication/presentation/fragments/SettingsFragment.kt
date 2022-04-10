package ge.gogichaishvili.simpleapplication.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ge.gogichaishvili.simpleapplication.R
import ge.gogichaishvili.simpleapplication.databinding.FragmentSettingsBinding
import ge.gogichaishvili.simpleapplication.tools.SharedPreferenceManager
import ge.gogichaishvili.simpleapplication.tools.Tools
import ge.gogichaishvili.simpleapplication.tools.hideKeyboard

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private var counter: Int = 1

    private lateinit var pref: SharedPreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = SharedPreferenceManager(requireContext())
        val playerInfo = pref.getPlayerData()
        binding.etPlayerName.setText(playerInfo.firstName.toString())
        binding.ivAvatar.setImageResource(playerInfo.avatar)
        counter = playerInfo.id

        binding.btnNext.setOnClickListener {
            Tools.playSound(requireContext(), R.raw.click)
            counter++
            if (counter > 10) {
                counter = 1
            }

            /*val uri = "@drawable/avatar_$counter"
            val imageResource = resources.getIdentifier(uri, null, activity?.packageName)   */

            binding.ivAvatar.setImageResource(getAvatarImage(counter))
        }

        binding.btnPrevious.setOnClickListener {
            Tools.playSound(requireContext(), R.raw.click)
            counter--
            if (counter < 1) {
                counter = 10
            }
            binding.ivAvatar.setImageResource(getAvatarImage(counter))
        }

        binding.btnSave.setOnClickListener {
            hideKeyboard()
            if (binding.etPlayerName.text.toString().trim().isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please enter your player name",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                pref.savePlayerInfo(
                    binding.etPlayerName.text.toString().trim(),
                    getAvatarImage(counter),
                    counter
                )
                Snackbar.make(binding.root, getString(R.string.saved), Snackbar.LENGTH_SHORT).show()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, MainFragment())
                    .addToBackStack(MainFragment::class.java.name)
                    .commit()
            }
        }
    }


    private fun getAvatarImage(counter: Int): Int {
        val avatarImage = when (counter) {
            1 -> R.drawable.avatar_1
            2 -> R.drawable.avatar_2
            3 -> R.drawable.avatar_3
            4 -> R.drawable.avatar_4
            5 -> R.drawable.avatar_5
            6 -> R.drawable.avatar_6
            7 -> R.drawable.avatar_7
            8 -> R.drawable.avatar_8
            9 -> R.drawable.avatar_9
            else -> R.drawable.avatar_10
        }
        return avatarImage
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}