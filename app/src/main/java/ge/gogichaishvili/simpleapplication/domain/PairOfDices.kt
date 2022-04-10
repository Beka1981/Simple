package ge.gogichaishvili.simpleapplication.domain

import android.content.Context
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatImageView
import ge.gogichaishvili.simpleapplication.R
import ge.gogichaishvili.simpleapplication.tools.Tools

class PairOfDices(
    private val context: Context,
    private val viewForDiceOne: AppCompatImageView,
    private val viewForDiceTwo: AppCompatImageView,
) : Dice() {

    fun rollDices(): Int {
        val diceOne = rollDice()
        val diceTwo = rollDice()
        val anim = AnimationUtils.loadAnimation(context, R.anim.rotate)
        viewForDiceOne.startAnimation(anim)
        viewForDiceTwo.startAnimation(anim)
        viewForDiceOne.setImageResource(diceOne.diceImage)
        viewForDiceTwo.setImageResource(diceTwo.diceImage)
        Tools.playSound(context, R.raw.roll)
        return calculateCurrentScore(diceOne.diceValue, diceTwo.diceValue)
    }

    private fun calculateCurrentScore(dice1: Int, dice2: Int): Int {
        return if (dice1 == dice2) {
            (dice1 + dice2) * 2
        } else {
            dice1 + dice2
        }
    }

}