package fr.pitdev.dayoff.data.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.pitdev.dayoff.common.base.utils.BASE_URL
import fr.pitdev.dayoff.data.BuildConfig
import fr.pitdev.dayoff.data.remote.api.DayOffApiService
import fr.pitdev.dayoff.data.remote.interceptors.CacheInterceptor
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {


    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptorLevel =
        DefaultHttpLoggingInterceptorLevel()


    @Provides
    fun providesOkHttpClient(
        @ApplicationContext context: Context,
        loggingInterceptor: HttpLoggingInterceptorLevel,
        @InterceptorCache cacheInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().run {
            addInterceptor(cacheInterceptor)
            when {
                BuildConfig.DEBUG -> {
                    addInterceptor(loggingInterceptor.body())
                }
                BuildConfig.BUILD_TYPE == "release" -> {
                    addInterceptor(loggingInterceptor.none())
                }
                else -> addInterceptor(loggingInterceptor.basic())
            }
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS) // Way too much?
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)

            // enable OkHttp cache for now
            cache(
                Cache(
                    directory = File(context.cacheDir, "http_cache"),
                    maxSize = 50L * 1024L * 1024L
                )
            )
            build()
        }
    }

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @ConverterFactoryGson
    fun provideGsonFactory(gson: Gson): Converter.Factory = GsonConverterFactory.create(gson)

    @Provides
    @ConverterFactoryMoshi
    fun provideMoshiFactory(moshi: Moshi): Converter.Factory = MoshiConverterFactory.create(moshi)

    @Provides
    fun provideRetrofit(
        @ConverterFactoryMoshi convertFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(convertFactory)
            .build()
    }

    @InterceptorCache
    @Provides
    fun provideInterceptor(): Interceptor = CacheInterceptor()

    @Provides
    @Singleton
    fun providesDayOffApiService(retrofit: Retrofit): DayOffApiService =
        retrofit.create(DayOffApiService::class.java)


    private const val CONNECT_TIMEOUT = 30L
    private const val READ_TIMEOUT = 30L
}

interface HttpLoggingInterceptorLevel {
    fun basic(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    fun headers(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)

    fun body(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    fun none() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
}

class DefaultHttpLoggingInterceptorLevel : HttpLoggingInterceptorLevel

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ConverterFactoryMoshi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ConverterFactoryGson

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InterceptorCache