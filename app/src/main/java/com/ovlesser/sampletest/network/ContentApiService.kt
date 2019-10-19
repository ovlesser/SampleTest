package com.ovlesser.sampletest.network

import com.ovlesser.sampletest.BuildConfig
import com.ovlesser.sampletest.model.Photo
import com.ovlesser.sampletest.model.User
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ContentApiService {

    @GET("users")
    fun users(): Single<List<User>>

    @GET("photos")
    fun photos(@Query("albumId") albumId: Int? = 0): Single<List<Photo>>

    companion object {
        private const val TAG = "ContentAPI"
        // using Jackson to map Json object to class instance,
        // because Jackson is the fastest json parser compared to other parsers like gson
        private fun makeJsonConverterFactory(): Converter.Factory {
            return JacksonConverterFactory.create(AppModuleProvider.objectMapper)
        }

        private fun makeNetworkClient(parameters: Map<String, Any>? = null): OkHttpClient {

            val clientBuilder = OkHttpClient().newBuilder()
            // disabled unused interceptors, the interceptor code is commented for debugging purpose
//            val requestInterceptor = parameters.let {
//                Interceptor { chain ->
//                    val builder = chain.request().url().newBuilder()
//                    it?.forEach {
//                        builder.addEncodedQueryParameter(it.key, it.value.toString())
//                    }
//                    val newUrl = builder.build()
//                    val newRequest = chain.request().newBuilder().url(newUrl).build()
//                    Log.d(TAG, newUrl.toString())
//                    return@Interceptor chain.proceed(newRequest)
//                }
//            }
//            clientBuilder.networkInterceptors().add(requestInterceptor)
//
//            // Inject response handler
//            val responseInterceptor = Interceptor { chain ->
//                val request = chain.request()
//                val response = chain.proceed(request)
//                return@Interceptor when (response.code()) {
//                    204 -> throw Exception("No Content")
//                    else -> response
//                }
//            }
//            clientBuilder.networkInterceptors().add(responseInterceptor)
            clientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })

            return clientBuilder.build()
        }

        fun create(parameters: Map<String, Any>? = null): ContentApiService {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory( makeJsonConverterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(AppModuleProvider.apiBaseUrl)

            retrofitBuilder.client(makeNetworkClient(parameters))

            return retrofitBuilder.build().create(ContentApiService::class.java)
        }
    }
}
