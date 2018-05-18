package com.iambedant.pizzaapp.di.module

import com.iambedant.pizzaapp.data.remote.PizzaApi
import com.iambedant.pizzaapp.data.remote.PizzaService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by @iamBedant on 16/05/18.
 */
@Module
class NetworkModule{
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(PizzaApi.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun providePizzaApi(retrofit: Retrofit): PizzaApi = retrofit.create(PizzaApi::class.java)

    @Provides
    @Singleton
    fun provideChuckNorrisService(pizzaApi: PizzaApi): PizzaService = PizzaService(pizzaApi)
}