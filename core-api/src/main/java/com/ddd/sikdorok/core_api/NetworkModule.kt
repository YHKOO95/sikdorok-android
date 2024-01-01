package com.ddd.sikdorok.core_api

import android.content.Context
import com.ddd.sikdorok.core_api.annotation.FlipperInterceptor
import com.ddd.sikdorok.core_api.annotation.HeaderInterceptor
import com.ddd.sikdorok.core_api.annotation.LoggingInterceptor
import com.ddd.sikdorok.core_api.annotation.NoAuthOkHttpClient
import com.ddd.sikdorok.core_api.annotation.NoAuthRetrofit
import com.ddd.sikdorok.core_api.annotation.NormalOkHttpClient
import com.ddd.sikdorok.core_api.annotation.NormalRetrofit
import com.ddd.sikdorok.core_api.annotation.RefreshInterceptor
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    const val BASE_URL = "https://sikdorok.jeffrey-oh.click"

    @Provides
    @Singleton
    @NormalRetrofit
    fun providesNormalRetrofit(
        @NormalOkHttpClient client: OkHttpClient
    ) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    @NoAuthRetrofit
    fun providesNoAuthRetrofit(
        @NoAuthOkHttpClient client: OkHttpClient
    ) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    // OKHttpClients
    @Provides
    @Singleton
    @NormalOkHttpClient
    fun providesNormalOkHttpClient(
        @ApplicationContext context: Context,
        @LoggingInterceptor interceptor: Interceptor,
        @HeaderInterceptor accessTokenInterceptor: Interceptor,
        @FlipperInterceptor flipperInterceptor: Interceptor,
        @RefreshInterceptor refreshTokenInterceptor: Authenticator
    ) = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addNetworkInterceptor(flipperInterceptor)
        .addInterceptor(accessTokenInterceptor)
        .authenticator(refreshTokenInterceptor)
        .build()

    @Provides
    @Singleton
    @NoAuthOkHttpClient
    fun providesNoAuthOkHttpClient(
        @ApplicationContext context: Context,
        @FlipperInterceptor flipperInterceptor: Interceptor,
        @LoggingInterceptor interceptor: Interceptor
    ) = OkHttpClient.Builder()
        .addNetworkInterceptor(
            FlipperOkhttpInterceptor(
                AndroidFlipperClient.getInstance(context).getPlugin(NetworkFlipperPlugin.ID)
            )
        )
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    @LoggingInterceptor
    fun providesHttpLoggingInterceptor(
        @ApplicationContext context: Context
    ): Interceptor {
        val interceptor = HttpLoggingInterceptor {
            Timber.i(it)
        }

        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}

