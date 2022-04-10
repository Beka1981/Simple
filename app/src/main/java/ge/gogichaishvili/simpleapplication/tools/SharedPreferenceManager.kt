package ge.gogichaishvili.simpleapplication.tools

import android.content.Context
import android.content.SharedPreferences
import ge.gogichaishvili.simpleapplication.R
import ge.gogichaishvili.simpleapplication.models.GameStatistics
import ge.gogichaishvili.simpleapplication.models.PlayerData

class SharedPreferenceManager(context: Context) {

    private val preference: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = preference.edit()

    fun savePlayerInfo(firstName: String?, avatar: Int, id: Int) {
        editor.apply {
            putString(FIRST_NAME_KEY, firstName)
            putInt(AVATAR_KEY, avatar)
            putInt(ID_KEY, id)
        }.apply()
    }

    fun getPlayerData(): PlayerData {
        val firstName = preference.getString(FIRST_NAME_KEY, "Player")
        val avatar = preference.getInt(AVATAR_KEY, R.drawable.avatar_1)
        val id = preference.getInt(ID_KEY, 1)
        return PlayerData(
            firstName = firstName,
            avatar = avatar,
            id = id
        )
    }

    fun saveGameStatistics(win: Int, lose: Int) {
        editor.apply {
            putInt(WIN_KEY, win)
            putInt(LOSE_KEY, lose)
        }.apply()
    }

    fun getGameStatistics(): GameStatistics {
        val win = preference.getInt(WIN_KEY, 0)
        val lose = preference.getInt(LOSE_KEY, 0)
        return GameStatistics(
            win = win,
            lose = lose
        )
    }

    fun clearAll() {
        editor.clear()
        editor.apply()
    }

    companion object {
        private const val PREFERENCE_NAME = "MySharedPreference"
        private const val FIRST_NAME_KEY = "Player"
        private const val AVATAR_KEY = "2131165270"
        private const val ID_KEY = "1"

        private const val WIN_KEY = "Win"
        private const val LOSE_KEY = "Lose"
    }
}