package com.example.devnews.di

import com.example.devnews.BuildConfig
import com.example.devnews.data.remote.api.CategoryApi
import com.example.devnews.data.remote.api.NewsApi
import com.example.devnews.data.repository.impl.CategoryRepoImpl
import com.example.devnews.data.repository.impl.NewsRepositoryImpl
import com.example.devnews.di.annotations.BaseUrl
import com.example.devnews.domain.repositories.CategoryRepository
import com.example.devnews.domain.repositories.NewsRepository
import com.example.devnews.domain.usecases.ToggleLikeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val baseUrl = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
            val request =
                chain.request().newBuilder().addHeader("Content-Type", "application/json").build()
            chain.proceed(request)
        }.build()
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
    }

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = baseUrl

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)

    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApi): NewsRepository = NewsRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideToggleNewsLike(repo: NewsRepository): ToggleLikeUseCase = ToggleLikeUseCase(repo)

    @Provides
    @Singleton
    fun provideCategoryApi(retrofit: Retrofit): CategoryApi =
        retrofit.create(CategoryApi::class.java)

    @Provides
    @Singleton
    fun provideCategoryRepository(api: CategoryApi): CategoryRepository = CategoryRepoImpl(api)
}