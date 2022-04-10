package ge.gogichaishvili.simpleapplication.presentation.activities

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import ge.gogichaishvili.simpleapplication.R
import ge.gogichaishvili.simpleapplication.databinding.ActivityMainBinding
import ge.gogichaishvili.simpleapplication.presentation.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        _binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(_binding?.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, MainFragment())
            .addToBackStack(MainFragment::class.java.name)
            .commit()

    }
}