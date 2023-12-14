package com.febyputri.absensiapp.di

import com.febyputri.absensiapp.network.ApiClient
import com.febyputri.absensiapp.repository.AppRepository
import com.febyputri.absensiapp.repository.AppRepositoryImpl
import com.febyputri.absensiapp.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        okHttpClient: OkHttpClient,
    ): ApiClient {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(ApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        apiService: ApiClient,
    ): AppRepository {
        return AppRepositoryImpl(apiService)
    }
}