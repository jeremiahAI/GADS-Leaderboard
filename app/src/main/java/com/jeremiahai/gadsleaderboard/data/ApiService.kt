package com.jeremiahai.gadsleaderboard.data

import com.google.gson.JsonObject
import com.jeremiahai.gadsleaderboard.data.model.GadsLearner
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface GadsApiService {

    @GET("/api/hours")
    fun getLearningLeaders(): Deferred<Response<List<GadsLearner>>>

    @GET("/api/skilliq")
    fun getSkillIqLeaders(): Deferred<Response<List<GadsLearner>>>

}

interface GoogleDocsApiService {
    @POST("/forms/d/e/1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
    @FormUrlEncoded
    fun uploadToGoogleDocs(
        @Field("entry.1824927963") emailAddress: String,
        @Field("entry.1877115667") firstName: String,
        @Field("entry.2006916086") lastName: String,
        @Field("entry.284483984") githubLink: String
    ): Deferred<Response<JsonObject>>
}

