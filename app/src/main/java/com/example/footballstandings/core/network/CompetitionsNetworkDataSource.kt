package com.example.footballstandings.core.network

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.example.footballstandings.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import javax.inject.Inject
import javax.inject.Singleton

private const val BASE_URL = "https://v3.football.api-sports.io/"

interface FsService {
    @GET("leagues")
    suspend fun getCompetitions(): CompetitionsResponse
}

@Singleton
class CompetitionsNetworkDataSource  {
    val service: FsService by lazy {
        val apiKey = BuildConfig.API_KEY
        val httpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("x-apisports-key", apiKey)
                    .build()
                return@addInterceptor chain.proceed(request)
            }
            .build()
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return@lazy Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient)
            .build()
            .create(FsService::class.java)
    }
}
