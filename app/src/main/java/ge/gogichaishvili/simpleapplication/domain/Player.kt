package ge.gogichaishvili.simpleapplication.domain

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView

class Player(
    private val context: Context,
    val name: String = "Player",
    val avatar: Int = 1,
    var win: Int = 0,
    var lose: Int = 0,
    private val viewForDice1: AppCompatImageView,
    private val viewForDice2: AppCompatImageView
) {
    fun rollPairOfDices(): Int {
        val pairOfDices = PairOfDices(context, viewForDice1, viewForDice2)
        return pairOfDices.rollDices()
    }
}