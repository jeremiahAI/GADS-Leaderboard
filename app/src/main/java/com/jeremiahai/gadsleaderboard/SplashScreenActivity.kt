package com.jeremiahai.gadsleaderboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.jeremiahai.gadsleaderboard.leaderBoard.LeaderBoardActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        Handler().postDelayed({ navigateToMain() }, 3000)
    }

    private fun navigateToMain() {
        val intent = Intent(this, LeaderBoardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivityWithFadeAnimation(intent)
    }

}

fun Activity.startActivityWithFadeAnimation(intent: Intent) {
    startActivity(intent)
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
}