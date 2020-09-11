package com.jeremiahai.gadsleaderboard.submission

import android.os.Handler
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jeremiahai.gadsleaderboard.data.NetworkRepository
import com.jeremiahai.gadsleaderboard.data.SingleEvent
import kotlin.random.Random

class SubmitViewModel @ViewModelInject constructor(
    val repository: NetworkRepository
) : ViewModel() {
    fun submit() {
        progressIndicatorLiveData.value = true
        Handler().postDelayed({

            progressIndicatorLiveData.value = false
            submitStatusLiveData.value = SingleEvent(Random.nextBoolean())
        }, 300)
    }

    val progressIndicatorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var submitStatusLiveData: MutableLiveData<SingleEvent<Boolean>> = MutableLiveData()
    private val submitLiveData = MediatorLiveData<String>()
}