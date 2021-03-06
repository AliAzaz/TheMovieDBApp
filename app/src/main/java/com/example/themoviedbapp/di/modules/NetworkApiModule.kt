package com.example.themoviedbapp.di.modules

import com.example.themoviedbapp.di.auth.AuthApi
import com.example.themoviedbapp.di.auth.remote.ApiResponseCallAdapterFactory
import com.example.themoviedbapp.utils.CONSTANTS
import com.example.themoviedbapp.utils.CONSTANTS.BASE_URL
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * @author AliAzazAlam on 5/16/2021.
 */
@Module
class NetworkApiModule {

    @Singleton
    @Provides
    fun buildBackendApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun buildRetrofitClient(
        okHttpClient: OkHttpClient,
        coroutineCallAdapterFactory: CoroutineCallAdapterFactory,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(coroutineCallAdapterFactory)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun buildOkHttpClient(chainKeyInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient().newBuilder().also { item ->
            val log = HttpLoggingInterceptor()
            log.level = HttpLoggingInterceptor.Level.BODY
            item.addInterceptor(log)
            item.retryOnConnectionFailure(true)
        }
            .addNetworkInterceptor(FlipperOkhttpInterceptor(NetworkFlipperPlugin()))
            .addNetworkInterceptor(chainKeyInterceptor)
            .build()
    }


    @Provides
    @Singleton
    fun getGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun getCoroutineCallAdapter(): CoroutineCallAdapterFactory {
        return CoroutineCallAdapterFactory.invoke()
    }

    @Provides
    @Singleton
    fun getChainKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            chain.request().let {
                val urlBuilder = it.url.newBuilder()
                    .addQueryParameter("api_key", CONSTANTS.API_KEY)
                    .build()
                chain.proceed(it.newBuilder().url(urlBuilder).build())
            }
        }
    }

}