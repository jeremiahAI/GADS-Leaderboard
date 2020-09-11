package com.jeremiahai.gadsleaderboard.di

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jeremiahai.gadsleaderboard.BuildConfig
import com.jeremiahai.gadsleaderboard.data.GadsApiService
import com.jeremiahai.gadsleaderboard.data.GoogleDocsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RemoteModule {
    @Singleton
    @Provides
    fun provideGoogleDocsApiService(@GdocsRetrofit retrofit: Retrofit): GoogleDocsApiService {
        return retrofit.create(GoogleDocsApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideApiService(@GadsRetrofit retrofit: Retrofit): GadsApiService {
        return retrofit.create(GadsApiService::class.java)
    }

    private val GADS_BASE_URL = "https://gadsapi.herokuapp.com"
    private val GOOGLE_DOCS_BASE_URL = "https://docs.google.com"

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .apply {
                if (!BuildConfig.BUILD_TYPE.contains("release")) {
                    val logging = HttpLoggingInterceptor()
                    logging.level = HttpLoggingInterceptor.Level.BODY
                    addNetworkInterceptor(logging)
//                        .addInterceptor(OkHttpProfilerInterceptor())
                }
            }
            .build()
    }

    @Singleton
    @Provides
    @GdocsRetrofit
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GOOGLE_DOCS_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    @GadsRetrofit
    fun provideGadsRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GADS_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GdocsRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GadsRetrofit