package ge.gogichaishvili.simpleapplication.tools

import android.app.Activity
import android.app.Application
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment

object Tools {

    fun playSound(context: Context, audio: Int) {

        val mediaPlayer = MediaPlayer.create(context, audio)

        mediaPlayer.apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )

            mediaPlayer.isLooping = false

            mediaPlayer.setVolume(5f, 5f)

            mediaPlayer.setOnCompletionListener { player ->
                player.stop()
                player.release()
            }

            mediaPlayer.start()

        }

    }



    fun alphaInAnimation(view: View) {
        view.alpha = 0.5f
        view.visibility = View.VISIBLE
        view.animate()
            .alpha(1f)
            .translationY(view.height.toFloat())
            .rotation(360f)
            .scaleX(0.5f).scaleY(0.5f)
            .setDuration(400)
            .setListener(null)
    }

}