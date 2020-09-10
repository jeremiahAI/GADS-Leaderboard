package com.jeremiahai.gadsleaderboard.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jeremiahai.gadsleaderboard.data.model.GadsLearner
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class NetworkRepository constructor(
    val service: ApiService
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
        return makeCall(service.getLearningLeaders())
    }

    suspend fun getSkillIqLeaders(): LiveData<Resource<List<GadsLearner>>> {
        return makeCall(service.getSkillIqLeaders())
    }
}
