package com.jeremiahai.gadsleaderboard.utils

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.jeremiahai.gadsleaderboard.R
import com.jeremiahai.gadsleaderboard.leaderBoard.LeaderBoardFragment

val POSITION: String? = "position"

class TabAdapter(val parent: ViewGroup, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return LeaderBoardFragment().apply {
            arguments = Bundle().apply {
                putInt(POSITION, position)
            }
        }
    }

    override fun getCount(): Int = 2


    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> parent.context.getString(R.string.learning_leaders)
            1 -> parent.context.getString(R.string.skill_leaders)
            else -> ""
        }
    }
}