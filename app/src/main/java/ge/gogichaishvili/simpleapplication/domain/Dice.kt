package ge.gogichaishvili.simpleapplication.domain

import ge.gogichaishvili.simpleapplication.R
import ge.gogichaishvili.simpleapplication.models.DiceModel
import java.util.*

open class Dice() {
    fun rollDice(): DiceModel {
        val diceValue = Random().nextInt(6) + 1
        val diceImage = when (diceValue) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        return DiceModel(diceValue, diceImage)
    }
}