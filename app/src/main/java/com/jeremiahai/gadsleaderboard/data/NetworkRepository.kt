package com.jeremiahai.gadsleaderboard.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.jeremiahai.gadsleaderboard.data.model.GadsLearner
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    val gadsService: GadsApiService,
    val googleService: GoogleDocsApiService
) {

    private suspend fun <ResponseType>
            makeCall(deferredResponse: Deferred<Response<ResponseType>>): LiveData<Resource<ResponseType>> {

        val result = MutableLiveData<Resource<ResponseType>>()

        try {
            val response = deferredResponse.await()

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    result.postValue(Resource(Status.SUCCESS, response.body(), response.message()))
                }
            } else {
                withContext(Dispatchers.Main) {
                    result.postValue(
                        Resource(
                            Status.ERROR,
                            response.body(),
                            response.errorBody()?.string(),
                            response.code()
                        )
                    )
                }
            }
        } catch (ignore: Exception) {
            result.postValue(
                Resource(Status.ERROR, null, ignore.message)
            )
        }

        return result

    }


    suspend fun getLearningLeaders(): LiveData<Resource<List<GadsLearner>>> {
        return makeCall(gadsService.getLearningLeaders())
    }

    suspend fun getSkillIqLeaders(): LiveData<Resource<List<GadsLearner>>> {
        return makeCall(gadsService.getSkillIqLeaders())
    }

    suspend fun submitDetails(
        email: String,
        firstName: String,
        lastName: String,
        githubLink: String
    ): LiveData<Resource<JsonObject>> {
        return makeCall(googleService.uploadToGoogleDocs(email, firstName, lastName, githubLink))
    }
}
