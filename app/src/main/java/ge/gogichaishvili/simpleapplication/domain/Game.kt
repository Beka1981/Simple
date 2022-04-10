package ge.gogichaishvili.simpleapplication.domain

import android.content.Context
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import ge.gogichaishvili.simpleapplication.R
import ge.gogichaishvili.simpleapplication.models.CheckRollResultModel
import ge.gogichaishvili.simpleapplication.models.OpponentAvatarModel
import ge.gogichaishvili.simpleapplication.presentation.fragments.CustomDialogFragment
import ge.gogichaishvili.simpleapplication.tools.SharedPreferenceManager
import ge.gogichaishvili.simpleapplication.tools.Tools
import java.util.*

class Game(
    private val context: Context,
    private val currentScore: Int,
    private val totalScore: Int,
    private val playerName: String
) {

    private lateinit var pref: SharedPreferenceManager

    private fun checkRollResult(): CheckRollResultModel {
        return when {
            totalScore >= 111 -> {
                CheckRollResultModel(true, "$playerName win the game !")
            }
            currentScore == 11 -> {
                CheckRollResultModel(true, "Zanzibar $playerName win the game !")
            }
            else -> CheckRollResultModel(false, null)
        }
    }

    fun checkGameResult(isMyTurn: Boolean) : Boolean {
        val isPlayerWin = checkRollResult()
        return if (isPlayerWin.isPlayerWin) {
            val dialog = CustomDialogFragment()
            pref = SharedPreferenceManager(context)
            val gameStatistic = pref.getGameStatistics()
            if (!isMyTurn) {
                Tools.playSound(context, R.raw.applause)
                pref.saveGameStatistics(gameStatistic.lose, gameStatistic.win.plus(1))
                dialog.arguments = bundleOf("DATA" to isPlayerWin.message)
            } else {
                Tools.playSound(context, R.raw.busy)
                pref.saveGameStatistics(gameStatistic.lose.plus(1), gameStatistic.win)
                dialog.arguments = bundleOf("DATA" to context.getString(R.string.lose))
            }
            dialog.show(
                (context as FragmentActivity).supportFragmentManager,
                "CustomDialogFragment"
            )
            true
        } else false
    }



    companion object {

        private val manPlayers = listOf(
            "გიორგი",
            "ნიკოლოზი",
            "ირაკლი",
            "დავითი",
            "ილია",
            "ბექა",
            "თორნიკე",
            "ლუკა",
            "ანდრია",
            "ლაშა"
        )

        private val womanPlayers = listOf(
            "ანა",
            "მარიამი",
            "ეკატერინე",
            "სალომე",
            "თამარი",
            "ელენე",
            "ლიზი",
            "ნათია",
            "სესილი",
            "თათია"
        )

        fun getOpponentAvatar(): OpponentAvatarModel {

            val playerId = Random().nextInt(10) + 1

            val isMan = playerId % 2 != 0

            val name = if (isMan) {
                manPlayers[playerId - 1]
            } else {
                womanPlayers[playerId - 1]
            }

            val avatarImage = when (playerId) {
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
            return OpponentAvatarModel(name, avatarImage)
        }
    }


}