package com.jeremiahai.gadsleaderboard.leaderBoard

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeremiahai.gadsleaderboard.data.NetworkRepository
import com.jeremiahai.gadsleaderboard.data.SingleEvent
import com.jeremiahai.gadsleaderboard.data.Status
import com.jeremiahai.gadsleaderboard.data.model.GadsLearner
import kotlinx.coroutines.launch

class LeaderBoardViewModel @ViewModelInject constructor(
    val repository: NetworkRepository
) : ViewModel() {

    val progressIndicatorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var errorMessage: MutableLiveData<SingleEvent<String>> = MutableLiveData()
    val skillIqLeadersLiveData = MediatorLiveData<List<GadsLearner>>()
    val learningLeadersLiveData = MediatorLiveData<List<GadsLearner>>()


    fun onInit() {
        getLearningLeaders()
        getSkillIqLeaders()
    }

    private fun getSkillIqLeaders() {
        progressIndicatorLiveData.value = true
        viewModelScope.launch {
            skillIqLeadersLiveData.addSource(repository.getSkillIqLeaders()) {
                progressIndicatorLiveData.value = false
                when (it.status) {
                    Status.SUCCESS -> {
                        skillIqLeadersLiveData.value = it.data
                    }
                    Status.ERROR -> {
                        errorMessage.value = SingleEvent("Error retrieving LeaderBoard Data")
                    }
                    else -> {
                    }
                }
            }
        }

    }

    private fun getLearningLeaders() {
        progressIndicatorLiveData.value = true
        viewModelScope.launch {
            learningLeadersLiveData.addSource(repository.getLearningLeaders()) {
                progressIndicatorLiveData.value = false
                when (it.status) {
                    Status.SUCCESS -> {
                        learningLeadersLiveData.value = it.data
                    }
                    Status.ERROR -> {
                        errorMessage.value = SingleEvent("Error retrieving LeaderBoard Data")
                    }
                    else -> {
                    }
                }
            }
        }

    }

}