package com.jeremiahai.gadsleaderboard.leaderBoard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeremiahai.gadsleaderboard.R
import com.jeremiahai.gadsleaderboard.data.model.GadsLearner
import com.jeremiahai.gadsleaderboard.utils.LeaderBoardAdapter
import com.jeremiahai.gadsleaderboard.utils.POSITION
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_leader_board.*

@AndroidEntryPoint
class LeaderBoardFragment : Fragment() {
    private var v: View? = null
    val viewModel: LeaderBoardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_leader_board, container, false)

        arguments?.getInt(POSITION)?.let { setupViewModel(it) }
        return v;
    }

    private fun setupViewModel(position: Int) {
        val observer = Observer<List<GadsLearner>> { learnersList ->
            leaderBoardRv.layoutManager = LinearLayoutManager(context)
            leaderBoardRv.adapter =
                LeaderBoardAdapter(
                    position,
                    learnersList
                )
        }

        viewModel.apply {
            if (position == 0) learningLeadersLiveData.observe(viewLifecycleOwner, observer)
            else skillIqLeadersLiveData.observe(viewLifecycleOwner, observer)
        }
    }

}