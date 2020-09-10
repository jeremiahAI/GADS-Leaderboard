package com.jeremiahai.gadsleaderboard.data

import com.jeremiahai.gadsleaderboard.data.model.GadsLearner
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {

    @GET("/api/hours")
    fun getLearningLeaders(): Deferred<Response<List<GadsLearner>>>

    @GET("/api/skilliq")
    fun getSkillIqLeaders(): Deferred<Response<List<GadsLearner>>>

}

