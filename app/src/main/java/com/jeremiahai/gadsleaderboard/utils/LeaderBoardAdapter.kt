package com.jeremiahai.gadsleaderboard.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jeremiahai.gadsleaderboard.R
import com.jeremiahai.gadsleaderboard.data.model.GadsLearner
import kotlinx.android.synthetic.main.leader_board_item.view.*

class LeaderBoardAdapter(val type: Int, private val leaderBoardList: List<GadsLearner>) :
    RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.leader_board_item, parent, false)
        )
    }

    override fun getItemCount(): Int = leaderBoardList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(leaderBoardList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(learner: GadsLearner) {
            learner.apply {
                Glide.with(itemView)
                    .load(badgeUrl)
                    .error(if (type == 0) R.drawable.top_learner else R.drawable.skill_iq)
                    .placeholder(if (type == 0) R.drawable.top_learner else R.drawable.skill_iq)
                    .into(itemView.badgeImageView)

                itemView.nameTv.text = name
                itemView.detailsTv.text =
                    "${if (type == 0) "$hours learning hours" else "$score skill IQ score"}, $country."

            }
        }
    }

}
