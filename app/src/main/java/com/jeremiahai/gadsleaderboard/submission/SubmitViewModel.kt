package com.jeremiahai.gadsleaderboard.submission

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeremiahai.gadsleaderboard.data.NetworkRepository
import com.jeremiahai.gadsleaderboard.data.SingleEvent
import com.jeremiahai.gadsleaderboard.data.Status
import kotlinx.coroutines.launch


class SubmitViewModel @ViewModelInject constructor(
    private val repository: NetworkRepository
) : ViewModel() {

    val progressIndicatorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var submitStatusLiveData: MediatorLiveData<SingleEvent<Boolean>> = MediatorLiveData()

    fun submit(
        email: String,
        firstName: String,
        lastName: String,
        githubLink: String
    ) {
        progressIndicatorLiveData.value = true
        viewModelScope.launch {
            submitStatusLiveData.addSource(
                repository.submitDetails(
                    email, firstName, lastName, githubLink
                )
            ) {
                progressIndicatorLiveData.value = false
                submitStatusLiveData.value = SingleEvent(it.status == Status.SUCCESS)
            }
        }
    }
}