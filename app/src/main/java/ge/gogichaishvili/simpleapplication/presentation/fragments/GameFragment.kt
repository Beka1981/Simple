package ge.gogichaishvili.simpleapplication.presentation.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ge.gogichaishvili.simpleapplication.R
import ge.gogichaishvili.simpleapplication.databinding.FragmentGameBinding
import ge.gogichaishvili.simpleapplication.domain.Game
import ge.gogichaishvili.simpleapplication.domain.Player
import ge.gogichaishvili.simpleapplication.tools.SharedPreferenceManager
import ge.gogichaishvili.simpleapplication.tools.Tools
import ge.gogichaishvili.simpleapplication.tools.Tools.setLocked
import ge.gogichaishvili.simpleapplication.tools.Tools.setUnlocked
import java.util.*


class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    //sensor manager
    private var sensorManager: SensorManager? = null
    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f


    private lateinit var player: Player
    private lateinit var opponent: Player

    private var playerTotalScore: Int = 0
    private var opponentTotalScore: Int = 0
    private var isMyTurn: Boolean = true

    private lateinit var pref: SharedPreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return _binding?.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Getting the Sensor Manager instance
        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager

        Objects.requireNonNull(sensorManager)!!
            .registerListener(sensorListener, sensorManager!!
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)

        acceleration = 10f
        currentAcceleration = SensorManager.GRAVITY_EARTH
        lastAcceleration = SensorManager.GRAVITY_EARTH


        pref = SharedPreferenceManager(requireContext())
        val playerInfo = pref.getPlayerData()

        val opponentInfo = Game.getOpponentAvatar()

        val gameStatistics = pref.getGameStatistics()
        Toast.makeText(
            requireContext(),
            "You have played this game ${gameStatistics.win + gameStatistics.lose} times, won ${gameStatistics.win} times and lost ${gameStatistics.lose} times",
            Toast.LENGTH_LONG
        ).show()

        player = Player(
            requireContext(),
            playerInfo.firstName.toString(),
            playerInfo.avatar,
            0,
            0,
            binding.ivDice1,
            binding.ivDice2
        )
        opponent = Player(
            requireContext(),
            opponentInfo.name,
            opponentInfo.avatar,
            0,
            0,
            binding.ivDice1,
            binding.ivDice2
        )

        binding.tvPlayerOneName.text = player.name
        binding.tvPlayerTwoName.text = opponent.name

        binding.ivPlayer.borderColor = requireContext().getColor(R.color.text_color_gold)
        binding.ivPlayer.setImageResource(player.avatar)

        binding.ivOpponent.setImageResource(opponent.avatar)

        Tools.playSound(requireContext(), R.raw.success)

        binding.btnRoll1.setOnClickListener {

            if (isMyTurn) {
                val currentScore = player.rollPairOfDices()
                binding.tvCurrentScore.text = "${getString(R.string.current_score)} $currentScore"

                playerTotalScore += currentScore
                binding.tvPlayerOneScore.text = "Score: $playerTotalScore"

                binding.ivPlayer.borderColor = requireContext().getColor(R.color.white)
                setLocked(binding.ivPlayer)
                binding.ivOpponent.borderColor = requireContext().getColor(R.color.text_color_gold)
                setUnlocked(binding.ivOpponent)
                binding.tvPlayerOneName.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.hint_color
                    )
                )
                binding.tvPlayerTwoName.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )

                isMyTurn = false
                binding.btnRoll1.text = getString(R.string.opponent_roll)

                checkGameResult(currentScore, playerTotalScore, player.name)
            } else {

                val currentScore = opponent.rollPairOfDices()
                binding.tvCurrentScore.text =
                    "${getString(R.string.opponent_current_score)} $currentScore"

                opponentTotalScore += currentScore
                binding.tvPlayerTwoScore.text = "Score: $opponentTotalScore"

                binding.ivOpponent.borderColor = requireContext().getColor(R.color.white)
                setLocked(binding.ivOpponent)
                binding.ivPlayer.borderColor = requireContext().getColor(R.color.text_color_gold)
                setUnlocked(binding.ivPlayer)
                binding.tvPlayerOneName.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                binding.tvPlayerTwoName.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.hint_color
                    )
                )

                isMyTurn = true
                binding.btnRoll1.text = getString(R.string.roll)

                checkGameResult(currentScore, opponentTotalScore, opponent.name)

            }

        }

        binding.btnReplay.setOnClickListener {
            resetGame()
        }

    }

    private fun checkGameResult(currentScore: Int, TotalScore: Int, playerName: String) {
        val game = Game(requireContext(), currentScore, TotalScore, playerName)
        val isGameOver = game.checkGameResult(isMyTurn)
        if (isGameOver) {
            binding.btnRoll1.visibility = View.GONE
            binding.btnReplay.visibility = View.VISIBLE
        }
    }

    private fun resetGame() {
        Tools.playSound(requireContext(), R.raw.success)
        playerTotalScore = 0
        opponentTotalScore = 0
        isMyTurn = true
        binding.tvPlayerOneScore.text = "${getString(R.string.score)}"
        binding.tvPlayerTwoScore.text = "${getString(R.string.score)}"
        binding.tvCurrentScore.text = ""
        binding.ivPlayer.borderColor = requireContext().getColor(R.color.text_color_gold)
        setUnlocked(binding.ivPlayer)
        binding.ivOpponent.borderColor = requireContext().getColor(R.color.white)
        setLocked(binding.ivOpponent)
        binding.ivDice1.setImageResource(0)
        binding.ivDice2.setImageResource(0)
        binding.tvPlayerOneName.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        binding.tvPlayerTwoName.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.hint_color
            )
        )
        binding.btnRoll1.text = getString(R.string.roll)
        binding.btnReplay.visibility = View.GONE
        binding.btnRoll1.visibility = View.VISIBLE

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    //detect shake
    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {

            // Fetching x,y,z values
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            lastAcceleration = currentAcceleration

            // Getting current accelerations
            // with the help of fetched x,y,z values
            currentAcceleration = kotlin.math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + delta

            // Display a Toast message if
            // acceleration value is over 12
            if (acceleration > 12) {
                binding.btnRoll1.performClick()
            }
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    override fun onResume() {
        sensorManager?.registerListener(sensorListener, sensorManager!!.getDefaultSensor(
            Sensor .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
        )
        super.onResume()
    }

    override fun onPause() {
        sensorManager!!.unregisterListener(sensorListener)
        super.onPause()
    }
}


