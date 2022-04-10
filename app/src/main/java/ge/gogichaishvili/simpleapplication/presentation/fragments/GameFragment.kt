package ge.gogichaishvili.simpleapplication.presentation.fragments

import android.annotation.SuppressLint
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

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

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
                binding.ivOpponent.borderColor = requireContext().getColor(R.color.text_color_gold)
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
                binding.ivPlayer.borderColor = requireContext().getColor(R.color.text_color_gold)
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
        binding.ivOpponent.borderColor = requireContext().getColor(R.color.white)
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
}